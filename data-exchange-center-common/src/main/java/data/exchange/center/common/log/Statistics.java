package data.exchange.center.common.log;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月6日 下午5:14:16</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class Statistics implements Serializable {
    
    private static final long serialVersionUID = -6921183057683641441L;
    
	private String invokeDate;
	private long   elapsed;
	private int    concurrent;
	private String service;
	private String method;
	private String consumer;
	private String provider;
	private long   invokeTime;
	private int    invokeTimes;
	public String getInvokeDate() {
		return invokeDate;
	}
	public void setInvokeDate(String invokeDate) {
		this.invokeDate = invokeDate;
	}
	public long getElapsed() {
		return elapsed;
	}
	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}
	public int getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(int concurrent) {
		this.concurrent = concurrent;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public long getInvokeTime() {
		return invokeTime;
	}
	public void setInvokeTime(long invokeTime) {
		this.invokeTime = invokeTime;
	}
	public int getInvokeTimes() {
		return invokeTimes;
	}
	public void setInvokeTimes(int invokeTimes) {
		this.invokeTimes = invokeTimes;
	}
	@Override
	public String toString() {
		return "Statistics [invokeDate=" + invokeDate + ", elapsed=" + elapsed + ", concurrent=" + concurrent
				+ ", service=" + service + ", method=" + method + ", consumer=" + consumer + ", provider=" + provider
				+ ", invokeTime=" + invokeTime + ", invokeTimes=" + invokeTimes + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + concurrent;
		result = prime * result + ((consumer == null) ? 0 : consumer.hashCode());
		result = prime * result + (int) (elapsed ^ (elapsed >>> 32));
		result = prime * result + ((invokeDate == null) ? 0 : invokeDate.hashCode());
		result = prime * result + (int) (invokeTime ^ (invokeTime >>> 32));
		result = prime * result + invokeTimes;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistics other = (Statistics) obj;
		if (concurrent != other.concurrent)
			return false;
		if (consumer == null) {
			if (other.consumer != null)
				return false;
		} else if (!consumer.equals(other.consumer))
			return false;
		if (elapsed != other.elapsed)
			return false;
		if (invokeDate == null) {
			if (other.invokeDate != null)
				return false;
		} else if (!invokeDate.equals(other.invokeDate))
			return false;
		if (invokeTime != other.invokeTime)
			return false;
		if (invokeTimes != other.invokeTimes)
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}
	public Statistics(String invokeDate, long elapsed, int concurrent, String service, String method, String consumer,
			String provider, long invokeTime, int invokeTimes) {
		super();
		this.invokeDate = invokeDate;
		this.elapsed = elapsed;
		this.concurrent = concurrent;
		this.service = service;
		this.method = method;
		this.consumer = consumer;
		this.provider = provider;
		this.invokeTime = invokeTime;
		this.invokeTimes = invokeTimes;
	}
}