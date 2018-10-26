package data.exchange.center.service.pujiang.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.pujiang.domain.AjbsInfo;
import data.exchange.center.service.pujiang.domain.AjbsList;
import data.exchange.center.service.pujiang.domain.Ajcl;
import data.exchange.center.service.pujiang.domain.UserInfo;

@Mapper
public interface PujiangMapper {

	List<AjbsInfo> getAjbsInfo(Map<String, Object> params) throws Exception;

	List<Ajcl>     getAjclList(Map<String, Object> params) throws Exception;

	List<UserInfo> getUserInfo(String fydm) throws Exception;

	List<Map<String, Object>> getBmInfo() throws Exception;

	List<AjbsList> getAjbsList(Map<String, Object> params) throws Exception;
}
