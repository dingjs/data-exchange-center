package data.exchange.center.service.unstructured.node.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;
import data.exchange.center.service.unstructured.node.mapper.tdh.ContrstMapper;
import data.exchange.center.service.unstructured.node.service.ContrstService;

/**
 * 
 * Description:
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2017年10月11日 下午5:05:02
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ContrstServiceImpl implements ContrstService {


	@Autowired
	private ContrstMapper contrstMapper;

	@Override
	public List<TempEajJz> getEajJZ(Map<String, Object> map) throws Exception {
		try {
			List<TempEajJz> getTempEajJz = contrstMapper.getEajJZ(map);
			return getTempEajJz;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public List<TempEajJzwjAll> getEajJzwjAll(String ajbs) throws Exception {
		try {
			List<TempEajJzwjAll> TempEajJzwjAll = contrstMapper.getEajJzwjAll(ajbs);
			return TempEajJzwjAll;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public List<TempEajJzwjAllNew> getEajJzwjAllNew(String ajbs) throws Exception {
		try {
			List<TempEajJzwjAllNew> TempEajJzwjAllNew = contrstMapper.getEajJzwjAllNew(ajbs);
			return TempEajJzwjAllNew;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public List<TempEajMlxx> getEajMlxx(String ajbs) throws Exception {
		try {
			List<TempEajMlxx> TempEajMlxx = contrstMapper.getEajMlxx(ajbs);
			return TempEajMlxx;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TempEajMlxxGc> getEajMlxxGc(String ajbs) throws Exception {
		try {
			List<TempEajMlxxGc> TempEajMlxxGc = contrstMapper.getEajMlxxGc(ajbs);
			return TempEajMlxxGc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TempEajSsjcyx> getSsjcyx(String ajbs) throws Exception {
		try {
			List<TempEajSsjcyx> TempEajSsjcyx = contrstMapper.getSsjcyx(ajbs);
			return TempEajSsjcyx;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public String getAllAjxxCBR(String ajbs) throws Exception {
		try {
			return  contrstMapper.getAllAjxxCBR(ajbs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

}
