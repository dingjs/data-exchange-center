package data.exchange.center.service.download.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.download.service.DownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月13日 上午11:08:11</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
@Api("文件下载api")
public class DownloadController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	DownloadService downloadService;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 上午11:05:09
	 * @param key ajbs_xh
	 * @return
	 */
	@ApiOperation("文件下载")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name="key",dataType="String",required=true,value="文件id",defaultValue=""),
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@RequestMapping("/download")
	public byte[] download(@RequestParam("key")String key) {
		
		byte[] bytes = null;
		try {
			bytes = downloadService.download(key);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return bytes;
	}
	
	/**
	 * 
	 * @function key  为ajbs
	 * @author wenyuguang
	 * @creaetime 2018年1月22日 下午5:11:26
	 * @param key
	 * @return
	 */
	@RequestMapping("/getFilePath")
	public String getFilePath(@RequestParam("key")String key) {
		
		String filePath = null;
		try {
			filePath = downloadService.getFilePath(key);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return filePath;
	}
}
