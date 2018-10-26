package data.exchange.center.api.gateway.mapper;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.api.gateway.domain.Statistics;

@Mapper
public interface StatisticsMapper {

	void addStatistics(Statistics statistics) throws Exception;
	
}
