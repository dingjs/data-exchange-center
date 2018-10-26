package data.exchange.center.service.swh.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.axis.encoding.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import data.exchange.center.service.swh.domain.EaJDa;
import data.exchange.center.service.swh.domain.EaJJZMl;
import data.exchange.center.service.swh.domain.EaJz;
import data.exchange.center.service.swh.domain.EajDaMl;
import data.exchange.center.service.swh.domain.TempEajJz;
import data.exchange.center.service.swh.mapper.sgy.SwhSgyDataMapper;
import data.exchange.center.service.swh.mapper.tdh.SwhDataMapper;
import data.exchange.center.service.swh.service.SwhService;
import data.exchange.center.service.swh.task.ContrastThead;
import data.exchange.center.service.swh.util.Constant;
import data.exchange.center.service.swh.util.DecodeUtil;
import data.exchange.center.service.swh.util.Singleton;
import oracle.sql.BLOB;

@Service
public class SwhServiceImpl implements SwhService {
    @Value("${tdh.server.username}")
    private String username;
    @Value("${thd.server.password}")
    private String password;
    @Value("${tdh.ws.username}")
    private String wsusername;
    @Value("${thd.ws.password}")
    private String wspassword;

    @Autowired
    SwhDataMapper swhDataMapper;
    @Autowired
    SwhSgyDataMapper swhSgyDataMapper;

