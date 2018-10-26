package data.exchange.center.service.listener.counter;

public class Counter implements Runnable {

	// 变量上线值
	public final static int NUM = Integer.MAX_VALUE;
	// 计数值
	public int count;
	// 加锁
	private static byte[] lock = new byte[0];

	public Counter(int count) {
		this.count = count;
	}
	@Override
	public void run() {
		while (true) {
			synchronized (lock) {
				count++;
			}
		}
	}
}
