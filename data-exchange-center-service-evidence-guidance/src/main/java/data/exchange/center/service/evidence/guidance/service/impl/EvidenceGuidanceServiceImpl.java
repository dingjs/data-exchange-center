package data.exchange.center.service.evidence.guidance.service.impl;

import org.springframework.stereotype.Service;

import data.exchange.center.service.evidence.guidance.service.EvidenceGuidanceService;
import data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebService;
import data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceService;
import data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceServiceLocator;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月27日 下午4:51:57</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class EvidenceGuidanceServiceImpl implements EvidenceGuidanceService {

	@Override
	public Object UPCaseEvidence (String ajbh, String ajlb, String ajxz, String ajzt, String[] userinfo ) throws Exception {
		
		CaseEvidenceWebServiceService caseEvidenceWebServiceService = new CaseEvidenceWebServiceServiceLocator();
		CaseEvidenceWebService stub = caseEvidenceWebServiceService.getCaseEvidenceWebServicePort();
		return stub.UPCaseEvidence (ajbh, ajlb, ajxz, ajzt, userinfo);
	}
}
