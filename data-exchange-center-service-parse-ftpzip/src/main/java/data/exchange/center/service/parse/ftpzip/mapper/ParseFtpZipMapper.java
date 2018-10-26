package data.exchange.center.service.parse.ftpzip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.parse.ftpzip.domain.CallBackInfo;
import data.exchange.center.service.parse.ftpzip.domain.FileInfo;
import data.exchange.center.service.parse.ftpzip.domain.FtpInfo;
import data.exchange.center.service.parse.ftpzip.domain.FtpPathInfo;
import data.exchange.center.service.parse.ftpzip.domain.WsFtpPathInfo;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月19日 下午6:33:22</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface ParseFtpZipMapper {

	/**
	 * 
	 * @function 获取FTP路径信息
	 * @author wenyuguang
	 * @creaetime 2017年10月20日 上午9:39:58
	 * @return
	 * @throws Exception
	 */
	FileInfo getFileInfo(String taskId) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月20日 上午10:03:58
	 * @param ftp
	 * @return
	 * @throws Exception
	 */
	FtpInfo getFtpInfoByFtpAlias(String ftpAlias) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月20日 下午1:35:36
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	List<FtpPathInfo> getFtpPathInfo(String taskId) throws Exception;

	/**
	 * 
	 * @function 保存XML到数据库
	 * @author wenyuguang
	 * @creaetime 2017年10月21日 下午2:22:57
	 * @param params
	 * @throws Exception
	 */
	void saveXml(Map<String, Object> params) throws Exception;

	/**
	 * 
	 * @function 获取文书相关的记录
	 * @author wenyuguang
	 * @creaetime 2017年10月24日 下午8:54:07
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	List<WsFtpPathInfo> getWsFtpPathInfo(String taskId) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年11月1日 上午10:27:32
	 * @param params
	 * @throws Exception
	 */
	void transferTcyToFy(Map<String, Object> params) throws Exception;
	/**
	 * 
	 * @function 反馈信息
	 * @author wenyuguang
	 * @creaetime 2017年12月22日 上午11:19:58
	 * @param params
	 * @throws Exception
	 */
	void callBack(Map<String, Object> params) throws Exception;
	
	CallBackInfo getCallBackInfo(String xtptbh) throws Exception;

	void saveErrMsg(String taskId) throws Exception;

	/**
	 * 
	 * @function 根据taskId清理数据
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 下午12:27:25
	 * @param taskId
	 * @throws Exception
	 */
	void deleteData(String taskId) throws Exception;
}
