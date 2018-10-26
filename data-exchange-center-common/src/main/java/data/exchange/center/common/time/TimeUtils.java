package data.exchange.center.common.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * Description:时间工具类
 * <p>Company: xinya </p>
 * <p>Date:2017年10月17日 上午11:06:23</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class TimeUtils {

	/**
	 * 时间间隔参数5秒，单位毫秒
	 */
	public static long interval           = 5000;
	/**
	 * 1分钟  60*1秒，单位毫秒
	 */
	public static final long ONE_SECOND   = 60000*1;
	/**
	 * 2分钟  60*2秒，单位毫秒
	 */
	public static final long TWO_SECOND   = 60000*2;
	/**
	 * 3分钟  60*3秒，单位毫秒
	 */
	public static final long THREE_SECOND = 60000*3;
	/**
	 * 4分钟  60*4秒，单位毫秒
	 */
	public static final long FOUR_SECOND  = 60000*4;
	/**
	 * 5分钟  60*5秒，单位毫秒
	 */
	public static final long FIVE_SECOND  = 60000*5;
	/**
	 * 6分钟  60*6秒，单位毫秒
	 */
	public static final long SIX_SECOND   = 60000*6;
	/**
	 * 7分钟  60*7秒，单位毫秒
	 */
	public static final long SEVEN_SECOND = 60000*7;
	/**
	 * 8分钟  60*8秒，单位毫秒
	 */
	public static final long EIGHT_SECOND = 60000*8;
	/**
	 * 9分钟  60*9秒，单位毫秒
	 */
	public static final long NINE_SECOND = 60000*9;
	/**
	 * 10分钟  60*10秒，单位毫秒
	 */
	public static final long TEN_SECOND = 60000*10;
	/**
	 * 1天 24*60*60 单位毫秒
	 */
	public static final long ONE_DAY    = 24*60*60*1000;
	/**
	 * 30天 30*24*60*60 单位秒
	 */
	public static final long THIRTY_DAY = 30*24*60*60;
	
	/**
	 * 
	 * @function 获取当前时间 格式为：yyyy-MM-dd HH:mm:ss
	 * @author wenyuguang
	 * @creaetime 2018年2月7日 下午3:46:35
	 * @return
	 */
	public static String getNowTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 
	 * @function 获取当前时间 格式为：yyyyMMddHHmmss
	 * @author Tony
	 * @creaetime 2018年4月26日 下午6:33:10
	 * @return
	 */
	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	
	/**
	 * 
	 * @function 获取当前时间 格式为：yyyyMMddHHmmss
	 * @author Tony
	 * @creaetime 2018年4月26日 下午6:33:10
	 * @return
	 */
	public static String getNTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 
	 * @function 获取当前日期 格式为：yyyyMMddHHmmss
	 * @author Tony
	 * @creaetime 2018年4月26日 下午6:33:10
	 * @return
	 */
	public static String getNowDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 
	 * @function 获取昨天日期 格式为：yyyyMMdd
	 * @author Tony
	 * @creaetime 2018年4月26日 下午6:33:10
	 * @return
	 */
	public static String getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
}
