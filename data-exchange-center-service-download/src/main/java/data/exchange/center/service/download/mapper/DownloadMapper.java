package data.exchange.center.service.download.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import data.exchange.center.service.download.domain.FileBlob;
import data.exchange.center.service.download.domain.FilePath;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月12日 下午4:43:00</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface DownloadMapper {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年1月22日 下午5:22:16
	 * @param ajbs
	 * @param xh
	 * @return
	 * @throws Exception
	 */
	FilePath getFilePath(@Param("ajbs") String ajbs, @Param("xh") String xh) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 上午11:01:06
	 * @param param
	 * @return
	 * @throws Exception
	 */
	FileBlob getBlob(Map<String, String> param) throws Exception;

}
