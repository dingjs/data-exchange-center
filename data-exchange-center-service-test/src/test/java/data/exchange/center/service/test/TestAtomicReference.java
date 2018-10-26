package data.exchange.center.service.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicReference {
	public static void main(String[] args) throws InterruptedException {
		dfasd111();
	}

	private static AtomicReference<Integer> ar = new AtomicReference<Integer>(0);

	public static void dfasd111() throws InterruptedException {
		int t = 10;
		final int c = 100;
		final CountDownLatch latch = new CountDownLatch(t);
		for (int i = 0; i < t; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < c; i++) {
						while (true) {
							Integer temp = ar.get();
							if (ar.compareAndSet(temp, temp + 1)) {
								break;
							}
						}
					}
					latch.countDown();
				}
			}).start();
		}
		latch.await();
		System.out.println(ar.get()); // 10000000
	}

	public final void test() {
		System.out.println(this.getClass());
	}
}