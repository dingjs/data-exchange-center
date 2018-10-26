package data.exchange.center.service.unstructured.node.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Description:
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2017年10月11日 上午10:35:11
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class Constant {

	private static final Logger logger = LoggerFactory.getLogger(Constant.class);

	public final static int PAGE_NUM = 10000;
	/**
	 * 消息队列使用
	 */
	public final static String AJBS = "AJBS";
	public final static String FYDM = "FYDM";
	public final static String AJLX = "AJLX";
	public final static String AJZT = "AJZT";
	public final static String LEVEL = "LEVEL";
	public final static String LARQ = "LARQ";
	public final static String AH = "AH";
	public final static String CBRXM = "CBRXM";
	public final static String AJSOURCE = "AJSOURCE";
	public final static String FLAG = "FLAG";
	public final static String MSG = "MSG";

	public final static String ERROR_STR = "-1";
	public final static String CORRECT_STR = "1";
	/**
	 * 线程判断
	 */
	public final static String ENABLE = "enable";
	public final static String DISABLE = "disable";
	public final static String YES = "YES";
	public final static String NO = "NO";

	// 线程名称前缀
	public final static String RW = "RW_";

	/**
	 * 数据抽取完毕发送的队列名
	 */
	public final static String READY_QUEUE = "readyQueue";
	public final static String ERROR_QUEUE = "errorQueue";
	public final static String NEGATIVE_QUEUE = "negativeQueue";

	public static Integer n_zore = 0;

	public static Integer n_one = 1;

	public static Integer n_negativen_one = -1;

	public static Integer n_two = 2;

	public static Integer n_Three = 3;

	public static Integer n_Four = 4;

	public static Integer n_five = 5;

	public static Integer n_six = 6;

	// 表名
	public static String C_TEMP_EAJ_JZ = "TEMP_EAJ_JZ";

	public static String C_TEMP_EAJ_SSJCYX = "TEMP_EAJ_SSJCYX";

	public static String C_TEMP_EAJ_MLXX = "TEMP_EAJ_MLXX";

	public static String C_TEMP_EAJ_MLXX_GC = "TEMP_EAJ_MLXX_GC";

	public static String C_TEMP_EAJ_JZWJ_ALL_NEW = "TEMP_EAJ_JZWJ_ALL_NEW";

	public static String C_TEMP_EAJ_JZWJ_ALL = "TEMP_EAJ_JZWJ_ALL";
	// 前缀
	public static String C_EAJ_MLXX = "_EAJ_MLXX";
	public static String C_EAJ_JZWJ_ALL_NEW = "_EAJ_JZWJ_ALL_NEW";
	public static String C_EAJ_JZ = "_EAJ_JZ";
	public static String C_EAJ_JZWJ_ALL = "_EAJ_JZWJ_ALL";
	public static String C_EAJ_MLXX_GC = "_EAJ_MLXX_GC";
	public static String C_EAJ_SSJCYX = "_EAJ_SSJCYX";

	public static String C_AJBX_ = "ajbx_";
	// 操作类型
	public static String C_TYPE_INSERT = "insert";
	public static String C_TYPE_DELETE = "delete";
	public static String C_TYPE_UPDATE = "update";

	// 对比后的对列sourecid
	public static String C_INCREMENT_AJSOURCEID = "007";
	/**
	 * 锁ID前缀
	 */
	public static String LOCK_NO = "l.";
	
	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static ConcurrentMap toMap(Object bean) {
		Class<? extends Object> clazz = bean.getClass();
		ConcurrentMap<Object, Object> returnMap = new ConcurrentHashMap<Object, Object>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = null;
					result = readMethod.invoke(bean, new Object[0]);
					if (null != propertyName) {
						propertyName = propertyName.toString();
					}
					if (result == null) {
						returnMap.put(propertyName, "");
					} else {
						returnMap.put(propertyName, result);
					}
				}
			}
		} catch (IntrospectionException e) {
			logger.error("toMap错误：分析类属性失败" + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("toMap错误：实例化 JavaBean 失败" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("toMap错误：映射错误" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("toMap错误：调用属性的 setter 方法失败" + e.getMessage());
		}
		return returnMap;
	}

	/**
	 * 根据通达海业务数据的案件状态转为标准的案件状态
	 * 
	 * @param ajzt
	 * @return
	 */
	public static String getAjzt(String ajzt) {
		Integer nAjzt = Integer.valueOf(ajzt);

		if (nAjzt >= 300 && nAjzt < 400) {
			return String.valueOf("3");

		} else if (nAjzt >= 400 && nAjzt < 500) {
			return String.valueOf("4");

		} else if (nAjzt >= 500 && nAjzt < 800) {
			return String.valueOf("5");

		} else if (nAjzt >= 800 && nAjzt < 970) {
			return String.valueOf("7");

		} else if (nAjzt >= 970) {
			return String.valueOf("8");

		}
		return ajzt;

	}

	// java根据线程ID中断线程组中的某一个线程
	// 更多 0
	// java多线程
	/**
	 * Interrupt the <code>Thread</code> using it thread id
	 */
	public static synchronized boolean interruptThread(String threadName) {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		Thread[] threads = new Thread[threadGroup.activeCount()];
		threadGroup.enumerate(threads);

		for (Thread thread : threads) {
			if (thread != null && thread.getName() == threadName) {
				if (Thread.State.RUNNABLE != thread.getState()) {
					try {
						thread.interrupt();
						return true;
					} catch (Throwable t) {
						; // Swallow any exceptions.
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取任务sql
	 * 
	 * @param src
	 * @param nodeId
	 * @return
	 */
	public static String getSql(String src, String nodeId) {
		int srca = src.indexOf("<" + nodeId + ">") + nodeId.length() + n_two;
		int srcb = src.indexOf("</" + nodeId + ">");
		return src.substring(srca, srcb);
	}
}
