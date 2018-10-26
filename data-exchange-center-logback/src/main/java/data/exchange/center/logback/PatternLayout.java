package data.exchange.center.logback;

import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.classic.joran.action.ContextNameAction;
import ch.qos.logback.classic.pattern.CallerDataConverter;
import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.pattern.ContextNameConverter;
import ch.qos.logback.classic.pattern.DateConverter;
import ch.qos.logback.classic.pattern.EnsureExceptionHandling;
import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.pattern.FileOfCallerConverter;
import ch.qos.logback.classic.pattern.LevelConverter;
import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import ch.qos.logback.classic.pattern.LoggerConverter;
import ch.qos.logback.classic.pattern.MDCConverter;
import ch.qos.logback.classic.pattern.MarkerConverter;
import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.pattern.MethodOfCallerConverter;
import ch.qos.logback.classic.pattern.NopThrowableInformationConverter;
import ch.qos.logback.classic.pattern.PropertyConverter;
import ch.qos.logback.classic.pattern.RelativeTimeConverter;
import ch.qos.logback.classic.pattern.ThreadConverter;
import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.pattern.PatternLayoutBase;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月1日 下午3:07:25</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class PatternLayout extends PatternLayoutBase<ILoggingEvent> {

	public static final Map<String, String> defaultConverterMap = new HashMap<String, String>();

	static {

		defaultConverterMap.put("d", DateConverter.class.getName());
		defaultConverterMap.put("date", DateConverter.class.getName());

		defaultConverterMap.put("r", RelativeTimeConverter.class.getName());
		defaultConverterMap.put("relative", RelativeTimeConverter.class.getName());

		defaultConverterMap.put("level", LevelConverter.class.getName());
		defaultConverterMap.put("le", LevelConverter.class.getName());
		defaultConverterMap.put("p", LevelConverter.class.getName());

		defaultConverterMap.put("t", ThreadConverter.class.getName());
		defaultConverterMap.put("thread", ThreadConverter.class.getName());

		defaultConverterMap.put("lo", LoggerConverter.class.getName());
		defaultConverterMap.put("logger", LoggerConverter.class.getName());
		defaultConverterMap.put("c", LoggerConverter.class.getName());

		defaultConverterMap.put("m", MessageConverter.class.getName());
		defaultConverterMap.put("msg", MessageConverter.class.getName());
		defaultConverterMap.put("message", MessageConverter.class.getName());

		defaultConverterMap.put("C", ClassOfCallerConverter.class.getName());
		defaultConverterMap.put("class", ClassOfCallerConverter.class.getName());

		defaultConverterMap.put("M", MethodOfCallerConverter.class.getName());
		defaultConverterMap.put("method", MethodOfCallerConverter.class.getName());

		defaultConverterMap.put("L", LineOfCallerConverter.class.getName());
		defaultConverterMap.put("line", LineOfCallerConverter.class.getName());

		defaultConverterMap.put("F", FileOfCallerConverter.class.getName());
		defaultConverterMap.put("file", FileOfCallerConverter.class.getName());

		defaultConverterMap.put("X", MDCConverter.class.getName());
		defaultConverterMap.put("mdc", MDCConverter.class.getName());

		defaultConverterMap.put("ex", ThrowableProxyConverter.class.getName());
		defaultConverterMap.put("exception", ThrowableProxyConverter.class.getName());
		defaultConverterMap.put("throwable", ThrowableProxyConverter.class.getName());

		defaultConverterMap.put("xEx", ExtendedThrowableProxyConverter.class.getName());
		defaultConverterMap.put("xException", ExtendedThrowableProxyConverter.class.getName());
		defaultConverterMap.put("xThrowable", ExtendedThrowableProxyConverter.class.getName());

		defaultConverterMap.put("nopex", NopThrowableInformationConverter.class.getName());
		defaultConverterMap.put("nopexception", NopThrowableInformationConverter.class.getName());

		defaultConverterMap.put("cn", ContextNameAction.class.getName());
		defaultConverterMap.put("contextName", ContextNameConverter.class.getName());

		defaultConverterMap.put("caller", CallerDataConverter.class.getName());

		defaultConverterMap.put("marker", MarkerConverter.class.getName());

		defaultConverterMap.put("property", PropertyConverter.class.getName());

		defaultConverterMap.put("n", LineSeparatorConverter.class.getName());
	}

	public PatternLayout() {
		this.postCompileProcessor = new EnsureExceptionHandling();
	}

	public Map<String, String> getDefaultConverterMap() {
		return defaultConverterMap;
	}

	public String doLayout(ILoggingEvent event) {
		if (!isStarted()) {
			return CoreConstants.EMPTY_STRING;
		}
		return writeLoopOnConverters(event);
	}

}