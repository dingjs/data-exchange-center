package service.sjsb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.thunisoft.exchangeplatform.epclient.client.EPClient;
import com.thunisoft.exchangeplatform.epclient.client.clientcallers.HttpSendCaller;
import com.thunisoft.exchangeplatform.protocol.EPProtocol;
import com.thunisoft.exchangeplatform.service.call.ServiceAddress;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.time.TimeUtils;
import service.sjsb.domain.Ajxx;
import service.sjsb.domain.AjxxInfo;
import service.sjsb.domain.DaMlxx;
import service.sjsb.domain.JzMlxx;
import service.sjsb.domain.Stwj;
import service.sjsb.service.SjsbService;
import service.sjsb.service.TaskService;
import service.sjsb.util.FileZipCompressUtil;
import service.sjsb.util.Xmlvalidate;

@Service
public class TaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	private static String fileFormate = "pdf|PDF|doc|DOC|docx|DOCX|tif|TIF|tiff|TIFF|wps|WPS|jpg|JPG|jpeg|JPEG|bmp|BMP|png|PNG|mp3|MP3|wma|WMA|ra|RA|rm|RM|rmvb|RMVB|txt|TXT|htm|HTM|html|HTML";

	private static String xsdName = "DA.xsd";
	@Autowired
	private SjsbService sjsbService;

	@Override
	@SuppressWarnings("unchecked")
	public void startTask() {
		ExecutorService executorService = Executors.newFixedThreadPool(40);

		String root = "D://SJSB//";
		String daDir = "DA//";
		String jzDir = "JZ//";
		if (!new File(root + daDir).exists())
			new File(root + daDir).mkdirs();
		if (!new File(root + jzDir).exists())
			new File(root + jzDir).mkdirs();

		List<AjxxInfo> ajList = sjsbService.getAjbs();
		logger.info("本次任务总案件数：" + ajList.size());
		for (AjxxInfo ajxx : ajList) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					Map<String, Object> data = sjsbService.getAjData(ajxx.getAjbs(), ajxx.getAjlx(), ajxx.getFydm());
					if (data.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {

						Map<String, Object> d = (Map<String, Object>) data.get(CodeUtil.RETURN_MSG);
						int flag = (int) d.get("v_cursor1");
						Ajxx daAjxx = ((List<Ajxx>) d.get("v_cursor2")).get(0);
						Ajxx jzAjxx = ((List<Ajxx>) d.get("v_cursor3")).get(0);
						List<Stwj> daStwj = (List<Stwj>) d.get("v_cursor4");
						List<Stwj> jzStwj = (List<Stwj>) d.get("v_cursor5");
						List<DaMlxx> daMlxx = (List<DaMlxx>) d.get("v_cursor6");
						List<JzMlxx> jzMlxx = (List<JzMlxx>) d.get("v_cursor7");

						FileOutputStream fileOutputStream = null;
						FileOutputStream fileOutputStream1 = null;
						try {
							if (StringUtils.isNotBlank(ajxx.getAjbs()) && StringUtils.isNotBlank(daAjxx.getAh())
									&& StringUtils.isNotBlank(daAjxx.getDm())
									&& StringUtils.isNotBlank(daAjxx.getLarq())
									&& StringUtils.isNotBlank(daAjxx.getCbr())
									&& StringUtils.isNotBlank(daAjxx.getJarq()) && daStwj.size() > 1) {
								String daXmlName = "DA_" + ajxx.getJzajlx() + "_" + ajxx.getAjbs() + "_1.xml";
								ByteArrayOutputStream daOutPut = createDaXml(ajxx, daAjxx, daMlxx, daStwj);
								fileOutputStream = new FileOutputStream(root + daDir + daXmlName);
								fileOutputStream.write(daOutPut.toByteArray());
								if (daOutPut != null) {
									daOutPut.close();
								}
							}
							if (StringUtils.isNotBlank(ajxx.getAjbs()) && StringUtils.isNotBlank(jzAjxx.getAh())
									&& StringUtils.isNotBlank(jzAjxx.getDm())
									&& StringUtils.isNotBlank(jzAjxx.getLarq())
									&& StringUtils.isNotBlank(jzAjxx.getCbr()) && jzStwj.size() > 1) {
								String jzXmlName = "DA_" + ajxx.getJzajlx() + "_" + ajxx.getAjbs() + "_0.xml";
								ByteArrayOutputStream jzOutPut = createJzXml(ajxx, jzAjxx, jzMlxx, jzStwj);
								fileOutputStream1 = new FileOutputStream(root + jzDir + jzXmlName);
								fileOutputStream1.write(jzOutPut.toByteArray());
								if (jzOutPut != null) {
									jzOutPut.close();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(String.format("生成xml出现异常：%s", e.toString()));
						} finally {
							try {
								if (fileOutputStream != null) {
									fileOutputStream.close();
								}
								if (fileOutputStream1 != null) {
									fileOutputStream1.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
		executorService.shutdown();

		try {
			boolean loop = true;
			do {
				loop = !executorService.awaitTermination(TimeUtils.interval, TimeUnit.MILLISECONDS);
			} while (loop);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<File> deleteList = new ArrayList<>();
		String[] dirs = { daDir, jzDir };
		for (String dir : dirs) {
			// 档案文件夹
			File file = new File(root + dir);
			File[] xmls = file.listFiles();
			int xmlCount = 0;// 统计一共有多少xml
			for (File xml : xmls) {
				if (xml.getName().endsWith("xml")) {
					xmlCount += 1;
				}
			}
			if (0 < xmlCount && xmlCount <= 1000) {
				List<File> fileList = new ArrayList<>();
				for (File xml : xmls) {
					if (xml.getName().endsWith("xml")) {
						try {
							boolean retb = new Xmlvalidate().validateXml(xsdName, root + dir + xml.getName());
							if (!retb) {
								xml.delete();
								xmlCount = xmlCount - 1;
							} else {
								fileList.add(xml);
							}
						} catch (SAXException | IOException e1) {
							logger.error(e1.toString());
							continue;
						}

						deleteList.add(xml);
					}
				}
				if (!fileList.isEmpty()) {
					// DA_4位法院代码_YYYYMMDDHHMMSS_2位分组ID_2017.zip
					String zipName = "DA_3000_" + TimeUtils.getNowDate() + "_01_" + xmlCount + ".zip";
					FileOutputStream fos2 = null;
					try {
						fos2 = new FileOutputStream(new File(root + dir + zipName));
						FileZipCompressUtil.toZip(fileList, fos2);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						if (fos2 != null) {
							try {
								fos2.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					sjsbService.addSjsbFile(root + dir + zipName);
				}
			} else {
				int num = 1;
				List<File> fileList = new ArrayList<>();
				for (File xml : xmls) {// 循环整个文件夹文件树

					if (xml.getName().endsWith("xml")) {// 筛选出xml
						try {
							boolean retb = new Xmlvalidate().validateXml(xsdName, root + dir + xml.getName());
							if(retb) {
								fileList.add(xml);
								num = num + 1;
							}else {
								xml.delete();
							}
						} catch (SAXException | IOException e1) {
							logger.error(e1.toString());
							continue;
						}
						deleteList.add(xml);
					}
					if (fileList.size() == 1000) {// 文件夹内可能有zip包，所以用fileList,size
						String zipName = "DA_3000_" + TimeUtils.getNowDate() + "_01_1000.zip";
						FileOutputStream fos2 = null;
						try {
							fos2 = new FileOutputStream(new File(root + dir + zipName));
							FileZipCompressUtil.toZip(fileList, fos2);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} finally {
							if (fos2 != null) {
								try {
									fos2.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						fileList.clear();
						sjsbService.addSjsbFile(root + dir + zipName);
						num = num - 1000;
					}
				}
				if (fileList.size() > 0) {
					String zipName = "DA_3000_" + TimeUtils.getNowDate() + "_01_" + num + ".zip";
					FileOutputStream fos2 = null;
					try {
						fos2 = new FileOutputStream(new File(root + dir + zipName));
						FileZipCompressUtil.toZip(fileList, fos2);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						if (fos2 != null) {
							try {
								fos2.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					sjsbService.addSjsbFile(root + dir + zipName);
					num += 1;
				}
			}
		}
		for (File deletefile : deleteList) {
			deletefile.delete();
		}
		List<String> zipnameList = sjsbService.getSjsbFile();
		HttpSendCaller caller = null;
		String url = "http://150.0.2.124/ExchangePlatform/";
		EPClient client = null;
		try {
			client = new EPClient(ServiceAddress.valueOf(url));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		EPProtocol protocol = new EPProtocol("电子档案", "最高法院_四川");
		for (String zipname : zipnameList) {
			try {
				File file = new File(zipname);
				caller = new HttpSendCaller(file, protocol, file.getName());
				client.call(caller);
				logger.info(String.format("----------%s 上报完毕----------------", zipname));
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (Exception e) {
				System.out.println(caller.getTaskId());
				logger.error(String.format("上报出现错误：%s", e.toString()));
				e.printStackTrace();
				continue;
			}
		}
		//清理
		List<String> deleteZipnameList = sjsbService.getDeleteSjsbFile();
		for (String zipname : deleteZipnameList) {
			try {
				File file = new File(zipname);
				if(file.exists()) {
					file.delete();
				}
			}catch (Exception e) {
				continue;
			}
		}
	}

	public static void main(String[] args) {
		HttpSendCaller caller = null;
		try {
			String url = "http://150.0.2.124/ExchangePlatform/";
			EPClient client = new EPClient(ServiceAddress.valueOf(url));
			EPProtocol protocol = new EPProtocol("电子档案", "最高法院_四川");
			File file = new File("D://SJSB//DA//DA_3000_20180524165213_01_1000.zip");
			caller = new HttpSendCaller(file, protocol, file.getName());
			client.call(caller);
			System.out.println(file.getName());
		} catch (Exception e) {
			System.out.println(caller.getTaskId());
			logger.error(String.format("上报出现错误：%s", e.toString()));
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @function 四川的档案卷对应最高院的卷宗卷
	 * @author Tony
	 * @creaetime 2018年5月11日 下午4:18:54
	 * @param daAjxx
	 * @param damlxxList
	 * @param dawjxxList
	 * @return
	 */
	public ByteArrayOutputStream createDaXml(AjxxInfo ajxx, Ajxx daAjxx, List<DaMlxx> damlxxList,
			List<Stwj> dawjxxList) {
		try {
			// 档案
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("档案信息", "http://dataexchange.court.gov.cn/2009/data");
			root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.addAttribute("xsi:schemaLocation", "http://dataexchange.court.gov.cn/2009/data DA_档案信息.xsd");
			root.addElement("立案日期").addText(daAjxx.getLarq());
			root.addElement("结案日期").addText(daAjxx.getJarq());
			root.addElement("档案或卷宗").addText("1");
			root.addElement("案件标识").addText(ajxx.getAjbs());
			root.addElement("承办人").addText(daAjxx.getCbr());
			root.addElement("案号").addText(daAjxx.getAh());
			root.addElement("总页数").addText(String.valueOf(dawjxxList.size()));
			// root.addElement("经办法院").addText(daAjxx.getDm());
			root.addElement("案件类别").addText(daAjxx.getAjlb());
			root.addElement("案件类型").addText(ajxx.getJzajlx());
			Element mlxx = root.addElement("目录信息");
			for (DaMlxx daMlxx : damlxxList) {
				Element r = mlxx.addElement("R");
				r.addElement("目录编号").addText(daMlxx.getMlbh());
				r.addElement("父目录编号").addText(daMlxx.getFmlbh());
				r.addElement("目录名称").addText(daMlxx.getMlmc());
				r.addElement("是否属于正卷").addText(daMlxx.getSfsyzq());
				r.addElement("序号").addText(daMlxx.getXh());
				if (!StringUtils.isNotEmpty(daMlxx.getKsys())) {
					r.addElement("开始页数").addText("0");
				} else {
					r.addElement("开始页数").addText(daMlxx.getKsys());
				}
				if (!StringUtils.isNotEmpty(daMlxx.getJsys())) {
					r.addElement("结束页数").addText("0");
				} else {
					r.addElement("结束页数").addText(daMlxx.getJsys());
				}

			}
			Element wjxx = root.addElement("文件信息");
			for (Stwj daStwj : dawjxxList) {
				if (fileFormate.contains(daStwj.getWjmc().trim().substring(daStwj.getWjmc().indexOf(".") + 1))
						&& !daStwj.getWjmc().trim().startsWith(".")) {
					Element r = wjxx.addElement("R");
					r.addElement("序号").addText(daStwj.getXh());
					r.addElement("文件名称").addText(daStwj.getWjmc());
					r.addElement("是否属于正卷").addText(daStwj.getSfsyzq());
					r.addElement("所属目录编号").addText(daStwj.getSsmlbh());

					if (daStwj.getWjdx().contains(".")) {
						BigDecimal wjdx = new BigDecimal(daStwj.getWjdx());
						String intwjdx = String.valueOf(wjdx.multiply(new BigDecimal("1024")).intValue());
						r.addElement("文件大小").addText(intwjdx);
					} else {
						r.addElement("文件大小").addText(daStwj.getWjdx() == "" ? "0" : daStwj.getWjdx());
					}
					String wjbs = "stwj_" + ajxx.getAjbs() + "_" + daStwj.getXh();
					r.addElement("文件标识").addText(new String(Base64.encodeBase64(wjbs.getBytes()), "UTF-8"));
				}
			}
			OutputFormat format = new OutputFormat("   ", true);
			format.setEncoding("UTF-8");// 设置编码格式
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @function 四川的过程卷宗卷对应最高院的档案卷
	 * @author Tony
	 * @creaetime 2018年5月11日 下午4:19:18
	 * @param jzAjxx
	 * @param jzmlxxList
	 * @param jzwjxxList
	 * @return
	 */
	public ByteArrayOutputStream createJzXml(AjxxInfo ajxx, Ajxx jzAjxx, List<JzMlxx> jzmlxxList,
			List<Stwj> jzwjxxList) {
		try {
			// 档案
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("档案信息", "http://dataexchange.court.gov.cn/2009/data");
			root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.addAttribute("xsi:schemaLocation", "http://dataexchange.court.gov.cn/2009/data DA_档案信息.xsd");
			root.addElement("立案日期").addText(jzAjxx.getLarq());
			if (!StringUtils.isEmpty(jzAjxx.getJarq())) {
				root.addElement("结案日期").addText(jzAjxx.getJarq());
			}
			root.addElement("档案或卷宗").addText("0");
			root.addElement("案件标识").addText(ajxx.getAjbs());
			root.addElement("承办人").addText(jzAjxx.getCbr());
			root.addElement("案号").addText(jzAjxx.getAh());
			root.addElement("总页数").addText(String.valueOf(jzwjxxList.size()));
			// root.addElement("经办法院").addText(jzAjxx.getDm());
			root.addElement("案件类别").addText(jzAjxx.getAjlb());
			root.addElement("案件类型").addText(ajxx.getJzajlx());
			Element mlxx = root.addElement("目录信息");
			for (JzMlxx jzMlxx : jzmlxxList) {
				Element r = mlxx.addElement("R");
				r.addElement("目录编号").addText(jzMlxx.getMlbh());
				if (!StringUtils.isEmpty(jzMlxx.getFmlbh())) {
					r.addElement("父目录编号").addText(jzMlxx.getFmlbh());
				}
				r.addElement("目录名称").addText(jzMlxx.getMlmc());
				r.addElement("是否属于正卷").addText(jzMlxx.getSfsyzq());
				r.addElement("序号").addText(jzMlxx.getXh());
				r.addElement("开始页数").addText("0");
				r.addElement("结束页数").addText("0");
			}
			Element wjxx = root.addElement("文件信息");
			for (Stwj jzStwj : jzwjxxList) {
				if (fileFormate.contains(jzStwj.getWjmc().trim().substring(jzStwj.getWjmc().indexOf(".") + 1))
						&& !jzStwj.getWjmc().trim().startsWith(".")) {
					Element r = wjxx.addElement("R");
					r.addElement("序号").addText(jzStwj.getXh());
					r.addElement("文件名称").addText(jzStwj.getWjmc());
					r.addElement("是否属于正卷").addText(jzStwj.getSfsyzq());
					r.addElement("所属目录编号").addText(jzStwj.getSsmlbh());
					if (jzStwj.getWjdx().contains(".")) {
						BigDecimal wjdx = new BigDecimal(jzStwj.getWjdx());
						String intwjdx = String.valueOf(wjdx.multiply(new BigDecimal("1024")).intValue());
						r.addElement("文件大小").addText(intwjdx);
					} else {
						r.addElement("文件大小").addText(jzStwj.getWjdx() == "" ? "0" : jzStwj.getWjdx());
					}
					String wjbs = "stwj_" + ajxx.getAjbs() + "_" + jzStwj.getXh();
					r.addElement("文件标识").addText(new String(Base64.encodeBase64(wjbs.getBytes()), "UTF-8"));
				}
			}

			OutputFormat format = new OutputFormat("   ", true);
			format.setEncoding("UTF-8");// 设置编码格式
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
