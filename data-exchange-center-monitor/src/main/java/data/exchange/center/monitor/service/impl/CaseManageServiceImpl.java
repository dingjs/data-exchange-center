package data.exchange.center.monitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.monitor.domain.FileList;
import data.exchange.center.monitor.repository.CaseManageRepository;
import data.exchange.center.monitor.service.CaseManageService;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月24日 下午5:18:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class CaseManageServiceImpl implements CaseManageService {

	@Autowired
	CaseManageRepository caseManageRepository;
	
	@Override
	public List<FileList> getFileList(String ajbs) {
		return caseManageRepository.getFileList(ajbs);
	}
}
