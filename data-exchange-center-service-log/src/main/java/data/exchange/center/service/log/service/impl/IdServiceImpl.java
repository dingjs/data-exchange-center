package data.exchange.center.service.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.log.idproduce.SnowflakeIdWorker;
import data.exchange.center.service.log.service.IdService;

@Service
public class IdServiceImpl implements IdService {

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	
	@Override
	public long IdWorker() {
		//目前id生成不是太厉害，workid和datacenterid暂时写死
		return snowflakeIdWorker.nextId();
	}
}
