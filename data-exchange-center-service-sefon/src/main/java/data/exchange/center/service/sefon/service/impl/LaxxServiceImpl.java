package data.exchange.center.service.sefon.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.sefon.domain.Laxx;
import data.exchange.center.service.sefon.mapper.MsSahlxxxMapper;
import data.exchange.center.service.sefon.service.LaxxService;



@Service("laxxService")
public class LaxxServiceImpl implements LaxxService {

	@Autowired
	private MsSahlxxxMapper msSahlxxxMapper;
	

	@Override
	public List<Laxx> getAjbsInfo(Map<String, Object> param) throws Exception {
		return msSahlxxxMapper.getAjInfo(param);
	}
}
