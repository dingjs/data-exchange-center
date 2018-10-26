package data.exchange.center.common.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import data.exchange.center.common.code.CodeUtil;

public class XmlXsdvalidate {

	public synchronized static Map<String, String> validateXml(String xsdPath, String xmlPath) throws SAXException, IOException {
		Map<String, String> retM = new HashMap<String, String>();
		try {
			// 建立schema工厂
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
			File schemaFile = new File(xsdPath);
			// 利用schema工厂，接收验证文档文件对象生成Schema对象
			Schema schema = schemaFactory.newSchema(schemaFile);
			// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
			Validator validator = schema.newValidator();
			// 得到验证的数据源
			Source source = new StreamSource(xmlPath);
			// 开始验证，成功输出success!!!，失败输出fail
			validator.validate(source);
			retM.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			retM.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
		} catch (Exception e) {
			retM.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			retM.put(CodeUtil.RETURN_MSG, e.toString());
		}
		return retM;
	}
	
	/**
	 * 
	 * @function 
	 * @author Tony
	 * @creaetime 2018年6月21日 下午5:50:46
	 * @param xsdName 校验的xsd文件，直接填写文件名称，xsd文件放在项目的resource中即可
	 * @param xmlPath 对应的xml绝对路径
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public synchronized Map<String, String> validateXmls(String xsdName, String xmlPath) throws SAXException, IOException {
		Map<String, String> retM = new HashMap<String, String>();
		try {
			
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputStream stream = getClass().getClassLoader().getResourceAsStream(xsdName);
			File schemaFile = new File(xsdName);
			org.apache.commons.io.FileUtils.copyInputStreamToFile(stream, schemaFile);
			Schema schema = schemaFactory.newSchema(schemaFile);
			// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
			Validator validator = schema.newValidator();
			// 得到验证的数据源
			Source source = new StreamSource(xmlPath);
			// 开始验证，成功输出success!!!，失败输出fail
			validator.validate(source);
			retM.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			retM.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
		} catch (Exception e) {
			retM.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			retM.put(CodeUtil.RETURN_MSG, e.toString());
		}
		return retM;
	}
}