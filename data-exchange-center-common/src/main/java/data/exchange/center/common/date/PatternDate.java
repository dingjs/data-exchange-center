package data.exchange.center.common.date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Description:日期匹配
 * <p>Company: xinya </p>
 * <p>Date:2017年10月16日 上午11:53:55</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class PatternDate {
	
	/**
	 * datetime
	 */
	public static final String datetime = "datetime";
	/**
	 * date
	 */
	public static final String date = "date";

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月16日 上午11:53:39
	 * @param dateStr
	 * @return
	 */
	public static boolean patternDate(String dateStr){
		String eL = "(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}
	
	/**
	 * 
	 * @function 日期格式校验
	 * @author wenyuguang
	 * @creaetime 2017年12月22日 上午11:27:03
	 * @param dateStr
	 * @param type 为date时为yyyyMMdd校验，datetime为yyyyMMddHHmmss格式校验
	 * @return
	 */
	public static boolean patternDate(String dateStr, String type){
		String eL = "(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])";
		String str = "^((?:19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0\\d|1\\d|2[0-3])(0\\d|[1-5]\\d)(0\\d|[1-5]\\d)$";//yyyyMMddHHmmss  
		if(type.equalsIgnoreCase("date")) {
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(dateStr);
			return m.matches();
		}else if(type.equalsIgnoreCase("datetime")){
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(dateStr);
			return m.matches();
		}
		return false;
	}
}
