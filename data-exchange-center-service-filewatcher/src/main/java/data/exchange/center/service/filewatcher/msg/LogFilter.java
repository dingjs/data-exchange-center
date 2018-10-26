package data.exchange.center.service.filewatcher.msg;

import java.text.DateFormat;
import java.util.Date;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogFilter extends Filter<Object>{

	@Override
	public FilterReply decide(Object event) {
		LoggerMessage loggerMessage = new LoggerMessage(
                ((ILoggingEvent) event).getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(((ILoggingEvent) event).getTimeStamp())),
                ((ILoggingEvent) event).getThreadName(),
                ((ILoggingEvent) event).getLoggerName(),
                ((ILoggingEvent) event).getLevel().levelStr
        );
        LoggerQueue.getInstance().push(loggerMessage);
        return FilterReply.ACCEPT;
	}
}  