package data.exchange.center.api.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.api.gateway.domain.Statistics;
import data.exchange.center.api.gateway.mapper.StatisticsMapper;
import data.exchange.center.api.gateway.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsMapper statisticsMapper;
	
	@Override
	public void addStatistics(Statistics statistics) throws Exception {
		statisticsMapper.addStatistics(statistics);
	}
}
