package data.exchange.center.service.unstructured.node.mapper.tdh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;

@Mapper
public interface ContrstMapper {
	public List<TempEajJz> getEajJZ(Map<String, Object> map)throws Exception;

	public List<TempEajJzwjAll> getEajJzwjAll(String ajbs)throws Exception;

	public List<TempEajJzwjAllNew> getEajJzwjAllNew(String ajbs)throws Exception;

	public List<TempEajMlxx> getEajMlxx(String ajbs)throws Exception;

	public List<TempEajMlxxGc> getEajMlxxGc(String ajbs)throws Exception;

	public List<TempEajSsjcyx> getSsjcyx(String ajbs)throws Exception;
	
	public String getAllAjxxCBR(String ajbs)throws Exception;


}
