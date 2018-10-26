package data.exchange.center.service.sfgk.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.dom4j.Element;
import org.springframework.util.StringUtils;

public class ParseUtil {

	/**
	 * 
	 * @function 值为空的不要
	 * @author Tony
	 * @creaetime 2018年6月25日 上午9:48:01
	 * @param node
	 * @param text
	 * @param value
	 */
	public static void setNodeText(Element node, String text, String value) {
		if(!StringUtils.isEmpty(value)) {
			node.addElement(text).addText(value);
		}
	}
	
	/**
	 * 
	 * @function 日期或者日期时间转换成yyyy-MM-dd'T'HH:mm:ss.SSS格式
	 * @author Tony
	 * @creaetime 2018年6月25日 上午10:17:58
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateTime(String dateTime) {
		if(StringUtils.isEmpty(dateTime))
			return "";
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dateTime));
		} catch (Exception e) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(dateTime))+"T00:00:00.000";
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @function 日期或者日期时间转换成yyyy-MM-dd格式
	 * @author Tony
	 * @creaetime 2018年6月25日 上午10:19:35
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String parseDate(String date) throws ParseException {
		if(StringUtils.isEmpty(date))
			return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(date.replaceAll("_", "-")));
	}
	
	/**
	 * 
	 * @function 保存xml到指定目录
	 * @author Tony
	 * @creaetime 2018年6月22日 上午10:58:51
	 * @param fileName
	 * @param outStream
	 */
	public static synchronized void saveXml(String fileName, ByteArrayOutputStream outStream) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(outStream.toByteArray());
			fileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
