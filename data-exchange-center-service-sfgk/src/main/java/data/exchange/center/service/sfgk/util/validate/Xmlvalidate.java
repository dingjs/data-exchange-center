package data.exchange.center.service.sfgk.util.validate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * 
 * Description:xml校验工具
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2018年4月28日 下午12:55:51
 * </p>
 * 
 * @author Tony
 * @version 1.0
 *
 */
public class Xmlvalidate {

	private static Logger logger = LoggerFactory.getLogger(Xmlvalidate.class);

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
	public boolean validateXml(String xsdName, String xmlPath) throws SAXException, IOException {
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
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}