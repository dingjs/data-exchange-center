package data.exchange.center.logback;

import ch.qos.logback.classic.PatternLayout;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月1日 下午3:06:05</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class SgyLoggerPatternLayout extends PatternLayout {
	static {
		defaultConverterMap.put("sgyLoggerPatternLayout", SgyLogerConvert.class.getName());
	}
}