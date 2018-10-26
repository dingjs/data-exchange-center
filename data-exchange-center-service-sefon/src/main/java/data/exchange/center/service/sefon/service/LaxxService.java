package data.exchange.center.service.sefon.service;

import java.util.List;
import java.util.Map;

import data.exchange.center.service.sefon.domain.Laxx;

public interface LaxxService {
	/**
	 * bmj
	 * 
	 * @param ksLasj
	 * @param Jslasj
	 * @return
	 * @throws Exception
	 */
	public List<Laxx> getAjbsInfo(Map<String, Object> param) throws Exception;
}
