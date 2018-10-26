package data.exchange.center.service.guangan;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DataExchangeCenterServiceGuanganApplicationTests {

	@Test
	public void contextLoads() throws ParseException {
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");    
        java.util.Date beginDate;
        java.util.Date endDate;
        beginDate = format.parse("20170630");
        endDate= format.parse("20170701");    
        long day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        System.out.println(day);
	}

}
