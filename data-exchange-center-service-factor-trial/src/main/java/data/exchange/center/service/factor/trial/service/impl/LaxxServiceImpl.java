package data.exchange.center.service.factor.trial.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.factor.trial.mapper.MsSahlxxxMapper;
import data.exchange.center.service.factor.trial.service.LaxxService;


@Service("laxxService")
public class LaxxServiceImpl implements LaxxService {

	@Autowired
	private MsSahlxxxMapper msSahlxxxMapper;
	

	@Override
	public List<Object> getAjbsInfo(Map<String, Object> param) throws Exception {
		return msSahlxxxMapper.getAjInfo(param);
	}
}
