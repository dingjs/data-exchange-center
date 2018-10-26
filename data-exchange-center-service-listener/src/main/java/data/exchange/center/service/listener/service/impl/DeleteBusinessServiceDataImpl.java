package data.exchange.center.service.listener.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.listener.mapper.DeleteBusinessDataMapper;
import data.exchange.center.service.listener.service.DeleteBusinessDataService;

@Service
public class DeleteBusinessServiceDataImpl implements DeleteBusinessDataService {

	@Autowired
	private DeleteBusinessDataMapper deleteBusinessDataMapper;
	
	@Override
	public void delete_BUF_EXTERNAL2XML(String ajbs) throws Exception {
		deleteBusinessDataMapper.delete_BUF_EXTERNAL2XML(ajbs);
	}

	@Override
	public void delete_BUF_AJSC2XML(String ajbs) throws Exception {
		deleteBusinessDataMapper.delete_BUF_AJSC2XML(ajbs);
	}

	@Override
	public void delete_BUF_ZZJGRY2XML(String ajbs) throws Exception {
		deleteBusinessDataMapper.delete_BUF_ZZJGRY2XML(ajbs);
	}

	@Override
	public void delete_FJGH(Map<String, Object> params) throws Exception {
		deleteBusinessDataMapper.delete_FJGH(params);
	}
}
