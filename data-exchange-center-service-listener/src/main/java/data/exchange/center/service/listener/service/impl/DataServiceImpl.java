package data.exchange.center.service.listener.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.listener.mapper.DataMapper;
import data.exchange.center.service.listener.service.DataService;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DataMapper dataMapper;
	
	@Override
	public List<Map<String, Object>> getBusinessData() throws Exception {
		return dataMapper.getBusinessData();
	}

	@Override
	public List<Map<String, Object>> getSpztData() throws Exception {
		return dataMapper.getSpztData();
	}

	@Override
	public void getDataFromTdh() throws Exception {
		dataMapper.getDataFromTdh();
	}
}
