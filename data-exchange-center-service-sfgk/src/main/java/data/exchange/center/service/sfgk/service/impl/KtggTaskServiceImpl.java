package data.exchange.center.service.sfgk.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.sfgk.domain.Aj;
import data.exchange.center.service.sfgk.domain.Ktgkxx;
import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.util.FileInfoUtil;
import data.exchange.center.service.sfgk.util.FileZipCompressUtil;
import data.exchange.center.service.sfgk.util.XsdName;
import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

@Service("ktggTaskService")
public class KtggTaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(KtggTaskServiceImpl.class);
	
	@Autowired
	private SfgkService sfgkService;

	@Override
	public void startTask() throws Exception {
		logger.info("开始生产 开庭公告 xml");
		String root = "D://SFGK//";
		int count = 0;
		int filenum = 1;
		ByteArrayOutputStream outputStream = null;
		int c = 0;
		List<Aj> ajList = sfgkService.selectAj();
		
		for(Aj aj:ajList) {
			Map<String, Object> ret = sfgkService.getKtgkxx(aj.getAjbs(), aj.getAjlx());
			if(ret.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
				@SuppressWarnings("unchecked")
				List<Ktgkxx> ktgkxxList = (List<Ktgkxx>) ret.get(CodeUtil.RETURN_MSG);
				
				for(Ktgkxx ktgkxx:ktgkxxList) {
					if(!StringUtils.isEmpty(ktgkxx.getKssj())&&
							!StringUtils.isEmpty(ktgkxx.getFtmc())&&
							!StringUtils.isEmpty(ktgkxx.getZsfgxm())&&
							!StringUtils.isEmpty(ktgkxx.getDm())&&
							!StringUtils.isEmpty(ktgkxx.getFymc())&&
							!StringUtils.isEmpty(ktgkxx.getGgnr())&&
							!StringUtils.isEmpty(ktgkxx.getGgbt())&&
							!StringUtils.isEmpty(ktgkxx.getPqrq())&&
							!StringUtils.isEmpty(ktgkxx.getGggkztdm())&&
							!StringUtils.isEmpty(ktgkxx.getGggkztmc())&&
							!StringUtils.isEmpty(ktgkxx.getSsfqxktdm())&&
							!StringUtils.isEmpty(ktgkxx.getSsfqxktmc())&&
							getDaySub(ktgkxx.getPqrq(), ktgkxx.getKssj())>=3) {
						c +=1;
						System.err.println(c);
						String bgrXh = ktgkxx.getBgrxh();
						String ygrXh = ktgkxx.getYgxh();
						//被告人查询
						StringBuffer tranBgr = new StringBuffer();
						if(!StringUtils.isEmpty(bgrXh)) {
							String bgr[] = bgrXh.split(",");
							int k = 0;
							for(String bg:bgr) {
								String mc = sfgkService.getYastmlMc(aj.getAjbs(), bg.trim());
								if(k == 0) {
									tranBgr.append(mc);
								}else {
									tranBgr.append(",").append(mc);
								}
							} 
							ktgkxx.setBgrxh(tranBgr.toString());
						}
						
						if(!aj.getAjlx().startsWith("1")) {//刑事案件不需要原告信息
							//原告人查询
							StringBuffer tranYgr = new StringBuffer();
							if(!StringUtils.isEmpty(ygrXh)) {
								String ygr[] = ygrXh.split(",");
								int i = 0;
								for(String yg:ygr) {
									String mc = sfgkService.getYastmlMc(aj.getAjbs(), yg);
									if(i == 0) {
										tranYgr.append(mc);
									}else {
										tranYgr.append(",").append(mc);
									}
								} 
								ktgkxx.setYgxh(tranYgr.toString());
							}
						}else {
							ktgkxx.setYgxh("");
						}
						
						if(count == 0) {//第一次需要创建xml
							outputStream = createXml(ktgkxx);
							File file = new File(root);
							if(!file.exists()) file.mkdir();
							count +=1;
						}else if(count==1000){//1000条后置0，重新产生xml
							ByteArrayOutputStream outStream = appendXML(new String(outputStream.toByteArray(), "utf-8"), ktgkxx);
							String xmlfileName = root+String.format(FileInfoUtil.XML_NAME_KTGG, filenum);
							FileOutputStream fileOutputStream = new FileOutputStream(xmlfileName);
							fileOutputStream.write(outStream.toByteArray());
							fileOutputStream.close();
							filenum +=1;
							count=0;
						}else {
							outputStream = appendXML(new String(outputStream.toByteArray(), "utf-8"), ktgkxx);
							count +=1;
						}
					}
				}
			}
		}
		if(!StringUtils.isEmpty(outputStream)) {//说明上面至少有一条数据正确
			if(count<1000) {
				String xmlfileName = root+String.format(FileInfoUtil.XML_NAME_KTGG, filenum);
				FileOutputStream fileOutputStream = new FileOutputStream(xmlfileName);
				fileOutputStream.write(outputStream.toByteArray());
				fileOutputStream.close();
				filenum=1;
			}
		}
		filenum=1;
//		for(String ajl:ajlxs) {
		
//		}
		
		/**
		 * 开始压缩xml成zip
		 */
		List<File> deleteList = new ArrayList<>();
		File file = new File(root);
		File xmls[] = file.listFiles();
		int xmlCount = 0;//统计一共有多少xml
		for(File xml:xmls) {
			if(xml.getName().endsWith("XML")) {
				xmlCount += 1;
			}
		}
		String dirc = root+"//ktgg//";
		File fi = new File(dirc);
		if(!fi.exists()) fi.mkdir();
		if(0<xmlCount&&xmlCount<=1000) {
			List<File> fileList = new ArrayList<>();
			for(File xml:xmls) {
				if(xml.getName().endsWith("XML")) {
					
					if(new Xmlvalidate().validateXml(XsdName.KTGG, root+xml.getName())) {//校验通过的才压缩，并且压缩后删除
						fileList.add(xml);
					}
					deleteList.add(xml);
				}
			}
			if(!fileList.isEmpty()) {
				//约定ZIP包名称：类型_EXPORT_更新开始时间_更新结束时间_法院代码_导出时间_序号.ZIP
				String zipname = FileInfoUtil.ZIP_LX_KTGG
						+"_export_"
						+TimeUtils.getYesterdayDate()+"000000"
						+"_"+TimeUtils.getYesterdayDate()+"235959"
						+"_3000_"
						+TimeUtils.getNTime()+"_1.zip";
				FileOutputStream fos2 = new FileOutputStream(new File(dirc+zipname));
				FileZipCompressUtil.toZip(fileList, fos2);
				sfgkService.addSfgkFile(dirc+zipname);
			}
		}else {
			int num = 1;
			List<File> fileList = new ArrayList<>();
			for(File xml:xmls) {//循环整个文件夹文件树
				if(xml.getName().endsWith("XML")) {//筛选出xml
					if(new Xmlvalidate().validateXml(XsdName.KTGG, root+xml.getName())) {
						fileList.add(xml);
					}
					deleteList.add(xml);
				}
				if(fileList.size()==1000) {//文件夹内可能有zip包，所以用fileList,size
					//约定ZIP包名称：类型_EXPORT_更新开始时间_更新结束时间_法院代码_导出时间_序号.ZIP
					String zipname = FileInfoUtil.ZIP_LX_KTGG
							+"_export_"
							+TimeUtils.getYesterdayDate()+"000000"
							+"_"+TimeUtils.getYesterdayDate()+"235959"
							+"_3000_"
						+TimeUtils.getNowDate()+"_"+num+".zip";
					FileOutputStream fos2 = new FileOutputStream(new File(dirc+zipname));
					FileZipCompressUtil.toZip(fileList, fos2);
					fileList.clear();
					sfgkService.addSfgkFile(dirc+zipname);
					num +=1;
				}
			}
			if(fileList.size()>0) {
				String zipname = FileInfoUtil.ZIP_LX_KTGG
						+"_export_"
						+TimeUtils.getYesterdayDate()+"000000"
						+"_"+TimeUtils.getYesterdayDate()+"235959"
						+"_3000_"
						+TimeUtils.getNowDate()+"_"+num+".zip";
				FileOutputStream fos2 = new FileOutputStream(new File(dirc+zipname));
				FileZipCompressUtil.toZip(fileList, fos2);
				sfgkService.addSfgkFile(dirc+zipname);
				num +=1;
			}
		}
		for(File deletefile:deleteList) {
			deletefile.delete();
		}
	}

	/**
	 * 
	 * @function 生产xml
	 * @author Tony
	 * @param ktgkxxList
	 * @creaetime 2018年4月26日 下午3:17:24
	 * @return
	 */
	public ByteArrayOutputStream createXml(Ktgkxx ttgkxx) {
		try {
			
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("开庭公告","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd"); 
			Element ktggxx = root.addElement("开庭公告信息");
			Element r = ktggxx.addElement("R");
			r.addElement("编号").addText(ttgkxx.getAjbs() == null ? "" : ttgkxx.getAjbs());
			r.addElement("案件编号").addText(ttgkxx.getAjbs() == null ? "" : ttgkxx.getAjbs());
			r.addElement("案件的唯一标识").addText(ttgkxx.getAjbs() == null ? "" : ttgkxx.getAjbs());
			r.addElement("案号").addText(ttgkxx.getAh() == null ? "" : ttgkxx.getAh());
			r.addElement("案件类别代码").addText(ttgkxx.getAjlbdm() == null ? "" : ttgkxx.getAjlbdm());
			r.addElement("案件类别名称").addText(ttgkxx.getAjlbmc() == null ? "" : ttgkxx.getAjlbmc());
			r.addElement("开庭日期").addText(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(ttgkxx.getKssj())) == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(ttgkxx.getKssj())));
			r.addElement("法庭名称").addText(ttgkxx.getFtmc() == null ? "" : ttgkxx.getFtmc());
			r.addElement("案由编号").addText(ttgkxx.getLaay() == null ? "" : ttgkxx.getLaay());
			r.addElement("主审法官姓名").addText(ttgkxx.getZsfgxm() == null ? "" : ttgkxx.getZsfgxm());
			r.addElement("原告或者上诉人名称").addText(ttgkxx.getYgxh() == null ? "" : ttgkxx.getYgxh());
			r.addElement("被告或者被上诉人名称").addText(ttgkxx.getBgrxh() == null ? "" : ttgkxx.getBgrxh());
			r.addElement("公告类型").addText(ttgkxx.getGglx() == null ? "" : ttgkxx.getGglx());
			r.addElement("法院代码").addText(ttgkxx.getDm() == null ? "" : ttgkxx.getDm());
			r.addElement("法院名称").addText(ttgkxx.getFymc() == null ? "" : ttgkxx.getFymc());
			r.addElement("公告内容").addText(ttgkxx.getGgnr() == null ? "" : ttgkxx.getGgnr());
			r.addElement("公告标题").addText(ttgkxx.getGgbt() == null ? "" : ttgkxx.getGgbt());
			r.addElement("发布时间").addText(ttgkxx.getPqrq() == null ? "" : ttgkxx.getPqrq().substring(0, 10)+"T00:00:00.000");
			r.addElement("更新时间").addText(ttgkxx.getPqrq() == null ? "" : ttgkxx.getPqrq().substring(0, 10)+"T00:00:00.000");
			r.addElement("公告公开状态代码").addText(ttgkxx.getGggkztdm() == null ? "" : ttgkxx.getGggkztdm());
			r.addElement("公告公开状态名称").addText(ttgkxx.getGggkztmc() == null ? "" : ttgkxx.getGggkztmc());
			r.addElement("是否取消开庭代码").addText(ttgkxx.getSsfqxktdm() == null ? "" : ttgkxx.getSsfqxktdm());
			r.addElement("是否取消开庭名称").addText(ttgkxx.getSsfqxktmc() == null ? "" : ttgkxx.getSsfqxktmc());

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
	 * @function 追加xml
	 * @author Tony
	 * @creaetime 2018年4月26日 下午3:11:59
	 * @throws IOException
	 */
	public ByteArrayOutputStream appendXML(String xml, Ktgkxx ttgkxx) throws IOException {
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element ktggxx = root.element("开庭公告信息");
			Element r = ktggxx.addElement("R");

			r.addElement("编号").addText(ttgkxx.getAjbs() == null ? "" : ttgkxx.getAjbs());
			r.addElement("案件编号").addText(ttgkxx.getAjbs() == null ? "" : ttgkxx.getAjbs());
			r.addElement("案件的唯一标识").addText(ttgkxx.getAjbs() == null ? "" : ttgkxx.getAjbs());
			r.addElement("案号").addText(ttgkxx.getAh() == null ? "" : ttgkxx.getAh());
			r.addElement("案件类别代码").addText(ttgkxx.getAjlbdm() == null ? "" : ttgkxx.getAjlbdm());
			r.addElement("案件类别名称").addText(ttgkxx.getAjlbmc() == null ? "" : ttgkxx.getAjlbmc());
			r.addElement("开庭日期").addText(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(ttgkxx.getKssj())) == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(ttgkxx.getKssj())));
			r.addElement("法庭名称").addText(ttgkxx.getFtmc() == null ? "" : ttgkxx.getFtmc());
			r.addElement("案由编号").addText(ttgkxx.getLaay() == null ? "" : ttgkxx.getLaay());
			r.addElement("主审法官姓名").addText(ttgkxx.getZsfgxm() == null ? "" : ttgkxx.getZsfgxm());
			r.addElement("原告或者上诉人名称").addText(ttgkxx.getYgxh() == null ? "" : ttgkxx.getYgxh());
			r.addElement("被告或者被上诉人名称").addText(ttgkxx.getBgrxh() == null ? "" : ttgkxx.getBgrxh());
			r.addElement("公告类型").addText(ttgkxx.getGglx() == null ? "" : ttgkxx.getGglx());
			r.addElement("法院代码").addText(ttgkxx.getDm() == null ? "" : ttgkxx.getDm());
			r.addElement("法院名称").addText(ttgkxx.getFymc() == null ? "" : ttgkxx.getFymc());
			r.addElement("公告内容").addText(ttgkxx.getGgnr() == null ? "" : ttgkxx.getGgnr());
			r.addElement("公告标题").addText(ttgkxx.getGgbt() == null ? "" : ttgkxx.getGgbt());
			r.addElement("发布时间").addText(ttgkxx.getPqrq() == null ? "" : ttgkxx.getPqrq().substring(0, 10)+"T00:00:00.000");
			r.addElement("更新时间").addText(ttgkxx.getPqrq() == null ? "" : ttgkxx.getPqrq().substring(0, 10)+"T00:00:00.000");
			r.addElement("公告公开状态代码").addText(ttgkxx.getGggkztdm() == null ? "" : ttgkxx.getGggkztdm());
			r.addElement("公告公开状态名称").addText(ttgkxx.getGggkztmc() == null ? "" : ttgkxx.getGggkztmc());
			r.addElement("是否取消开庭代码").addText(ttgkxx.getSsfqxktdm() == null ? "" : ttgkxx.getSsfqxktdm());
			r.addElement("是否取消开庭名称").addText(ttgkxx.getSsfqxktmc() == null ? "" : ttgkxx.getSsfqxktmc());

			OutputFormat format = OutputFormat.createPrettyPrint();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (DocumentException | ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return null;
	}

	public String getStr(int filenum) {
		StringBuffer str = new StringBuffer();
		if (String.valueOf(filenum).length() == 1) {
			str.append("000").append(String.valueOf(filenum));
		} else if (String.valueOf(filenum).length() == 2) {
			str.append("00").append(String.valueOf(filenum));
		} else if (String.valueOf(filenum).length() == 3) {
			str.append("0").append(String.valueOf(filenum));
		} else if (String.valueOf(filenum).length() == 4) {
			str.append(String.valueOf(filenum));
		}
		return str.toString();
	}

	/**
	 * 功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			e.printStackTrace();
			e.getMessage();
		}
		return day;
	}
}