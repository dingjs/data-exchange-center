package data.exchange.center.common.log;

public class LogLevel {

	public final static String ALL   = "ALL"; 	//各级包括自定义级别
	public final static String DEBUG = "DEBUG"; 	//指定细粒度信息事件是最有用的应用程序调试
	public final static String ERROR = "ERROR"; 	//错误事件可能仍然允许应用程序继续运行
	public final static String FATAL = "FATAL"; 	//指定非常严重的错误事件，这可能导致应用程序中止
	public final static String INFO  = "INFO"; 	//指定能够突出在粗粒度级别的应用程序运行情况的信息的消息
	public final static String OFF   = "OFF"; 	//这是最高等级，为了关闭日志记录
	public final static String TRACE = "TRACE"; 	//指定细粒度比DEBUG更低的信息事件
	public final static String WARN  = "WARN"; 	//指定具有潜在危害的情况
}
