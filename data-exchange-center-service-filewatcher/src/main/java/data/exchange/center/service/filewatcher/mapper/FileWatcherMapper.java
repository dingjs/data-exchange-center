package data.exchange.center.service.filewatcher.mapper;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.filewatcher.domain.FileInfo;
import data.exchange.center.service.filewatcher.domain.FtpInfo;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年9月19日 下午3:16:23</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface FileWatcherMapper {

	/**
	 * 
	 * @function 根据ftp别名获取ftp账号等信息
	 * @author wenyuguang
	 * @creaetime 2017年9月27日 下午2:32:54
	 * @param ftpAlias
	 * @return
	 * @throws Exception
	 */
	FtpInfo getFtpInfoByFtpAlias(String ftpAlias) throws Exception;

	/**
	 * 
	 * @function 保存ftp文件相关信息
	 * @author wenyuguang
	 * @creaetime 2017年10月17日 下午2:28:49
	 * @param fileInfo
	 */
	void saveFtpFileInfo(FileInfo fileInfo);

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年12月22日 下午4:17:39
	 * @param string
	 * @return
	 */
	String getXtptbh(String taskId);

	void deleteFtpFileInfo(FileInfo fileInfo); 
}
