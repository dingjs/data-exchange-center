package data.exchange.center.service.filewatcher.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

	void addLog(Map<String, Object> param);

}
