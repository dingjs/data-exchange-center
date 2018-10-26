package data.exchange.center.service.sfgk.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.sfgk.domain.Aj;
import data.exchange.center.service.sfgk.domain.ms.Dsr;
import data.exchange.center.service.sfgk.domain.ms.Msajjbxx;
import data.exchange.center.service.sfgk.domain.ms.Pcglr;
import data.exchange.center.service.sfgk.domain.ms.Ssdbxx;
import data.exchange.center.service.sfgk.domain.ms.Ssfjmhxx;
import data.exchange.center.service.sfgk.domain.pc.Pcajjbxx;
import data.exchange.center.service.sfgk.domain.pc.Pcdsr;
import data.exchange.center.service.sfgk.domain.pc.Pcywjg;
import data.exchange.center.service.sfgk.domain.xs.Bgrxx;
import data.exchange.center.service.sfgk.domain.xs.Bhrxx;
import data.exchange.center.service.sfgk.domain.xs.Gxclxx;
import data.exchange.center.service.sfgk.domain.xs.Kcsx;
import data.exchange.center.service.sfgk.domain.xs.Ksqk;
import data.exchange.center.service.sfgk.domain.xs.Spzzcy;
import data.exchange.center.service.sfgk.domain.xs.Tsxx;
import data.exchange.center.service.sfgk.domain.xs.Xsajjbxx;
import data.exchange.center.service.sfgk.domain.xs.Xsfdmsdsr;
import data.exchange.center.service.sfgk.domain.xs.Xsfdmsxx;
import data.exchange.center.service.sfgk.domain.xs.Yaqk;
import data.exchange.center.service.sfgk.domain.xs.Ycsx;
import data.exchange.center.service.sfgk.domain.xs.Zjxx;
import data.exchange.center.service.sfgk.domain.xs.Zsrxx;
import data.exchange.center.service.sfgk.domain.xs.Zzhf;
import data.exchange.center.service.sfgk.domain.xz.Bgxzxw;
import data.exchange.center.service.sfgk.domain.xz.Fyxx;
import data.exchange.center.service.sfgk.domain.xz.Sdxx;
import data.exchange.center.service.sfgk.domain.xz.Ssbqhxyzx;
import data.exchange.center.service.sfgk.domain.xz.Ssqk;
import data.exchange.center.service.sfgk.domain.xz.Xzajjbxx;
import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.util.Code;
import data.exchange.center.service.sfgk.util.FileInfoUtil;
import data.exchange.center.service.sfgk.util.FileZipCompressUtil;
import data.exchange.center.service.sfgk.util.ParseUtil;
import data.exchange.center.service.sfgk.util.XsdName;
import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

/**
 * 
 * Description: 案件公开
 * <p>Company: xinya </p>
 * <p>Date:2018年6月22日 上午9:44:57</p>
 * @author Tony
 * @version 1.0
 *
 */
