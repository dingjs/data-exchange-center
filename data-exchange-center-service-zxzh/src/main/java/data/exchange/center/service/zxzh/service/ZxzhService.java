package data.exchange.center.service.zxzh.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月27日 下午4:52:26</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface ZxzhService {
    public String getZzjg(@RequestParam("xml") String xml);

    public String getCaseList(@RequestParam("xml") String xml);

    public String getDailyUpdateCaseList(String xml) ;

    public String getCaseXml(String xml) ;

    public String getCaseZip(String xml) ;

    public String getWsData(String xml);

    public String getCaseXml09(String xml) ;

    public String getDelZip(String xml) ;
	
}