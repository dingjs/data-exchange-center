package data.exchange.center.service.log.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

	void logger(Map<String, Object> param) throws Exception;

	void deleteLog(Map<String, String> param) throws Exception;
}
