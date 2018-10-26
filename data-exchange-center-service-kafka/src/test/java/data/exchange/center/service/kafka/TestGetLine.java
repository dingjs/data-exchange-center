package data.exchange.center.service.kafka;

import java.util.Random;

public class TestGetLine {
	public static void main(String args[]) {
		System.out.println("This is "+ Class.class.getName()+ getLineInfo());
		
		
		System.out.println(new Random().nextInt(30));
	}
	public static String getLineInfo() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getFileName() + ": Line " + ste.getLineNumber();
	}
}
