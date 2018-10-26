package service.sjsb;

import java.io.IOException;

import org.xml.sax.SAXException;

public class TestXsdValidate {

	public static void main(String[] args) {
		String xsdPath = "E://DA.xsd";
		String xmlPath = "E://DA_32_300000000031238_1.xml";

		try {
			if (TestXmlvalidate.validateXml(xsdPath, xmlPath)) {
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
