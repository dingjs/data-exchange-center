package data.exchange.center.service.parse.ftpzip.service;

import java.util.Map;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月20日 上午9:45:50</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface ParseFtpZipService {

	/**
	 * 
	 * @function 解压并解析zip
	 * @author wenyuguang
	 * @creaetime 2017年10月20日 上午9:46:47
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> unzipAndParse(String taskId) throws Exception;
	
	/**
	 * 
	 * @function 解析xml数据
	 * @author wenyuguang
	 * @creaetime 2017年10月20日 下午1:06:54
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public Object parseXml(String xml) throws Exception;

	public Map<String, Object> callBack(String xtptbh, String lcjdbh, String lcslbh, 
			String rwh, String jsdwbm, String jsdwlx,
			String jsdwmc, String fsdwlx, String fsdwbm, 
			String fsdwmc, String jgzt, String ztms, String fhsj, 
			String ywlcbm, String jdbm) throws Exception;
	
	public Map<String, Object> handleCallBack(String xtptbh) throws Exception;

	/**
	 * 
	 * @function 保存错误消息用于反馈
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 上午10:32:08
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveErrMsg(String taskId) throws Exception;
}
