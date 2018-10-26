package data.exchange.center.service.businessdata.mapper.tdh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.businessdata.domain.DCMonXmlOutDel;
import data.exchange.center.service.businessdata.domain.DCMonXmlOutZzjg;
import data.exchange.center.service.businessdata.domain.DcMonXmlOutAll;

@Mapper
public interface BusinessDataMapper {
	int getPageDcMonXmlOut() throws Exception;
	
	List<DcMonXmlOutAll> getDcMonXmlOut(Map<String, Object> params) throws Exception;
	
	int getPageDcMonXmlOutDe() throws Exception;
	
	List<DCMonXmlOutDel> getDcMonXmlOutDel(Map<String, Object> params) throws Exception;
	
	int getPageDcMonXmlOutJg() throws Exception;
	
	List<DCMonXmlOutZzjg> getDcMonXmlOutJg(Map<String, Object> params) throws Exception;
	
	int setOutFlag(Map<String, Object> params) throws Exception;

	
	int setDelFlag(Map<String, Object> params) throws Exception;
	
	int setJgFlag(Map<String, Object> params) throws Exception;
	

}
