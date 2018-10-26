package data.exchange.center.service.pcaj.schedule;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.pcaj.service.PcajService;

/**
 * 
 * Description:定时下载
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2017年12月25日 下午4:06:48
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class Scheduler implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	private PcajService pcajService;
	
	private String ROOT  = "d://pcaj";
	private String ERROR = "d://pcaj//error//";
	private String OK    = "d://pcaj//ok//";
	private String OUT   = "d://pcaj//out//";

	@SuppressWarnings("unchecked")
	// @Scheduled(cron="0 0/1 * * * ?")
	@Override
	// public void run(String... args) throws Exception {
	// // TODO Auto-generated method stub
	//
	// }
	public void run(String... args) throws ParseException, ClientProtocolException, IOException {
		long start = System.currentTimeMillis();
		File file = new File(ROOT);
		if(!file.exists()) {
			file.mkdirs();
		}
		File fileDirErr = new File(ERROR);
		if(!fileDirErr.exists()) {
			fileDirErr.mkdirs();
		}
		File fileDirOk = new File(OK);
		if(!fileDirOk.exists()) {
			fileDirOk.mkdirs();
		}
		File fileDirOut = new File(OUT);
		if(!fileDirOut.exists()) {
			fileDirOut.mkdirs();
		}
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

		StringBuilder requestUrl = new StringBuilder();
		requestUrl.append("http://192.1.36.74:8080/drsp/services/resource/api/ac24e525a86f17ac3924957c8a688fbb.gxml"
				+ "?systemMark=true" + "&ticket=b8a682e71a45a15f305e4a6a8fa3311c");
		// requestUrl.append("&n_ajlx=25");
		requestUrl.append("&d_rksj_start=");
		requestUrl.append(URLEncoder.encode("2010-01-01 00:00:00", "UTF-8"));
		requestUrl.append("&d_rksj_end=");
		requestUrl.append(URLEncoder.encode(getYesterdayDate() + " 23:59:00", "UTF-8"));

		InputStream input = getRequest(requestUrl.toString());
		if (input == null) {
			return;
		}
		String resultXml = IOUtils.toString(input, "UTF-8");

		Map<String, Object> map = parseXmlContent(resultXml);
		List<String> pathList = (List<String>) map.get("path");
		for (String path : pathList) {
			String pathUrl = "http://192.1.36.74:8080/drsp/services/resource/api/ac24e525a86f17ac3924957c8a688fbb.stream?systemMark=true&ticket=301e56eb156a86cd663c2ef8fee93b06";
			pathUrl = pathUrl + "&path=" + path;
			String localPath = "d://pcaj//" + path.substring(path.lastIndexOf("/"), path.length());
			InputStream zipInput = null;
			FileOutputStream fileout = null;
			try {
				zipInput = getRequest(pathUrl);
				fileout = new FileOutputStream(new File(localPath));
				byte[] buff = new byte[100];
				int rc = 0;
				while ((rc = zipInput.read(buff, 0, 100)) > 0) {
					fileout.write(buff, 0, rc);
				}
				fileout.flush();
			} catch (Exception e) {

			} finally {
				if (zipInput != null) {
					zipInput.close();
				}
				if (fileout != null) {
					fileout.close();
				}
			}

			// 解析zip包
			ZipFile zf = null;
			InputStream in = null;
			ZipInputStream zin = null;
			ZipEntry ze = null;
			try {
				zf = new ZipFile(localPath);
				in = new BufferedInputStream(new FileInputStream(localPath));
				zin = new ZipInputStream(in);
				int i = 0;
				while ((ze = zin.getNextEntry()) != null) {

					i = i + 1;
					if (ze.isDirectory()) {
					} else {
						BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
						String line;
						StringBuffer xml = new StringBuffer();
						while ((line = br.readLine()) != null) {
							xml.append(line);
						}
						br.close();
						String fydm = ze.getName().substring(ze.getName().indexOf("_") + 1,
								ze.getName().indexOf("_") + 5);
						String ajlx = ze.getName().substring(0, ze.getName().indexOf("_"));
						// 放入xml内容解析
						final ZipEntry ze1 = ze;
						fixedThreadPool.execute(new Runnable() {
							public void run() {
								try {
									TimeUnit.MILLISECONDS.sleep(50);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								if(!ze1.getName().contains("JG")) {
									try {
										Map<String, Object> returnMap = pcajService.parseXml(xml.toString(), fydm, ajlx);
										System.err.println(returnMap);
										if (returnMap.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_FAIL)) {
											FileOutputStream f = new FileOutputStream(new File(ERROR + ze1.getName()));
											f.write((returnMap.get(CodeUtil.RETURN_MSG).toString() + "\n").getBytes());
											f.write(xml.toString().getBytes());
											f.flush();
											f.close();
										} else {
											FileOutputStream f = new FileOutputStream(new File(OK + ze1.getName()));
											f.write(xml.toString().getBytes());
											f.flush();
											f.close();
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}else {
									try {
										FileOutputStream f = new FileOutputStream(new File(OUT + ze1.getName()));
										f.write(xml.toString().getBytes());
										f.flush();
										f.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						});
					}
					System.err.println(i);
				}

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {
				if (zin != null) {
					zin.closeEntry();
				}
				if (zin != null) {
					zin.close();
				}
				if (zf != null) {
					zf.close();
				}
			}
		}
		long end = System.currentTimeMillis();
		System.err.println("耗时："+(end-start)/1000+"s");
	}

	public String getYesterdayDate() throws ParseException {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		Date endDate = dft.parse(dft.format(date.getTime()));
		return new SimpleDateFormat("yyyy-MM-dd").format(endDate);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> parseXmlContent(String xml) {
		Map<String, Object> returnMap = new HashMap<>();
		List<Object> pathList = new ArrayList<>();
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		List<Element> childElements = rootElement.elements();
		for (Element element : childElements) {
			if (element.getName().equalsIgnoreCase("BRIEF")) {// 数量
				List<Element> numElement = element.elements();
				returnMap.put("num", Integer.valueOf(numElement.get(0).getTextTrim()));
			} else {
				List<Element> numElement = element.elements();
				for (Element zipElement : numElement) {
					pathList.add(zipElement.getTextTrim());
				}
			}
		}
		returnMap.put("path", pathList);
		return returnMap;
	}

	public static InputStream getRequest(String url) {
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("请求破产案件下行接口出错:" + response.toString());
				return null;
			}
			InputStream input = entity.getContent();
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
