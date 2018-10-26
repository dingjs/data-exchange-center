package data.exchange.center.monitor.service;

import java.util.List;

import data.exchange.center.monitor.domain.FileList;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月24日 下午5:18:04</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface CaseManageService {

	/**
	 * 
	 * @function 根据案件标识获取文件列表
	 * @author wenyuguang
	 * @creaetime 2017年8月1日 下午3:45:36
	 * @param ajbs
	 * @return
	 */
	List<FileList> getFileList(String ajbs);
}
