package data.exchange.center.common.log;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import data.exchange.center.common.log.Statistics;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月6日 下午5:20:34</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@FeignClient("service-statistics")
public interface StatisticsService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年11月6日 下午5:23:55
	 * @param statistics
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStatistics" ,method = RequestMethod.POST)
    public Object addStatistics(Statistics statistics) throws Exception;
}
