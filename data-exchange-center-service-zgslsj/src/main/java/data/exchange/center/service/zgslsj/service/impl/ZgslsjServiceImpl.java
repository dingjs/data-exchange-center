package data.exchange.center.service.zgslsj.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import data.exchange.center.service.zgslsj.service.ZgslsjService;
import data.exchange.center.service.zgslsj.util.Singleton;

@Service
public class ZgslsjServiceImpl implements ZgslsjService {


    @Override
    public byte[] getRyxx(Map<String, String> map) {
        Garkhttptest hcc = Garkhttptest.getInstance();
        try {
            StringBuilder urlBuilder1 = new StringBuilder();
            urlBuilder1.append(
                    "http://192.1.36.74:8080/drsp/services/resource/api/2c90405a51195d270151196707e50002.gxml?systemMark=true&ticket=d57641466ed05964a79464270fdabcee");
            urlBuilder1.append("&xtmc=");
            urlBuilder1.append(URLEncoder.encode(map.get("xtmc"), "UTF-8"));
            urlBuilder1.append("&ip=");
            urlBuilder1.append(URLEncoder.encode(map.get("ip"), "UTF-8"));
            urlBuilder1.append("&user=");
            urlBuilder1.append(URLEncoder.encode(map.get("user"), "UTF-8"));
            urlBuilder1.append("&fydm=");
            urlBuilder1.append(URLEncoder.encode(map.get("fydm"), "UTF-8"));
            if (StringUtils.isNotBlank(map.get("ah"))) {
                urlBuilder1.append("&ah=");
                urlBuilder1.append(URLEncoder.encode(map.get("ah"), "UTF-8"));
            }
            return hcc.postXmlMethod(urlBuilder1.toString(), map.get("xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] getLsxx(String xml) {
        HttpClientCenter hcc = HttpClientCenter.getInstance();
        try {
            String url = "http://192.1.36.74:8080/drsp/services/resource/api/4028b281562f551e01562f562aac0002.gxml?systemMark=true&ticket=bee2b96b7ef04fe805761ad6e4d2f4e9";
            return hcc.postXmlMethod(url, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] getJgxx(String jgmc) {
        try {
            JgHttpClientCenter hcc = JgHttpClientCenter.getInstance();
            StringBuilder urlBuilder1 = new StringBuilder();
            //urlBuilder1.append("服务地址");
            urlBuilder1.append("http://192.1.36.74:8080/drsp/services/resource/api/2c9040125457e10401545816181b00e6.gxml?systemMark=true&ticket=831e4b4362f50172993c274f061ff722&startPage=1");
            urlBuilder1.append("&jgmc=");
            urlBuilder1.append(URLEncoder.encode(jgmc, "UTF-8"));
            return hcc.getMethod(urlBuilder1.toString());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        return null;
    }

	@Override
	public byte[] getXML(String xml) throws IOException, ServiceException {
		String jkxlh= "lkU984LfjkvnuY09114dfs09ddS2gdsjfr3fsdg";
        String jkid = "ACDR1";
        String newxml =  Singleton.getInstance().queryObjectOutACDR1(jkxlh, jkid, xml);     
        byte[] encodeBase64 = Base64.encodeBase64(newxml.getBytes("UTF-8"));
		return encodeBase64;
	}

}
