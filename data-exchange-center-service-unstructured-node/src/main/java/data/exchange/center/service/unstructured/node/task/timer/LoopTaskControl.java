package data.exchange.center.service.unstructured.node.task.timer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import data.exchange.center.service.unstructured.node.domain.SubTask;
import data.exchange.center.service.unstructured.node.domain.TaskInfo;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.mapper.sgy.TimerMapper;
import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
import data.exchange.center.service.unstructured.node.task.push.thread.queue.PushAjbsToQueueRw;
import data.exchange.center.service.unstructured.node.util.Constant;

public class LoopTaskControl implements Runnable{
	private TaskInfo taskInfo;
	private TimerMapper timerMapper;
	private AgentGetDataMapper agentGetDataMapper;
	private AgentPushDataMapper agentPushDataMapper;
	private RedisTemplate<Object, Object> redisTemplate;
	private static Logger logger = LoggerFactory.getLogger(LoopTaskControl.class);
	
	public LoopTaskControl(TaskInfo taskInfo, TimerMapper timerMapper, AgentGetDataMapper agentGetDataMapper,
			RedisTemplate<Object, Object> redisTemplate, AgentPushDataMapper agentPushDataMapper) throws Exception {
		this.taskInfo = taskInfo;
		this.timerMapper = timerMapper;
		this.agentGetDataMapper = agentGetDataMapper;
		this.redisTemplate = redisTemplate;
		this.agentPushDataMapper = agentPushDataMapper;
	}
	@Override
	public void run() {
		Integer ntaskcnt = taskInfo.getnTaskcnt();
		String loop = taskInfo.getcLoop();
		String taskName = taskInfo.getcTaskname();
		if (loop.equals(Constant.YES)) {
			// 循环任务
			while (!System.getProperty(Constant.RW + taskName).equals(Constant.DISABLE)) {
				try {
					Thread thread = new Thread(
							LoopTaskControl(
									taskInfo, 
									timerMapper, 
									agentGetDataMapper, 
									redisTemplate,
									null
									));
					thread.setName(Constant.RW + taskName);
					thread.start();
					TimeUnit.SECONDS.sleep(5);
				} catch (Exception e) {
					logger.error("循环任务出现错误"+e.getMessage());
					new InterruptedException().printStackTrace();
					continue;
				}
			}
		} else if (loop.equals(Constant.NO)) {
			// 指定次数任务
			for (int i = 0; i < ntaskcnt; i++) {
				Thread thread;
				try {
					thread = new Thread(
							LoopTaskControl(
									taskInfo, 
									timerMapper, 
									agentGetDataMapper, 
									redisTemplate,
									null));
					thread.setName(Constant.RW + taskName);
					thread.start();
					thread.join();
					if (System.getProperty(Constant.RW + taskName).equals(Constant.DISABLE)) {
						break;
					}
				} catch (Exception e) {
					logger.error("指定次数任务错误"+e.getMessage());
					e.printStackTrace();
				}
		
			}
		}
	}
	// 并行的子线程
	private Runnable LoopTaskControl(TaskInfo taskInfo, TimerMapper timerMapper, AgentGetDataMapper agentGetDataMapper,
			RedisTemplate<Object, Object> redisTemplate,String Str) throws Exception {
		List<SubTask> subTaskList = timerMapper.getSubbTask(taskInfo.getnTaskid());
		for (int i = 0; i < subTaskList.size(); i++) {
			SubTask subTask = subTaskList.get(i);
			Thread thread = new Thread(new PushAjbsToQueueRw(agentGetDataMapper, taskInfo, subTask, redisTemplate,agentPushDataMapper));
			thread.setName(Constant.RW + taskInfo.getcTaskname() + subTask.getNorder());
			thread.start();
			thread.join();
			if (System.getProperty(Constant.RW + taskInfo.getcTaskname()).equals(Constant.DISABLE)) {
				break;
			}
		}
		return null;
	}

	
}
