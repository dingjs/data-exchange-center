package data.exchange.center.api.gateway.domain;

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
	private String service;
	private String method;
	private String consumer;
	private String provider;
	private String type;
	private long   invokeTime;
	private int    success;
	private int    failure;
	private long    elapsed;
	private int    concurrent;
	private int    maxElapsed;
	private int    maxConcurrent;
	public String getInvokeDate() {
		return invokeDate;
	}
	public void setInvokeDate(String invokeDate) {
		this.invokeDate = invokeDate;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getInvokeTime() {
		return invokeTime;
	}
	public void setInvokeTime(long invokeTime) {
		this.invokeTime = invokeTime;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFailure() {
		return failure;
	}
	public void setFailure(int failure) {
		this.failure = failure;
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
	public int getMaxElapsed() {
		return maxElapsed;
	}
	public void setMaxElapsed(int maxElapsed) {
		this.maxElapsed = maxElapsed;
	}
	public int getMaxConcurrent() {
		return maxConcurrent;
	}
	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}
}