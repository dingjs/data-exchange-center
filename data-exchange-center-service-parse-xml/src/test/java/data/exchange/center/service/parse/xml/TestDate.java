package data.exchange.center.service.parse.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = format.parse("20180327");
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
