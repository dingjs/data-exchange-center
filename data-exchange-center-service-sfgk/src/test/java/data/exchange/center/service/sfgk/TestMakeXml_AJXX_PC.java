package data.exchange.center.service.sfgk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml_AJXX_PC {

	public static void main(String[] args) throws IOException {
		Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("刑事案件","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
	    Element node1 = root.addElement("赔偿案件基本信息");
	    node1.addElement("唯一主键编号").addText("");
	    node1.addElement("案件标识").addText("");
	    node1.addElement("案号").addText("");
	    node1.addElement("案件类型代码").addText("");
	    node1.addElement("案件类型名称").addText("");
	    node1.addElement("案件类型标识代码").addText("");
	    node1.addElement("案件类型标识名称").addText("");
	    node1.addElement("经办法院代码").addText("");
	    node1.addElement("经办法院名称").addText("");
	    node1.addElement("案件来源代码").addText("");
	    node1.addElement("案件来源名称").addText("");
	    node1.addElement("新案件来源代码").addText("");
	    node1.addElement("新案件来源名称").addText("");
	    node1.addElement("立案日期").addText("");
	    node1.addElement("立案案由代码").addText("");
	    node1.addElement("立案案由名称").addText("");
	    node1.addElement("请求赔偿金额").addText("");
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
	    node1.addElement("赔偿方式代码").addText("");
	    node1.addElement("赔偿方式名称").addText("");
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

	    Element node3 = root.addElement("庭审信息");
	    Element r3 = node3.addElement("R");
	    r3.addElement("编号").addText("");
	    r3.addElement("案件编号").addText("");
	    r3.addElement("序号").addText("");
	    r3.addElement("法庭用途代码").addText("");
	    r3.addElement("法庭用途名称").addText("");
	    r3.addElement("开始时间").addText("");
	    r3.addElement("结束时间").addText("");
	    r3.addElement("地点").addText("");
	    r3.addElement("是否公开开庭代码").addText("");
	    r3.addElement("是否公开开庭名称").addText("");

	    Element node4 = root.addElement("延长审限");
	    Element r4 = node4.addElement("R");
	    r4.addElement("编号").addText("");
	    r4.addElement("案件编号").addText("");
	    r4.addElement("序号").addText("");
	    r4.addElement("申请日期").addText("");
	    r4.addElement("申请事由或原因代码").addText("");
	    r4.addElement("申请事由或原因名称").addText("");
	    r4.addElement("延长期间代码").addText("");
	    r4.addElement("延长期间名称").addText("");
	    r4.addElement("审批意见").addText("");
	    r4.addElement("开始日期").addText("");

	    Element node5 = root.addElement("扣除审限");
	    Element r5 = node5.addElement("R");
	    r5.addElement("编号").addText("");
	    r5.addElement("案件编号").addText("");
	    r5.addElement("序号").addText("");
	    r5.addElement("扣除审限类型代码").addText("");
	    r5.addElement("扣除审限类型名称").addText("");
	    r5.addElement("扣除审限事由代码").addText("");
	    r5.addElement("扣除审限事由名称").addText("");
	    r5.addElement("起始日期").addText("");
	    r5.addElement("结束日期").addText("");

	    Element node6 = root.addElement("中止恢复");
	    Element r6 = node6.addElement("R");
	    r6.addElement("编号").addText("");
	    r6.addElement("案件编号").addText("");
	    r6.addElement("序号").addText("");
	    r6.addElement("中止日期").addText("");
	    r6.addElement("中止事由代码").addText("");
	    r6.addElement("中止事由名称").addText("");
	    r6.addElement("恢复日期").addText("");

	    Element node7 = root.addElement("赔偿当事人");
	    Element r7 = node7.addElement("R");
	    r7.addElement("编号").addText("");
	    r7.addElement("案件编号").addText("");
	    r7.addElement("序号").addText("");
	    r7.addElement("类型代码").addText("");
	    r7.addElement("类型名称").addText("");
	    r7.addElement("名称").addText("");
	    r7.addElement("诉讼地位代码").addText("");
	    r7.addElement("诉讼地位名称").addText("");
	    r7.addElement("法定代表人").addText("");

	    Element node8 = root.addElement("当事人诉讼代理人");
	    Element r8 = node8.addElement("R");
	    r8.addElement("编号").addText("");
	    r8.addElement("案件编号").addText("");
	    r8.addElement("序号").addText("");
	    r8.addElement("代理人类型代码").addText("");
	    r8.addElement("代理人类型名称").addText("");
	    r8.addElement("代理人姓名").addText("");
	    r8.addElement("代理人单位").addText("");

	    Element node9 = root.addElement("赔偿义务机关");
	    Element r9 = node9.addElement("R");
	    r9.addElement("编号").addText("");
	    r9.addElement("案件编号").addText("");
	    r9.addElement("序号").addText("");
	    r9.addElement("类型代码").addText("");
	    r9.addElement("类型名称").addText("");
	    r9.addElement("赔偿义务机关").addText("");
	    r9.addElement("赔偿金额").addText("");

	    Element node10 = root.addElement("审判组织成员");
	    Element r10 = node10.addElement("R");
	    r10.addElement("编号").addText("");
	    r10.addElement("案件编号").addText("");
	    r10.addElement("序号").addText("");
	    r10.addElement("姓名").addText("");
	    r10.addElement("角色代码").addText("");
	    r10.addElement("角色名称").addText("");
	    r10.addElement("联系电话").addText("");

	    Element node11 = root.addElement("审判组织成员变更");
	    Element r11 = node11.addElement("R");
	    r11.addElement("编号").addText("");
	    r11.addElement("案件编号").addText("");
	    r11.addElement("序号").addText("");
	    r11.addElement("原成员").addText("");
	    r11.addElement("变更原因代码").addText("");
	    r11.addElement("变更原因名称").addText("");
	    r11.addElement("新成员").addText("");
	    r11.addElement("新成员角色代码").addText("");
	    r11.addElement("新成员角色名称").addText("");
	    r11.addElement("变更日期").addText("");

	    Element node12 = root.addElement("回避信息");
	    Element r12 = node12.addElement("R");
	    r12.addElement("编号").addText("");
	    r12.addElement("案件编号").addText("");
	    r12.addElement("序号").addText("");
	    r12.addElement("回避类型代码").addText("");
	    r12.addElement("回避类型名称").addText("");
	    r12.addElement("被申请回避角色代码").addText("");
	    r12.addElement("被申请回避角色名称").addText("");
	    r12.addElement("被申请回避人").addText("");
	    r12.addElement("回避事由代码").addText("");
	    r12.addElement("回避事由名称").addText("");
	    r12.addElement("回避申请人").addText("");
	    r12.addElement("回避申请日期").addText("");
	    r12.addElement("回避决定人代码").addText("");
	    r12.addElement("回避决定人名称").addText("");
	    r12.addElement("决定日期").addText("");
	    r12.addElement("回避处理结果代码").addText("");
	    r12.addElement("回避处理结果名称").addText("");

	    Element node13 = root.addElement("证据信息");
	    Element r13 = node13.addElement("R");
	    r13.addElement("编号").addText("");
	    r13.addElement("案件编号").addText("");
	    r13.addElement("序号").addText("");
	    r13.addElement("证据名称").addText("");
	    r13.addElement("证据类型代码").addText("");
	    r13.addElement("证据类型名称").addText("");

	    Element node14 = root.addElement("复议信息");
	    Element r14 = node14.addElement("R");
	    r14.addElement("编号").addText("");
	    r14.addElement("案件编号").addText("");
	    r14.addElement("序号").addText("");
	    r14.addElement("类型代码").addText("");
	    r14.addElement("类型名称").addText("");
	    r14.addElement("申请人").addText("");
	    r14.addElement("申请日期").addText("");
	    r14.addElement("处理日期").addText("");
	    r14.addElement("处理结果代码").addText("");
	    r14.addElement("处理结果名称").addText("");

	    Element node15 = root.addElement("裁判文书上网信息");
	    Element r15 = node15.addElement("R");
	    r15.addElement("编号").addText("");
	    r15.addElement("案件编号").addText("");
	    r15.addElement("序号").addText("");
	    r15.addElement("文书类型代码").addText("");
	    r15.addElement("文书类型名称").addText("");
	    r15.addElement("上网时间").addText("");
	    r15.addElement("查询方式").addText("");

	    Element node16 = root.addElement("送达信息");
	    Element r16 = node16.addElement("R");
	    r16.addElement("编号").addText("");
	    r16.addElement("案件编号").addText("");
	    r16.addElement("序号").addText("");
	    r16.addElement("文书类型代码").addText("");
	    r16.addElement("文书类型名称").addText("");
	    r16.addElement("送达方式代码").addText("");
	    r16.addElement("送达方式名称").addText("");
	    r16.addElement("受送达人").addText("");
	    r16.addElement("送达日期").addText("");
	    r16.addElement("签收时间").addText("");

		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("D://MS1.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
