package data.exchange.center.service.sfgk;

import java.io.IOException;

import org.xml.sax.SAXException;

import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

public class TestXsdValidate {

	public static void main(String[] args) {
		String xsdPath = "D://DX.xsd";
		String xmlPath = "D://DX.XML";
		Xmlvalidate Xmlvalidate = new Xmlvalidate();
		try {
			if (Xmlvalidate.validateXml(xsdPath, xmlPath)) {
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
