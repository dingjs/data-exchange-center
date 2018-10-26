package data.exchange.center.service.swh.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import data.exchange.center.service.swh.domain.Eaj;
import data.exchange.center.service.swh.domain.EajFtsy;
import data.exchange.center.service.swh.domain.EajGxyy;
import data.exchange.center.service.swh.domain.EajHbqk;
import data.exchange.center.service.swh.domain.EajMtbq;
import data.exchange.center.service.swh.domain.EajQxbg;
import data.exchange.center.service.swh.domain.EajSala;
import data.exchange.center.service.swh.domain.EajSjqk;
import data.exchange.center.service.swh.domain.EajSl;
import data.exchange.center.service.swh.domain.EajSpxy;
import data.exchange.center.service.swh.domain.EajXsfzdx;
import data.exchange.center.service.swh.domain.EajYaxx;
import data.exchange.center.service.swh.domain.EajZj;
import data.exchange.center.service.swh.domain.Edsr;
import data.exchange.center.service.swh.domain.EdsrDzlx;
import data.exchange.center.service.swh.domain.EdsrQklj;
import data.exchange.center.service.swh.domain.EdsrQt;
import data.exchange.center.service.swh.domain.EdsrQzcs;
import data.exchange.center.service.swh.domain.EdsrZkzm;
import data.exchange.center.service.swh.domain.Edsrlxqj;
import data.exchange.center.service.swh.domain.EfTzcd;
import data.exchange.center.service.swh.mapper.sgy.SwhSgyDataMapper;
import data.exchange.center.service.swh.util.Constant;
import data.exchange.center.service.swh.util.DecodeUtil;

public class ContrastThead implements Runnable {
    private String data;
    private SwhSgyDataMapper swhSgyDataMapper;

    public ContrastThead(String data, SwhSgyDataMapper swhSgyDataMapper) {
        this.data = data;
        this.swhSgyDataMapper = swhSgyDataMapper;
    }

