package data.exchange.center.service.gaoxin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.gaoxin.domain.AjbsInfo;
import data.exchange.center.service.gaoxin.domain.AjbsList;
import data.exchange.center.service.gaoxin.domain.Ajcl;
import data.exchange.center.service.gaoxin.domain.UserInfo;

@Mapper
public interface GaoxinMapper {

	List<AjbsInfo> getAjbsInfo(Map<String, Object> params) throws Exception;

	List<Ajcl>     getAjclList(Map<String, Object> params) throws Exception;

	List<UserInfo> getUserInfo(String fydm) throws Exception;

	List<Map<String, Object>> getBmInfo() throws Exception;

	List<AjbsList> getAjbsList(Map<String, Object> params) throws Exception;
}
