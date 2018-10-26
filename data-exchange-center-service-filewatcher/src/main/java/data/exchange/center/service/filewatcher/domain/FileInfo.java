package data.exchange.center.service.filewatcher.domain;

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
	private long fileSize;
	/**
	 * 文件时间戳
	 */
	private Date date;
	/**
	 * 文件所属类型  公安，检察院，政法
	 */
	private String type;
	/**
	 * FTP保存后的路径信息
	 */
	private String filePath;
	
	public FileInfo(String taskId, String fileName, long fileSize, Date date, String type, String filePath) {
		super();
		this.taskId = taskId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.date = date;
		this.type = type;
		this.filePath = filePath;
	}
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
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
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
	@Override
	public String toString() {
		return "FileInfo [taskId=" + taskId + ", fileName=" + fileName + ", fileSize=" + fileSize + ", date=" + date
				+ ", type=" + type + ", filePath=" + filePath + "]";
	}
}
