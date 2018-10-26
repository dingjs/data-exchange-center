package data.exchange.center.consumer.test.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import data.exchange.center.consumer.test.service.TestClient;

/**
 * 
 * @author yuguang
 *
 */
@Component
public class TestClientHystrix implements TestClient{

	@Override
	public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
		return -9999;
	}

}
