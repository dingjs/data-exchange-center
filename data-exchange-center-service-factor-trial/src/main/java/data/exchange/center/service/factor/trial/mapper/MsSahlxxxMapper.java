package data.exchange.center.service.factor.trial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsSahlxxxMapper {
	
	List<Object> getAjInfo(Map<String, Object> param) throws Exception;
	
}