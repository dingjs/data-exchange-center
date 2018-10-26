package data.exchange.center.service.sefon.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class CommUtils {

	/** 标准代码S_BZDM */
	public static Map<String, String> bzdmMap = new HashMap<String, String>();

	public static String trim(String str) {
		return str == null ? "" : str.trim();
	}

	public static String trim(Object obj) {
		if (obj == null) {
			return "";
		}
		String str = obj.toString();
		return str.trim();
	}

	public static Date StrToDate(String s, String format) {
		if (s == null) {
			return null;
		}
		SimpleDateFormat sim = new SimpleDateFormat(format);
		try {
			Date d = sim.parse(s);
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("源数据时间格式错误：" + s);
			e.printStackTrace();
		}
		return null;
	}

	public static String Tomorrow(String d, String f) {
		Date t = Tomorrow(StrToDate(d, f));
		return formatDate(t, f);
	}

	public static Date Tomorrow(Date d) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	public static Date Yesterday(Date d) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	public static String numToStr(int val, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append("0");
		}
		sb.append(val);
		return sb.substring(sb.length() - len);
	}

	/**
	 * Base64编码字符串
	 * 
	 * @param str
	 * @return
	 * @author 莫德锐
	 * @date 2013-2-21
	 */
	public static String encodeBase64(String str) {
		try {
			return new String(Base64.encodeBase64(trim(str).getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Base64解码字符串
	 * 
	 * @param str
	 * @return
	 * @author 莫德锐
	 * @date 2013-2-21
	 */
	public static String decodeBase64(String str) {
		if (str == null)
			return "";
		try {
			return new String(Base64.decodeBase64(trim(str).getBytes()), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Base64解码字符串
	 * 
	 * @param str
	 * @return
	 * @author 莫德锐
	 * @date 2013-2-21
	 */
	public static String decodeBase64(String str, String charset) {
		try {
			return new String(Base64.decodeBase64(trim(str).getBytes()), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 下载xml文件到临时文件夹 （此方法只能用户HTTP协议。获取文件成功后可根据"File file = new
	 * File(filename);"获得该文件file）
	 * 
	 * @param url
	 *            http路径URL
	 * @param filename
	 *            临时存放文件名（含临时文件夹路径）
	 * @return 返回值：true获取文件成功. | false获取文件失败.
	 * @author 莫德锐
	 * @date 2013-2-20
	 */
	public static boolean downloadFileFromUrl(String url, String filename) {
		// 此方法只能用HTTP协议

		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			URL Url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
			dis = new DataInputStream(connection.getInputStream());
			dos = new DataOutputStream(new FileOutputStream(filename));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = dis.read(buffer)) > 0) {
				dos.write(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (dos != null)
					dos.close();
			} catch (Exception e) {
			} finally {
				try {
					if (dis != null)
						dis.close();
				} catch (Exception e) {
				}
			}
		}
		return true;
	}

	public static DataInputStream downloadFileFromUrl(String url) {
		// 此方法只能用HTTP协议
		DataInputStream dis = null;
		try {
			URL Url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
			dis = new DataInputStream(connection.getInputStream());
			return dis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

		}
	}

	/**
	 * 字符串转16进制
	 * 
	 * @param ss
	 *            字符串
	 * @return
	 * @date 2013-2-21
	 */
	public static String stringToHex(String ss) {
		StringBuffer buffer = new StringBuffer();
		int len = ss.length();
		for (int i = 0; i < len; ++i) {
			buffer.append(charToHex(ss.charAt(i)));
		}
		return buffer.toString();
	}

	/**
	 * 字符转16进制
	 * 
	 * @param c
	 *            字符
	 * @return
	 * @date 2013-2-21
	 */
	public static String charToHex(char c) {
		String s = Integer.toHexString(c & 0xFF);
		if (s.length() == 1) {
			return "0" + s;
		} else {
			return s;
		}
	}

	/**
	 * 转小序
	 * 
	 * @param a
	 * @return
	 * @date 2013-2-21
	 */
	public static int ntohl(int a) {
		return ((a & 0xFF00) >> 8) | ((a & 0x00FF) << 8);
	}

	/**
	 * 转大序
	 * 
	 * @param a
	 * @return
	 * @date 2013-2-21
	 */
	public static int ntolh(int a) {
		return ((a & 0x00FF) << 8) | ((a & 0xFF00) >> 8);
	}

	/**
	 * 数字转换成字符串.
	 * 
	 * @param ob
	 *            int或者long型数值
	 * @param cnt
	 *            转换成字符串的位数,不足位前面补0.
	 * @return .
	 */
	public static String numToStrP(Object ob, int cnt) {
		StringBuilder formatStr = new StringBuilder();
		for (int i = 0; i < cnt; i++) {
			formatStr.append("0");
		}
		String tt = formatStr.toString() + String.valueOf(ob);
		int len = tt.length();
		return tt.substring(len - cnt, len);
	}

	/**
	 * 获取服务器时间（“yyyy-MM-dd HH:mm:ss,SSS”格式的字符串）
	 * 
	 * @return now time yyyy-MM-dd HH:mm:ss,SSS
	 */
	public static String getServerTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		String servTime = sdf.format(new Date());
		sdf = null;
		return servTime;
	}

	/**
	 * 是否为NULL对象.
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}

	public static boolean isEmptyStr(String str) {
		if (isNull(str))
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String formatDate(Date date, String formatter) {
		if (isNull(date))
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}

	/**
	 * 默认中文日期格式化. "yyyy年MM月dd日"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy年MM月dd日");
	}

	/***
	 * 全角空格为12288，半角空格为32 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 * 将字符串中的全角字符转为半角
	 * 
	 * @param src
	 *            要转换的包含全角的任意字符串
	 * @return 转换之后的字符串
	 */
	public static String toSemiangle(String src) {
		char[] c = src.toCharArray();
		for (int index = 0; index < c.length; index++) {
			if (c[index] == 12288) {// 全角空格
				c[index] = (char) 32;
			} else if (c[index] > 65280 && c[index] < 65375) {// 其他全角字符
				c[index] = (char) (c[index] - 65248);
			}
		}
		return String.valueOf(c);
	}

	/**
	 * 除去字符串中非以下情形的字符（慎用：含全角字符的请转半角后再使用此方法）： <BR>
	 * 1、中文、英文、数字 <BR>
	 * 2、英文符号+*-/,.;:'"()_[] <BR>
	 * 3、中文符号【】、，。·——
	 * 
	 * @param src
	 * @return
	 */
	public static String removeAllCharNoDefine(String src) {
		if (src != null) {
			src = src.replaceAll("(?i)[^\\w(\"'-_)【、。·——】\u4E00-\u9FA5\uF500-\uFA2D]", "");
		}
		return src;
	}

	public static String decodeLowHex(String base64Str) {
		if (isEmptyStr(base64Str))
			return "";
		byte[] data = null;
		try {
			data = Base64.decodeBase64(trim(base64Str).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuilder hex = new StringBuilder();
		for (byte d : data) {
			hex.append(charToHex((char) d));
		}
		return hex.toString();
	}

	/**
	 * 将复选对象，解码成数组
	 * 
	 * @param base64Str
	 * @return
	 */
	public static List<Integer> decodeLowHexInt(String base64Str) {
		List<Integer> arr = new ArrayList<Integer>();
		if (isEmptyStr(base64Str))
			return arr;
		byte[] data = null;
		try {
			data = Base64.decodeBase64(trim(base64Str).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuilder hex = new StringBuilder();
		int i = 0;
		for (byte d : data) {
			hex.append(charToHex((char) d));
			i++;

			if (i % 2 == 0) {
				arr.add(ntohl(Integer.parseInt(hex.toString(), 16)));
				hex.delete(0, hex.length());
			}
		}
		return arr;
	}

	/**
	 * 将代码复选的选编码成base64,复选对象
	 * 
	 * @param arr
	 * @return
	 */
	public static String encodeLowHexInt(List<Integer> arr) {
		if (arr == null || arr.size() == 0)
			return "";
		byte[] data = new byte[arr.size() * 2];
		int k = 0;
		for (Integer i : arr) {
			String hex = "0000" + Integer.toHexString(ntohl(i));

			hex = hex.substring(hex.length() - 4);
			// System.out.println(hex+hex.substring(0,2)+hex.substring(2));
			data[k + 0] = (byte) Integer.parseInt(hex.substring(0, 2), 16);
			data[k + 1] = (byte) Integer.parseInt(hex.substring(2), 16);
			k = k + 2;
		}
		try {
			return new String(Base64.encodeBase64(data), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将2进制的编码的代码，翻译成对应的序号值
	 * 
	 * @param value
	 * @return
	 */
	public static List<Integer> decodeBinInt(long value) {
		List<Integer> list = new ArrayList<Integer>();
		String bstr = Long.toBinaryString(value);
		int len = bstr.length();
		for (int i = 0; i < len; i++) {
			if ("1".equals(bstr.substring(i, i + 1))) {
				list.add(len - i);
			}
		}
		return list;
	}

	public static long encodeBinInt(List<Integer> arr) {
		if (arr == null || arr.size() == 0)
			return 0;
		Map<String, Integer> mp = new HashMap<String, Integer>();
		for (Integer i : arr) {
			mp.put(String.valueOf(i), i);
		}
		StringBuilder sb = new StringBuilder();
		// 最大32位
		for (int i = 32; i > 0; i--) {
			if (mp.get(String.valueOf(i)) == null) {
				sb.append("0");
			} else {
				sb.append("1");
			}
		}
		return Long.parseLong(sb.toString(), 2);
	}

	/**
	 * 将指定的时间格式的字符串转换为另外一种格式的时间串
	 * 
	 * @param text
	 * @param stype
	 * @param dtype
	 * @return
	 */
	public static String formatDateTime(String text, String stype, String dtype) {
		if (isEmptyStr(text))
			return "";

		if (stype.indexOf("-") > -1 && text.indexOf("-") == -1)
			return text;
		if (stype.indexOf("-") == -1 && text.indexOf("-") > -1)
			return text;

		SimpleDateFormat sdf = new SimpleDateFormat(stype);
		try {
			return formatDate(sdf.parse(text), dtype);
		} catch (ParseException e) {
			e.printStackTrace();
			return text;
		}
	}

	/**
	 * 是否为15法标案件类型代码
	 * 
	 * @param
	 * @return
	 */
	public static boolean isAjlxdm(String ajlxdm) {
		int dm = 0;
		try {
			dm = Integer.parseInt(ajlxdm);
		} catch (Exception e) {
			return false;
		}
		if (dm > 0 && dm <= 197) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		List<Integer> list = decodeLowHexInt("CQA=");
		for (Integer i : list) {
			System.out.println(i);
		}
	}

	// 获取压缩包序号
	public static String getZipXh(Object ob, int cnt) {
		StringBuilder formatStr = new StringBuilder();
		for (int i = 0; i < cnt; i++) {
			formatStr.append("0");
		}
		String tt = formatStr.toString() + String.valueOf(ob);
		int len = tt.length();
		return tt.substring(len - cnt, len);
	}

	/**
	 * 流文件写入文件，由于is后续要用，所以此处不关闭
	 * 
	 * @param path
	 * @param xmlName
	 * @param is
	 */
	public static void writeToFile(String path, String xmlName, InputStream is) {
		FileOutputStream fos = null;
		try {
			String filePath = path;
			File file = new File(filePath + "//" + xmlName);
			if (file.exists()) {
				file.delete();
			}
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			byte[] buf = new byte[1024];
			fos = new FileOutputStream(file);
			int len = is.read(buf);
			while (len != -1) {
				fos.write(buf, 0, len);
				fos.flush();
				len = is.read(buf);
			}
			fos.flush();
			file = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
