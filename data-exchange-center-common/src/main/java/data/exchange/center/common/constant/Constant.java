package data.exchange.center.common.constant;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年5月18日 上午11:54:47</p>
 * @author Tony
 * @version 1.0
 *
 */
public class Constant {

	public final static int PAGE_NUM = 10000;
	/**
	 * 消息队列使用
	 */
	public final static String AJBS     = "AJBS";
	public final static String FYDM     = "FYDM";
	public final static String AJLX     = "AJLX";
	public final static String AJZT     = "AJZT";
	public final static String LEVEL    = "LEVEL";
	public final static String TYPE     = "TYPE";
	public final static String LARQ     = "LARQ";
	public final static String AH       = "AH";
	public final static String AJSOURCE = "AJSOURCE";
	public final static String FLAG     = "FLAG";
	public final static String MSG      = "MSG";

	public final static String ERROR_STR = "-1";
	public final static String CORRECT_STR = "1";
	
	public final static String RECOVERY = "recovery";
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
	 * RTF处理锁定
	 */
	public final static String RTF_LK = "RTF.L";
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
	 * 非结构化锁ID前缀
	 */
//	public static String LOCK_NO = "redis_distribution_lock_no_ajbs_";
	public static String LOCK_NO = "l.";
	/**
	 * 结构化锁ID前缀
	 */
	public static String BUZ_LOCK_NO = "buz.data";
	
	public static String C_BUZ_SJLX_SPZT = "spzt";
	public static String C_BUZ_SJLX_DELETE = "delete";
	public static String C_BUZ_SJLX_INSERT = "insert";
	public static String SJLX = "SJLX";
	/**
	 * 非结构化数据收取显示
	 */
	public static String C_ERROR_CONNECT = "连接出现错误";
	public static String C_NORMAL_CONNECT = "连接正常";
	public static String C_IN_CONNECT = "正在连接";
	public static String C_NO_CONNECT = "未连接";
	
	public static String C_MQSTATE = "MQSTATE";
	public static String C_ZXDSTATE = "ZXDSTATE";
	public static String C_YWDSTATE = "YWDSTATE";
	public static String C_REDISTATE = "REDISTATE";
	
	
	
	
	
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
	//
	  
}
