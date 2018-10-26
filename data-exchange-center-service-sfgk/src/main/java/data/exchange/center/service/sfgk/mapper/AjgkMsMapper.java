package data.exchange.center.service.sfgk.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AjgkMsMapper {

	void getMsAjgkxx(Map<String, Object> params);
}