    public Map<String, String> getJg(String rybs) {

        try {
            Map<String, String> map = new HashMap<>();
            Document document = DocumentHelper.createDocument();
            Element element = document.addElement("Request");
            Element fy = element.addElement("FY");
            fy.addText(Constant.srcToBase64("N00"));
            Element bs = element.addElement("BS");
            bs.addText(rybs);
            String ryXml = Singleton.getInstance().getRy(wsusername, wspassword,
                    Constant.srcToBase64(document.asXML()));

            Document documentRy = DocumentHelper.parseText(Constant.base64Tosrc(ryXml));
            Element node = documentRy.getRootElement();
            Element data = node.element("Data");
            Element ry = data.element("RY");

            Element bmdm = element.addElement("BMDM");
            bmdm.addText(ry.element("YHBM").getText());

            String jgXml = Singleton.getInstance().getZzjg(wsusername, wspassword,
                    Constant.srcToBase64(document.asXML()));
            Document documentJg = DocumentHelper.parseText(Constant.base64Tosrc(jgXml));
            Element nodeJg = documentJg.getRootElement();
            Element dataJg = nodeJg.element("Data");
            Element zzjg = dataJg.element("ZZJG");
            map.put("cbrxm", Constant.base64Tosrc(ry.element("XM").getText()));
            map.put("cbrbs", Constant.base64Tosrc(ry.element("YHDM").getText()));
            map.put("jgbs", Constant.base64Tosrc(zzjg.element("JGBS").getText()));
            map.put("cbbmmc", Constant.base64Tosrc(zzjg.element("MC").getText()));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String GetCaseInfo(String caseID) {
        Map<String, String> map = new HashMap<>();
        map.put("ahdm", caseID);
        map.put("ws", caseID.substring(caseID.length() - 1, caseID.length()));
        try {
            Document document = DocumentHelper.createDocument();
            Element caseList = document.addElement("caseList");
            String data = Singleton.getInstance().getCaseInfo(Constant.srcToBase64(username),
                    Constant.srcToBase64(password), Constant.srcToBase64(caseID), null);
            Thread thread = new Thread(new ContrastThead(data, swhSgyDataMapper));
            thread.start();
            srcToxml(data, caseList);
            // 创建输出格式(OutputFormat对象)
            // OutputFormat format = OutputFormat.createPrettyPrint();
            // //8、创建输出对象
            // File file = new File("E:/dom4jToxml.xml");
            // if(!file.exists()){
            // file.createNewFile();
            // }
            // XMLWriter writer = new XMLWriter(new FileOutputStream(file),
            // format);
            // //设置不自动进行转义
            // writer.setEscapeText(false);
            // // 生成XML文件
            // writer.write(document);
            // //关闭XMLWriter对象
            // writer.close();
            return document.asXML();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getwj(String caseId) {
        Map<String, String> map = new HashMap<>();
        map.put("ahdm", caseId);
        map.put("ws", caseId.substring(caseId.length() - 1, caseId.length()));
        try {

            List<EaJJZMl> eajJzMlList = swhDataMapper.getEajJzMl(map);
            List<EaJz> eaJzList = swhDataMapper.getEaJz(map);
            List<EajDaMl> eaJDaMlList = swhDataMapper.getEaJDaMl(map);
            List<EaJDa> eajDaList = swhDataMapper.getEajDa(map);

            Document document = DocumentHelper.createDocument();
            Element caseList = document.addElement("caseList");
            reflect(eajJzMlList, caseList, "jzml");
            reflect(eaJzList, caseList, "jz");
            reflect(eaJDaMlList, caseList, "daml");
            reflect(eajDaList, caseList, "da");
            // // 创建输出格式(OutputFormat对象)
            // OutputFormat format = OutputFormat.createPrettyPrint();
            // //XMLWriter writer = new XMLWriter(new FileOutputStream(new
            // File("xml/dom4jToxml.xml")),format);
            // //8、创建输出对象
            // File file = new File("E:/dom4jToxml.xml");
            // if(!file.exists()){
            // file.createNewFile();
            // }
            // XMLWriter writer = new XMLWriter(new FileOutputStream(file),
            // format);
            // //设置不自动进行转义
            // writer.setEscapeText(false);
            // // 生成XML文件
            // writer.write(document);
            // //关闭XMLWriter对象
            // writer.close();
            return document.asXML();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public String srcToxml(String data, Element element) {
        try {
            Element caseList = element.addElement("CaseList");
            Element caseEl = caseList.addElement("Case");
            Document document = DocumentHelper.parseText(Constant.base64Tosrc(data));
            Element node = document.getRootElement();
            Element caseInfo = node.element("CaseInfo");
            Element eaj = caseInfo.element("EAJ");
            Element ftsyList = caseInfo.element("EAJ_FTSY_LIST");

            int tc = 0;
            Element code = caseEl.addElement("Code");
            code.addText(Constant.base64Tosrc(eaj.attribute("AHDM").getText()));
            Element caseNO = caseEl.addElement("CaseNO");
            caseNO.addText(Constant.base64Tosrc(eaj.attribute("AH").getText()));
            Element caseType = caseEl.addElement("CaseType");
            caseType.addText(Constant.base64Tosrc(eaj.attribute("XTAJLX").getText()));
            Element caseCause = caseEl.addElement("CaseCause");
            caseCause.addText(Constant.base64Tosrc(eaj.attribute("SAAY").getText()));
            Element title = caseEl.addElement("Title");
            title.addText(Constant.base64Tosrc(eaj.attribute("AJMC").getText()));
            Element ajlb = caseEl.addElement("Ajlb");
            ajlb.addText(Constant.base64Tosrc(eaj.attribute("AJLB").getText()));
            Element spcx = caseEl.addElement("Spcx");
            spcx.addText(Constant.base64Tosrc(eaj.attribute("SPCX").getText()));
            // 取最大一次的庭次，为空则为0
            if (ftsyList != null) {
                List<Element> ftsys = ftsyList.elements();
                for (Element ftsy : ftsys) {
                    if (Constant.base64Tosrc(ftsy.attribute("TC").getText()) != null
                            && tc < Integer.valueOf(Constant.base64Tosrc(ftsy.attribute("TC").getText()))) {
                        tc = Integer.valueOf(Constant.base64Tosrc(ftsy.attribute("TC").getText()));
                    }
                }
            }
            Map<String, String> map = getJg(eaj.attribute("CBR").getText());
            Element acceptPersonnelID = caseEl.addElement("AcceptPersonnelID");
            acceptPersonnelID.addText(map.get("cbrbs"));
            Element acceptPersonnelName = caseEl.addElement("AcceptPersonnelName");
            acceptPersonnelName.addText(map.get("cbrxm"));
            Element acceptDepartmentID = caseEl.addElement("AcceptDepartmentID");
            acceptDepartmentID.addText(map.get("jgbs"));
            Element acceptDepartmentName = caseEl.addElement("AcceptDepartmentName");
            acceptDepartmentName.addText(map.get("cbbmmc"));
            Element trialTimes = caseEl.addElement("TrialTimes");
            trialTimes.addText(String.valueOf(tc));

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCbrXm(String userid, String pwd, String cbr) throws RemoteException, ServiceException {
        String data = Singleton.getInstance().getRy(Constant.srcToBase64(userid), Constant.srcToBase64(pwd), null);
        return null;
    }

    @Override
    public String GetCaseList(String latestSynchTime, String ajzt, String pageNum) {
        try {
            String data = Singleton.getInstance().getCaseList(Constant.srcToBase64(username),
                    Constant.srcToBase64(password), Constant.srcToBase64(latestSynchTime), Constant.srcToBase64(ajzt),
                    Constant.srcToBase64(pageNum), Constant.srcToBase64("N00"));
            return data;
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getLb(String caseId, String xh) {
        Map<String, String> map = new HashMap<>();
        map.put("ahdm", caseId);
        map.put("xh", xh);
        map.put("ws", caseId.substring(caseId.length() - 1, caseId.length()));
        try {
            TempEajJz tempEajJz = swhDataMapper.getLb(map);
            tempEajJz = blobToBytes(tempEajJz);
            return tempEajJz.getNR();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TempEajJz blobToBytes(TempEajJz tempEajJz) throws Exception {
        if (tempEajJz.getNR() instanceof oracle.sql.BLOB || tempEajJz.getNR() == null) {
            byte[] b = null;
            if (tempEajJz.getNR() == null) {
                b = null;
            } else {
                b = DecodeUtil.blobToBytes((BLOB) tempEajJz.getNR());
            }

            if (!org.springframework.util.StringUtils.isEmpty(tempEajJz.getYSBZ())
                    && ("2").equalsIgnoreCase(tempEajJz.getYSBZ()) && (BLOB) tempEajJz.getNR() != null) {
                byte[] before = DecodeUtil.blobToBytes((BLOB) tempEajJz.getNR());
                InputStream in = new ByteArrayInputStream(before);
                byte[] blobByte = DecodeUtil.deCompress(in);
                tempEajJz.setNR(blobByte);
            } else {
                tempEajJz.setNR(b);
            }
        }
        return tempEajJz;
    }

    @SuppressWarnings("unchecked")
    public static void reflect(Object obj, Element element, String elementName) throws Exception {
        Element fjgh = element.addElement(elementName + "List");
        List<Object> listObj = (List<Object>) obj;
        for (int i = 0; i < listObj.size(); i++) {
            Object e = listObj.get(i);
            Class cls = e.getClass();
            Field[] fields = cls.getDeclaredFields();
            Element wj = fjgh.addElement(elementName);
            for (int j = 0; j < fields.length; j++) {
                Field f = fields[j];
                f.setAccessible(true);
                if (f.get(e) != null) {
                    wj.addAttribute(f.getName(), String.valueOf(f.get(e)));
                }
            }
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
            // zzr.addText(node.element("ZZR").getText());
            Element zfjbs = document.addElement("ZFJBS");
            zfjbs.addText("f");
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
            Element jmxh = document.addElement("JMXH");
            Element lb = document.addElement("LB");
            lb.addText("09_01091-4");
            String xml1= document.asXML();
            System.out.println(xml1);
            String xml = Singleton.getInstance().caseEvolumeSave(wsusername, wspassword,
                    Constant.srcToBase64(document.asXML()));
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] getWjByte(String fileUrl) {
        InputStream in = null;
        ByteArrayOutputStream swapStream = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(fileUrl);
            HttpResponse response = httpClient.execute(httpGet);
            in = response.getEntity().getContent();
            swapStream = new ByteArrayOutputStream();
            int count = 0;
            byte[] buffer = new byte[in.available()];
            while ((count = in.read(buffer)) != -1) {
                if (count != 0) {
                    swapStream.write(buffer, 0, count);
                } else {
                    break;
                }
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
            
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
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        return null;
    }
}
