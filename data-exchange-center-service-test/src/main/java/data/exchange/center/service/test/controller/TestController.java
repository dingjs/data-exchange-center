/**
 * a-springcloud-test-provider-one
 * created by yuguang at 2017年4月26日
 */
package data.exchange.center.service.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.test.service.TestService;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年4月26日 下午10:20:02</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@RestController
@Transactional
public class TestController {

    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    TestService testService;
    @Autowired
    data.exchange.center.service.test.service.LoggerService loggerService;
    
    
    @RequestMapping(value = "/log" ,method = RequestMethod.GET)
    public void log() throws Exception {
    	loggerService.logger("serviceName", 111, "id", "ajbs", "lx", "fydm", "srccode", "msg");
    }
    
    
    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
    	logger.info(a+b);
        Integer r = a + b;
        System.err.println(testService.test());
        return r;
    }
    
    @RequestMapping(value = "/testGetPage" ,method = RequestMethod.GET)
    public Object test(@RequestParam("startPage") Integer a, @RequestParam("endPage") Integer b) {
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("otherInfo1", "根据页码取的json串或者文件");
    	returnMap.put("otherInfo2", "根据页码取的json串或者文件");
        return returnMap;
    }
    
    @RequestMapping(value = "/test" ,method = RequestMethod.GET)
    public Object test() {
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("pageNum", "10");
    	returnMap.put("otherInfo1", "json串或者文件");
    	returnMap.put("otherInfo2", "json串或者文件");
        return returnMap;
    }
    
    @RequestMapping(value = "/{serviceName}/{method}" ,method = RequestMethod.GET)
    public Object test1(@PathVariable(value="serviceName") String serviceName,
    		@PathVariable(value="method") String method) {
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("otherInfo1", serviceName);
    	returnMap.put("otherInfo2", method);
        return returnMap;
    }
}