@Service("ajgkTaskService")
public class AjgkTaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(AjgkTaskServiceImpl.class);
	
	@Autowired
	private SfgkService sfgkService;

	@Override
	public void startTask() throws Exception {
		logger.info("开始生产 案件公开 xml");
		try {
			String root = "D://SFGK//";
			int count = 0;
			int filenum = 1;
			List<Aj> ajList = sfgkService.selectAjgkAj();
			String dirc = root+"//ajxx//";
			File file = new File(dirc);
			if(!file.exists())
				file.mkdirs();
			List<File> fileList = new ArrayList<>();
			List<File> deleteList = new ArrayList<>();
			for(Aj aj:ajList) {
				try {
					String xmlfileName = null;
					boolean retb = false;
					if(aj.getAjlx().startsWith(Code.XS_NUMBER)) {
						Map<String, Object> ret = sfgkService.getXsAjgkxx(aj.getAjbs(), aj.getAjlx());
						ByteArrayOutputStream outStreamXml = makeXsXml(ret);
						xmlfileName = root+String.format(FileInfoUtil.XML_NAME_AJGK_XS, aj.getAjbs(), count);
						ParseUtil.saveXml(xmlfileName, outStreamXml);
						retb = new Xmlvalidate().validateXml(XsdName.XS, xmlfileName);
					}else if(aj.getAjlx().startsWith(Code.MS_NUMBER)){
						Map<String, Object> ret = sfgkService.getMsAjgkxx(aj.getAjbs(), aj.getAjlx());
						ByteArrayOutputStream outStreamXml = makeMsXml(ret);
						xmlfileName = root+String.format(FileInfoUtil.XML_NAME_AJGK_MS, aj.getAjbs(), count);
						ParseUtil.saveXml(xmlfileName, outStreamXml);
						retb = new Xmlvalidate().validateXml(XsdName.MS, xmlfileName);
					}else if(aj.getAjlx().startsWith(Code.XZ_NUMBER)) {
						Map<String, Object> ret = sfgkService.getXzAjgkxx(aj.getAjbs(), aj.getAjlx());
						ByteArrayOutputStream outStreamXml = makeXzXml(ret);
						xmlfileName = root+String.format(FileInfoUtil.XML_NAME_AJGK_XZ, aj.getAjbs(), count);
						ParseUtil.saveXml(xmlfileName, outStreamXml);
						retb = new Xmlvalidate().validateXml(XsdName.XZ, xmlfileName);
					}else if(aj.getAjlx().startsWith(Code.PC_NUMBER)) {
						Map<String, Object> ret = sfgkService.getPcAjgkxx(aj.getAjbs(), aj.getAjlx());
						ByteArrayOutputStream outStreamXml = makePcXml(ret);
						xmlfileName = root+String.format(FileInfoUtil.XML_NAME_AJGK_PC, aj.getAjbs(), count);
						ParseUtil.saveXml(xmlfileName, outStreamXml);
						retb = new Xmlvalidate().validateXml(XsdName.PC, xmlfileName);
					}
					
					//删除校验不通过的
					//校验不通过的不累加值
					if(!retb) {
						new File(xmlfileName).delete();
					}else {
						fileList.add(new File(xmlfileName));
						deleteList.add(new File(xmlfileName));
						count ++;
					}
					if(count == 1000) {
						String zipname = FileInfoUtil.ZIP_LX_AJXX
								+"_export_"
								+TimeUtils.getYesterdayDate()+"000000"
								+"_"+TimeUtils.getYesterdayDate()+"235959"
								+"_3000_"
								+TimeUtils.getNTime()+"_"+filenum+".zip";
						FileOutputStream fos2 = new FileOutputStream(new File(dirc+zipname));
						FileZipCompressUtil.toZip(fileList, fos2);
						sfgkService.addAjgkFile(dirc+zipname);
						count = 1;
						fileList.clear();
						filenum ++;
					}
				} catch (Exception e) {
					logger.error(e.toString());
					continue;
				}
			}
			if(count > 0) {
				String zipname = FileInfoUtil.ZIP_LX_AJXX
						+"_export_"
						+TimeUtils.getYesterdayDate()+"000000"
						+"_"+TimeUtils.getYesterdayDate()+"235959"
						+"_3000_"
						+TimeUtils.getNTime()+"_"+filenum+".zip";
				FileOutputStream fos2 = new FileOutputStream(new File(dirc+zipname));
				FileZipCompressUtil.toZip(fileList, fos2);
				sfgkService.addAjgkFile(dirc+zipname);
				count = 1;
				fileList.clear();
			}
			for(File deletefile:deleteList) {
				deletefile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}

	/**
	 * 刑事xml制作
	 * @function table中包含15个游标
	 * @author Tony
	 * @creaetime 2018年6月22日 下午4:12:05
	 * @param table
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ByteArrayOutputStream makeXsXml(Map<String, Object> table) {
		try {
			List<Xsajjbxx>  xsajjbxxList  = (List<Xsajjbxx>)  table.get(Code.V_CURSOR_1);
			List<Yaqk>      yaqkList      = (List<Yaqk>)      table.get(Code.V_CURSOR_2);
			List<Ksqk>      ksqkList      = (List<Ksqk>)      table.get(Code.V_CURSOR_3);
			List<Tsxx>      tsxxList      = (List<Tsxx>)      table.get(Code.V_CURSOR_4);
			List<Ycsx>      ycsxList      = (List<Ycsx>)      table.get(Code.V_CURSOR_5);
			List<Kcsx>      kcsxList      = (List<Kcsx>)      table.get(Code.V_CURSOR_6);
			List<Bgrxx>     bgrxxList     = (List<Bgrxx>)     table.get(Code.V_CURSOR_7);
			List<Zsrxx>     zsrxxList     = (List<Zsrxx>)     table.get(Code.V_CURSOR_8);
			List<Bhrxx>     bhrxxList     = (List<Bhrxx>)     table.get(Code.V_CURSOR_9);
			List<Spzzcy>    spzzcyList    = (List<Spzzcy>)    table.get(Code.V_CURSOR_10);
			List<Xsfdmsdsr> xsfdmsdsrList = (List<Xsfdmsdsr>) table.get(Code.V_CURSOR_11);
			List<Xsfdmsxx>  xsfdmsxxList  = (List<Xsfdmsxx>)  table.get(Code.V_CURSOR_12);
			List<Zjxx>      zjxxList      = (List<Zjxx>)      table.get(Code.V_CURSOR_13);
			List<Zzhf>      zzhfList      = (List<Zzhf>)      table.get(Code.V_CURSOR_14);
			List<Gxclxx>    gxclxxList    = (List<Gxclxx>)    table.get(Code.V_CURSOR_15);
			
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("刑事案件","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		    Element node1 = root.addElement("刑事案件基本信息");
		    ParseUtil.setNodeText(node1, "唯一主键编号", xsajjbxxList.get(0).getWyzjbh());
		    ParseUtil.setNodeText(node1,"案件标识",xsajjbxxList.get(0).getAjbs());
		    ParseUtil.setNodeText(node1,"案号",xsajjbxxList.get(0).getAh());
		    ParseUtil.setNodeText(node1,"案件类型代码",xsajjbxxList.get(0).getAjlxdm());
		    ParseUtil.setNodeText(node1,"案件类型名称",xsajjbxxList.get(0).getAjlxmc());
		    ParseUtil.setNodeText(node1,"案件类型标识代码",xsajjbxxList.get(0).getAjlxbsdm());
		    ParseUtil.setNodeText(node1,"案件类型标识名称",xsajjbxxList.get(0).getAjlxbsmc());
		    ParseUtil.setNodeText(node1,"经办法院代码",xsajjbxxList.get(0).getJbfydm());
		    ParseUtil.setNodeText(node1,"经办法院名称",xsajjbxxList.get(0).getJbfymc());
		    ParseUtil.setNodeText(node1,"审判程序代码",xsajjbxxList.get(0).getSpcxdm());
		    ParseUtil.setNodeText(node1,"审判程序名称",xsajjbxxList.get(0).getSpcxmc());
		    ParseUtil.setNodeText(node1,"适用程序代码",xsajjbxxList.get(0).getSycxdm());
		    ParseUtil.setNodeText(node1,"适用程序名称",xsajjbxxList.get(0).getSycxmc());
		    ParseUtil.setNodeText(node1,"案件来源代码",xsajjbxxList.get(0).getAjlydm());
		    ParseUtil.setNodeText(node1,"案件来源名称",xsajjbxxList.get(0).getAjlymc());
		    ParseUtil.setNodeText(node1,"新案件来源代码",xsajjbxxList.get(0).getXajlydm());
		    ParseUtil.setNodeText(node1,"新案件来源名称",xsajjbxxList.get(0).getXajlymc());
		    ParseUtil.setNodeText(node1,"立案日期",ParseUtil.parseDate(xsajjbxxList.get(0).getLarq()));
		    ParseUtil.setNodeText(node1,"立案案由代码",xsajjbxxList.get(0).getLaaydm());
		    ParseUtil.setNodeText(node1,"立案案由名称",xsajjbxxList.get(0).getLaaymc());
		    ParseUtil.setNodeText(node1,"公诉机关",xsajjbxxList.get(0).getGsjg());
		    ParseUtil.setNodeText(node1,"诉讼性质代码",xsajjbxxList.get(0).getSsxzdm());
		    ParseUtil.setNodeText(node1,"诉讼性质名称",xsajjbxxList.get(0).getSsxzmc());
		    ParseUtil.setNodeText(node1,"是否附带民事诉讼代码",xsajjbxxList.get(0).getSffdmsssdm());
		    ParseUtil.setNodeText(node1,"是否附带民事诉讼名称",xsajjbxxList.get(0).getSffdmsssmc());
		    ParseUtil.setNodeText(node1,"案件进展阶段代码",xsajjbxxList.get(0).getAjjzjddm());
		    ParseUtil.setNodeText(node1,"案件进展阶段名称",xsajjbxxList.get(0).getAjjzjdmc());
		    ParseUtil.setNodeText(node1,"公开状态代码",xsajjbxxList.get(0).getGkztdm());
		    ParseUtil.setNodeText(node1,"公开状态名称",xsajjbxxList.get(0).getGkztmc());
		    ParseUtil.setNodeText(node1,"公开日期",ParseUtil.parseDate(xsajjbxxList.get(0).getGkrq()));
		    ParseUtil.setNodeText(node1,"撤销日期",ParseUtil.parseDate(xsajjbxxList.get(0).getCxrq()));
		    ParseUtil.setNodeText(node1,"有效状态代码",xsajjbxxList.get(0).getYxztdm());
		    ParseUtil.setNodeText(node1,"有效状态名称",xsajjbxxList.get(0).getYxztmc());
		    ParseUtil.setNodeText(node1,"承办审判庭",xsajjbxxList.get(0).getCbspt());
		    ParseUtil.setNodeText(node1,"承办人",xsajjbxxList.get(0).getCbr());
		    ParseUtil.setNodeText(node1,"法定审限天数",xsajjbxxList.get(0).getFdsxts());
		    ParseUtil.setNodeText(node1,"审限届满日期",ParseUtil.parseDate(xsajjbxxList.get(0).getSxjmrq()));
		    ParseUtil.setNodeText(node1,"结案日期",ParseUtil.parseDate(xsajjbxxList.get(0).getJarq()));
		    ParseUtil.setNodeText(node1,"结案案由代码",xsajjbxxList.get(0).getJaaydm());
		    ParseUtil.setNodeText(node1,"结案案由名称",xsajjbxxList.get(0).getJaaymc());
		    ParseUtil.setNodeText(node1,"结案方式代码",xsajjbxxList.get(0).getJafsdm());
		    ParseUtil.setNodeText(node1,"结案方式名称",xsajjbxxList.get(0).getJafsmc());
		    ParseUtil.setNodeText(node1,"新结案方式代码",xsajjbxxList.get(0).getXjafsdm());
		    ParseUtil.setNodeText(node1,"新结案方式名称",xsajjbxxList.get(0).getXjafsmc());
		    ParseUtil.setNodeText(node1,"创建时间",ParseUtil.parseDateTime(xsajjbxxList.get(0).getCjsj()));
		    ParseUtil.setNodeText(node1,"创建人",xsajjbxxList.get(0).getCjr());
		    ParseUtil.setNodeText(node1,"更新时间",ParseUtil.parseDateTime(xsajjbxxList.get(0).getGxsj()));
		    ParseUtil.setNodeText(node1,"更新人",xsajjbxxList.get(0).getGxr());

		    if(yaqkList.size()>0) {
		    	Element node2 = root.addElement("原案情况");
			    for(Yaqk yaqk:yaqkList) {
			    	Element r2 = node2.addElement("R");
				    ParseUtil.setNodeText(r2,"编号",yaqk.getBh());
				    ParseUtil.setNodeText(r2,"案件编号",yaqk.getAjbh());
				    ParseUtil.setNodeText(r2,"序号",yaqk.getXh());
				    ParseUtil.setNodeText(r2,"案号",yaqk.getAh());
				    ParseUtil.setNodeText(r2,"经办法院代码",yaqk.getJbfydm());
				    ParseUtil.setNodeText(r2,"经办法院名称",yaqk.getJbfymc());
			    }
		    }
		    
		    if(gxclxxList.size()>0) {
		    	Element node3 = root.addElement("管辖处理信息");
			    for(Gxclxx gxclxx:gxclxxList) {
			    	Element r3 = node3.addElement("R");
				    ParseUtil.setNodeText(r3,"编号",gxclxx.getBh());
				    ParseUtil.setNodeText(r3,"案件编号",gxclxx.getAjbh());
				    ParseUtil.setNodeText(r3,"序号",gxclxx.getBh());
				    ParseUtil.setNodeText(r3,"管辖问题类型代码",gxclxx.getGxwtlxdm());
				    ParseUtil.setNodeText(r3,"管辖问题类型名称",gxclxx.getGxwtlxmc());
				    ParseUtil.setNodeText(r3,"提出管辖异议人",gxclxx.getTcgxyyr());
				    ParseUtil.setNodeText(r3,"提出管辖异议日期",ParseUtil.parseDate(gxclxx.getTcgxyyrq()));
				    ParseUtil.setNodeText(r3,"管辖异议处理结果代码",gxclxx.getGxyycljgdm());
				    ParseUtil.setNodeText(r3,"管辖异议处理结果名称",gxclxx.getGxyycljgmc());
				    ParseUtil.setNodeText(r3,"管辖异议处理日期",ParseUtil.parseDate(gxclxx.getGxyyclrq()));
			    }
		    }
		    
		    if(ksqkList.size()>0) {
		    	Element node4 = root.addElement("抗诉情况");
			    ParseUtil.setNodeText(node4,"编号",ksqkList.get(0).getBh());
			    ParseUtil.setNodeText(node4,"案件编号",ksqkList.get(0).getAjbh());
			    ParseUtil.setNodeText(node4,"抗诉机关",ksqkList.get(0).getKsjg());
			    ParseUtil.setNodeText(node4,"提出抗诉日期",ParseUtil.parseDate(ksqkList.get(0).getTcksrq()));
		    }

//		    Element node5 = root.addElement("上诉情况");
//		    node5.addElement("编号").addText("");
//		    node5.addElement("案件编号").addText("");
//		    node5.addElement("上诉人").addText("");
//		    node5.addElement("提出上诉日期").addText("");

		    if(tsxxList.size()>0) {
		    	Element node6 = root.addElement("庭审信息");
			    for(Tsxx tsxx:tsxxList) {
			    	Element r6 = node6.addElement("R");
				    ParseUtil.setNodeText(r6,"编号",tsxx.getBh());
				    ParseUtil.setNodeText(r6,"案件编号",tsxx.getAjbh());
				    ParseUtil.setNodeText(r6,"序号",tsxx.getXh());
				    ParseUtil.setNodeText(r6,"法庭用途代码",tsxx.getFtytdm());
				    ParseUtil.setNodeText(r6,"法庭用途名称",tsxx.getFtytmc());
				    ParseUtil.setNodeText(r6,"开始时间",ParseUtil.parseDateTime(tsxx.getKssj()));
				    ParseUtil.setNodeText(r6,"结束时间",ParseUtil.parseDateTime(tsxx.getJssj()));
				    ParseUtil.setNodeText(r6,"地点",tsxx.getDd());
				    ParseUtil.setNodeText(r6,"是否公开开庭代码",tsxx.getSfgkktdm());
				    ParseUtil.setNodeText(r6,"是否公开开庭名称",tsxx.getSfgkktmc());
			    }
		    }
		    
		    if(ycsxList.size()>0) {
		    	Element node7 = root.addElement("延长审限");
			    for(Ycsx ycsx:ycsxList) {
			    	Element r7 = node7.addElement("R");
				    ParseUtil.setNodeText(r7,"编号",ycsx.getBh());
				    ParseUtil.setNodeText(r7,"案件编号",ycsx.getAjbh());
				    ParseUtil.setNodeText(r7,"序号",ycsx.getXh());
				    ParseUtil.setNodeText(r7,"申请日期",ParseUtil.parseDate(ycsx.getSqrq()));
				    ParseUtil.setNodeText(r7,"申请事由或原因代码",ycsx.getSqsyhyydm());
				    ParseUtil.setNodeText(r7,"申请事由或原因名称",ycsx.getSqsyhyymc());
				    ParseUtil.setNodeText(r7,"延长期间代码",ycsx.getYcqjdm());
				    ParseUtil.setNodeText(r7,"延长期间名称",ycsx.getYcqjmc());
				    ParseUtil.setNodeText(r7,"审批意见",ycsx.getSpyj());
				    ParseUtil.setNodeText(r7,"开始日期",ParseUtil.parseDate(ycsx.getKsrq()));
			    }
		    }
		    
		    if(kcsxList.size()>0) {
		    	Element node8 = root.addElement("扣除审限");
			    for(Kcsx kcsx:kcsxList) {
			    	Element r8 = node8.addElement("R");
				    ParseUtil.setNodeText(r8,"编号",kcsx.getBh());
				    ParseUtil.setNodeText(r8,"案件编号",kcsx.getAjbh());
				    ParseUtil.setNodeText(r8,"序号",kcsx.getXh());
				    ParseUtil.setNodeText(r8,"扣除审限类型代码",kcsx.getKcsxlxdm());
				    ParseUtil.setNodeText(r8,"扣除审限类型名称",kcsx.getKcsxlxmc());
				    ParseUtil.setNodeText(r8,"扣除审限事由代码",kcsx.getKcsxsydm());
				    ParseUtil.setNodeText(r8,"扣除审限事由名称",kcsx.getKcsxsymc());
				    ParseUtil.setNodeText(r8,"起始日期",ParseUtil.parseDate(kcsx.getQsrq()));
				    ParseUtil.setNodeText(r8,"结束日期",ParseUtil.parseDate(kcsx.getJsrq()));
			    }
		    }
		    
		    if(zzhfList.size()>0) {
		    	Element node9 = root.addElement("中止恢复");
			    for(Zzhf zzhf:zzhfList) {
			    	Element r9 = node9.addElement("R");
				    ParseUtil.setNodeText(r9,"编号",zzhf.getBh());
				    ParseUtil.setNodeText(r9,"案件编号",zzhf.getAjbh());
				    ParseUtil.setNodeText(r9,"序号",zzhf.getXh());
				    ParseUtil.setNodeText(r9,"中止日期",ParseUtil.parseDate(zzhf.getZzrq()));
				    ParseUtil.setNodeText(r9,"中止事由代码",zzhf.getZzsydm());
				    ParseUtil.setNodeText(r9,"中止事由名称",zzhf.getZzsymc());
				    ParseUtil.setNodeText(r9,"恢复日期",ParseUtil.parseDate(zzhf.getHfrq()));
			    }
		    }
		    
		    
		    if(bgrxxList.size()>0) {
		    	Element node10 = root.addElement("被告人信息");
			    for(Bgrxx bgrxx:bgrxxList) {
			    	Element r10 = node10.addElement("R");
				    ParseUtil.setNodeText(r10,"编号",bgrxx.getBh());
				    ParseUtil.setNodeText(r10,"案件编号",bgrxx.getAjbh());
				    ParseUtil.setNodeText(r10,"序号",bgrxx.getXh());
				    ParseUtil.setNodeText(r10,"类型代码",bgrxx.getLxdm());
				    ParseUtil.setNodeText(r10,"类型名称",bgrxx.getLxmc());
				    ParseUtil.setNodeText(r10,"诉讼地位代码",bgrxx.getSsdwdm());
				    ParseUtil.setNodeText(r10,"诉讼地位名称",bgrxx.getSsdwmc());
				    ParseUtil.setNodeText(r10,"名称",bgrxx.getMc());
				    ParseUtil.setNodeText(r10,"法定代表人",bgrxx.getFddbr());
			    }
		    }
		    
		    

//		    Element node11 = root.addElement("被告人的辩护人");
//		    Element r11 = node11.addElement("R");
//		    r11.addElement("编号").addText("");
//		    r11.addElement("案件编号").addText("");
//		    r11.addElement("序号").addText("");
//		    r11.addElement("被告人编号").addText("");
//		    r11.addElement("辩护人类型代码").addText("");
//		    r11.addElement("辩护人类型名称").addText("");
//		    r11.addElement("辩护人姓名").addText("");
//		    r11.addElement("辩护人单位").addText("");
		    if(zsrxxList.size()>0) {
		    	Element node12 = root.addElement("自诉人信息");
			    for(Zsrxx zsrxx:zsrxxList) {
			    	Element r12 = node12.addElement("R");
				    ParseUtil.setNodeText(r12,"编号",zsrxx.getBh());
				    ParseUtil.setNodeText(r12,"案件编号",zsrxx.getAjbh());
				    ParseUtil.setNodeText(r12,"序号",zsrxx.getXh());
				    ParseUtil.setNodeText(r12,"类型代码",zsrxx.getLxdm());
				    ParseUtil.setNodeText(r12,"类型名称",zsrxx.getLxmc());
				    ParseUtil.setNodeText(r12,"诉讼地位代码",zsrxx.getSsdwdm());
				    ParseUtil.setNodeText(r12,"诉讼地位名称",zsrxx.getSsdwmc());
				    ParseUtil.setNodeText(r12,"名称",zsrxx.getMc());
				    ParseUtil.setNodeText(r12,"法定代表人",zsrxx.getFddbr());
			    }
		    }
		    
		    

//		    Element node13 = root.addElement("自诉人的诉讼代理人");
//		    Element r13 = node13.addElement("R");
//		    r13.addElement("编号").addText("");
//		    r13.addElement("案件编号").addText("");
//		    r13.addElement("序号").addText("");
//		    r13.addElement("自诉人编号").addText("");
//		    r13.addElement("代理人类型代码").addText("");
//		    r13.addElement("代理人类型名称").addText("");
//		    r13.addElement("代理人姓名").addText("");
//		    r13.addElement("代理人单位").addText("");
		    if(bhrxxList.size()>0) {
		    	Element node14 = root.addElement("被害人信息");
			    for(Bhrxx bhrxx:bhrxxList) {
			    	Element r14 = node14.addElement("R");
				    ParseUtil.setNodeText(r14,"编号",bhrxx.getBh());
				    ParseUtil.setNodeText(r14,"案件编号",bhrxx.getAjbh());
				    ParseUtil.setNodeText(r14,"序号",bhrxx.getXh());
				    ParseUtil.setNodeText(r14,"类型代码",bhrxx.getLxdm());
				    ParseUtil.setNodeText(r14,"类型名称",bhrxx.getLxmc());
				    ParseUtil.setNodeText(r14,"名称",bhrxx.getMc());
			    }
		    }
		    
		    

//		    Element node15 = root.addElement("被害人的诉讼代理人");
//		    Element r15 = node15.addElement("R");
//		    r15.addElement("编号").addText("");
//		    r15.addElement("案件编号").addText("");
//		    r15.addElement("序号").addText("");
//		    r15.addElement("被害人编号").addText("");
//		    r15.addElement("代理人类型代码").addText("");
//		    r15.addElement("代理人类型名称").addText("");
//		    r15.addElement("代理人姓名").addText("");
//		    r15.addElement("代理人单位").addText("");
		    if(spzzcyList.size()>0) {
		    	Element node16 = root.addElement("审判组织成员");
			    for(Spzzcy spzzcy:spzzcyList) {
			    	Element r16 = node16.addElement("R");
				    ParseUtil.setNodeText(r16,"编号",spzzcy.getBh());
				    ParseUtil.setNodeText(r16,"案件编号",spzzcy.getAjbh());
				    ParseUtil.setNodeText(r16,"序号",spzzcy.getXh());
				    ParseUtil.setNodeText(r16,"姓名",spzzcy.getXm());
				    ParseUtil.setNodeText(r16,"角色代码",spzzcy.getJsdm());
				    ParseUtil.setNodeText(r16,"角色名称",spzzcy.getJsmc());
				    ParseUtil.setNodeText(r16,"联系电话",spzzcy.getLxdh());
			    }
		    }
		    
		    

//		    Element node17 = root.addElement("审判组织成员变更");
//		    Element r17 = node17.addElement("R");
//		    r17.addElement("编号").addText("");
//		    r17.addElement("案件编号").addText("");
//		    r17.addElement("序号").addText("");
//		    r17.addElement("原成员").addText("");
//		    r17.addElement("变更原因代码").addText("");
//		    r17.addElement("变更原因名称").addText("");
//		    r17.addElement("新成员").addText("");
//		    r17.addElement("新成员角色代码").addText("");
//		    r17.addElement("新成员角色名称").addText("");
//		    r17.addElement("变更日期").addText("");

//		    Element node18 = root.addElement("回避信息");
//		    Element r18 = node18.addElement("R");
//		    r18.addElement("编号").addText("");
//		    r18.addElement("案件编号").addText("");
//		    r18.addElement("序号").addText("");
//		    r18.addElement("回避类型代码").addText("");
//		    r18.addElement("回避类型名称").addText("");
//		    r18.addElement("被申请回避角色代码").addText("");
//		    r18.addElement("被申请回避角色名称").addText("");
//		    r18.addElement("被申请回避人").addText("");
//		    r18.addElement("回避事由代码").addText("");
//		    r18.addElement("回避事由名称").addText("");
//		    r18.addElement("回避申请人").addText("");
//		    r18.addElement("回避申请日期").addText("");
//		    r18.addElement("回避决定人代码").addText("");
//		    r18.addElement("回避决定人名称").addText("");
//		    r18.addElement("决定日期").addText("");
//		    r18.addElement("回避处理结果代码").addText("");
//		    r18.addElement("回避处理结果名称").addText("");
		    if(xsfdmsdsrList.size()>0) {
		    	Element node19 = root.addElement("刑事附带民事当事人");
			    for(Xsfdmsdsr xsfdmsdsr:xsfdmsdsrList) {
			    	Element r19 = node19.addElement("R");
				    ParseUtil.setNodeText(r19,"编号",xsfdmsdsr.getBh());
				    ParseUtil.setNodeText(r19,"案件编号",xsfdmsdsr.getAjbh());
				    ParseUtil.setNodeText(r19,"序号",xsfdmsdsr.getXh());
				    ParseUtil.setNodeText(r19,"类型代码",xsfdmsdsr.getLxdm());
				    ParseUtil.setNodeText(r19,"类型名称",xsfdmsdsr.getLxmc());
				    ParseUtil.setNodeText(r19,"诉讼地位代码",xsfdmsdsr.getSsdwdm());
				    ParseUtil.setNodeText(r19,"诉讼地位名称",xsfdmsdsr.getSsdwmc());
				    ParseUtil.setNodeText(r19,"附带民诉当事人",xsfdmsdsr.getFdmsdsr());
			    }
		    }
		    
		    
		    if(xsfdmsxxList.size()>0) {
		    	Element node20 = root.addElement("刑事附带民事信息");
			    for(Xsfdmsxx xsfdmsxx:xsfdmsxxList) {
			    	ParseUtil.setNodeText(node20,"编号",xsfdmsxx.getBh());
			    	ParseUtil.setNodeText(node20,"案件编号",xsfdmsxx.getAjbh());
			    	ParseUtil.setNodeText(node20,"提起人类型代码",xsfdmsxx.getTqrlxdm());
			    	ParseUtil.setNodeText(node20,"提起人类型名称",xsfdmsxx.getTqrlxmc());
			    	ParseUtil.setNodeText(node20,"提起诉讼日期",ParseUtil.parseDate(xsfdmsxx.getTqssrq()));
			    	ParseUtil.setNodeText(node20,"处理日期",ParseUtil.parseDate(xsfdmsxx.getClrq()));
			    	ParseUtil.setNodeText(node20,"处理方式代码",xsfdmsxx.getClfsdm());
			    	ParseUtil.setNodeText(node20,"处理方式名称",xsfdmsxx.getClfsmc());
			    	ParseUtil.setNodeText(node20,"请求赔偿金额",xsfdmsxx.getQqpcje());
			    }
		    }
		    
		    

//		    Element node21 = root.addElement("诉讼保全和先予执行");
//		    Element r21 = node21.addElement("R");
//		    r21.addElement("编号").addText("");
//		    r21.addElement("案件编号").addText("");
//		    r21.addElement("序号").addText("");
//		    r21.addElement("类型代码").addText("");
//		    r21.addElement("类型名称").addText("");
//		    r21.addElement("保全发动方代码").addText("");
//		    r21.addElement("保全发动方名称").addText("");
//		    r21.addElement("申请人").addText("");
//		    r21.addElement("被申请人").addText("");
//		    r21.addElement("申请日期").addText("");
//		    r21.addElement("申请金额").addText("");
//		    r21.addElement("申请财产").addText("");
//		    r21.addElement("裁定日期").addText("");
//		    r21.addElement("裁定结果代码").addText("");
//		    r21.addElement("裁定结果名称").addText("");
//		    r21.addElement("保全措施代码").addText("");
//		    r21.addElement("保全措施名称").addText("");
//		    r21.addElement("先予执行事由代码").addText("");
//		    r21.addElement("先予执行事由名称").addText("");
//		    r21.addElement("执行日期").addText("");
//		    r21.addElement("实际执行金额").addText("");
//		    r21.addElement("解除保全日期").addText("");
//		    r21.addElement("解除保全原因代码").addText("");
//		    r21.addElement("解除保全原因名称").addText("");

//		    Element node22 = root.addElement("诉讼担保信息");
//		    Element r22 = node22.addElement("R");
//		    r22.addElement("编号").addText("");
//		    r22.addElement("案件编号").addText("");
//		    r22.addElement("序号").addText("");
//		    r22.addElement("当事人").addText("");
//		    r22.addElement("事由代码").addText("");
//		    r22.addElement("事由名称").addText("");
//		    r22.addElement("担保人").addText("");
//		    r22.addElement("与当事人关系代码").addText("");
//		    r22.addElement("与当事人关系名称").addText("");
//		    r22.addElement("担保方式代码").addText("");
//		    r22.addElement("担保方式名称").addText("");
		    if(zjxxList.size()>0) {
		    	Element node23 = root.addElement("证据信息");
			    for(Zjxx zjxx:zjxxList) {
			    	Element r23 = node23.addElement("R");
				    ParseUtil.setNodeText(r23,"编号",zjxx.getBh());
				    ParseUtil.setNodeText(r23,"案件编号",zjxx.getAjbh());
				    ParseUtil.setNodeText(r23,"序号",zjxx.getXh());
				    ParseUtil.setNodeText(r23,"证据名称",zjxx.getZjmc());
				    ParseUtil.setNodeText(r23,"证据类型代码",zjxx.getZjlxdm());
				    ParseUtil.setNodeText(r23,"证据类型名称",zjxx.getZjlxmc());
			    }
		    }
		    
		    

//		    Element node24 = root.addElement("程序变更");
//		    node24.addElement("编号").addText("");
//		    node24.addElement("案件编号").addText("");
//		    node24.addElement("变更类型代码").addText("");
//		    node24.addElement("变更类型名称").addText("");
//		    node24.addElement("变更原因代码").addText("");
//		    node24.addElement("变更原因名称").addText("");
//		    node24.addElement("变更日期").addText("");

//		    Element node25 = root.addElement("复议信息");
//		    Element r25 = node25.addElement("R");
//		    r25.addElement("编号").addText("");
//		    r25.addElement("案件编号").addText("");
//		    r25.addElement("序号").addText("");
//		    r25.addElement("类型代码").addText("");
//		    r25.addElement("类型名称").addText("");
//		    r25.addElement("申请人").addText("");
//		    r25.addElement("申请日期").addText("");
//		    r25.addElement("处理日期").addText("");
//		    r25.addElement("处理结果代码").addText("");
//		    r25.addElement("处理结果名称").addText("");

//		    Element node26 = root.addElement("裁判文书上网信息");
//		    Element r26 = node26.addElement("R");
//		    r26.addElement("编号").addText("");
//		    r26.addElement("案件编号").addText("");
//		    r26.addElement("序号").addText("");
//		    r26.addElement("文书类型代码").addText("");
//		    r26.addElement("文书类型名称").addText("");
//		    r26.addElement("上网时间").addText("");
//		    r26.addElement("查询方式").addText("");

//		    Element node27 = root.addElement("送达信息");
//		    Element r27 = node27.addElement("R");
//		    r27.addElement("编号").addText("");
//		    r27.addElement("案件编号").addText("");
//		    r27.addElement("序号").addText("");
//		    r27.addElement("文书类型代码").addText("");
//		    r27.addElement("文书类型名称").addText("");
//		    r27.addElement("送达方式代码").addText("");
//		    r27.addElement("送达方式名称").addText("");
//		    r27.addElement("受送达人").addText("");
//		    r27.addElement("送达日期").addText("");
//		    r27.addElement("签收时间").addText("");
			
		    OutputFormat format = OutputFormat.createPrettyPrint();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ByteArrayOutputStream makeMsXml(Map<String, Object> table) {
		try {
			List<Msajjbxx>  xsajjbxxList  = (List<Msajjbxx>)  table.get(Code.V_CURSOR_1);
			List<data.exchange.center.service.sfgk.domain.ms.Yaqk>      yaqkList      = (List<data.exchange.center.service.sfgk.domain.ms.Yaqk>)      table.get(Code.V_CURSOR_2);
			List<data.exchange.center.service.sfgk.domain.ms.Ksqk>      ksqkList      = (List<data.exchange.center.service.sfgk.domain.ms.Ksqk>)      table.get(Code.V_CURSOR_3);
			List<data.exchange.center.service.sfgk.domain.ms.Tsxx>      tsxxList      = (List<data.exchange.center.service.sfgk.domain.ms.Tsxx>)      table.get(Code.V_CURSOR_4);
			List<data.exchange.center.service.sfgk.domain.ms.Ycsx>      ycsxList      = (List<data.exchange.center.service.sfgk.domain.ms.Ycsx>)      table.get(Code.V_CURSOR_5);
			List<data.exchange.center.service.sfgk.domain.ms.Kcsx>      kcsxList      = (List<data.exchange.center.service.sfgk.domain.ms.Kcsx>)      table.get(Code.V_CURSOR_6);
			List<data.exchange.center.service.sfgk.domain.ms.Spzzcy>    spzzcyList    = (List<data.exchange.center.service.sfgk.domain.ms.Spzzcy>)    table.get(Code.V_CURSOR_7);
			List<data.exchange.center.service.sfgk.domain.ms.Zjxx>      zjxxList      = (List<data.exchange.center.service.sfgk.domain.ms.Zjxx>)      table.get(Code.V_CURSOR_8);
			List<Ssfjmhxx>  ssfjmhxxList  = (List<Ssfjmhxx>)  table.get(Code.V_CURSOR_9);
			List<Dsr>       dsrList       = (List<Dsr>)       table.get(Code.V_CURSOR_10);
			List<Ssdbxx>    ssdbxxList    = (List<Ssdbxx>)    table.get(Code.V_CURSOR_11);
			List<Pcglr>     pcglrList     = (List<Pcglr>)     table.get(Code.V_CURSOR_12);
			List<data.exchange.center.service.sfgk.domain.ms.Zzhf>      zzhfList      = (List<data.exchange.center.service.sfgk.domain.ms.Zzhf>)      table.get(Code.V_CURSOR_13);
			
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("民事案件","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		    Element node1 = root.addElement("民事案件基本信息");
		    ParseUtil.setNodeText(node1,"唯一主键编号",xsajjbxxList.get(0).getWyzjbh());
		    ParseUtil.setNodeText(node1,"案件标识",xsajjbxxList.get(0).getAjbs());
		    ParseUtil.setNodeText(node1,"案号",xsajjbxxList.get(0).getAh());
		    ParseUtil.setNodeText(node1,"案件类型代码",xsajjbxxList.get(0).getAjlxdm());
		    ParseUtil.setNodeText(node1,"案件类型名称",xsajjbxxList.get(0).getAjlxmc());
		    ParseUtil.setNodeText(node1,"案件类型标识代码",xsajjbxxList.get(0).getAjlxbsdm());
		    ParseUtil.setNodeText(node1,"案件类型标识名称",xsajjbxxList.get(0).getAjlxbsmc());
		    ParseUtil.setNodeText(node1,"经办法院代码",xsajjbxxList.get(0).getJbfydm());
		    ParseUtil.setNodeText(node1,"经办法院名称",xsajjbxxList.get(0).getJbfymc());
		    ParseUtil.setNodeText(node1,"审判程序代码",xsajjbxxList.get(0).getSpcxdm());
		    ParseUtil.setNodeText(node1,"审判程序名称",xsajjbxxList.get(0).getSpcxmc());
		    ParseUtil.setNodeText(node1,"适用程序代码",xsajjbxxList.get(0).getSycxdm());
		    ParseUtil.setNodeText(node1,"适用程序名称",xsajjbxxList.get(0).getSycxmc());
		    ParseUtil.setNodeText(node1,"案件来源代码",xsajjbxxList.get(0).getAjlydm());
		    ParseUtil.setNodeText(node1,"案件来源名称",xsajjbxxList.get(0).getAjlymc());
		    ParseUtil.setNodeText(node1,"新案件来源代码",xsajjbxxList.get(0).getXajlydm());
		    ParseUtil.setNodeText(node1,"新案件来源名称",xsajjbxxList.get(0).getXajlymc());
		    ParseUtil.setNodeText(node1,"立案日期",ParseUtil.parseDate(xsajjbxxList.get(0).getLarq()));
		    ParseUtil.setNodeText(node1,"立案案由代码",xsajjbxxList.get(0).getLaaydm());
		    ParseUtil.setNodeText(node1,"立案案由名称",xsajjbxxList.get(0).getLaaymc());
		    ParseUtil.setNodeText(node1,"诉讼标的金额",xsajjbxxList.get(0).getSsbdje());
		    ParseUtil.setNodeText(node1,"案件进展阶段代码",xsajjbxxList.get(0).getAjjzjddm());
		    ParseUtil.setNodeText(node1,"案件进展阶段名称",xsajjbxxList.get(0).getAjjzjdmc());
		    ParseUtil.setNodeText(node1,"公开状态代码",xsajjbxxList.get(0).getGkztdm());
		    ParseUtil.setNodeText(node1,"公开状态名称",xsajjbxxList.get(0).getGkztmc());
		    ParseUtil.setNodeText(node1,"公开日期",ParseUtil.parseDate(xsajjbxxList.get(0).getGkrq()));
		    ParseUtil.setNodeText(node1,"撤销日期",ParseUtil.parseDate(xsajjbxxList.get(0).getCxrq()));
		    ParseUtil.setNodeText(node1,"有效状态代码",xsajjbxxList.get(0).getYxztdm());
		    ParseUtil.setNodeText(node1,"有效状态名称",xsajjbxxList.get(0).getYxztmc());
		    ParseUtil.setNodeText(node1,"承办审判庭",xsajjbxxList.get(0).getCbspt());
		    ParseUtil.setNodeText(node1,"承办人",xsajjbxxList.get(0).getCbr());
		    ParseUtil.setNodeText(node1,"法定审限天数",xsajjbxxList.get(0).getFdsxts());
		    ParseUtil.setNodeText(node1,"审限届满日期",ParseUtil.parseDate(xsajjbxxList.get(0).getSxjmrq()));
		    ParseUtil.setNodeText(node1,"结案日期",ParseUtil.parseDate(xsajjbxxList.get(0).getJarq()));
		    ParseUtil.setNodeText(node1,"结案案由代码",xsajjbxxList.get(0).getJaaydm());
		    ParseUtil.setNodeText(node1,"结案案由名称",xsajjbxxList.get(0).getJaaymc());
		    ParseUtil.setNodeText(node1,"结案方式代码",xsajjbxxList.get(0).getJafsdm());
		    ParseUtil.setNodeText(node1,"结案方式名称",xsajjbxxList.get(0).getJafsmc());
		    ParseUtil.setNodeText(node1,"新结案方式代码",xsajjbxxList.get(0).getXjafsdm());
		    ParseUtil.setNodeText(node1,"新结案方式名称",xsajjbxxList.get(0).getXjafsmc());
		    ParseUtil.setNodeText(node1,"创建时间",ParseUtil.parseDateTime(xsajjbxxList.get(0).getCjsj()));
		    ParseUtil.setNodeText(node1,"创建人",xsajjbxxList.get(0).getCjr());
		    ParseUtil.setNodeText(node1,"更新时间",ParseUtil.parseDateTime(xsajjbxxList.get(0).getGxsj()));
		    ParseUtil.setNodeText(node1,"更新人",xsajjbxxList.get(0).getGxr());

		    if(yaqkList.size()>0) {
		    	Element node2 = root.addElement("原案情况");
			    for(data.exchange.center.service.sfgk.domain.ms.Yaqk yaqk:yaqkList) {
			    	Element r2 = node2.addElement("R");
				    ParseUtil.setNodeText(r2,"编号",yaqk.getBh());
				    ParseUtil.setNodeText(r2,"案件编号",yaqk.getAjbh());
				    ParseUtil.setNodeText(r2,"序号",yaqk.getXh());
				    ParseUtil.setNodeText(r2,"案号",yaqk.getXh());
				    ParseUtil.setNodeText(r2,"经办法院代码",yaqk.getJbfydm());
				    ParseUtil.setNodeText(r2,"经办法院名称",yaqk.getJbfymc());
			    }
		    }
		    
		    if(ksqkList.size()>0) {
		    	Element node3 = root.addElement("抗诉情况");
			    ParseUtil.setNodeText(node3,"编号",ksqkList.get(0).getBh());
			    ParseUtil.setNodeText(node3,"案件编号",ksqkList.get(0).getAjbh());
			    ParseUtil.setNodeText(node3,"抗诉机关",ksqkList.get(0).getKsjg());
			    ParseUtil.setNodeText(node3,"提出抗诉日期",ParseUtil.parseDate(ksqkList.get(0).getTcksrq()));
		    }
		    
		    if(tsxxList.size()>0) {
		    	Element node4 = root.addElement("庭审信息");
			    for(data.exchange.center.service.sfgk.domain.ms.Tsxx tsxx:tsxxList) {
			    	Element r4 = node4.addElement("R");
				    ParseUtil.setNodeText(r4,"编号",tsxx.getBh());
				    ParseUtil.setNodeText(r4,"案件编号",tsxx.getAjbh());
				    ParseUtil.setNodeText(r4,"序号",tsxx.getXh());
				    ParseUtil.setNodeText(r4,"法庭用途代码",tsxx.getFtytdm());
				    ParseUtil.setNodeText(r4,"法庭用途名称",tsxx.getFtytmc());
				    ParseUtil.setNodeText(r4,"开始时间",ParseUtil.parseDateTime(tsxx.getKssj()));
				    ParseUtil.setNodeText(r4,"结束时间",ParseUtil.parseDateTime(tsxx.getJssj()));
				    ParseUtil.setNodeText(r4,"地点",tsxx.getDd());
				    ParseUtil.setNodeText(r4,"是否公开开庭代码",tsxx.getSfgkktdm());
				    ParseUtil.setNodeText(r4,"是否公开开庭名称",tsxx.getSfgkktmc());
			    }
		    }
		    
		    
		    if(ycsxList.size()>0) {
		    	Element node5 = root.addElement("延长审限");
			    for(data.exchange.center.service.sfgk.domain.ms.Ycsx ycxx:ycsxList) {
			    	Element r5 = node5.addElement("R");
				    ParseUtil.setNodeText(r5,"编号",ycxx.getBh());
				    ParseUtil.setNodeText(r5,"案件编号",ycxx.getAjbh());
				    ParseUtil.setNodeText(r5,"序号",ycxx.getXh());
				    ParseUtil.setNodeText(r5,"申请日期",ParseUtil.parseDate(ycxx.getSqrq()));
				    ParseUtil.setNodeText(r5,"申请事由或原因代码",ycxx.getSqsyhyydm());
				    ParseUtil.setNodeText(r5,"申请事由或原因名称",ycxx.getSqsyhyymc());
				    ParseUtil.setNodeText(r5,"延长期间代码",ycxx.getYcqjdm());
				    ParseUtil.setNodeText(r5,"延长期间名称",ycxx.getYcqjmc());
				    ParseUtil.setNodeText(r5,"审批意见",ycxx.getSpyj());
				    ParseUtil.setNodeText(r5,"开始日期",ParseUtil.parseDate(ycxx.getKsrq()));
			    }
		    }
		    
		    
		    if(kcsxList.size()>0) {
		    	Element node6 = root.addElement("扣除审限");
			    for(data.exchange.center.service.sfgk.domain.ms.Kcsx kcsx:kcsxList) {
			    	Element r6 = node6.addElement("R");
				    ParseUtil.setNodeText(r6,"编号",kcsx.getBh());
				    ParseUtil.setNodeText(r6,"案件编号",kcsx.getAjbh());
				    ParseUtil.setNodeText(r6,"序号",kcsx.getXh());
				    ParseUtil.setNodeText(r6,"扣除审限类型代码",kcsx.getKcsxlxdm());
				    ParseUtil.setNodeText(r6,"扣除审限类型名称",kcsx.getKcsxlxmc());
				    ParseUtil.setNodeText(r6,"扣除审限事由代码",kcsx.getKcsxsydm());
				    ParseUtil.setNodeText(r6,"扣除审限事由名称",kcsx.getKcsxsymc());
				    ParseUtil.setNodeText(r6,"起始日期",ParseUtil.parseDate(kcsx.getQsrq()));
				    ParseUtil.setNodeText(r6,"结束日期",ParseUtil.parseDate(kcsx.getJsrq()));
			    }
		    }
		    
		    
		    if(zzhfList.size()>0) {
		    	Element node7 = root.addElement("中止恢复");
			    for(data.exchange.center.service.sfgk.domain.ms.Zzhf zzhf:zzhfList) {
			    	Element r7 = node7.addElement("R");
				    ParseUtil.setNodeText(r7,"编号",zzhf.getBh());
				    ParseUtil.setNodeText(r7,"案件编号",zzhf.getAjbh());
				    ParseUtil.setNodeText(r7,"序号",zzhf.getXh());
				    ParseUtil.setNodeText(r7,"中止日期",ParseUtil.parseDate(zzhf.getZzrq()));
				    ParseUtil.setNodeText(r7,"中止事由代码",zzhf.getZzsydm());
				    ParseUtil.setNodeText(r7,"中止事由名称",zzhf.getZzsymc());
				    ParseUtil.setNodeText(r7,"恢复日期",ParseUtil.parseDate(zzhf.getHfrq()));
			    }
		    }
		    
		    
		    if(ssfjmhxxList.size()>0) {
		    	Element node8 = root.addElement("诉讼费减免缓信息");
			    for(Ssfjmhxx ssfjmhxx:ssfjmhxxList) {
			    	Element r8 = node8.addElement("R");
				    ParseUtil.setNodeText(r8,"编号",ssfjmhxx.getBh());
				    ParseUtil.setNodeText(r8,"案件编号",ssfjmhxx.getAjbh());
				    ParseUtil.setNodeText(r8,"序号",ssfjmhxx.getXh());
				    ParseUtil.setNodeText(r8,"诉讼费救助类型代码",ssfjmhxx.getSsfjzlxdm());
				    ParseUtil.setNodeText(r8,"诉讼费救助类型名称",ssfjmhxx.getSsfjzlxmc());
				    ParseUtil.setNodeText(r8,"救助申请人",ssfjmhxx.getJzsqr());
				    ParseUtil.setNodeText(r8,"救助申请日期",ParseUtil.parseDate(ssfjmhxx.getJzsqrq()));
				    ParseUtil.setNodeText(r8,"申请减交数额",ssfjmhxx.getSqjjse());
				    ParseUtil.setNodeText(r8,"申请缓交期限",ssfjmhxx.getSqhjqx());
				    ParseUtil.setNodeText(r8,"审批意见",ssfjmhxx.getSpyj());
				    ParseUtil.setNodeText(r8,"审批日期",ParseUtil.parseDate(ssfjmhxx.getSpyj()));
			    }
		    }
		    
		    
		    if(dsrList.size()>0) {
		    	Element node9 = root.addElement("当事人");
			    for(Dsr dsr:dsrList) {
			    	Element r9 = node9.addElement("R");
				    ParseUtil.setNodeText(r9,"编号",dsr.getBh());
				    ParseUtil.setNodeText(r9,"案件编号",dsr.getAjbh());
				    ParseUtil.setNodeText(r9,"序号",dsr.getXh());
				    ParseUtil.setNodeText(r9,"当事人类型代码",dsr.getDsrlxdm());
				    ParseUtil.setNodeText(r9,"当事人类型名称",dsr.getDsrlxmc());
				    ParseUtil.setNodeText(r9,"诉讼地位代码",dsr.getSsdwdm());
				    ParseUtil.setNodeText(r9,"诉讼地位名称",dsr.getSsdwmc());
				    ParseUtil.setNodeText(r9,"名称",dsr.getMc());
				    ParseUtil.setNodeText(r9,"法定代表人",dsr.getFddbr());
			    }
		    }
		    
		    
		    if(spzzcyList.size()>0) {
		    	Element node10 = root.addElement("审判组织成员");
			    for(data.exchange.center.service.sfgk.domain.ms.Spzzcy spzzcy:spzzcyList) {
			    	Element r10 = node10.addElement("R");
			    	ParseUtil.setNodeText(r10,"编号",spzzcy.getBh());
			    	ParseUtil.setNodeText(r10,"案件编号",spzzcy.getAjbh());
			    	ParseUtil.setNodeText(r10,"序号",spzzcy.getXh());
			    	ParseUtil.setNodeText(r10,"姓名",spzzcy.getXm());
			    	ParseUtil.setNodeText(r10,"角色代码",spzzcy.getJsdm());
			    	ParseUtil.setNodeText(r10,"角色名称",spzzcy.getJsmc());
			    	ParseUtil.setNodeText(r10,"联系电话",spzzcy.getLxdh());
			    }
		    }
		    
		    if(ssdbxxList.size()>0) {
		    	Element node11 = root.addElement("诉讼担保信息");
			    for(Ssdbxx ssdbxx:ssdbxxList) {
			    	Element r11 = node11.addElement("R");
				    ParseUtil.setNodeText(r11,"编号",ssdbxx.getBh());
				    ParseUtil.setNodeText(r11,"案件编号",ssdbxx.getAjbh());
				    ParseUtil.setNodeText(r11,"序号",ssdbxx.getXh());
				    ParseUtil.setNodeText(r11,"当事人",ssdbxx.getDsr());
				    ParseUtil.setNodeText(r11,"事由代码",ssdbxx.getSydm());
				    ParseUtil.setNodeText(r11,"事由名称",ssdbxx.getSymc());
				    ParseUtil.setNodeText(r11,"担保人",ssdbxx.getDbr());
				    ParseUtil.setNodeText(r11,"与当事人关系代码",ssdbxx.getYdsrgxdm());
				    ParseUtil.setNodeText(r11,"与当事人关系名称",ssdbxx.getYdsrgxmc());
				    ParseUtil.setNodeText(r11,"担保方式代码",ssdbxx.getDbfsdm());
				    ParseUtil.setNodeText(r11,"担保方式名称",ssdbxx.getDbfsmc());
			    }
		    }
		    
		    
		    if(zjxxList.size()>0) {
		    	Element node12 = root.addElement("证据信息");
			    for(data.exchange.center.service.sfgk.domain.ms.Zjxx zjxx:zjxxList) {
			    	Element r12 = node12.addElement("R");
			    	ParseUtil.setNodeText(r12,"编号",zjxx.getBh());
			    	ParseUtil.setNodeText(r12,"案件编号",zjxx.getAjbh());
			    	ParseUtil.setNodeText(r12,"序号",zjxx.getXh());
			    	ParseUtil.setNodeText(r12,"证据名称",zjxx.getZjmc());
			    	ParseUtil.setNodeText(r12,"证据类型代码",zjxx.getZjlxdm());
			    	ParseUtil.setNodeText(r12,"证据类型名称",zjxx.getZjlxmc());
			    }
		    }
		    
		    if(pcglrList.size()>0) {
		    	Element node13 = root.addElement("破产管理人");
			    for(Pcglr pcglr:pcglrList) {
			    	Element r13 = node13.addElement("R");
				    ParseUtil.setNodeText(r13,"编号",pcglr.getBh());
				    ParseUtil.setNodeText(r13,"案件编号",pcglr.getAjbh());
				    ParseUtil.setNodeText(r13,"序号",pcglr.getXh());
				    ParseUtil.setNodeText(r13,"管理人类型代码",pcglr.getGlrlxdm());
				    ParseUtil.setNodeText(r13,"管理人类型名称",pcglr.getGlrlxmc());
				    ParseUtil.setNodeText(r13,"担任管理人职务代码",pcglr.getDrglrzwdm());
				    ParseUtil.setNodeText(r13,"担任管理人职务名称",pcglr.getGlrlxmc());
				    ParseUtil.setNodeText(r13,"破产管理人",pcglr.getPcglr());
				    ParseUtil.setNodeText(r13,"管理人指定日期",ParseUtil.parseDate(pcglr.getGlrzdrq()));
			    }
		    }
		    
			
		    OutputFormat format = OutputFormat.createPrettyPrint();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ByteArrayOutputStream makeXzXml(Map<String, Object> table) {
		try {
			List<Xzajjbxx>   xzajjbxxList   = (List<Xzajjbxx>)   table.get(Code.V_CURSOR_1);
			List<Ssqk>       ssqkList       = (List<Ssqk>)       table.get(Code.V_CURSOR_4);
			List<Bgxzxw>     bgxzxwList     = (List<Bgxzxw>)     table.get(Code.V_CURSOR_10);
			List<Ssbqhxyzx>  ssbqhxyzxList  = (List<Ssbqhxyzx>)  table.get(Code.V_CURSOR_12);
			List<Fyxx>       fyxxList       = (List<Fyxx>)       table.get(Code.V_CURSOR_15);
			List<Sdxx>       sdxxList       = (List<Sdxx>)       table.get(Code.V_CURSOR_16);
			List<data.exchange.center.service.sfgk.domain.xz.Yaqk>      yaqkList       = (List<data.exchange.center.service.sfgk.domain.xz.Yaqk>)      table.get(Code.V_CURSOR_2);
			List<data.exchange.center.service.sfgk.domain.xz.Ksqk>      ksqkList       = (List<data.exchange.center.service.sfgk.domain.xz.Ksqk>)      table.get(Code.V_CURSOR_3);
			List<data.exchange.center.service.sfgk.domain.xz.Tsxx>      tsxxList       = (List<data.exchange.center.service.sfgk.domain.xz.Tsxx>)      table.get(Code.V_CURSOR_5);
			List<data.exchange.center.service.sfgk.domain.xz.Ycsx>      ycsxList       = (List<data.exchange.center.service.sfgk.domain.xz.Ycsx>)      table.get(Code.V_CURSOR_6);
			List<data.exchange.center.service.sfgk.domain.xz.Kcsx>      kcsxList       = (List<data.exchange.center.service.sfgk.domain.xz.Kcsx>)     table.get(Code.V_CURSOR_7);
			List<data.exchange.center.service.sfgk.domain.xz.Ssfjmhxx>  ssfjmhxxList   = (List<data.exchange.center.service.sfgk.domain.xz.Ssfjmhxx>)     table.get(Code.V_CURSOR_8);
			List<data.exchange.center.service.sfgk.domain.xz.Dsr>       dsrList        = (List<data.exchange.center.service.sfgk.domain.xz.Dsr>)     table.get(Code.V_CURSOR_9);
			List<data.exchange.center.service.sfgk.domain.xz.Spzzcy>    spzzcyList     = (List<data.exchange.center.service.sfgk.domain.xz.Spzzcy>) table.get(Code.V_CURSOR_11);
			List<data.exchange.center.service.sfgk.domain.xz.Ssdbxx>    ssdbxxList     = (List<data.exchange.center.service.sfgk.domain.xz.Ssdbxx>)      table.get(Code.V_CURSOR_13);
			List<data.exchange.center.service.sfgk.domain.xz.Zjxx>      zjxxList       = (List<data.exchange.center.service.sfgk.domain.xz.Zjxx>)      table.get(Code.V_CURSOR_14);
			
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("行政案件","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		    Element node1 = root.addElement("行政案件基本信息");
		    ParseUtil.setNodeText(node1,"唯一主键编号",xzajjbxxList.get(0).getWyzjbh());
		    ParseUtil.setNodeText(node1,"案件标识",xzajjbxxList.get(0).getAjbs());
		    ParseUtil.setNodeText(node1,"案号",xzajjbxxList.get(0).getAh());
		    ParseUtil.setNodeText(node1,"案件类型代码",xzajjbxxList.get(0).getAjlxdm());
		    ParseUtil.setNodeText(node1,"案件类型名称",xzajjbxxList.get(0).getAjlxmc());
		    ParseUtil.setNodeText(node1,"案件类型标识代码",xzajjbxxList.get(0).getAjlxbsdm());
		    ParseUtil.setNodeText(node1,"案件类型标识名称",xzajjbxxList.get(0).getAjlxbsmc());
		    ParseUtil.setNodeText(node1,"经办法院代码",xzajjbxxList.get(0).getJbfydm());
		    ParseUtil.setNodeText(node1,"经办法院名称",xzajjbxxList.get(0).getJbfymc());
		    ParseUtil.setNodeText(node1,"审判程序代码",xzajjbxxList.get(0).getSpcxdm());
		    ParseUtil.setNodeText(node1,"审判程序名称",xzajjbxxList.get(0).getSpcxmc());
		    ParseUtil.setNodeText(node1,"适用程序代码",xzajjbxxList.get(0).getSycxdm());
		    ParseUtil.setNodeText(node1,"适用程序名称",xzajjbxxList.get(0).getSycxmc());
		    ParseUtil.setNodeText(node1,"案件来源代码",xzajjbxxList.get(0).getAjlydm());
		    ParseUtil.setNodeText(node1,"案件来源名称",xzajjbxxList.get(0).getAjlymc());
		    ParseUtil.setNodeText(node1,"新案件来源代码",xzajjbxxList.get(0).getXajlydm());
		    ParseUtil.setNodeText(node1,"新案件来源名称",xzajjbxxList.get(0).getXajlymc());
		    ParseUtil.setNodeText(node1,"立案日期",ParseUtil.parseDate(xzajjbxxList.get(0).getLarq()));
		    ParseUtil.setNodeText(node1,"立案案由代码",xzajjbxxList.get(0).getLaaydm());
		    ParseUtil.setNodeText(node1,"立案案由名称",xzajjbxxList.get(0).getLaaymc());
		    ParseUtil.setNodeText(node1,"提起行政赔偿代码",xzajjbxxList.get(0).getTqxzpcdm());
		    ParseUtil.setNodeText(node1,"提起行政赔偿名称",xzajjbxxList.get(0).getTqxzpcmc());
		    ParseUtil.setNodeText(node1,"标的物",xzajjbxxList.get(0).getBdw());
		    ParseUtil.setNodeText(node1,"标的行为名称",xzajjbxxList.get(0).getBdxwmc());
		    ParseUtil.setNodeText(node1,"标的行为代码",xzajjbxxList.get(0).getBdxwdm());
		    ParseUtil.setNodeText(node1,"诉讼标的额",xzajjbxxList.get(0).getSsbde());
		    ParseUtil.setNodeText(node1,"行政管理范围代码",xzajjbxxList.get(0).getXzglfwdm());
		    ParseUtil.setNodeText(node1,"行政管理范围名称",xzajjbxxList.get(0).getXzglfwmc());
		    ParseUtil.setNodeText(node1,"案件进展阶段代码",xzajjbxxList.get(0).getAjjzjddm());
		    ParseUtil.setNodeText(node1,"案件进展阶段名称",xzajjbxxList.get(0).getAjjzjdmc());
		    ParseUtil.setNodeText(node1,"公开状态代码",xzajjbxxList.get(0).getGkztdm());
		    ParseUtil.setNodeText(node1,"公开状态名称",xzajjbxxList.get(0).getGkztmc());
		    ParseUtil.setNodeText(node1,"公开日期",ParseUtil.parseDate(xzajjbxxList.get(0).getGkrq()));
		    ParseUtil.setNodeText(node1,"撤销日期",ParseUtil.parseDate(xzajjbxxList.get(0).getCxrq()));
		    ParseUtil.setNodeText(node1,"有效状态代码",xzajjbxxList.get(0).getYxztdm());
		    ParseUtil.setNodeText(node1,"有效状态名称",xzajjbxxList.get(0).getYxztmc());
		    ParseUtil.setNodeText(node1,"承办审判庭",xzajjbxxList.get(0).getCbspt());
		    ParseUtil.setNodeText(node1,"承办人",xzajjbxxList.get(0).getCbr());
		    ParseUtil.setNodeText(node1,"法定审限天数",xzajjbxxList.get(0).getFdsxts());
		    ParseUtil.setNodeText(node1,"审限届满日期",ParseUtil.parseDate(xzajjbxxList.get(0).getSxjmrq()));
		    ParseUtil.setNodeText(node1,"结案日期",ParseUtil.parseDate(xzajjbxxList.get(0).getJarq()));
		    ParseUtil.setNodeText(node1,"结案案由代码",xzajjbxxList.get(0).getJaaydm());
		    ParseUtil.setNodeText(node1,"结案案由名称",xzajjbxxList.get(0).getJaaymc());
		    ParseUtil.setNodeText(node1,"结案方式代码",xzajjbxxList.get(0).getJafsdm());
		    ParseUtil.setNodeText(node1,"结案方式名称",xzajjbxxList.get(0).getJafsmc());
		    ParseUtil.setNodeText(node1,"新结案方式代码",xzajjbxxList.get(0).getXjafsdm());
		    ParseUtil.setNodeText(node1,"新结案方式名称",xzajjbxxList.get(0).getXjafsmc());
		    ParseUtil.setNodeText(node1,"创建时间",ParseUtil.parseDateTime(xzajjbxxList.get(0).getCjsj()));
		    ParseUtil.setNodeText(node1,"创建人",xzajjbxxList.get(0).getCjr());
		    ParseUtil.setNodeText(node1,"更新时间",ParseUtil.parseDateTime(xzajjbxxList.get(0).getGxsj()));
		    ParseUtil.setNodeText(node1,"更新人",xzajjbxxList.get(0).getGxr());

		    if(yaqkList.size()>0) {
		    	Element node2 = root.addElement("原案情况");
			    for(data.exchange.center.service.sfgk.domain.xz.Yaqk yaqk:yaqkList) {
			    	Element r2 = node2.addElement("R");
				    ParseUtil.setNodeText(r2,"编号",yaqk.getBh());
				    ParseUtil.setNodeText(r2,"案件编号",yaqk.getAjbh());
				    ParseUtil.setNodeText(r2,"序号",yaqk.getXh());
				    ParseUtil.setNodeText(r2,"案号",yaqk.getAh());
				    ParseUtil.setNodeText(r2,"经办法院代码",yaqk.getJbfydm());
				    ParseUtil.setNodeText(r2,"经办法院名称",yaqk.getJbfymc());
			    }
		    }
		    
		    if(ksqkList.size()>0) {
		    	Element node3 = root.addElement("抗诉情况");
			    ParseUtil.setNodeText(node3,"编号",ksqkList.get(0).getBh());
			    ParseUtil.setNodeText(node3,"案件编号",ksqkList.get(0).getAjbh());
			    ParseUtil.setNodeText(node3,"抗诉机关",ksqkList.get(0).getKsjg());
			    ParseUtil.setNodeText(node3,"提出抗诉日期",ParseUtil.parseDate(ksqkList.get(0).getTcksrq()));
		    }
		    
		    if(ssqkList.size()>0) {
		    	Element node4 = root.addElement("上诉情况");
			    ParseUtil.setNodeText(node4,"编号",ssqkList.get(0).getBh());
			    ParseUtil.setNodeText(node4,"案件编号",ssqkList.get(0).getAjbh());
			    ParseUtil.setNodeText(node4,"上诉人",ssqkList.get(0).getSsr());
			    ParseUtil.setNodeText(node4,"提出上诉日期",ParseUtil.parseDate(ssqkList.get(0).getTcssrq()));
		    }
		    
		    if(tsxxList.size()>0) {
		    	Element node5 = root.addElement("庭审信息");
			    for(data.exchange.center.service.sfgk.domain.xz.Tsxx tsxx:tsxxList) {
			    	Element r5 = node5.addElement("R");
				    ParseUtil.setNodeText(r5,"编号",tsxx.getBh());
				    ParseUtil.setNodeText(r5,"案件编号",tsxx.getAjbh());
				    ParseUtil.setNodeText(r5,"序号",tsxx.getXh());
				    ParseUtil.setNodeText(r5,"法庭用途代码",tsxx.getFtytdm());
				    ParseUtil.setNodeText(r5,"法庭用途名称",tsxx.getFtytmc());
				    ParseUtil.setNodeText(r5,"开始时间",ParseUtil.parseDateTime(tsxx.getKssj()));
				    ParseUtil.setNodeText(r5,"结束时间",ParseUtil.parseDateTime(tsxx.getJssj()));
				    ParseUtil.setNodeText(r5,"地点",tsxx.getDd());
				    ParseUtil.setNodeText(r5,"是否公开开庭代码",tsxx.getSfgkktdm());
				    ParseUtil.setNodeText(r5,"是否公开开庭名称",tsxx.getSfgkktmc());
			    }
		    }
		    
		    if(ycsxList.size()>0) {
		    	Element node6 = root.addElement("延长审限");
			    for(data.exchange.center.service.sfgk.domain.xz.Ycsx ycsx:ycsxList) {
			    	Element r6 = node6.addElement("R");
				    ParseUtil.setNodeText(r6,"编号",ycsx.getBh());
				    ParseUtil.setNodeText(r6,"案件编号",ycsx.getAjbh());
				    ParseUtil.setNodeText(r6,"序号",ycsx.getXh());
				    ParseUtil.setNodeText(r6,"申请日期",ParseUtil.parseDate(ycsx.getSqrq()));
				    ParseUtil.setNodeText(r6,"申请事由或原因代码",ycsx.getSqsyhyydm());
				    ParseUtil.setNodeText(r6,"申请事由或原因名称",ycsx.getSqsyhyymc());
				    ParseUtil.setNodeText(r6,"延长期间代码",ycsx.getYcqjdm());
				    ParseUtil.setNodeText(r6,"延长期间名称",ycsx.getYcqjmc());
				    ParseUtil.setNodeText(r6,"审批意见",ycsx.getSpyj());
				    ParseUtil.setNodeText(r6,"开始日期",ParseUtil.parseDate(ycsx.getKsrq()));
			    }
		    }
		    
		    if(kcsxList.size()>0) {
		    	Element node7 = root.addElement("扣除审限");
			    for(data.exchange.center.service.sfgk.domain.xz.Kcsx kcsx:kcsxList) {
			    	Element r7 = node7.addElement("R");
				    ParseUtil.setNodeText(r7,"编号",kcsx.getBh());
				    ParseUtil.setNodeText(r7,"案件编号",kcsx.getAjbh());
				    ParseUtil.setNodeText(r7,"序号",kcsx.getXh());
				    ParseUtil.setNodeText(r7,"扣除审限类型代码",kcsx.getKcsxlxdm());
				    ParseUtil.setNodeText(r7,"扣除审限类型名称",kcsx.getKcsxlxmc());
				    ParseUtil.setNodeText(r7,"扣除审限事由代码",kcsx.getKcsxsydm());
				    ParseUtil.setNodeText(r7,"扣除审限事由名称",kcsx.getKcsxsymc());
				    ParseUtil.setNodeText(r7,"起始日期",ParseUtil.parseDate(kcsx.getQsrq()));
				    ParseUtil.setNodeText(r7,"结束日期",ParseUtil.parseDate(kcsx.getJsrq()));
			    }
		    }
		    
		    if(ssfjmhxxList.size()>0) {
		    	Element node8 = root.addElement("诉讼费减免缓信息");
			    for(data.exchange.center.service.sfgk.domain.xz.Ssfjmhxx ssfjmhxx:ssfjmhxxList) {
			    	Element r8 = node8.addElement("R");
				    ParseUtil.setNodeText(r8,"编号",ssfjmhxx.getBh());
				    ParseUtil.setNodeText(r8,"案件编号",ssfjmhxx.getAjbh());
				    ParseUtil.setNodeText(r8,"序号",ssfjmhxx.getXh());
				    ParseUtil.setNodeText(r8,"诉讼费救助类型代码",ssfjmhxx.getSsfjzlxdm());
				    ParseUtil.setNodeText(r8,"诉讼费救助类型名称",ssfjmhxx.getSsfjzlxmc());
				    ParseUtil.setNodeText(r8,"救助申请人",ssfjmhxx.getJzsqr());
				    ParseUtil.setNodeText(r8,"救助申请日期",ParseUtil.parseDate(ssfjmhxx.getJzsqrq()));
				    ParseUtil.setNodeText(r8,"申请减交数额",ssfjmhxx.getSqjjse());
				    ParseUtil.setNodeText(r8,"申请缓交期限",ssfjmhxx.getSqhjqx());
				    ParseUtil.setNodeText(r8,"审批意见",ssfjmhxx.getSpyj());
				    ParseUtil.setNodeText(r8,"审批日期",ParseUtil.parseDate(ssfjmhxx.getSprq()));
			    }
		    }
		    
		    if(dsrList.size()>0) {
		    	Element node9 = root.addElement("当事人");
		    	for(data.exchange.center.service.sfgk.domain.xz.Dsr dsr:dsrList) {
		    		Element r9 = node9.addElement("R");
				    ParseUtil.setNodeText(r9,"编号",dsr.getBh());
				    ParseUtil.setNodeText(r9,"案件编号",dsr.getAjbh());
				    ParseUtil.setNodeText(r9,"序号",dsr.getXh());
				    ParseUtil.setNodeText(r9,"当事人类型代码",dsr.getDsrlxdm());
				    ParseUtil.setNodeText(r9,"当事人类型名称",dsr.getDsrlxmc());
				    ParseUtil.setNodeText(r9,"名称",dsr.getMc());
				    ParseUtil.setNodeText(r9,"诉讼地位代码",dsr.getSsdwdm());
				    ParseUtil.setNodeText(r9,"诉讼地位名称",dsr.getSsdwmc());
				    ParseUtil.setNodeText(r9,"法定代表人",dsr.getFddbr());
				    ParseUtil.setNodeText(r9,"地址",dsr.getDz());
				    ParseUtil.setNodeText(r9,"提起赔偿金额",dsr.getTqpcje());
		    	}
		    }
		    
		    if(bgxzxwList.size()>0) {
		    	Element node10 = root.addElement("被告行政行为");
			    for(data.exchange.center.service.sfgk.domain.xz.Bgxzxw bgxzxw:bgxzxwList) {
			    	Element r10 = node10.addElement("R");
				    ParseUtil.setNodeText(r10,"编号",bgxzxw.getBh());
				    ParseUtil.setNodeText(r10,"案件编号",bgxzxw.getAjbh());
				    ParseUtil.setNodeText(r10,"序号",bgxzxw.getXh());
				    ParseUtil.setNodeText(r10,"被告",bgxzxw.getBg());
				    ParseUtil.setNodeText(r10,"被告类型代码",bgxzxw.getBglxdm());
				    ParseUtil.setNodeText(r10,"被告类型名称",bgxzxw.getBglxmc());
				    ParseUtil.setNodeText(r10,"行政行为种类代码",bgxzxw.getXzxwzldm());
				    ParseUtil.setNodeText(r10,"行政行为种类名称",bgxzxw.getXzxwzlmc());
				    ParseUtil.setNodeText(r10,"做出行政行为日期",ParseUtil.parseDate(bgxzxw.getZcxzxwrq()));
				    ParseUtil.setNodeText(r10,"赔偿处理代码",bgxzxw.getPccldm());
				    ParseUtil.setNodeText(r10,"赔偿处理名称",bgxzxw.getPcclmc());
				    ParseUtil.setNodeText(r10,"赔偿方式代码",bgxzxw.getPcfsdm());
				    ParseUtil.setNodeText(r10,"赔偿方式名称",bgxzxw.getPcfsmc());
				    ParseUtil.setNodeText(r10,"赔偿金额",bgxzxw.getPcje());
			    }
		    }
		    
		    if(spzzcyList.size()>0) {
		    	Element node11 = root.addElement("审判组织成员");
			    for(data.exchange.center.service.sfgk.domain.xz.Spzzcy spzzcy:spzzcyList) {
			    	Element r11 = node11.addElement("R");
				    ParseUtil.setNodeText(r11,"编号",spzzcy.getBh());
				    ParseUtil.setNodeText(r11,"案件编号",spzzcy.getAjbh());
				    ParseUtil.setNodeText(r11,"序号",spzzcy.getXh());
				    ParseUtil.setNodeText(r11,"姓名",spzzcy.getXm());
				    ParseUtil.setNodeText(r11,"角色代码",spzzcy.getJsdm());
				    ParseUtil.setNodeText(r11,"角色名称",spzzcy.getJsmc());
				    ParseUtil.setNodeText(r11,"联系电话",spzzcy.getLxdh());
			    }
		    }
		    
		    if(ssbqhxyzxList.size()>0) {
		    	Element node12 = root.addElement("诉讼保全和先予执行");
			    for(data.exchange.center.service.sfgk.domain.xz.Ssbqhxyzx ssbqhxyzx:ssbqhxyzxList) {
			    	Element r12 = node12.addElement("R");
				    ParseUtil.setNodeText(r12,"编号",ssbqhxyzx.getBh());
				    ParseUtil.setNodeText(r12,"案件编号",ssbqhxyzx.getAjbh());
				    ParseUtil.setNodeText(r12,"序号",ssbqhxyzx.getXh());
				    ParseUtil.setNodeText(r12,"类型代码",ssbqhxyzx.getLxdm());
				    ParseUtil.setNodeText(r12,"类型名称",ssbqhxyzx.getLxmc());
				    ParseUtil.setNodeText(r12,"保全发动方代码",ssbqhxyzx.getBqfdfdm());
				    ParseUtil.setNodeText(r12,"保全发动方名称",ssbqhxyzx.getBqfdfmc());
				    ParseUtil.setNodeText(r12,"申请人",ssbqhxyzx.getSqr());
				    ParseUtil.setNodeText(r12,"被申请人",ssbqhxyzx.getBsqr());
				    ParseUtil.setNodeText(r12,"申请日期",ParseUtil.parseDate(ssbqhxyzx.getSqrq()));
				    ParseUtil.setNodeText(r12,"申请金额",ssbqhxyzx.getSqje());
				    ParseUtil.setNodeText(r12,"申请财产",ssbqhxyzx.getSqcc());
				    ParseUtil.setNodeText(r12,"裁定日期",ParseUtil.parseDate(ssbqhxyzx.getCdrq()));
				    ParseUtil.setNodeText(r12,"裁定结果代码",ssbqhxyzx.getCdjgdm());
				    ParseUtil.setNodeText(r12,"裁定结果名称",ssbqhxyzx.getCdjgmc());
				    ParseUtil.setNodeText(r12,"保全措施代码",ssbqhxyzx.getBqcsdm());
				    ParseUtil.setNodeText(r12,"保全措施名称",ssbqhxyzx.getBqcsmc());
				    ParseUtil.setNodeText(r12,"先予执行事由代码",ssbqhxyzx.getXyzxsydm());
				    ParseUtil.setNodeText(r12,"先予执行事由名称",ssbqhxyzx.getXyzxsymc());
				    ParseUtil.setNodeText(r12,"执行日期",ParseUtil.parseDate(ssbqhxyzx.getZxrq()));
				    ParseUtil.setNodeText(r12,"实际执行金额",ssbqhxyzx.getSjzxje());
				    ParseUtil.setNodeText(r12,"解除保全日期",ParseUtil.parseDate(ssbqhxyzx.getJcbqrq()));
				    ParseUtil.setNodeText(r12,"解除保全原因代码",ssbqhxyzx.getJcbqyydm());
				    ParseUtil.setNodeText(r12,"解除保全原因名称",ssbqhxyzx.getJcbqyymc());
			    }
		    }
		    
		    if(ssdbxxList.size()>0) {
		    	Element node13 = root.addElement("诉讼担保信息");
			    for(data.exchange.center.service.sfgk.domain.xz.Ssdbxx ssdbxx:ssdbxxList) {
			    	Element r13 = node13.addElement("R");
				    ParseUtil.setNodeText(r13,"编号",ssdbxx.getBh());
				    ParseUtil.setNodeText(r13,"案件编号",ssdbxx.getAjbh());
				    ParseUtil.setNodeText(r13,"序号",ssdbxx.getXh());
				    ParseUtil.setNodeText(r13,"当事人",ssdbxx.getDsr());
				    ParseUtil.setNodeText(r13,"事由代码",ssdbxx.getSydm());
				    ParseUtil.setNodeText(r13,"事由名称",ssdbxx.getSymc());
				    ParseUtil.setNodeText(r13,"担保人",ssdbxx.getDbr());
				    ParseUtil.setNodeText(r13,"与当事人关系代码",ssdbxx.getYdsrgxdm());
				    ParseUtil.setNodeText(r13,"与当事人关系名称",ssdbxx.getYdsrgxmc());
				    ParseUtil.setNodeText(r13,"担保方式代码",ssdbxx.getDbfsdm());
				    ParseUtil.setNodeText(r13,"担保方式名称",ssdbxx.getDbfsmc());
			    }
		    }
		    
		    
		    if(zjxxList.size()>0) {
		    	Element node14 = root.addElement("证据信息");
			    for(data.exchange.center.service.sfgk.domain.xz.Zjxx zjxx:zjxxList) {
			    	Element r14 = node14.addElement("R");
				    ParseUtil.setNodeText(r14,"编号",zjxx.getBh());
				    ParseUtil.setNodeText(r14,"案件编号",zjxx.getAjbh());
				    ParseUtil.setNodeText(r14,"序号",zjxx.getXh());
				    ParseUtil.setNodeText(r14,"证据名称",zjxx.getZjmc());
				    ParseUtil.setNodeText(r14,"证据类型代码",zjxx.getZjlxdm());
				    ParseUtil.setNodeText(r14,"证据类型名称",zjxx.getZjlxmc());
			    }
		    }
		    
		    
		    if(fyxxList.size()>0) {
		    	Element node15 = root.addElement("复议信息");
			    for(data.exchange.center.service.sfgk.domain.xz.Fyxx fyxx:fyxxList) {
			    	Element r15 = node15.addElement("R");
				    ParseUtil.setNodeText(r15,"编号",fyxx.getBh());
				    ParseUtil.setNodeText(r15,"案件编号",fyxx.getAjbh());
				    ParseUtil.setNodeText(r15,"序号",fyxx.getXh());
				    ParseUtil.setNodeText(r15,"类型代码",fyxx.getLxdm());
				    ParseUtil.setNodeText(r15,"类型名称",fyxx.getLxmc());
				    ParseUtil.setNodeText(r15,"申请人",fyxx.getSqr());
				    ParseUtil.setNodeText(r15,"申请日期",ParseUtil.parseDate(fyxx.getSqrq()));
				    ParseUtil.setNodeText(r15,"处理日期",ParseUtil.parseDate(fyxx.getClrq()));
				    ParseUtil.setNodeText(r15,"处理结果代码",fyxx.getCljgdm());
				    ParseUtil.setNodeText(r15,"处理结果名称",fyxx.getCljgmc());
			    }
		    }
		    
		    if(sdxxList.size()>0) {
		    	Element node16 = root.addElement("送达信息");
			    for(data.exchange.center.service.sfgk.domain.xz.Sdxx sdxx:sdxxList) {
			    	Element r16 = node16.addElement("R");
				    ParseUtil.setNodeText(r16,"编号",sdxx.getBh());
				    ParseUtil.setNodeText(r16,"案件编号",sdxx.getAjbh());
				    ParseUtil.setNodeText(r16,"序号",sdxx.getXh());
				    ParseUtil.setNodeText(r16,"文书类型代码",sdxx.getWslxdm());
				    ParseUtil.setNodeText(r16,"文书类型名称",sdxx.getWslxmc());
				    ParseUtil.setNodeText(r16,"送达方式代码",sdxx.getSdfsdm());
				    ParseUtil.setNodeText(r16,"送达方式名称",sdxx.getSdfsmc());
				    ParseUtil.setNodeText(r16,"受送达人",sdxx.getSsdr());
				    ParseUtil.setNodeText(r16,"送达日期",ParseUtil.parseDate(sdxx.getSdrq()));
				    ParseUtil.setNodeText(r16,"签收时间",ParseUtil.parseDateTime(sdxx.getQssj()));
			    }
		    }
			
		    OutputFormat format = OutputFormat.createPrettyPrint();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ByteArrayOutputStream makePcXml(Map<String, Object> table) {
		try {
			List<Pcajjbxx>  pcajjbxxList   = (List<Pcajjbxx>)   table.get(Code.V_CURSOR_1);
			List<Pcdsr>     pcdsrList      = (List<Pcdsr>)      table.get(Code.V_CURSOR_7);
			List<Pcywjg>    pcywjgList     = (List<Pcywjg>)     table.get(Code.V_CURSOR_9);
			List<data.exchange.center.service.sfgk.domain.pc.Yaqk>      yaqkList      = (List<data.exchange.center.service.sfgk.domain.pc.Yaqk>)      table.get(Code.V_CURSOR_2);
			List<data.exchange.center.service.sfgk.domain.pc.Tsxx>      tsxxList      = (List<data.exchange.center.service.sfgk.domain.pc.Tsxx>)      table.get(Code.V_CURSOR_3);
			List<data.exchange.center.service.sfgk.domain.pc.Ycsx>      ycsxList      = (List<data.exchange.center.service.sfgk.domain.pc.Ycsx>)      table.get(Code.V_CURSOR_4);
			List<data.exchange.center.service.sfgk.domain.pc.Kcsx>      kcsxList      = (List<data.exchange.center.service.sfgk.domain.pc.Kcsx>)      table.get(Code.V_CURSOR_5);
			List<data.exchange.center.service.sfgk.domain.pc.Zjxx>      zjxxList      = (List<data.exchange.center.service.sfgk.domain.pc.Zjxx>)      table.get(Code.V_CURSOR_6);
			List<data.exchange.center.service.sfgk.domain.pc.Spzzcy>    spzzcyList    = (List<data.exchange.center.service.sfgk.domain.pc.Spzzcy>)    table.get(Code.V_CURSOR_8);
			List<data.exchange.center.service.sfgk.domain.pc.Zzhf>      zzhfList      = (List<data.exchange.center.service.sfgk.domain.pc.Zzhf>)      table.get(Code.V_CURSOR_10);
			
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("赔偿案件","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		    Element node1 = root.addElement("赔偿案件基本信息");
		    ParseUtil.setNodeText(node1,"唯一主键编号",pcajjbxxList.get(0).getWyzjbh());
		    ParseUtil.setNodeText(node1,"案件标识",pcajjbxxList.get(0).getAjbs());
		    ParseUtil.setNodeText(node1,"案号",pcajjbxxList.get(0).getAh());
		    ParseUtil.setNodeText(node1,"案件类型代码",pcajjbxxList.get(0).getAjlxdm());
		    ParseUtil.setNodeText(node1,"案件类型名称",pcajjbxxList.get(0).getAjlxmc());
		    ParseUtil.setNodeText(node1,"案件类型标识代码",pcajjbxxList.get(0).getAjlxbsdm());
		    ParseUtil.setNodeText(node1,"案件类型标识名称",pcajjbxxList.get(0).getAjlxbsmc());
		    ParseUtil.setNodeText(node1,"经办法院代码",pcajjbxxList.get(0).getJbfydm());
		    ParseUtil.setNodeText(node1,"经办法院名称",pcajjbxxList.get(0).getJbfymc());
		    ParseUtil.setNodeText(node1,"案件来源代码",pcajjbxxList.get(0).getAjlydm());
		    ParseUtil.setNodeText(node1,"案件来源名称",pcajjbxxList.get(0).getAjlymc());
		    ParseUtil.setNodeText(node1,"新案件来源代码",pcajjbxxList.get(0).getXajlydm());
		    ParseUtil.setNodeText(node1,"新案件来源名称",pcajjbxxList.get(0).getXajlymc());
		    ParseUtil.setNodeText(node1,"立案日期",ParseUtil.parseDate(pcajjbxxList.get(0).getLarq()));
		    ParseUtil.setNodeText(node1,"立案案由代码",pcajjbxxList.get(0).getLaaydm());
		    ParseUtil.setNodeText(node1,"立案案由名称",pcajjbxxList.get(0).getLaaymc());
		    ParseUtil.setNodeText(node1,"请求赔偿金额",pcajjbxxList.get(0).getQqpcje());
		    ParseUtil.setNodeText(node1,"案件进展阶段代码",pcajjbxxList.get(0).getAjjzjddm());
		    ParseUtil.setNodeText(node1,"案件进展阶段名称",pcajjbxxList.get(0).getAjjzjdmc());
		    ParseUtil.setNodeText(node1,"公开状态代码",pcajjbxxList.get(0).getGkztdm());
		    ParseUtil.setNodeText(node1,"公开状态名称",pcajjbxxList.get(0).getGkztmc());
		    ParseUtil.setNodeText(node1,"公开日期",ParseUtil.parseDate(pcajjbxxList.get(0).getGkrq()));
		    ParseUtil.setNodeText(node1,"撤销日期",ParseUtil.parseDate(pcajjbxxList.get(0).getCxrq()));
		    ParseUtil.setNodeText(node1,"有效状态代码",pcajjbxxList.get(0).getYxztdm());
		    ParseUtil.setNodeText(node1,"有效状态名称",pcajjbxxList.get(0).getYxztmc());
		    ParseUtil.setNodeText(node1,"承办审判庭",pcajjbxxList.get(0).getCbspt());
		    ParseUtil.setNodeText(node1,"承办人",pcajjbxxList.get(0).getCbr());
		    ParseUtil.setNodeText(node1,"法定审限天数",pcajjbxxList.get(0).getFdsxts());
		    ParseUtil.setNodeText(node1,"审限届满日期",ParseUtil.parseDate(pcajjbxxList.get(0).getSxjmrq()));
		    ParseUtil.setNodeText(node1,"结案日期",ParseUtil.parseDate(pcajjbxxList.get(0).getJarq()));
		    ParseUtil.setNodeText(node1,"结案案由代码",pcajjbxxList.get(0).getJaaydm());
		    ParseUtil.setNodeText(node1,"结案案由名称",pcajjbxxList.get(0).getJaaymc());
		    ParseUtil.setNodeText(node1,"结案方式代码",pcajjbxxList.get(0).getJafsdm());
		    ParseUtil.setNodeText(node1,"结案方式名称",pcajjbxxList.get(0).getJafsmc());
		    ParseUtil.setNodeText(node1,"新结案方式代码",pcajjbxxList.get(0).getXjafsdm());
		    ParseUtil.setNodeText(node1,"新结案方式名称",pcajjbxxList.get(0).getXjafsmc());
		    ParseUtil.setNodeText(node1,"赔偿方式代码",pcajjbxxList.get(0).getPcfsdm());
		    ParseUtil.setNodeText(node1,"赔偿方式名称",pcajjbxxList.get(0).getPcfsmc());
		    ParseUtil.setNodeText(node1,"创建时间",ParseUtil.parseDateTime(pcajjbxxList.get(0).getCjsj()));
		    ParseUtil.setNodeText(node1,"创建人",pcajjbxxList.get(0).getCjr());
		    ParseUtil.setNodeText(node1,"更新时间",ParseUtil.parseDateTime(pcajjbxxList.get(0).getGxsj()));
		    ParseUtil.setNodeText(node1,"更新人",pcajjbxxList.get(0).getGxr());

		    if(yaqkList.size()>0) {
		    	Element node2 = root.addElement("原案情况");
			    for(data.exchange.center.service.sfgk.domain.pc.Yaqk yaqk:yaqkList) {
			    	Element r2 = node2.addElement("R");
				    ParseUtil.setNodeText(r2,"编号",yaqk.getBh());
				    ParseUtil.setNodeText(r2,"案件编号",yaqk.getAjbh());
				    ParseUtil.setNodeText(r2,"序号",yaqk.getXh());
				    ParseUtil.setNodeText(r2,"案号",yaqk.getAh());
				    ParseUtil.setNodeText(r2,"经办法院代码",yaqk.getJbfydm());
				    ParseUtil.setNodeText(r2,"经办法院名称",yaqk.getJbfymc());
			    }
		    }

		    if(tsxxList.size()>0) {
		    	Element node3 = root.addElement("庭审信息");
			    for(data.exchange.center.service.sfgk.domain.pc.Tsxx txss:tsxxList) {
			    	Element r3 = node3.addElement("R");
				    ParseUtil.setNodeText(r3,"编号",txss.getBh());
				    ParseUtil.setNodeText(r3,"案件编号",txss.getAjbh());
				    ParseUtil.setNodeText(r3,"序号",txss.getXh());
				    ParseUtil.setNodeText(r3,"法庭用途代码",txss.getFtytdm());
				    ParseUtil.setNodeText(r3,"法庭用途名称",txss.getFtytmc());
				    ParseUtil.setNodeText(r3,"开始时间",ParseUtil.parseDateTime(txss.getKssj()));
				    ParseUtil.setNodeText(r3,"结束时间",ParseUtil.parseDateTime(txss.getJssj()));
				    ParseUtil.setNodeText(r3,"地点",txss.getDd());
				    ParseUtil.setNodeText(r3,"是否公开开庭代码",txss.getSfgkktdm());
				    ParseUtil.setNodeText(r3,"是否公开开庭名称",txss.getSfgkktmc());
			    }
		    }
		    
		    if(ycsxList.size()>0) {
		    	Element node4 = root.addElement("延长审限");
			    for(data.exchange.center.service.sfgk.domain.pc.Ycsx ycsx:ycsxList) {
			    	Element r4 = node4.addElement("R");
				    ParseUtil.setNodeText(r4,"编号",ycsx.getBh());
				    ParseUtil.setNodeText(r4,"案件编号",ycsx.getAjbh());
				    ParseUtil.setNodeText(r4,"序号",ycsx.getXh());
				    ParseUtil.setNodeText(r4,"申请日期",ParseUtil.parseDate(ycsx.getSqrq()));
				    ParseUtil.setNodeText(r4,"申请事由或原因代码",ycsx.getSqsyhyydm());
				    ParseUtil.setNodeText(r4,"申请事由或原因名称",ycsx.getSqsyhyymc());
				    ParseUtil.setNodeText(r4,"延长期间代码",ycsx.getYcqjdm());
				    ParseUtil.setNodeText(r4,"延长期间名称",ycsx.getYcqjmc());
				    ParseUtil.setNodeText(r4,"审批意见",ycsx.getSpyj());
				    ParseUtil.setNodeText(r4,"开始日期",ParseUtil.parseDate(ycsx.getKsrq()));
			    }
		    }
		    
		    if(kcsxList.size()>0) {
		    	Element node5 = root.addElement("扣除审限");
			    for(data.exchange.center.service.sfgk.domain.pc.Kcsx kcsx:kcsxList) {
			    	Element r5 = node5.addElement("R");
				    ParseUtil.setNodeText(r5,"编号",kcsx.getBh());
				    ParseUtil.setNodeText(r5,"案件编号",kcsx.getAjbh());
				    ParseUtil.setNodeText(r5,"序号",kcsx.getXh());
				    ParseUtil.setNodeText(r5,"扣除审限类型代码",kcsx.getKcsxlxdm());
				    ParseUtil.setNodeText(r5,"扣除审限类型名称",kcsx.getKcsxlxmc());
				    ParseUtil.setNodeText(r5,"扣除审限事由代码",kcsx.getKcsxsydm());
				    ParseUtil.setNodeText(r5,"扣除审限事由名称",kcsx.getKcsxsymc());
				    ParseUtil.setNodeText(r5,"起始日期",ParseUtil.parseDate(kcsx.getQsrq()));
				    ParseUtil.setNodeText(r5,"结束日期",ParseUtil.parseDate(kcsx.getJsrq()));
			    }
		    }
		    
		    if(zzhfList.size()>0) {
		    	Element node6 = root.addElement("中止恢复");
			    for(data.exchange.center.service.sfgk.domain.pc.Zzhf zzhf:zzhfList) {
			    	Element r6 = node6.addElement("R");
				    ParseUtil.setNodeText(r6,"编号",zzhf.getBh());
				    ParseUtil.setNodeText(r6,"案件编号",zzhf.getAjbh());
				    ParseUtil.setNodeText(r6,"序号",zzhf.getXh());
				    ParseUtil.setNodeText(r6,"中止日期",ParseUtil.parseDate(zzhf.getZzrq()));
				    ParseUtil.setNodeText(r6,"中止事由代码",zzhf.getZzsydm());
				    ParseUtil.setNodeText(r6,"中止事由名称",zzhf.getZzsymc());
				    ParseUtil.setNodeText(r6,"恢复日期",ParseUtil.parseDate(zzhf.getHfrq()));
			    }
		    }
		    
		    if(pcdsrList.size()>0) {
		    	Element node7 = root.addElement("赔偿当事人");
		    	for(data.exchange.center.service.sfgk.domain.pc.Pcdsr pcdsr:pcdsrList) {
		    		Element r7 = node7.addElement("R");
				    ParseUtil.setNodeText(r7,"编号",pcdsr.getBh());
				    ParseUtil.setNodeText(r7,"案件编号",pcdsr.getAjbh());
				    ParseUtil.setNodeText(r7,"序号",pcdsr.getXh());
				    ParseUtil.setNodeText(r7,"类型代码",pcdsr.getLxdm());
				    ParseUtil.setNodeText(r7,"类型名称",pcdsr.getLxmc());
				    ParseUtil.setNodeText(r7,"名称",pcdsr.getMc());
				    ParseUtil.setNodeText(r7,"诉讼地位代码",pcdsr.getSsdwdm());
				    ParseUtil.setNodeText(r7,"诉讼地位名称",pcdsr.getSsdwmc());
				    ParseUtil.setNodeText(r7,"法定代表人",pcdsr.getFddbr());
		    	}
		    }
		    
		    if(pcywjgList.size()>0) {
		    	Element node8 = root.addElement("赔偿义务机关");
		    	for(data.exchange.center.service.sfgk.domain.pc.Pcywjg pcywjg:pcywjgList) {
		    		Element r8 = node8.addElement("R");
				    ParseUtil.setNodeText(r8,"编号",pcywjg.getBh());
				    ParseUtil.setNodeText(r8,"案件编号",pcywjg.getAjbh());
				    ParseUtil.setNodeText(r8,"序号",pcywjg.getXh());
				    ParseUtil.setNodeText(r8,"类型代码",pcywjg.getLxdm());
				    ParseUtil.setNodeText(r8,"类型名称",pcywjg.getLxmc());
				    ParseUtil.setNodeText(r8,"赔偿义务机关",pcywjg.getPcywjg());
				    ParseUtil.setNodeText(r8,"赔偿金额",pcywjg.getPcje());
			    }
		    }
		    
		    if(spzzcyList.size()>0) {
		    	Element node9 = root.addElement("审判组织成员");
			    for(data.exchange.center.service.sfgk.domain.pc.Spzzcy spzzcy:spzzcyList) {
			    	Element r9 = node9.addElement("R");
				    ParseUtil.setNodeText(r9,"编号",spzzcy.getBh());
				    ParseUtil.setNodeText(r9,"案件编号",spzzcy.getAjbh());
				    ParseUtil.setNodeText(r9,"序号",spzzcy.getXh());
				    ParseUtil.setNodeText(r9,"姓名",spzzcy.getXm());
				    ParseUtil.setNodeText(r9,"角色代码",spzzcy.getJsdm());
				    ParseUtil.setNodeText(r9,"角色名称",spzzcy.getJsmc());
				    ParseUtil.setNodeText(r9,"联系电话",spzzcy.getLxdh());
			    }
		    }
		    
		    if(zjxxList.size()>0) {
		    	Element node10 = root.addElement("证据信息");
			    for(data.exchange.center.service.sfgk.domain.pc.Zjxx zjxx:zjxxList) {
			    	Element r10 = node10.addElement("R");
				    ParseUtil.setNodeText(r10,"编号",zjxx.getBh());
				    ParseUtil.setNodeText(r10,"案件编号",zjxx.getAjbh());
				    ParseUtil.setNodeText(r10,"序号",zjxx.getXh());
				    ParseUtil.setNodeText(r10,"证据名称",zjxx.getZjmc());
				    ParseUtil.setNodeText(r10,"证据类型代码",zjxx.getZjlxdm());
				    ParseUtil.setNodeText(r10,"证据类型名称",zjxx.getZjlxmc());
			    }
		    }
		    
		    OutputFormat format = OutputFormat.createPrettyPrint();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}