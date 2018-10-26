package data.exchange.center.service.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.test.mapper.TestMapper;
import data.exchange.center.service.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Autowired
	private TestMapper testMapper;
	
	@Override
	public int test() {
		int rent = testMapper.test();
		return rent;
	}
}
