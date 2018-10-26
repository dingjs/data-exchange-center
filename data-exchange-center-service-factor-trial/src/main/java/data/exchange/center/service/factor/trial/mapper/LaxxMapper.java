package data.exchange.center.service.factor.trial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.factor.trial.domain.Laxx;

@Mapper
public interface LaxxMapper {

	List<Laxx> getLaxx(Map<String, Object> map) throws Exception;
}
