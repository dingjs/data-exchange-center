package service.sjsb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import service.sjsb.domain.AjxxInfo;

@Mapper
public interface SjsbMapper {

	public List<AjxxInfo> getAjbs();

	public void getAjData(Map<String, Object> params);

	public void addSjsbFile(Map<String, String> params);

	public List<String> getSjsbFile();

	public List<String> getDeleteSjsbFile();

}
