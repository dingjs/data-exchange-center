package data.exchange.center.service.listener.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeleteBusinessDataMapper {

	void delete_BUF_EXTERNAL2XML(String ajbs) throws Exception;

	void delete_BUF_AJSC2XML(String ajbs) throws Exception;

	void delete_BUF_ZZJGRY2XML(String ajbs) throws Exception;

	void delete_FJGH(Map<String, Object> map) throws Exception;

}
