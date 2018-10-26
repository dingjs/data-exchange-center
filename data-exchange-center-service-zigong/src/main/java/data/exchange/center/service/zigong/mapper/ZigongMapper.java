package data.exchange.center.service.zigong.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.zigong.domain.AjbsList;
import data.exchange.center.service.zigong.domain.Tqtj;

@Mapper
public interface ZigongMapper {

	List<AjbsList> getAjbsList(Map<String, Object> params) throws Exception;

	List<Tqtj> getTqtj(Map<String, Object> params) throws Exception;
	
	List<Tqtj> getZxajTqtj(Map<String, Object> params) throws Exception;
	
	String getTbAjlx(Map<String, Object> params) throws Exception;
}
