package data.exchange.center.service.filewatcher.msg;

public class LoggerMessage{

    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;

    public LoggerMessage(String body, String timestamp, String threadName, String className, String level) {
        this.body = body;
        this.timestamp = timestamp;
        this.threadName = threadName;
        this.className = className;
        this.level = level;
    }

    public LoggerMessage() {
    }

	/**
	 * get body value
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * set body value
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * get timestamp value
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * set timestamp value
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * get threadName value
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * set threadName value
	 * @param threadName the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	/**
	 * get className value
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * set className value
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * get level value
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * set level value
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
    
}