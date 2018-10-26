package data.exchange.center.service.unstructured.node.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;

/**
 * 获取非结构化数据的结构化信息表
 * 
 * @author bmj
 * 
 */
@Service
public interface ContrstService {
	
	public List<TempEajJz> getEajJZ(Map<String, Object> map) throws Exception;

	public List<TempEajJzwjAll> getEajJzwjAll(String ajbs)throws Exception;

	public List<TempEajJzwjAllNew> getEajJzwjAllNew(String ajbs)throws Exception;

	public List<TempEajMlxx> getEajMlxx(String ajbs)throws Exception;

	public List<TempEajMlxxGc> getEajMlxxGc(String ajbs)throws Exception;

	public List<TempEajSsjcyx> getSsjcyx(String ajbs)throws Exception;
	
	public String getAllAjxxCBR(String ajbs)throws Exception;
	
}
