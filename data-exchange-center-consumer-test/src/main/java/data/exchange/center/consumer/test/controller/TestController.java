/**
 * a-springcloud-test-customer-two
 * created by yuguang at 2017年4月26日
 */
package data.exchange.center.consumer.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.consumer.test.service.TestClient;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年4月26日 下午10:46:16</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@RestController
public class TestController {

    @Autowired
    TestClient testClient;
    
    @RequestMapping(value = "/adds", method = RequestMethod.GET)
//    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public Integer add() {
        return testClient.add(10, 20);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public Integer adds(@RequestParam("a")Integer a, @RequestParam("b")Integer b) {
        return testClient.add(a, b);
    }
    
    public String addServiceFallback(Integer a, Integer b) {
        return "something error";
    }
}
