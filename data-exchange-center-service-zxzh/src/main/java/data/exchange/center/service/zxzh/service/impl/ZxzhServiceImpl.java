package data.exchange.center.service.zxzh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.zxzh.domain.ZxzhTemporary;
import data.exchange.center.service.zxzh.mapper.ZxzhMapper;
import data.exchange.center.service.zxzh.service.ZxzhService;
import data.exchange.center.service.zxzh.util.Singleton;

@Service
public class ZxzhServiceImpl implements ZxzhService {
    @Autowired
    private ZxzhMapper zxzhMapper;

    @Override
    public String getZzjg(String xml) {
        try {
            System.out.println(xml);
            String data  = Singleton.getInstance().getZzjg(xml);
            pushZzzhTemporary(xml,data,"Zzjg");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCaseList(String xml) {
        try {
            String data  = Singleton.getInstance().getCaseList(xml);
            pushZzzhTemporary(xml,data,"CaseList");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDailyUpdateCaseList(String xml) {
        try {
            String data  = Singleton.getInstance().getDailyUpdateCaseList(xml);
            pushZzzhTemporary(xml,data,"DailyUpdateCaseList");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCaseXml(String xml) {
        try {
            String data  = Singleton.getInstance().getCaseXml(xml);
            pushZzzhTemporary(xml,data,"CaseXml");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCaseZip(String xml) {
        try {
            String data  = Singleton.getInstance().getCaseZip(xml);
            pushZzzhTemporary(xml,data,"CaseZip");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getWsData(String xml) {
        try {
            String data  = Singleton.getInstance().getWsData(xml);
            pushZzzhTemporary(xml,data,"WsData");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCaseXml09(String xml) {
        try {
            String data  = Singleton.getInstance().getCaseXml09(xml);
            pushZzzhTemporary(xml,data,"CaseXml09");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDelZip(String xml) {
        try {
            String data  = Singleton.getInstance().getDelZip(xml);
            pushZzzhTemporary(xml,data,"DelZip");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void pushZzzhTemporary (String key,String data,String lx){
        ZxzhTemporary zxzhTemporary = new ZxzhTemporary();
        zxzhTemporary.setC_lx(lx);
        zxzhTemporary.setC_key(key);
        zxzhTemporary.setB_data(data.getBytes());
        zxzhMapper.pushZzzhTemporary(zxzhTemporary);
    }
}
