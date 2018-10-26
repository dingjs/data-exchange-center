package data.exchange.center.service.shehong.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.shehong.domain.AjbsInfo;
import data.exchange.center.service.shehong.domain.AjbsList;
import data.exchange.center.service.shehong.domain.Ajcl;
import data.exchange.center.service.shehong.domain.UserInfo;

@Mapper
public interface ShehongMapper {

	List<AjbsInfo> getAjbsInfo(Map<String, Object> params) throws Exception;

	List<Ajcl>     getAjclList(Map<String, Object> params) throws Exception;

	List<UserInfo> getUserInfo(String fydm) throws Exception;

	List<Map<String, Object>> getBmInfo() throws Exception;

	List<AjbsList> getAjbsList(Map<String, Object> params) throws Exception;
}
