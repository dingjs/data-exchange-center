/**
 * a-springcloud-test-provider-one
 * created by yuguang at 2017年4月26日
 */
package data.exchange.center.service.factor.trial.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.factor.trial.domain.Laxx;
import data.exchange.center.service.factor.trial.domain.MsSahlxx;
import data.exchange.center.service.factor.trial.service.AjxxAllService;
import data.exchange.center.service.factor.trial.service.DownloadService;
import data.exchange.center.service.factor.trial.service.LaxxService;
import data.exchange.center.service.factor.trial.service.impl.AjXmlServiceImpl;
import net.sf.json.JSONArray;

/**
 * Description:
 * <p>
 * Company: pelox
 * </p>
 * <p>
 * Date:2017年4月26日 下午10:20:02
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 **/
@RestController
@Transactional
public class LaxxController {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	LaxxService laxxService;
	@Autowired
	AjxxAllService ajxxAllService;
	@Autowired
	DownloadService downloadService;
	@Autowired
	AjXmlServiceImpl ajXmlServiceImpl;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAjjbxx", method = RequestMethod.GET)
	public Map<String, Object> add(@RequestParam("larq") String larq, @RequestParam("ajjd") String ajjd) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			param.put("larq", larq);
			param.put("ajjd", ajjd);
			laxxService.getAjbsInfo(param);
			List<MsSahlxx> msSahlxxx = (List<MsSahlxx>) param.get("v_cursor1");
			List<Laxx> laxx = (List<Laxx>) param.get("v_cursor2");
			map.put("msSahlxxx", msSahlxxx);
			map.put("laxx", laxx);
			returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取案件基本信息错误：参数:" + larq + ","+ ajjd + "; 错误详情:" + e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}

	}

	@RequestMapping(value = "/getAjxxAll", method = RequestMethod.GET)
	public Object getAjxxAll(@RequestParam("ajbs") String ajbs,@RequestParam("ajlx") String ajlx) {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = ajxxAllService.getAjbsInfo(ajbs, ajlx);
			JSONArray json = JSONArray.fromObject(returnMap); 
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("获取案件所有信息错误:ajbs"+ajbs+"案件类型"+ajlx+"错误详情：" + e.getMessage());
		}
		return null;

	}
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public Object getFile(@RequestParam("ajbs") String ajbs,@RequestParam("xh") String xh) {
		try {
			 return downloadService.download(ajbs+"_"+xh);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载非结构化数据错误:ajbs"+ajbs+"xh"+xh+"错误详情" + e.getMessage());
		}
		return null;

	}
	public static void main(String[] args) {
		final int taskSize =21;
		final int ajs =1000;
		  ExecutorService fixedThreadPool = Executors.newFixedThreadPool(taskSize);  
		  for (int i = 1; i <= taskSize; i++) {  
		   final int index = i;  
		   fixedThreadPool.execute(new Runnable() {  
		    public void run() {  
		     try { 
		    	 if(index<taskSize){
					System.out.println("index:"+index+"rownum:"+(ajs/taskSize)*(index-1)+"endRownum:"+(ajs/taskSize)*index);
				}else{
					System.out.println("index:"+index+"rownum:"+(ajs/taskSize)+"endRownum:"+ajs);
				}
		     } catch (Exception e) {  
		      e.printStackTrace();  
		     }  
		    }  
		   });  
		  }  
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/getAJxml", method = RequestMethod.GET)
	public void getAJxml(@RequestParam("ajs") final Integer ajs) {
		final int taskSize =21;
		  ExecutorService fixedThreadPool = Executors.newFixedThreadPool(taskSize);  
		  for (int i = 1; i <= taskSize; i++) {  
		   final int index = i;  
		   fixedThreadPool.execute(new Runnable() {  
		    public void run() {  
		     try { 
		    	 if(index<taskSize){
		    		 ajXmlServiceImpl.getAjXml((ajs/taskSize)*(index-1), (ajs/taskSize)*index);
				//	System.out.println("rownum"+(ajs/taskSize)*(index-1)+"endRownum"+(ajs/taskSize)*index);
				}else{
					 ajXmlServiceImpl.getAjXml((ajs/taskSize)*(index-1), ajs);
				//	System.out.println("rownum"+(ajs/taskSize)+"endRownum"+ajs);
				}
		     } catch (Exception e) {  
		      e.printStackTrace();  
		     }  
		    }  
		   });  
		  }  
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@RequestMapping(value = "/getMetaxml", method = RequestMethod.GET)
	public void getMetaxml() {
		try {
			ajXmlServiceImpl.getMataXml();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
