package data.exchange.center.api.gateway.service;

import data.exchange.center.api.gateway.domain.Statistics;

public interface StatisticsService {

	void addStatistics(Statistics statistics) throws Exception;
	
}
