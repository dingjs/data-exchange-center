package data.exchange.center.service.wuhou.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;

import data.exchange.center.service.wuhou.domain.AjbsInfo;
import data.exchange.center.service.wuhou.domain.AjbsList;
import data.exchange.center.service.wuhou.domain.Ajcl;
import data.exchange.center.service.wuhou.domain.UserInfo;
import data.exchange.center.service.wuhou.domain.XsAjbsInfo;
import data.exchange.center.service.wuhou.domain.XzAjbsInfo;
import data.exchange.center.service.wuhou.mapper.WuhouMapper;
import data.exchange.center.service.wuhou.service.DownLoadService;
import data.exchange.center.service.wuhou.service.WuhouService;
import data.exchange.center.service.wuhou.util.Constant;
import data.exchange.center.service.wuhou.util.Singleton;

@Service
public class WuhouServiceImpl implements WuhouService {

    @Autowired
    private WuhouMapper wuhouMapper;
    @Autowired
    private DownLoadService downLoadService;

    @Override
    public Object getAjbsInfo(String ajbs, String fydm, String ajlx) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ajbs", ajbs);
            params.put("fydm", fydm);
            params.put("ajlx", ajlx);
            if (ajlx.equals("21")) {
                List<AjbsInfo> ajInfo = wuhouMapper.getAjbsInfo(params);
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                map.put(CodeUtil.RETURN_MSG, ajInfo == null ? "" : ajInfo);
            }else if (ajlx.equals("31")) {
                List<XzAjbsInfo> ajInfo = wuhouMapper.getXzAjbsInfo(params);
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                map.put(CodeUtil.RETURN_MSG, ajInfo == null ? "" : ajInfo);
            }else if (ajlx.equals("11")) {
                List<XsAjbsInfo> ajInfo = wuhouMapper.getXsAjbsInfo(params);
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                map.put(CodeUtil.RETURN_MSG, ajInfo == null ? "" : ajInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
        }
        return map;
    }

    @Override
    public Object getAjcl(String ajbs, String xh) {
        byte[] bt = downLoadService.download(ajbs.concat("_").concat(xh));
        return bt;
    }

    @Override
    public Object getAjclList(String ajbs, String fydm, String ajlx) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ajbs", ajbs);
            params.put("fydm", fydm);
            params.put("ajlx", ajlx);
            List<Ajcl> ajInfoList = wuhouMapper.getAjclList(params);
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            map.put(CodeUtil.RETURN_MSG, ajInfoList == null ? "" : ajInfoList);
            System.out.println(ajInfoList.size());
            
        
        } catch (Exception e) {
            e.printStackTrace();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
        }
        return map;
    }

    @Override
    public Object getUserInfo(String fydm) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<UserInfo> ajInfoList = wuhouMapper.getUserInfo(fydm);
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            map.put(CodeUtil.RETURN_MSG, ajInfoList == null ? "" : ajInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
        }
        return map;
    }

    @Override
    public Object getBmInfo() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> bmInfoList = wuhouMapper.getBmInfo();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            map.put(CodeUtil.RETURN_MSG, bmInfoList == null ? "" : bmInfoList);

        } catch (Exception e) {
            e.printStackTrace();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
        }
        return map;
    }

    @Override
    public Object getAjbsList(String startDate, String endDate, String fydm) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                map.put(CodeUtil.RETURN_MSG, "参数startDate或者endDate不能为空");
                return map;
            }
            if (!patternDate(startDate, "udate")) {
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                map.put(CodeUtil.RETURN_MSG, "【开始时间】格式不正确,请用yyyyMMddHHmmss格式字符串类型日期");
                return map;
            }
            if (!patternDate(endDate, "udate")) {
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                map.put(CodeUtil.RETURN_MSG, "【结束时间】格式不正确,请用yyyyMMddHHmmss格式字符串类型日期");
                return map;
            }
            if (fydm == null) {
                map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                map.put(CodeUtil.RETURN_MSG, "法院代码为空");
                return map;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("fydm", fydm);
            List<AjbsList> ajbsList = wuhouMapper.getAjbsList(params);
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            map.put(CodeUtil.RETURN_MSG, ajbsList == null ? "" : ajbsList);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
            return map;
        }
    }

    private static boolean patternDate(String dateStr, String type) {
        String eL = "(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])";
        String str = "^((?:19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0\\d|1\\d|2[0-3])(0\\d|[1-5]\\d)(0\\d|[1-5]\\d)$";// yyyyMMddHHmmss
        if (type.equalsIgnoreCase("date")) {
            Pattern p = Pattern.compile(eL);
            Matcher m = p.matcher(dateStr);
            return m.matches();
        } else {
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(dateStr);
            return m.matches();
        }
    }

    @Override
    public String setDbxx(String dbxx) {
        try {
            Document dbxxDocument = DocumentHelper.parseText(dbxx);
            Document documentA = DocumentHelper.createDocument();
            Element node = dbxxDocument.getRootElement();
            Element document = documentA.addElement("Request");
            Element ahdm = document.addElement("AHDM");
            ahdm.addText(node.element("AHDM").getText());
            Element ah = document.addElement("AH");
            ah.addText(node.element("CaseNO").getText());
            Element mc = document.addElement("MC");
            mc.addText(node.element("MC").getText());
            Element zzr = document.addElement("ZZR");
            zzr.addText("123");
            Element zfjbs = document.addElement("ZFJBS");
            zfjbs.addText(node.element("ZFJBS").getText());
            Element ysbh = document.addElement("YSBH");
            Element wjmc = document.addElement("WJMC");
            wjmc.addText(node.element("MC").getText());
            Element wjgs = document.addElement("WJGS");
            wjgs.addText(node.element("WJGS").getText());
            byte[] record = getWjByte(node.element("Record").getText());
            if (record != null) {
                Element nr = document.addElement("NR");
                nr.addText(Base64.encode(record));
            }
            Element lb = document.addElement("LB");
            lb.addText(node.element("LB").getText());
            Element jmxh = document.addElement("JMXH");
            String xml1= document.asXML();
            System.out.println(xml1);
            
            String xml = Singleton.getInstance().caseEvolumeSave("CaseEvolumeInput", "INput140425",
                    Constant.srcToBase64(document.asXML()));
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public byte[] getWjByte(String fileName) {
        String fileUrl = "http://150.101.64.35:8902/v1/LoadFile?Record="+fileName;
        InputStream in = null;
        ByteArrayOutputStream swapStream = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
            swapStream = new ByteArrayOutputStream();
            in = conn.getInputStream();
            byte[] buffer = new byte[in.available()];
            int count = 0;

            FileOutputStream out = new FileOutputStream(new File("D:/1234.doc"));
            while ((count = in.read(buffer)) != -1) {
                if (count != 0) {
                    swapStream.write(buffer, 0, count);
                    out.write(buffer);
                } else {
                    break;
                }
            }
            byte[] in2b = swapStream.toByteArray();
            out.flush();
            out.close();
            return in2b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
               
                if (swapStream != null) {
                    swapStream.close();
                }
                if (in != null) {
                    in.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        return null;
    }
}
