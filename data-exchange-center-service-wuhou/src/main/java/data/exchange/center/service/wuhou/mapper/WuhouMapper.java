package data.exchange.center.service.wuhou.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.wuhou.domain.AjbsInfo;
import data.exchange.center.service.wuhou.domain.AjbsList;
import data.exchange.center.service.wuhou.domain.Ajcl;
import data.exchange.center.service.wuhou.domain.UserInfo;
import data.exchange.center.service.wuhou.domain.XsAjbsInfo;
import data.exchange.center.service.wuhou.domain.XzAjbsInfo;

@Mapper
public interface WuhouMapper {

	List<AjbsInfo> getAjbsInfo(Map<String, Object> params) throws Exception;

	List<Ajcl>     getAjclList(Map<String, Object> params) throws Exception;
	
	List<Ajcl>     getXzAjclList(Map<String, Object> params) throws Exception;

	List<UserInfo> getUserInfo(String fydm) throws Exception;

	List<Map<String, Object>> getBmInfo() throws Exception;

	List<AjbsList> getAjbsList(Map<String, Object> params) throws Exception;
	
	List<XzAjbsInfo> getXzAjbsInfo(Map<String, Object> params) throws Exception;
	
	List<XsAjbsInfo> getXsAjbsInfo(Map<String, Object> params) throws Exception;
	
	
}
