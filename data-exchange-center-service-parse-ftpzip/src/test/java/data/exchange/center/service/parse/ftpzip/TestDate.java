package data.exchange.center.service.parse.ftpzip;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2017-10-23 19:01:15");
        
        String date1 = "20151101095440";
        String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
        date1 = date1.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
        System.out.println(date1);
	}
}
