package data.exchange.center.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.monitor.domain.Mraj;
import data.exchange.center.monitor.repository.IndexRepository;
import data.exchange.center.monitor.service.IndexService;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月24日 下午3:12:05</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class IndexServiceImpl implements IndexService{

	@Autowired
	IndexRepository indexRepository;
	
	@Override
	public Mraj getMraj() {
		return indexRepository.getMraj();
	}

	@Override
	public int getUpdateCaseCount() {
		return indexRepository.getUpdateCaseCount();
	}

	@Override
	public int getDeleteCaseCount() {
		return indexRepository.getDeleteCaseCount();
	}

	@Override
	public Object getAjtj(String ajzt) {
		// TODO Auto-generated method stub
		return indexRepository.getAjtj(ajzt);
	}

	@Override
	public Object getAjs(String ajzt,String fyjc) {
		// TODO Auto-generated method stub
		return indexRepository.getAjs(ajzt, fyjc);
	}
}
