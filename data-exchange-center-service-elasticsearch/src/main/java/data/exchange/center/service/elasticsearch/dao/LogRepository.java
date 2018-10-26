package data.exchange.center.service.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import data.exchange.center.service.elasticsearch.domain.LogObject;

/**
 * 
 * Description:持久层
 * <p>Company: xinya </p>
 * <p>Date:2017年7月4日 下午4:04:58</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface LogRepository extends ElasticsearchRepository<LogObject, String> {

}