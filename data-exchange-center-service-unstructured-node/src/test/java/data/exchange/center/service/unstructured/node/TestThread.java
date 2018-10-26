package data.exchange.center.service.unstructured.node;

import java.util.concurrent.TimeUnit;

public class TestThread {

	volatile static String testString=null;
	
	public static void main(String[] args) throws InterruptedException {
		testString = "asd";
		boolean run = false;
		TestRunnable testRunnable = new TestRunnable();
		for(int i=0; i<3; i++) {
			Thread thread = new Thread(testRunnable);
			thread.setName("test"+i);
			thread.start();
		}
		TimeUnit.SECONDS.sleep(4);
//		testRunnable.setRun(run);
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		for (int i = 0; i < noThreads; i++) {
			System.out.println("线程号：" + i + " = " + lstThreads[i].getName());
			if(lstThreads[i].getName().equalsIgnoreCase("test2")) {
				lstThreads[i].stop();
			}
		}
	}
}
