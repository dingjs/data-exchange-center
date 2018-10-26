package data.exchange.center.service.sfgk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;

import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.util.Code;
import data.exchange.center.service.sfgk.util.XsdName;
import data.exchange.center.service.sfgk.util.rsa.RSAUtils;
import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月27日 上午9:34:45</p>
 * @author Tony
 * @version 1.0
 *
 */
@RestController
public class SfgkController {

	private static Logger logger = LoggerFactory.getLogger(SfgkController.class);
	
	@Autowired
	private SfgkService sfgkService;
	@Autowired
	@Qualifier("ktggTaskService")
	private TaskService ktggTaskService;
	@Autowired
	@Qualifier("ajckTaskService")
	private TaskService ajckTaskService;
	@Autowired
	@Qualifier("ajcxTaskService")
	private TaskService ajcxTaskService;
	@Autowired
	@Qualifier("ajgkTaskService")
	private TaskService ajgkTaskService;
	@Autowired
	@Qualifier("dxTaskService")
	private TaskService dxTaskService;
	@Autowired
	@Qualifier("fygkfsTaskService")
	private TaskService fygkfsTaskService;
	@Autowired
	@Qualifier("wzfwlTaskService")
	private TaskService wzfwlTaskService;
	@Autowired
	@Qualifier("yhdlTaskService")
	private TaskService yhdlTaskService;

	/**
	 * 
	 * @function 司法公开，根据业务类型判断业务
	 * @author Tony
	 * @creaetime 2018年4月25日 上午10:36:48
	 * @param YWLX
	 * @param GXJSSJ
	 * @param GXKSSJ
	 * @param FYBH
	 * @param VERSION
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/scsfgk", method = RequestMethod.POST)
	public Object scsfgk(@RequestBody String param) throws Exception {
		logger.info(String.format("完整请求加密参数：%s", URLDecoder.decode(param, "UTF-8")));
		String str = URLDecoder.decode(param, "UTF-8").replaceAll("PARAMS=", "");
		Map<String ,String> mapType = null;
		try {
			byte[] base = RSAUtils.decrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
					Base64.decodeBase64(str.getBytes("UTF-8")));
			String json = new String(base,"UTF-8");
			logger.info(String.format("收到数据请求:%s",json));
			mapType = JSON.parseObject(json,Map.class);
		} catch (Exception e) {
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("CODE", Code.CODE_ERROR_400);
			retMap.put("MESSAGE", "参数错误");
			return JSON.toJSONString(retMap);
		}
		
		String YWLX = mapType.get("YWLX");
//		String AJLX = mapType.get("AJLX");
		String GXJSSJ = mapType.get("GXJSSJ");
		String GXKSSJ = mapType.get("GXKSSJ");
		String FYBH = mapType.get("FYBH");
		String VERSION = mapType.get("VERSION");
		if (StringUtils.isEmpty(GXJSSJ) && StringUtils.isEmpty(GXKSSJ)) {
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("CODE", Code.CODE_ERROR_400);
			retMap.put("MESSAGE", "参数错误");
			return JSON.toJSONString(retMap);
		}
		if (YWLX.equals("AJXX")) {
			return sfgkService.ajxx(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("FYGKFS")) {
			return sfgkService.fygkfs(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("KTGG")) {
			return sfgkService.ktgg(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("AJCK")) {
			return sfgkService.ajck(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("WZFWL")) {
			return sfgkService.wzfwl(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("YHDL")) {
			return sfgkService.yhdl(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("AJCX")) {
			return sfgkService.ajcx(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else if (YWLX.equals("DX")) {
			return sfgkService.dx(YWLX, GXJSSJ, GXKSSJ, FYBH, VERSION);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("VERSION", VERSION);
			map.put("FYBH", FYBH);
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("CODE", Code.CODE_OK_200);
			retMap.put("MESSAGE", "成功");
			retMap.put("DATA", map);
			return JSON.toJSONString(retMap);
		}
	}

	/**
	 * 
	 * @function 下载压缩包
	 * @author Tony
	 * @creaetime 2018年4月25日 下午5:28:23
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download/{zipname:.+}", method = RequestMethod.GET)
	public void download(@PathVariable("zipname") String zipname, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info(String.format("收到下载请求:%s",zipname));
		String path = null;
		try {
			path = sfgkService.selectDownFilePath(zipname);
		} catch (Exception e1) {
			e1.printStackTrace();
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("CODE", Code.CODE_ERROR_404);
			retMap.put("MESSAGE", "资源不存在");
			PrintWriter out = null;  
			try {
				out = response.getWriter();
				out.append(JSON.toJSONString(retMap));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {  
		        if (out != null) {  
		            out.close();  
		        }  
		    } 
		}
		File downloadFile = new File(path);
		ServletContext context = request.getServletContext();
		String mimeType = context.getMimeType(path);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
			System.out.println("context getMimeType is null");
		}
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		try {
			InputStream myStream = new FileInputStream(path);
			IOUtils.copy(myStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @function 测试拉取昨天的数据
	 * @author Tony
	 * @creaetime 2018年4月28日 下午1:35:24
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() throws Exception {
//		ktggTaskService.startTask();
//		ajckTaskService.startTask();
		ajgkTaskService.startTask();
//		dxTaskService.startTask();
//		fygkfsTaskService.startTask();
//		wzfwlTaskService.startTask();
//		yhdlTaskService.startTask();
	}
	
	/**
	 * 
	 * @function 测试校验
	 * @author Tony
	 * @creaetime 2018年4月28日 下午1:35:43
	 * @throws Exception
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public void validate() throws Exception {
		System.out.println(new Xmlvalidate().validateXml(XsdName.KTGG, "D:\\SFGK\\T_SPGK_KTGG_2.XML"));
	}
}
