package data.exchange.center.service.guangan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.guangan.domain.AjbsInfo;
import data.exchange.center.service.guangan.mapper.GuanganMapper;
import data.exchange.center.service.guangan.service.GuanganService;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月26日 下午4:19:29</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service("guanganService")
public class GuanganServiceImpl implements GuanganService {

	@Autowired
	private GuanganMapper  guanganMapper;
	
	@Override
	public List<AjbsInfo> getUpdateAjbs(Map<String, Object> param) throws Exception {
		return guanganMapper.getUpdateAjbs(param);
	}

	@Override
	public List<Object> getAjbsInfo(Map<String, Object> param) throws Exception {
		return guanganMapper.getAjbsInfo(param);
	}

	@Override
	public List<Object> getSpzt(Map<String, Object> param) throws Exception {
		return guanganMapper.getSpzt(param);
	}

	@Override
	public List<Object> getYsajxx(Map<String, Object> param) throws Exception {
		return guanganMapper.getYsajxx(param);
	}

	@Override
	public List<String> getAjscList(String date) throws Exception {
		return guanganMapper.getAjscList(date);
	}

	@Override
	public List<Object> getJzxx(Map<String, Object> param) throws Exception {
		return guanganMapper.getJzxx(param);
	}

	@Override
	public List<Object> getZx(Map<String, Object> param) throws Exception {
		return guanganMapper.getZx(param);
	}
}
