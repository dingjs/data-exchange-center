package data.exchange.center.service.swh.service;

import java.util.Map;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月27日 下午4:52:26</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface SwhService {
    public String GetCaseInfo(String CaseID);
    
    public String GetCaseList(String latestSynchTime,String ajzt,String PageNum);
    
    public Object getLb(String caseId,String xh);
    
    public Object getwj(String caseId);
    
    public String setDbxx(String dbxx);
}