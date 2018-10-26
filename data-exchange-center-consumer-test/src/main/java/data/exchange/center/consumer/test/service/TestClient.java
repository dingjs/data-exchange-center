/**
 * a-springcloud-test-customer-two
 * created by yuguang at 2017年4月26日
 */
package data.exchange.center.consumer.test.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import data.exchange.center.consumer.test.fallback.TestClientHystrix;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年4月26日 下午10:45:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@FeignClient(value = "service-test", fallback = TestClientHystrix.class)
public interface TestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
