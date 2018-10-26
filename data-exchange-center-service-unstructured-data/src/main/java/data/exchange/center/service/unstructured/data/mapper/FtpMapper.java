package data.exchange.center.service.unstructured.data.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.unstructured.data.domain.Count;
import data.exchange.center.service.unstructured.data.domain.FTPClientInfoConfigure;
import data.exchange.center.service.unstructured.data.domain.UrlInfo;

@Mapper
public interface FtpMapper {

	List<Object> handleTempUnstructureData(Map<String, Object> param);
	/**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月17日 下午12:47:23
     * @param fydm
     * @param ftpName 
     * @throws Exception
     * @return
     */
    FTPClientInfoConfigure getFtpInfo(Map<String, Object> param) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月17日 下午1:16:35
     * @param ajbs
     * @return
     */
    List<UrlInfo> getUrlInfo(String ajbs) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年6月2日 下午6:14:09
     * @param string
     * @return
     */
    List<Count> getFtpCount(String ajbs) throws Exception;
    /**
     * 
     * @function 删除操作记录表
     * @author wenyuguang
     * @creaetime 2018年1月9日 下午6:34:44
     * @param params
     */
	void deleteDC_FJGH_EXTOPER(Map<String, Object> params) throws Exception;
	/**
	 * 处理路径信息
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年1月12日 下午1:08:57
	 * @param param
	 * @throws Exception
	 */
	void handlePath(Map<String, Object> param) throws Exception;
	/**
	 * 
	 * @function 处理问题FTP记录
	 * @author Tony
	 * @creaetime 2018年5月9日 上午10:20:28
	 * @param params
	 */
	void handleError(Map<String, Object> params);
}
