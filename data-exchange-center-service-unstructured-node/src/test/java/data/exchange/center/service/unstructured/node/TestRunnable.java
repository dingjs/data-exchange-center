package data.exchange.center.service.unstructured.node;

import java.util.concurrent.TimeUnit;

public class TestRunnable implements Runnable{

	public volatile boolean exit = false; 
	
	private String testString = "akdsjhfkldfh";
	private boolean run = true;
	
	public void setRun(boolean run) {
		this.run = run;
	}
	@Override
	public void run() {
		while(!exit) {
			try {
				String threadName = Thread.currentThread().getName();
				TimeUnit.SECONDS.sleep(10);
				System.out.println(threadName+":"+testString);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
