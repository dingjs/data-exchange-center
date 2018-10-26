package data.exchange.center.service.sfgk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.xml.sax.SAXException;

import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

/**
 * 校验xml工具类
 */
public class TestXmlvalidate {

	private TestXmlvalidate() {

	}

	public boolean validateXml(String xsdPath, String xmlPath) throws SAXException, IOException {
		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		InputStream stream = getClass().getClassLoader().getResourceAsStream(xsdPath);
		File schemaFile = new File(xsdPath);
		org.apache.commons.io.FileUtils.copyInputStreamToFile(stream, schemaFile);
//		File schemaFile = new File(xsdPath);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		Schema schema = schemaFactory.newSchema(schemaFile);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		Validator validator = schema.newValidator();
		// 得到验证的数据源
		Source source = new StreamSource(xmlPath);
		// 开始验证，成功输出success!!!，失败输出fail
		validator.validate(source);
		return true;
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		TestXmlvalidate Xmlvalidate = new TestXmlvalidate();
		try {
			if (Xmlvalidate.validateXml("DX.xsd", "d://DX.xml")) {
				System.out.println("校验通过");
			}
		} catch (SAXException e) {
			System.out.println("校验失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("校验失败");
			e.printStackTrace();
		}
	}
}