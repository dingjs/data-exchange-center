package data.exchange.center.service.pingshan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PingshanMapper {

	List<Map<String, Object>> getAjbsList(Map<String, Object> params) throws Exception;

	List<Map<String, Object>> getFybm() throws Exception;

	List<Map<String, Object>>  getBmxx() throws Exception;

	List<Map<String, Object>>  getYhxx() throws Exception;
	
	List<Map<String, Object>>  getDelAj(Map<String, Object> params) throws Exception;
	
	Map<String, Object> getAjbmxx() throws Exception;
	
	List<Map<String, Object>> getYASTML  (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSYSSAHLAXX  (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSYSAJSLQK  (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSYSBAQX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSYSXSFDMSSSGK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSYSJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSESSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSESAJSLQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSERAJSLQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSESJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSZSSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSZSAJSLQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXSZSJAFS (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSYSSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSYSAJSLQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSYSQTHJTSSQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSYSBAQX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSYSJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSESSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSESAJSLQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSESJAQK  (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSZSSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSZSJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZYSSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZYSAJSLQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZYSJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZESSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZESJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZZSSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getXZZSJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getSFPCSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getSFPCJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJSAHLAXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJAJZXQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJWTZXYSTHF (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJZDZX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJBAQX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJJAQK (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJSKSXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJDSR (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJFTSYJL (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJFDMSSSPCXX (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJCF (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJCQ (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJDJ (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getZXAJSDJL (Map<String, Object> params) throws Exception;
	//公共类
	List<Map<String, Object>> getSLJD (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getMSSDJL (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getSDJL (Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getAjDaj(Map<String, Object> params) throws Exception;

	List<Map<String, Object>> getAjGcj(Map<String, Object> params) throws Exception;
	
	List<Map<String, Object>> getFTSYJL(Map<String, Object> params) throws Exception;
	
}
