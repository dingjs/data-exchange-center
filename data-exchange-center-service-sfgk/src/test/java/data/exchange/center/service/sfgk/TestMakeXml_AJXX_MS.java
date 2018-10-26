package data.exchange.center.service.sfgk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml_AJXX_MS {

	public static void main(String[] args) throws IOException {
		Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("刑事案件","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
	    Element node1 = root.addElement("民事案件基本信息");
	    node1.addElement("唯一主键编号").addText("");
	    node1.addElement("案件标识").addText("");
	    node1.addElement("案号").addText("");
	    node1.addElement("案件类型代码").addText("");
	    node1.addElement("案件类型名称").addText("");
	    node1.addElement("案件类型标识代码").addText("");
	    node1.addElement("案件类型标识名称").addText("");
	    node1.addElement("经办法院代码").addText("");
	    node1.addElement("经办法院名称").addText("");
	    node1.addElement("审判程序代码").addText("");
	    node1.addElement("审判程序名称").addText("");
	    node1.addElement("适用程序代码").addText("");
	    node1.addElement("适用程序名称").addText("");
	    node1.addElement("案件来源代码").addText("");
	    node1.addElement("案件来源名称").addText("");
	    node1.addElement("新案件来源代码").addText("");
	    node1.addElement("新案件来源名称").addText("");
	    node1.addElement("立案日期").addText("");
	    node1.addElement("立案案由代码").addText("");
	    node1.addElement("立案案由名称").addText("");
	    node1.addElement("诉讼标的金额").addText("");
	    node1.addElement("案件进展阶段代码").addText("");
	    node1.addElement("案件进展阶段名称").addText("");
	    node1.addElement("公开状态代码").addText("");
	    node1.addElement("公开状态名称").addText("");
	    node1.addElement("公开日期").addText("");
	    node1.addElement("撤销日期").addText("");
	    node1.addElement("有效状态代码").addText("");
	    node1.addElement("有效状态名称").addText("");
	    node1.addElement("承办审判庭").addText("");
	    node1.addElement("承办人").addText("");
	    node1.addElement("法定审限天数").addText("");
	    node1.addElement("审限届满日期").addText("");
	    node1.addElement("结案日期").addText("");
	    node1.addElement("结案案由代码").addText("");
	    node1.addElement("结案案由名称").addText("");
	    node1.addElement("结案方式代码").addText("");
	    node1.addElement("结案方式名称").addText("");
	    node1.addElement("新结案方式代码").addText("");
	    node1.addElement("新结案方式名称").addText("");
	    node1.addElement("创建时间").addText("");
	    node1.addElement("创建人").addText("");
	    node1.addElement("更新时间").addText("");
	    node1.addElement("更新人").addText("");

	    Element node2 = root.addElement("原案情况");
	    Element r2 = node2.addElement("R");
	    r2.addElement("编号").addText("");
	    r2.addElement("案件编号").addText("");
	    r2.addElement("序号").addText("");
	    r2.addElement("案号").addText("");
	    r2.addElement("经办法院代码").addText("");
	    r2.addElement("经办法院名称").addText("");

	    Element node3 = root.addElement("管辖处理信息");
	    Element r3 = node3.addElement("R");
	    r3.addElement("编号").addText("");
	    r3.addElement("案件编号").addText("");
	    r3.addElement("序号").addText("");
	    r3.addElement("管辖问题类型代码").addText("");
	    r3.addElement("管辖问题类型名称").addText("");
	    r3.addElement("提出管辖异议人").addText("");
	    r3.addElement("提出管辖异议日期").addText("");
	    r3.addElement("管辖异议处理结果代码").addText("");
	    r3.addElement("管辖异议处理结果名称").addText("");
	    r3.addElement("管辖异议处理日期").addText("");

	    Element node4 = root.addElement("抗诉情况");
	    node4.addElement("编号").addText("");
	    node4.addElement("案件编号").addText("");
	    node4.addElement("抗诉机关").addText("");
	    node4.addElement("提出抗诉日期").addText("");

	    Element node5 = root.addElement("上诉情况");
	    node5.addElement("编号").addText("");
	    node5.addElement("案件编号").addText("");
	    node5.addElement("上诉人").addText("");
	    node5.addElement("提出上诉日期").addText("");

	    Element node6 = root.addElement("庭审信息");
	    Element r6 = node6.addElement("R");
	    r6.addElement("编号").addText("");
	    r6.addElement("案件编号").addText("");
	    r6.addElement("序号").addText("");
	    r6.addElement("法庭用途代码").addText("");
	    r6.addElement("法庭用途名称").addText("");
	    r6.addElement("开始时间").addText("");
	    r6.addElement("结束时间").addText("");
	    r6.addElement("地点").addText("");
	    r6.addElement("是否公开开庭代码").addText("");
	    r6.addElement("是否公开开庭名称").addText("");

	    Element node7 = root.addElement("延长审限");
	    Element r7 = node7.addElement("R");
	    r7.addElement("编号").addText("");
	    r7.addElement("案件编号").addText("");
	    r7.addElement("序号").addText("");
	    r7.addElement("申请日期").addText("");
	    r7.addElement("申请事由或原因代码").addText("");
	    r7.addElement("申请事由或原因名称").addText("");
	    r7.addElement("延长期间代码").addText("");
	    r7.addElement("延长期间名称").addText("");
	    r7.addElement("审批意见").addText("");
	    r7.addElement("开始日期").addText("");

	    Element node8 = root.addElement("扣除审限");
	    Element r8 = node8.addElement("R");
	    r8.addElement("编号").addText("");
	    r8.addElement("案件编号").addText("");
	    r8.addElement("序号").addText("");
	    r8.addElement("扣除审限类型代码").addText("");
	    r8.addElement("扣除审限类型名称").addText("");
	    r8.addElement("扣除审限事由代码").addText("");
	    r8.addElement("扣除审限事由名称").addText("");
	    r8.addElement("起始日期").addText("");
	    r8.addElement("结束日期").addText("");

	    Element node9 = root.addElement("中止恢复");
	    Element r9 = node9.addElement("R");
	    r9.addElement("编号").addText("");
	    r9.addElement("案件编号").addText("");
	    r9.addElement("序号").addText("");
	    r9.addElement("中止日期").addText("");
	    r9.addElement("中止事由代码").addText("");
	    r9.addElement("中止事由名称").addText("");
	    r9.addElement("恢复日期").addText("");

	    Element node10 = root.addElement("诉讼费减免缓信息");
	    Element r10 = node10.addElement("R");
	    r10.addElement("编号").addText("");
	    r10.addElement("案件编号").addText("");
	    r10.addElement("序号").addText("");
	    r10.addElement("诉讼费救助类型代码").addText("");
	    r10.addElement("诉讼费救助类型名称").addText("");
	    r10.addElement("救助申请人").addText("");
	    r10.addElement("救助申请日期").addText("");
	    r10.addElement("申请减交数额").addText("");
	    r10.addElement("申请缓交期限").addText("");
	    r10.addElement("审批意见").addText("");
	    r10.addElement("审批日期").addText("");

	    Element node11 = root.addElement("当事人");
	    Element r11 = node11.addElement("R");
	    r11.addElement("编号").addText("");
	    r11.addElement("案件编号").addText("");
	    r11.addElement("序号").addText("");
	    r11.addElement("当事人类型代码").addText("");
	    r11.addElement("当事人类型名称").addText("");
	    r11.addElement("诉讼地位代码").addText("");
	    r11.addElement("诉讼地位名称").addText("");
	    r11.addElement("名称").addText("");
	    r11.addElement("法定代表人").addText("");

	    Element node12 = root.addElement("代理人");
	    Element r12 = node12.addElement("R");
	    r12.addElement("编号").addText("");
	    r12.addElement("案件编号").addText("");
	    r12.addElement("序号").addText("");
	    r12.addElement("诉讼代理人类型代码").addText("");
	    r12.addElement("诉讼代理人类型名称").addText("");
	    r12.addElement("诉讼代理人姓名").addText("");
	    r12.addElement("诉讼代理人单位").addText("");

	    Element node13 = root.addElement("审判组织成员");
	    Element r13 = node13.addElement("R");
	    r13.addElement("编号").addText("");
	    r13.addElement("案件编号").addText("");
	    r13.addElement("序号").addText("");
	    r13.addElement("姓名").addText("");
	    r13.addElement("角色代码").addText("");
	    r13.addElement("角色名称").addText("");
	    r13.addElement("联系电话").addText("");

	    Element node14 = root.addElement("审判组织成员变更");
	    Element r14 = node14.addElement("R");
	    r14.addElement("编号").addText("");
	    r14.addElement("案件编号").addText("");
	    r14.addElement("序号").addText("");
	    r14.addElement("原成员").addText("");
	    r14.addElement("变更原因代码").addText("");
	    r14.addElement("变更原因名称").addText("");
	    r14.addElement("新成员").addText("");
	    r14.addElement("新成员角色代码").addText("");
	    r14.addElement("新成员角色名称").addText("");
	    r14.addElement("变更日期").addText("");

	    Element node15 = root.addElement("诉讼保全和先予执行");
	    Element r15 = node15.addElement("R");
	    r15.addElement("编号").addText("");
	    r15.addElement("案件编号").addText("");
	    r15.addElement("序号").addText("");
	    r15.addElement("类型代码").addText("");
	    r15.addElement("类型名称").addText("");
	    r15.addElement("保全发动方代码").addText("");
	    r15.addElement("保全发动方名称").addText("");
	    r15.addElement("申请人").addText("");
	    r15.addElement("被申请人").addText("");
	    r15.addElement("申请日期").addText("");
	    r15.addElement("申请金额").addText("");
	    r15.addElement("申请财产").addText("");
	    r15.addElement("裁定日期").addText("");
	    r15.addElement("裁定结果代码").addText("");
	    r15.addElement("裁定结果名称").addText("");
	    r15.addElement("保全措施代码").addText("");
	    r15.addElement("保全措施名称").addText("");
	    r15.addElement("先予执行事由代码").addText("");
	    r15.addElement("先予执行事由名称").addText("");
	    r15.addElement("执行日期").addText("");
	    r15.addElement("实际执行金额").addText("");
	    r15.addElement("解除保全日期").addText("");
	    r15.addElement("解除保全原因代码").addText("");
	    r15.addElement("解除保全原因名称").addText("");

	    Element node16 = root.addElement("诉讼担保信息");
	    Element r16 = node16.addElement("R");
	    r16.addElement("编号").addText("");
	    r16.addElement("案件编号").addText("");
	    r16.addElement("序号").addText("");
	    r16.addElement("当事人").addText("");
	    r16.addElement("事由代码").addText("");
	    r16.addElement("事由名称").addText("");
	    r16.addElement("担保人").addText("");
	    r16.addElement("与当事人关系代码").addText("");
	    r16.addElement("与当事人关系名称").addText("");
	    r16.addElement("担保方式代码").addText("");
	    r16.addElement("担保方式名称").addText("");

	    Element node17 = root.addElement("回避信息");
	    Element r17 = node17.addElement("R");
	    r17.addElement("编号").addText("");
	    r17.addElement("案件编号").addText("");
	    r17.addElement("序号").addText("");
	    r17.addElement("回避类型代码").addText("");
	    r17.addElement("回避类型名称").addText("");
	    r17.addElement("被申请回避角色代码").addText("");
	    r17.addElement("被申请回避角色名称").addText("");
	    r17.addElement("被申请回避人").addText("");
	    r17.addElement("回避事由代码").addText("");
	    r17.addElement("回避事由名称").addText("");
	    r17.addElement("回避申请人").addText("");
	    r17.addElement("回避申请日期").addText("");
	    r17.addElement("回避决定人代码").addText("");
	    r17.addElement("回避决定人名称").addText("");
	    r17.addElement("决定日期").addText("");
	    r17.addElement("回避处理结果代码").addText("");
	    r17.addElement("回避处理结果名称").addText("");

	    Element node18 = root.addElement("证据信息");
	    Element r18 = node18.addElement("R");
	    r18.addElement("编号").addText("");
	    r18.addElement("案件编号").addText("");
	    r18.addElement("序号").addText("");
	    r18.addElement("证据名称").addText("");
	    r18.addElement("证据类型代码").addText("");
	    r18.addElement("证据类型名称").addText("");

	    Element node19 = root.addElement("程序变更");
	    node19.addElement("编号").addText("");
	    node19.addElement("案件编号").addText("");
	    node19.addElement("变更类型代码").addText("");
	    node19.addElement("变更类型名称").addText("");
	    node19.addElement("变更原因代码").addText("");
	    node19.addElement("变更原因名称").addText("");
	    node19.addElement("变更日期").addText("");

	    Element node20 = root.addElement("复议信息");
	    Element r20 = node20.addElement("R");
	    r20.addElement("编号").addText("");
	    r20.addElement("案件编号").addText("");
	    r20.addElement("序号").addText("");
	    r20.addElement("类型代码").addText("");
	    r20.addElement("类型名称").addText("");
	    r20.addElement("申请人").addText("");
	    r20.addElement("申请日期").addText("");
	    r20.addElement("处理日期").addText("");
	    r20.addElement("处理结果代码").addText("");
	    r20.addElement("处理结果名称").addText("");

	    Element node21 = root.addElement("裁判文书上网信息");
	    Element r21 = node21.addElement("R");
	    r21.addElement("编号").addText("");
	    r21.addElement("案件编号").addText("");
	    r21.addElement("序号").addText("");
	    r21.addElement("文书类型代码").addText("");
	    r21.addElement("文书类型名称").addText("");
	    r21.addElement("上网时间").addText("");
	    r21.addElement("查询方式").addText("");

	    Element node22 = root.addElement("送达信息");
	    Element r22 = node22.addElement("R");
	    r22.addElement("编号").addText("");
	    r22.addElement("案件编号").addText("");
	    r22.addElement("序号").addText("");
	    r22.addElement("文书类型代码").addText("");
	    r22.addElement("文书类型名称").addText("");
	    r22.addElement("送达方式代码").addText("");
	    r22.addElement("送达方式名称").addText("");
	    r22.addElement("受送达人").addText("");
	    r22.addElement("送达日期").addText("");
	    r22.addElement("签收时间").addText("");

	    Element node23 = root.addElement("重整情况");
	    node23.addElement("编号").addText("");
	    node23.addElement("案件编号").addText("");
	    node23.addElement("申请重整日期").addText("");
	    node23.addElement("裁定重整日期").addText("");

	    Element node24 = root.addElement("和解情况");
	    node24.addElement("编号").addText("");
	    node24.addElement("案件编号").addText("");
	    node24.addElement("申请和解日期").addText("");
	    node24.addElement("裁定和解日期").addText("");

	    Element node25 = root.addElement("破产清算情况");
	    node25.addElement("编号").addText("");
	    node25.addElement("案件编号").addText("");
	    node25.addElement("宣告破产日期").addText("");
	    node25.addElement("裁定终结破产程序日期").addText("");
	    node25.addElement("终结破产程序事由代码").addText("");
	    node25.addElement("终结破产程序事由名称").addText("");

	    Element node26 = root.addElement("破产管理人");
	    Element r26 = node26.addElement("R");
	    r26.addElement("编号").addText("");
	    r26.addElement("案件编号").addText("");
	    r26.addElement("序号").addText("");
	    r26.addElement("管理人类型代码").addText("");
	    r26.addElement("管理人类型名称").addText("");
	    r26.addElement("担任管理人职务代码").addText("");
	    r26.addElement("担任管理人职务名称").addText("");
	    r26.addElement("破产管理人").addText("");
	    r26.addElement("管理人指定日期").addText("");

		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("D://MS1.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
