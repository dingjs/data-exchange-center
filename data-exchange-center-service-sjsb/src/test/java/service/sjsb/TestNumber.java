package service.sjsb;

import java.math.BigDecimal;

public class TestNumber {

	public static void main(String[] args) {
		BigDecimal bd = new BigDecimal("123.12323");
		BigDecimal bd1 = new BigDecimal("1024");
		BigDecimal bg = bd.multiply(bd1);
		System.out.println(bg);
		String fileName = "asdsd.rtf";
		System.out.println(fileName.substring(fileName.indexOf(".")));
	}
}
