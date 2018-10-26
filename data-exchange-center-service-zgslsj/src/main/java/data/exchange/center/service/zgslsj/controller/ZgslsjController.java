package data.exchange.center.service.zgslsj.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import data.exchange.center.service.zgslsj.service.ZgslsjService;

@RestController
public class ZgslsjController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ZgslsjService zgslsjService;

    @RequestMapping(value = "/getRyxx", method = RequestMethod.GET)
    public byte[] getgetRyxx(@RequestParam("xml") String xml, @RequestParam("xtmc") String xtmc,
            @RequestParam("ip") String ip, @RequestParam("user") String user, @RequestParam("fydm") String fydm,
            @RequestParam("ah") String ah,HttpServletRequest req) throws UnsupportedEncodingException {
        String remoteAddr = getIpAddress(req);

        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(xml) || StringUtils.isBlank(xtmc) || StringUtils.isBlank(remoteAddr)
                || StringUtils.isBlank(user)) {
            String src = "xml、ip、xtmc、user、fydm不能为空";
            return src.getBytes();
        }
        map.put("xml", xml);
        map.put("ip", ip);
        map.put("xtmc", xtmc);
        map.put("user", user);
        map.put("fydm", fydm);
        map.put("ah", ah);
        byte[] bt = zgslsjService.getRyxx(map);
        System.out.println(bt);
        return bt;
    }
    @RequestMapping(value = "/getLsxx", method = RequestMethod.GET)
    public byte[] getLsxx(@RequestParam("xml") String xml){
        return zgslsjService.getLsxx(xml);
    }
    @RequestMapping(value = "/getJgxx", method = RequestMethod.GET)
    public byte[] getJgxx(@RequestParam("jgmc") String jgmc){
        return zgslsjService.getJgxx(jgmc);
    }
    public static String getIpAddress(HttpServletRequest request){  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
    @RequestMapping(value = "/getDj", method = RequestMethod.GET)
    public byte[] getXML(@RequestParam("xml") String xml) throws Exception {
        return zgslsjService.getXML(xml);
    }
}
