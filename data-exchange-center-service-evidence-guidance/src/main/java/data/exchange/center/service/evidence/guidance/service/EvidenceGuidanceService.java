package data.exchange.center.service.evidence.guidance.service;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月27日 下午4:52:26</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface EvidenceGuidanceService {

	/**
	 * 
	 * @function 证据指引服务系统接口
	 * @author wenyuguang
	 * @creaetime 2017年10月27日 下午4:56:38
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @return
	 * @throws Exception
	 */
	public Object UPCaseEvidence (String ajbh, String ajlb, String ajxz, String ajzt, String[] userinfo ) throws Exception;
}