    @SuppressWarnings("unchecked")
    public void xmlToBean() throws Exception {
        String ahdm = null;
        Document document = DocumentHelper.parseText(Constant.base64Tosrc(data));
        Element node = document.getRootElement();
        Element caseInfo = node.element("CaseInfo");
        // eaj**************************************************************
        Element eaj = caseInfo.element("EAJ");
        Eaj eajBean = new Eaj();
        ahdm = Constant.elBase64Tosrc(eaj.attribute("AHDM"));
        try {

            eajBean.setAhdm(Constant.elBase64Tosrc(eaj.attribute("AHDM")));
            eajBean.setFydm(Constant.elBase64Tosrc(eaj.attribute("FYDM")));
            eajBean.setAh(Constant.elBase64Tosrc(eaj.attribute("AH")));
            eajBean.setSarq(DecodeUtil.strToDate(Constant.elBase64Tosrc(eaj.attribute("SARQ"))));
            eajBean.setLarq(DecodeUtil.strToDate(Constant.elBase64Tosrc(eaj.attribute("LARQ"))));
            eajBean.setKtrq(DecodeUtil.strToDate(Constant.elBase64Tosrc(eaj.attribute("KTRQ"))));
            eajBean.setJarq(DecodeUtil.strToDate(Constant.elBase64Tosrc(eaj.attribute("JARQ"))));
            eajBean.setXtajlx(Constant.elBase64Tosrc(eaj.attribute("XTAJLX")));
            eajBean.setSpcx(Constant.elBase64Tosrc(eaj.attribute("SPCX")));
            eajBean.setSsxz(Constant.elBase64Tosrc(eaj.attribute("SSXZ")));
            eajBean.setSycx(Constant.elBase64Tosrc(eaj.attribute("SYCX")));
            eajBean.setAjly(Constant.elBase64Tosrc(eaj.attribute("AJLY")));
            eajBean.setJafs(Constant.elBase64Tosrc(eaj.attribute("JAFS")));
            eajBean.setMqzz(Constant.elBase64Tosrc(eaj.attribute("MQZZ")));
            eajBean.setAjlb(Constant.elBase64Tosrc(eaj.attribute("AJLB")));
            eajBean.setAjzt(Constant.elBase64Tosrc(eaj.attribute("AJZT")));
            eajBean.setXla(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eaj.attribute("XLA"))));
            eajBean.setLastupdate(DecodeUtil.strToDateLong(Constant.elBase64Tosrc(eaj.attribute("LASTUPDATE"))));
            eajBean.setJbfy(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eaj.attribute("JBFY"))));

            // ysxx****************************************
            Element eajYaxxList = caseInfo.element("EAJ_YAXX_LIST");
            List<EajYaxx> eajYaxxListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajYaxxList != null) {
                List<Element> eajYaxxs = eajYaxxList.elements();
                for (Element eajYaxx : eajYaxxs) {
                    EajYaxx eajYaxxBean = new EajYaxx();
                    eajYaxxBean.setAhdm(ahdm);
                    eajYaxxBean.setXh(Constant.elBase64Tosrc(eajYaxx.attribute("XH")));
                    eajYaxxBean.setJbfy(Constant.elBase64Tosrc(eajYaxx.attribute("JBFY")));
                    eajYaxxBean.setYabs(Constant.elBase64Tosrc(eajYaxx.attribute("YABS")));
                    eajYaxxBean.setYsah(Constant.elBase64Tosrc(eajYaxx.attribute("YSAH")));
                    eajYaxxBean.setYcbrbs(Constant.elBase64Tosrc(eajYaxx.attribute("YCBRBS")));
                    eajYaxxListBean.add(eajYaxxBean);
                }
            }
            // sala****************************************
            Element eajSala = caseInfo.element("EAJ_SALA");
            EajSala eajSalaBean = new EajSala();
            if (eajSala != null) {
                eajSalaBean.setAhdm(Constant.elBase64Tosrc(eajSala.attribute("AHDM")));
                eajSalaBean.setJcjg(Constant.elBase64Tosrc(eajSala.attribute("JCJG")));
                eajSalaBean.setGsr(Constant.elBase64Tosrc(eajSala.attribute("GSR")));
                eajSalaBean.setFdmspc(Constant.elBase64Tosrc(eajSala.attribute("FDMSPC")));
                eajSalaBean.setFdmsssqq(Constant.elBase64Tosrc(eajSala.attribute("FDMSSSQQ")));
                eajSalaBean.setCzfs(Constant.elBase64Tosrc(eajSala.attribute("CZFS")));
                eajSalaBean.setSlf(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajSala.attribute("SLF"))));
                eajSalaBean.setAjzlx(Constant.elBase64Tosrc(eajSala.attribute("AJZLX")));
                eajSalaBean.setSdclrq(Constant.elBase64Tosrc(eajSala.attribute("SDCLRQ")));
            }
            // EAJ_SL****************************************
            Element eajSl = caseInfo.element("EAJ_SL");
            EajSl eajSlBean = new EajSl();
            if (eajSl != null) {
                eajSlBean.setAhdm(Constant.elBase64Tosrc(eajSl.attribute("AHDM")));
                eajSlBean.setAjsj(Constant.elBase64Tosrc(eajSl.attribute("AJSJ")));
                eajSlBean.setGtsslx(Constant.elBase64Tosrc(eajSl.attribute("GTSSLX")));
                eajSlBean.setGksl(Constant.elBase64Tosrc(eajSl.attribute("GKSL")));
                eajSlBean.setBgkslyy(Constant.elBase64Tosrc(eajSl.attribute("BGKSLYY")));
                eajSlBean.setKtsl(Constant.elBase64Tosrc(eajSl.attribute("KTSL")));
                eajSlBean.setXprq(Constant.elBase64Tosrc(eajSl.attribute("XPRQ")));
                eajSlBean.setSwcn(Constant.elBase64Tosrc(eajSl.attribute("SWCN")));
                eajSlBean.setSfgtfz(Constant.elBase64Tosrc(eajSl.attribute("SFGTFZ")));
                eajSlBean.setGtfzrs(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajSl.attribute("GTFZRS"))));
                eajSlBean.setSfjtfz(Constant.elBase64Tosrc(eajSl.attribute("SFJTFZ")));
                eajSlBean.setFzjtrs(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajSl.attribute("FZJTRS"))));
                eajSlBean.setSqrs(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajSl.attribute("SQRS"))));
                eajSlBean.setShrs(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajSl.attribute("SHRS"))));
                eajSlBean.setSdrs(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajSl.attribute("SDRS"))));
            }

            // EAJ_SL****************************************
            Element eajSjqk = caseInfo.element("EAJ_SJQK");
            EajSjqk eajSjqkBean = new EajSjqk();
            if (eajSjqk != null) {
                eajSjqkBean.setAhdm(Constant.elBase64Tosrc(eajSjqk.attribute("AHDM")));
                eajSjqkBean.setZzsy(Constant.elBase64Tosrc(eajSjqk.attribute("ZZSY")));
                eajSjqkBean.setJasy1(Constant.elBase64Tosrc(eajSjqk.attribute("JASY1")));
            }
            // EAJ_HBQK****************************************
            Element eajHbqkList = caseInfo.element("EAJ_HBQK_LIST");
            List<EajHbqk> eajHbqkListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajHbqkList != null) {
                List<Element> eajHbqks = eajHbqkList.elements();
                for (Element eajHbqk : eajHbqks) {
                    EajHbqk eajHbqkbean = new EajHbqk();
                    eajHbqkbean.setAhdm(Constant.elBase64Tosrc(eajHbqk.attribute("AHDM")));
                    eajHbqkbean.setXh(Constant.elBase64Tosrc(eajHbqk.attribute("XH")));
                    eajHbqkbean.setHblx(Constant.elBase64Tosrc(eajHbqk.attribute("HBLX")));
                    eajHbqkbean.setHbjg(Constant.elBase64Tosrc(eajHbqk.attribute("HBJG")));
                    eajHbqkListBean.add(eajHbqkbean);
                }

            }
            // EAJ_QXBG_LIST****************************************
            Element eajQxbgList = caseInfo.element("EAJ_QXBG_LIST");
            List<EajQxbg> eajQxbgsListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajQxbgList != null) {
                List<Element> eajQxbgs = eajQxbgList.elements();
                for (Element eajQxbg : eajQxbgs) {
                    EajQxbg eajQxbgbean = new EajQxbg();
                    eajQxbgbean.setAhdm(Constant.elBase64Tosrc(eajQxbg.attribute("AHDM")));
                    eajQxbgbean.setXh(Constant.elBase64Tosrc(eajQxbg.attribute("XH")));
                    eajQxbgbean.setZzsy(Constant.elBase64Tosrc(eajQxbg.attribute("ZZSY")));
                    eajQxbgsListBean.add(eajQxbgbean);
                }
            }

            // EAJ_MTBQ_LIST****************************************
            Element eajMtbqList = caseInfo.element("EAJ_MTBQ_LIST");
            List<EajMtbq> eajMtbqListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajMtbqList != null) {
                List<Element> eajMtbqs = eajMtbqList.elements();
                for (Element eajMtbq : eajMtbqs) {
                    EajMtbq eajMtbqbean = new EajMtbq();
                    eajMtbqbean.setAhdm(Constant.elBase64Tosrc(eajMtbq.attribute("AHDM")));
                    eajMtbqbean.setXh(Constant.elBase64Tosrc(eajMtbq.attribute("XH")));
                    eajMtbqbean.setBqlx(Constant.elBase64Tosrc(eajMtbq.attribute("BQLX")));
                    eajMtbqListBean.add(eajMtbqbean);
                }
            }

            // EAJ_MTBQ_LIST****************************************
            Element eajFtsyList = caseInfo.element("EAJ_FTSY_LIST");
            List<EajFtsy> eajFtsystListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajFtsyList != null) {
                List<Element> eajFtsys = eajFtsyList.elements();
                for (Element eajFtsy : eajFtsys) {
                    EajFtsy eajFtsyBean = new EajFtsy();
                    eajFtsyBean.setAhdm(Constant.elBase64Tosrc(eajFtsy.attribute("AHDM")));
                    eajFtsyBean.setXh(Constant.elBase64Tosrc(eajFtsy.attribute("XH")));
                    eajFtsyBean.setDtdsr(Constant.elBase64Tosrc(eajFtsy.attribute("DTDSR")));
                    eajFtsyBean.setKtrq(Constant.elBase64Tosrc(eajFtsy.attribute("KTRQ")));
                    eajFtsyBean.setKtft(Constant.elBase64Tosrc(eajFtsy.attribute("KTFT")));
                    eajFtsyBean.setPtrs(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajFtsy.attribute("PTRS"))));
                    eajFtsyBean.setGkkt(Constant.elBase64Tosrc(eajFtsy.attribute("GKKT")));
                    eajFtsystListBean.add(eajFtsyBean);
                }
            }
            // EAJ_GXYY_LIST****************************************
            Element eajGxyyList = caseInfo.element("EAJ_GXYY_LIST");
            List<EajGxyy> eajGxyyListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajGxyyList != null) {
                List<Element> eajGxyys = eajGxyyList.elements();
                for (Element eajGxyy : eajGxyys) {
                    EajGxyy eajGxyyBean = new EajGxyy();
                    eajGxyyBean.setAhdm(Constant.elBase64Tosrc(eajGxyy.attribute("AHDM")));
                    eajGxyyBean.setXh(Constant.elBase64Tosrc(eajGxyy.attribute("XH")));
                    eajGxyyBean.setGxyycl(Constant.elBase64Tosrc(eajGxyy.attribute("GXYYCL")));
                    eajGxyyBean.setGxwtlx(Constant.elBase64Tosrc(eajGxyy.attribute("GXWTLX")));
                    eajGxyyListBean.add(eajGxyyBean);
                }
            }
            // EZJ_LIST****************************************
            Element eajZjList = caseInfo.element("EZJ_LIST");
            List<EajZj> eajZjListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajZjList != null) {
                List<Element> eajZjs = eajZjList.elements();
                for (Element eajZj : eajZjs) {
                    EajZj eajZjBean = new EajZj();
                    eajZjBean.setAhdm(Constant.elBase64Tosrc(eajZj.attribute("AHDM")));
                    eajZjBean.setXh(Constant.elBase64Tosrc(eajZj.attribute("XH")));
                    eajZjBean.setJzr(Constant.elBase64Tosrc(eajZj.attribute("JZR")));
                    eajZjBean.setMc(Constant.elBase64Tosrc(eajZj.attribute("MC")));
                    eajZjBean.setLx(Constant.elBase64Tosrc(eajZj.attribute("LX")));
                    eajZjBean.setZjss(Constant.elBase64Tosrc(eajZj.attribute("ZJSS")));
                    eajZjBean.setRzly(DecodeUtil.strToByte(Constant.elBase64Tosrc(eajZj.attribute("RZLY"))));
                    eajZjBean.setBrzly(DecodeUtil.strToByte(Constant.elBase64Tosrc(eajZj.attribute("BRZLY"))));
                    eajZjBean.setRzjg(Constant.elBase64Tosrc(eajZj.attribute("RZJG")));
                    eajZjListBean.add(eajZjBean);
                }
            }
            // EAJ_SPCY_LIST****************************************
            Element eajSpcyList = caseInfo.element("EAJ_SPCY_LIST");
            List<EajSpxy> eajSpcyListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajSpcyList != null) {
                List<Element> eajSpcys = eajSpcyList.elements();
                for (Element eajSpcy : eajSpcys) {
                    EajSpxy eajSpcyBean = new EajSpxy();
                    eajSpcyBean.setAhdm(Constant.elBase64Tosrc(eajSpcy.attribute("AHDM")));
                    eajSpcyBean.setXh(Constant.elBase64Tosrc(eajSpcy.attribute("XH")));
                    eajSpcyBean.setJs(Constant.elBase64Tosrc(eajSpcy.attribute("JS")));
                    eajSpcyBean.setCy(Constant.elBase64Tosrc(eajSpcy.attribute("CY")));
                    eajSpcyListBean.add(eajSpcyBean);
                }
            }
            // EDSR_LIST****************************************
            Element edsrList = caseInfo.element("EDSR_LIST");
            List<Edsr> edsrListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrList != null) {
                List<Element> edsrs = edsrList.elements();
                for (Element edsr : edsrs) {
                    Edsr edsrBean = new Edsr();
                    edsrBean.setAhdm(Constant.elBase64Tosrc(edsr.attribute("CY")));
                    edsrBean.setAhdm(Constant.elBase64Tosrc(edsr.attribute("AHDM")));
                    edsrBean.setXh(Constant.elBase64Tosrc(edsr.attribute("XH")));
                    edsrBean.setMc(Constant.elBase64Tosrc(edsr.attribute("MC")));
                    edsrBean.setSsdw(Constant.elBase64Tosrc(edsr.attribute("SSDW")));
                    edsrBean.setLx(Constant.elBase64Tosrc(edsr.attribute("LX")));
                    edsrBean.setDz(Constant.elBase64Tosrc(edsr.attribute("DZ")));
                    edsrBean.setXb(Constant.elBase64Tosrc(edsr.attribute("XB")));
                    edsrBean.setCsrq(Constant.elBase64Tosrc(edsr.attribute("CSRQ")));
                    edsrBean.setNl(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsr.attribute("NL"))));
                    edsrBean.setMz(Constant.elBase64Tosrc(edsr.attribute("MZ")));
                    edsrBean.setWhcd(Constant.elBase64Tosrc(edsr.attribute("WHCD")));
                    edsrBean.setSf(Constant.elBase64Tosrc(edsr.attribute("SF")));
                    edsrBean.setGj(Constant.elBase64Tosrc(edsr.attribute("GJ")));
                    edsrBean.setYuanj(Constant.elBase64Tosrc(edsr.attribute("YUANJ")));
                    edsrBean.setHjszd(Constant.elBase64Tosrc(edsr.attribute("HJSZD")));
                    edsrBean.setSzdw(Constant.elBase64Tosrc(edsr.attribute("SZDW")));
                    edsrBean.setZw(Constant.elBase64Tosrc(edsr.attribute("ZW")));
                    edsrBean.setSfzhm(Constant.elBase64Tosrc(edsr.attribute("SFZHM")));
                    edsrBean.setQtzjzl(Constant.elBase64Tosrc(edsr.attribute("QTZJZL")));
                    edsrBean.setQtzjhm(Constant.elBase64Tosrc(edsr.attribute("QTZJHM")));
                    edsrBean.setDwfzzjzrr(Constant.elBase64Tosrc(edsr.attribute("DWFZZJZRR")));
                    edsrBean.setFznl(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsr.attribute("FZNL"))));
                    edsrBean.setFddbr(Constant.elBase64Tosrc(edsr.attribute("FDDBR")));
                    edsrBean.setTssf(Constant.elBase64Tosrc(edsr.attribute("TSSF")));
                    edsrBean.setTsslhbl(Constant.elBase64Tosrc(edsr.attribute("TSSLHBL")));
                    edsrBean.setFzje(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsr.attribute("FZJE"))));
                    edsrBean.setCfz(Constant.elBase64Tosrc(edsr.attribute("CFZ")));
                    edsrBean.setLaoj(Constant.elBase64Tosrc(edsr.attribute("LAOJ")));
                    edsrBean.setLeif(Constant.elBase64Tosrc(edsr.attribute("LEIF")));
                    edsrBean.setDlrbh(Constant.elBase64Tosrc(edsr.attribute("DLRBH")));
                    edsrBean.setZhiy(Constant.elBase64Tosrc(edsr.attribute("ZHIY")));
                    edsrListBean.add(edsrBean);
                }
            }
            // EDSR_QZCS_LIST****************************************
            Element edsrQzcsList = caseInfo.element("EDSR_QZCS_LIST");
            List<EdsrQzcs> edsrQzcsListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrQzcsList != null) {
                List<Element> edsrQzcss = edsrQzcsList.elements();
                for (Element edsrQzcs : edsrQzcss) {
                    EdsrQzcs edsrQzcsBean = new EdsrQzcs();
                    edsrQzcsBean.setAhdm(Constant.elBase64Tosrc(edsrQzcs.attribute("AHDM")));
                    edsrQzcsBean.setXh(Constant.elBase64Tosrc(edsrQzcs.attribute("XH")));
                    edsrQzcsBean.setBgr(Constant.elBase64Tosrc(edsrQzcs.attribute("BGR")));
                    edsrQzcsBean.setJycs(Constant.elBase64Tosrc(edsrQzcs.attribute("JYCS")));
                    edsrQzcsBean.setSsrq(Constant.elBase64Tosrc(edsrQzcs.attribute("SSRQ")));
                    edsrQzcsBean.setZl(Constant.elBase64Tosrc(edsrQzcs.attribute("ZL")));
                    edsrQzcsBean.setJg(Constant.elBase64Tosrc(edsrQzcs.attribute("JG")));
                    edsrQzcsListBean.add(edsrQzcsBean);
                }
            }
            // EDSR_QKLJ_LIST****************************************
            Element edsrQkljList = caseInfo.element("EDSR_QKLJ_LIST");
            List<EdsrQklj> edsrQkljListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrQkljList != null) {
                List<Element> edsrQkljs = edsrQkljList.elements();
                for (Element edsrQklj : edsrQkljs) {
                    EdsrQklj edsrQkljBean = new EdsrQklj();
                    edsrQkljBean.setAhdm(Constant.elBase64Tosrc(edsrQklj.attribute("AHDM")));
                    edsrQkljBean.setBgr(Constant.elBase64Tosrc(edsrQklj.attribute("BGR")));
                    edsrQkljBean.setXh(Constant.elBase64Tosrc(edsrQklj.attribute("XH")));
                    edsrQkljBean.setKsrq(Constant.elBase64Tosrc(edsrQklj.attribute("KSRQ")));
                    edsrQkljBean.setPcjg(Constant.elBase64Tosrc(edsrQklj.attribute("PCJG")));
                    edsrQkljBean.setCfnr(Constant.elBase64Tosrc(edsrQklj.attribute("CFNR")));
                    edsrQkljBean.setCfyy(Constant.elBase64Tosrc(edsrQklj.attribute("CFYY")));
                    edsrQkljBean.setJsrq(Constant.elBase64Tosrc(edsrQklj.attribute("JSRQ")));
                    edsrQkljListBean.add(edsrQkljBean);
                }
            }
            // EDSR_ZKZM_LIST****************************************
            Element edsrZkzmList = caseInfo.element("EDSR_ZKZM_LIST");
            List<EdsrZkzm> edsrZkzmListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrZkzmList != null) {
                List<Element> edsrZkzms = edsrZkzmList.elements();
                for (Element edsrZkzm : edsrZkzms) {
                    EdsrZkzm edsrZkzmBean = new EdsrZkzm();
                    edsrZkzmBean.setAhdm(Constant.elBase64Tosrc(edsrZkzm.attribute("AHDM")));
                    edsrZkzmBean.setBgr(Constant.elBase64Tosrc(edsrZkzm.attribute("BGR")));
                    edsrZkzmBean.setXh(Constant.elBase64Tosrc(edsrZkzm.attribute("XH")));
                    edsrZkzmBean.setZmzs(Constant.elBase64Tosrc(edsrZkzm.attribute("ZMZS")));
                    edsrZkzmListBean.add(edsrZkzmBean);
                }
            }
            // EDSR_DZLX_LIST****************************************
            Element edsrDzlxList = caseInfo.element("EDSR_DZLX_LIST");
            List<EdsrDzlx> edsrDzlxListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrDzlxList != null) {
                List<Element> edsrDzlxs = edsrDzlxList.elements();
                for (Element edsrDzlx : edsrDzlxs) {
                    EdsrDzlx edsrDzlxBean = new EdsrDzlx();
                    edsrDzlxBean.setAhdm(Constant.elBase64Tosrc(edsrDzlx.attribute("AHDM")));
                    edsrDzlxBean.setBgr(Constant.elBase64Tosrc(edsrDzlx.attribute("BGR")));
                    edsrDzlxBean.setXh(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("XH"))));
                    edsrDzlxBean.setDzzm(Constant.elBase64Tosrc(edsrDzlx.attribute("DZZM")));
                    edsrDzlxBean.setZxzl(Constant.elBase64Tosrc(edsrDzlx.attribute("ZXZL")));
                    edsrDzlxBean.setZyxqn(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("ZYXQN"))));
                    edsrDzlxBean.setZyxqy(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("ZYXQY"))));
                    edsrDzlxBean.setHxzl(Constant.elBase64Tosrc(edsrDzlx.attribute("HXZL")));
                    edsrDzlxBean.setHxxqn(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("HXXQN"))));
                    edsrDzlxBean.setHxxqy(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("HXXQY"))));
                    edsrDzlxBean.setDcfjx(Constant.elBase64Tosrc(edsrDzlx.attribute("DCFJX")));
                    edsrDzlxBean.setBcfjx(Constant.elBase64Tosrc(edsrDzlx.attribute("BCFJX")));
                    edsrDzlxBean.setBdzzqlzs(Constant.elBase64Tosrc(edsrDzlx.attribute("BDZZQLZS")));
                    edsrDzlxBean.setBdzzqln(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("BDZZQLN"))));
                    edsrDzlxBean.setBdzzqly(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("BDZZQLY"))));
                    edsrDzlxBean.setFjse(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("FJSE"))));
                    edsrDzlxBean.setMsccje(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrDzlx.attribute("MSCCJE"))));
                    edsrDzlxListBean.add(edsrDzlxBean);
                }
            }

            // EDSR_LXQJ_LIST****************************************
            Element edsrLxqjList = caseInfo.element("EDSR_LXQJ_LIST");
            List<Edsrlxqj> edsrLxqjListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrLxqjList != null) {
                List<Element> edsrLxqjs = edsrLxqjList.elements();
                for (Element edsrLxqj : edsrLxqjs) {
                    Edsrlxqj edsrlxqjBean = new Edsrlxqj();
                    edsrlxqjBean.setAhdm(Constant.elBase64Tosrc(edsrLxqj.attribute("AHDM")));
                    edsrlxqjBean.setBgr(Constant.elBase64Tosrc(edsrLxqj.attribute("BGR")));
                    edsrlxqjBean.setXh(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(edsrLxqj.attribute("XH"))));
                    edsrlxqjBean.setZl(Constant.elBase64Tosrc(edsrLxqj.attribute("ZL")));
                    edsrlxqjBean.setFzss(DecodeUtil.strToByte(Constant.elBase64Tosrc(edsrLxqj.attribute("FZSS"))));
                    edsrlxqjBean.setQj(Constant.elBase64Tosrc(edsrLxqj.attribute("QJ")));
                    edsrLxqjListBean.add(edsrlxqjBean);
                }
            }
            // EF_TZCD_LIST****************************************
            Element efTzcdList = caseInfo.element("EF_TZCD_LIST");
            List<EfTzcd> efTzcdListBean = Collections.synchronizedList(new ArrayList<>());
            if (efTzcdList != null) {
                List<Element> efTzcds = efTzcdList.elements();
                for (Element efTzcd : efTzcds) {
                    EfTzcd efTzcdBean = new EfTzcd();
                    efTzcdBean.setAhdm(Constant.elBase64Tosrc(efTzcd.attribute("AHDM")));
                    efTzcdBean.setCdje(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(efTzcd.attribute("CDJE"))));
                    efTzcdBean.setJnrxh(Constant.elBase64Tosrc(efTzcd.attribute("JNRXH")));
                    efTzcdListBean.add(efTzcdBean);
                }
            }
            // EAJ_XSFZDX_LIST****************************************
            Element eajXsfzdxList = caseInfo.element("EAJ_XSFZDX_LIST");
            List<EajXsfzdx> eajXsfzdxListBean = Collections.synchronizedList(new ArrayList<>());
            if (eajXsfzdxList != null) {
                List<Element> eajXsfzdxs = eajXsfzdxList.elements();
                for (Element eajXsfzdx : eajXsfzdxs) {
                    EajXsfzdx eajXsfzdxBean = new EajXsfzdx();
                    eajXsfzdxBean.setAhdm(Constant.elBase64Tosrc(eajXsfzdx.attribute("AHDM")));
                    eajXsfzdxBean.setXh(Constant.elBase64Tosrc(eajXsfzdx.attribute("XH")));
                    eajXsfzdxBean.setZl(Constant.elBase64Tosrc(eajXsfzdx.attribute("ZL")));
                    eajXsfzdxBean.setSl(DecodeUtil.srcToBgd(Constant.elBase64Tosrc(eajXsfzdx.attribute("SL"))));
                    eajXsfzdxBean.setJldw(Constant.elBase64Tosrc(eajXsfzdx.attribute("JLDW")));
                    eajXsfzdxListBean.add(eajXsfzdxBean);
                }

            }
            // EDSR_QT_LIST****************************************
            Element edsrQtList = caseInfo.element("EDSR_QT_LIST");
            List<EdsrQt> edsrQtListBean = Collections.synchronizedList(new ArrayList<>());
            if (edsrQtList != null) {
                List<Element> edsrQts = edsrQtList.elements();
                for (Element edsrQt : edsrQts) {
                    EdsrQt edsrQtBean = new EdsrQt();
                    edsrQtBean.setAhdm(Constant.elBase64Tosrc(edsrQt.attribute("AHDM")));
                    edsrQtBean.setXh(Constant.elBase64Tosrc(edsrQt.attribute("XH")));
                    edsrQtBean.setMc(Constant.elBase64Tosrc(edsrQt.attribute("MC")));
                    edsrQtBean.setZw(Constant.elBase64Tosrc(edsrQt.attribute("ZW")));
                    edsrQtBean.setLx(Constant.elBase64Tosrc(edsrQt.attribute("LX")));
                    edsrQtBean.setZw(Constant.elBase64Tosrc(edsrQt.attribute("ZW")));
                    edsrQtBean.setSzdw(Constant.elBase64Tosrc(edsrQt.attribute("SZDW")));
                    edsrQtBean.setZjzl(Constant.elBase64Tosrc(edsrQt.attribute("ZJZL")));
                    edsrQtBean.setZjhm(Constant.elBase64Tosrc(edsrQt.attribute("ZJHM")));
                    edsrQtListBean.add(edsrQtBean);
                }
            }

            swhSgyDataMapper.deleteEaj(ahdm);
            swhSgyDataMapper.pushEaj(eajBean);

            swhSgyDataMapper.deleteEajYaxx(ahdm);
            for (int i = 0; i < eajYaxxListBean.size(); i++) {
                EajYaxx eajYaxx = new EajYaxx();
                eajYaxx = eajYaxxListBean.get(i);
                swhSgyDataMapper.pushEajYaxx(eajYaxx);
            }

            swhSgyDataMapper.deleteEajSjqk(ahdm);
            if (eajSjqkBean.getAhdm() != null) {
                swhSgyDataMapper.pushEajSjqk(eajSjqkBean);
            }

            swhSgyDataMapper.deleteEajSala(ahdm);
            if (eajSalaBean.getAhdm() != null) {
                swhSgyDataMapper.pushEajSala(eajSalaBean);
            }

            swhSgyDataMapper.deleteEajSl(ahdm);
            if (eajSlBean.getAhdm() != null) {
                swhSgyDataMapper.pushEajSl(eajSlBean);
            }

            swhSgyDataMapper.deleteEajHbqk(ahdm);
            for (int i = 0; i < eajHbqkListBean.size(); i++) {
                EajHbqk eajHbqk = new EajHbqk();
                swhSgyDataMapper.pushEajHbqk(eajHbqk);
            }
            swhSgyDataMapper.deleteEajQxbg(ahdm);
            for (int i = 0; i < eajQxbgsListBean.size(); i++) {
                EajQxbg eajQxbg = new EajQxbg();
                eajQxbg = eajQxbgsListBean.get(i);
                swhSgyDataMapper.pushEajQxbg(eajQxbg);
            }
            swhSgyDataMapper.deleteEajMtbq(ahdm);
            for (int i = 0; i < eajMtbqListBean.size(); i++) {
                EajMtbq eajMtbq = new EajMtbq();
                eajMtbq = eajMtbqListBean.get(i);
                swhSgyDataMapper.pushEajMtqb(eajMtbq);
            }

            swhSgyDataMapper.deleteEajFtsy(ahdm);
            for (int i = 0; i < eajFtsystListBean.size(); i++) {
                EajFtsy eajFtsy = new EajFtsy();
                eajFtsy = eajFtsystListBean.get(i);
                swhSgyDataMapper.pushEajFtsy(eajFtsy);
            }

            swhSgyDataMapper.deleteEajGxyy(ahdm);
            for (int i = 0; i < eajGxyyListBean.size(); i++) {
                EajGxyy eajGxyy = new EajGxyy();
                eajGxyy = eajGxyyListBean.get(i);
                swhSgyDataMapper.pushEajGxyy(eajGxyy);
            }

            swhSgyDataMapper.deleteEajZj(ahdm);
            for (int i = 0; i < eajZjListBean.size(); i++) {
                EajZj eajZj = new EajZj();
                eajZj = eajZjListBean.get(i);
                swhSgyDataMapper.pushEajZj(eajZj);
            }

            swhSgyDataMapper.deleteEajSpxy(ahdm);
            for (int i = 0; i < eajSpcyListBean.size(); i++) {
                EajSpxy eajSpcy = new EajSpxy();
                eajSpcy = eajSpcyListBean.get(i);
                swhSgyDataMapper.pushEajSpxy(eajSpcy);
            }

            swhSgyDataMapper.deleteEdsr(ahdm);
            for (int i = 0; i < edsrListBean.size(); i++) {
                Edsr edsr = new Edsr();
                edsr = edsrListBean.get(i);
                swhSgyDataMapper.pushEdsr(edsr);
            }

            swhSgyDataMapper.deleteEdsrQzcs(ahdm);
            for (int i = 0; i < edsrQzcsListBean.size(); i++) {
                EdsrQzcs edsrQzcs = new EdsrQzcs();
                edsrQzcs = edsrQzcsListBean.get(i);
                swhSgyDataMapper.pushEdsrQzcs(edsrQzcs);
            }

            swhSgyDataMapper.deleteEdsrQklj(ahdm);
            for (int i = 0; i < edsrQkljListBean.size(); i++) {
                EdsrQklj edsrQklj = new EdsrQklj();
                edsrQklj = edsrQkljListBean.get(i);
                swhSgyDataMapper.pushEdsrQklj(edsrQklj);
            }

            swhSgyDataMapper.deleteEdsrZkzm(ahdm);
            for (int i = 0; i < edsrZkzmListBean.size(); i++) {
                EdsrZkzm edsrZkzm = new EdsrZkzm();
                edsrZkzm = edsrZkzmListBean.get(i);
                swhSgyDataMapper.pushEdsrZkzm(edsrZkzm);
            }

            swhSgyDataMapper.deleteEdsrDzlx(ahdm);
            for (int i = 0; i < edsrDzlxListBean.size(); i++) {
                EdsrDzlx edsrDzlx = new EdsrDzlx();
                edsrDzlx = edsrDzlxListBean.get(i);
                swhSgyDataMapper.pushEdsrDzlx(edsrDzlx);
            }

            swhSgyDataMapper.deleteEdsrlxqj(ahdm);
            for (int i = 0; i < edsrLxqjListBean.size(); i++) {
                Edsrlxqj edsrlxqj = new Edsrlxqj();
                edsrlxqj = edsrLxqjListBean.get(i);
                swhSgyDataMapper.pushEdsrlxqj(edsrlxqj);
            }

            swhSgyDataMapper.deleteEfTzcd(ahdm);
            for (int i = 0; i < efTzcdListBean.size(); i++) {
                EfTzcd efTzcd = new EfTzcd();
                efTzcd = efTzcdListBean.get(i);
                if (StringUtils.isNotBlank(efTzcd.getJnrxh())) {
                    swhSgyDataMapper.pushEfTzcd(efTzcd);
                }
            }

            swhSgyDataMapper.deleteEajXsfzdx(ahdm);
            for (int i = 0; i < eajXsfzdxListBean.size(); i++) {
                EajXsfzdx eajXsfzdx = new EajXsfzdx();
                eajXsfzdx = eajXsfzdxListBean.get(i);
                swhSgyDataMapper.pushEajXsfzdx(eajXsfzdx);
            }

            swhSgyDataMapper.deleteEdsrQt(ahdm);
            for (int i = 0; i < edsrQtListBean.size(); i++) {
                EdsrQt edsrQt = new EdsrQt();
                edsrQt = edsrQtListBean.get(i);
                swhSgyDataMapper.pushEdsrQt(edsrQt);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + ahdm);
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            xmlToBean();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}
