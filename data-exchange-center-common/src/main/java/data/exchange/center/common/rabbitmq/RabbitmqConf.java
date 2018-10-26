package data.exchange.center.common.rabbitmq;

/**
 * 
 * Description:队列配置信息
 * <p>Company: xinya </p>
 * <p>Date:2017年10月26日 上午9:33:33</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class RabbitmqConf {

	/**
	 * 协同平台队列名称
	 */
	public final static String XTBAPT_QUEUE                  = "xtbapt.queue";
	public final static String XTBAPT_EXCHANGE               = "xtbapt.exchange";
	public final static String XTBAPT_ROUTEKEY               = "xtbapt.routekey";
	
	/**
	 * 结构数据从通达海已经同步到数据中心缓存表中，准备进行数据解析填充
	 */
	public final static String UNDO_SYNC_DATA_QUEUE          = "undo.sync.data.queue";
	public final static String UNDO_SYNC_DATA_EXCHANGE       = "undo.sync.data.exchange";
	public final static String UNDO_SYNC_DATA_ROUTEKEY       = "undo.sync.data.routekey";
	/**
	 * 非结构化路径生成等处理
	 */
	public final static String UNDO_SYNC_PATH_QUEUE          = "undo.sync.path.queue";
	public final static String UNDO_SYNC_PATH_EXCHANGE       = "undo.sync.path.exchange";
	public final static String UNDO_SYNC_PATH_ROUTEKEY       = "undo.sync.path.routekey";
	/**
	 * 结构化数据处理完毕队列，准备抽取非结构化数据
	 */
	public final static String UNDO_SYNC_FTP_QUEUE           = "undo.sync.ftp.queue";
	public final static String UNDO_SYNC_FTP_EXCHANGE        = "undo.sync.ftp.exchange";
	public final static String UNDO_SYNC_FTP_ROUTEKEY        = "undo.sync.ftp.routekey";
	/**
	 * 业务数据结构化数据处理队列
	 */
	public final static String BUSINESS_DATA_QUEUE           = "business.data.queue";
	public final static String BUSINESS_DATA_EXCHANGE        = "business.data.exchange";
	public final static String BUSINESS_DATA_ROUTEKEY        = "business.data.routekey";
	
	   public final static String BUSINESS_DATA_QUEUE1           = "business.data.queue1";
	    public final static String BUSINESS_DATA_EXCHANGE1        = "business.data.exchange1";
	    public final static String BUSINESS_DATA_ROUTEKEY1       = "business.data.routekey1";
	/**
	 * RTF转DOC任务队列
	 */
	public final static String RTF_QUEUE                     = "rtf.queue";
	public final static String RTF_EXCHANGE                  = "rtf.exchange";
	public final static String RTF_ROUTEKEY                  = "rtf.routekey";
	/**
	 * RPC队列
	 */
	public final static String RPC_QUEUE                     = "rpc.queue";
	public final static String RPC_EXCHANGE                  = "rpc.exchange";
	public final static String RPC_ROUTEKEY                  = "rpc.routekey";
}
