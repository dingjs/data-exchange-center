package data.exchange.center.service.sefon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.sefon.domain.Laxx;


@Mapper
public interface LaxxMapper {

	List<Laxx> getLaxx(Map<String, Object> map) throws Exception;
}
