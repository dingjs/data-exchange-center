package data.exchange.center.service.zgslsj.service;

import java.util.Map;

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
public interface ZgslsjService {
	
    public  byte[]  getRyxx(Map<String, String> map);
    
    public  byte[]  getLsxx(String xml);
    
    public  byte[]  getJgxx(String jgmc);
    
    
    public  byte[]  getXML(String xml) throws Exception;
}