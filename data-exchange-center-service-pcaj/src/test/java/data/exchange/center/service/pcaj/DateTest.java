package data.exchange.center.service.pcaj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		Date endDate = dft.parse(dft.format(date.getTime()));
		String date1 = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		System.out.println(date1);
	}
	
}
