package data.exchange.center.service.unstructured.node;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import data.exchange.center.service.unstructured.node.conf.RabbitMqConfig;
import data.exchange.center.service.unstructured.node.task.push.thread.queue.PushAjbsToQueue;

public class TestScheduled {

	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	public void run() {
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
            //	new PushAjbsToQueue();
            }
        }, 5000,5000, TimeUnit.MILLISECONDS);
	}
	public static void main(String[] args) {
		System.out.println(String.format(RabbitMqConfig.agentQueue +"%s", "队列非空，等待出对完成方可入队操作"));
	}
}
