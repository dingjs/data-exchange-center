package data.exchange.center.service.unstructured.node.task.timer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.service.unstructured.node.domain.SubTask;
import data.exchange.center.service.unstructured.node.domain.TaskInfo;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.mapper.sgy.TimerMapper;
import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月20日 上午10:39:08</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class TaskTimer implements CommandLineRunner {

	@Value("${fydm}")
	private String fydm;
	@Autowired
	private TimerMapper timerMapper;
	@Autowired
	private AgentGetDataMapper agentGetDataMapper;
	@Autowired
	private AgentPushDataMapper agentPushDataMapper;
	@Resource(name = "sgyRedisTemplate")
	private RedisTemplate<Object, Object> redisTemplate;
	/**
	 * 定时任务执行器
	 */
	private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	@Override
	public void run(String... args) throws Exception {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				try {
				    System.out.println("开始查看任务列表");
					// 获取当前所有线程
					ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
					Thread[] threads = new Thread[threadGroup.activeCount()];
					threadGroup.enumerate(threads);
					// 启动线程
					 List<TaskInfo> taskInfoList = null;
				    try {
				         taskInfoList = timerMapper.getTask(fydm);
		                System.setProperty(Constant.C_ZXDSTATE, Constant.C_NORMAL_CONNECT);
		            } catch (Exception e) {
		                System.setProperty(Constant.C_ZXDSTATE, Constant.C_ERROR_CONNECT);
		            }
					
					for (TaskInfo taskInfo : taskInfoList) {
						boolean threadEnable = false;
						String taskName = taskInfo.getcTaskname();
						String enable = taskInfo.getcEnable();
						for (Thread thread : threads) {
							if (thread != null && thread.getName().equals( Constant.RW + taskName)) {
								threadEnable = true;
								break;
							}
						}
						if(!threadEnable && enable.equalsIgnoreCase(Constant.ENABLE)){
						    System.out.println(taskName+"任务开始执行");
							System.setProperty(Constant.RW + taskName,Constant.ENABLE);
							Thread thread = new Thread(
									new LoopTaskControl(
											taskInfo, 
											timerMapper, 
											agentGetDataMapper, 
											redisTemplate,
											agentPushDataMapper
											));
							thread.setName(Constant.RW + taskName);
							thread.start();
						}
					}
					// 判断是否有已经删除的线程
					for (Thread thread : threads) {
						boolean threadEnable = false;
						threadEnable = false;
						String taskName = null;
						String enable = null;
						for (TaskInfo taskInfo : taskInfoList) {
							List<SubTask> subTaskList = timerMapper.getSubbTask(taskInfo.getnTaskid());
							taskName = taskInfo.getcTaskname();
							enable = taskInfo.getcEnable();
							//判断现所有运行主线程是否有任务中没有的线程，如果有就关闭（只是针对任务线程）
							if (thread != null && thread.getName().equalsIgnoreCase( Constant.RW + taskName)) {
								threadEnable = true;
								break;
							}
							for (SubTask subTask : subTaskList) {
								if (thread != null && thread.getName().equalsIgnoreCase( Constant.RW + taskName+subTask.getNorder())) {
									threadEnable = true;
									break;
								}
							}
							// 判断主线程是否关闭
							if (enable.equals(Constant.DISABLE)) {
								//关闭主线程的（并行线程）
								Constant.interruptThread(Constant.RW + taskName);
								System.setProperty(Constant.RW + taskName,Constant.DISABLE);
								//若果主线程关闭，就关闭其下所有线程
								for (SubTask subTask : subTaskList) {
									Constant.interruptThread(Constant.RW + taskName + subTask.getNorder());
									break;
								}
							}
						}
						// 停止表中已经删除的线程
						if (!threadEnable && thread.getName().length() >= 3
								&& thread.getName().substring(0, 3).equalsIgnoreCase(Constant.RW)) {
							Constant.interruptThread(thread.getName());
							System.setProperty(thread.getName(),Constant.DISABLE);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 60000, TimeUnit.MILLISECONDS);
	}
}