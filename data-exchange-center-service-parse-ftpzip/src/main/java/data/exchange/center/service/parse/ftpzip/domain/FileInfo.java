package data.exchange.center.service.parse.ftpzip.domain;

import java.io.Serializable;
import java.util.Date;

public class FileInfo implements Serializable {

	/**
	 * 2017年10月19日下午5:37:41
	 * yuguang
	 */
	private static final long serialVersionUID = 3622496057399849021L;
	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private String fileSize;
	/**
	 * 文件时间戳
	 */
	private Date date;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * FTP保存后的路径信息
	 */
	private String filePath;
	
	private Date insertTime;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}
