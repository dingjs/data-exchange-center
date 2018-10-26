package data.exchange.center.service.download.service;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月12日 下午4:55:37</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface DownloadService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月12日 下午4:55:35
	 * @param key
	 * @return
	 * @throws Exception
	 */
	byte[] download(String key) throws Exception;

	/**
	 * 
	 * @function 获取文件ftp路径信息
	 * @author wenyuguang
	 * @creaetime 2017年9月30日 下午1:26:53
	 * @param key
	 * @return
	 * @throws Exception
	 */
	String getFilePath(String key) throws Exception;

}
