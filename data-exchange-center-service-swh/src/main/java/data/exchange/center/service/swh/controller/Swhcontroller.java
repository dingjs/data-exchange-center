package data.exchange.center.service.swh.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.swh.service.SwhService;
import data.exchange.center.service.swh.util.Constant;

/**
 * @ClassName: Swhcontroller
 * @Description:获取案件信息
 * @author: BaiMaojun
 * @Date: 2018年5月2日 上午9:56:42
 */
@RestController
public class Swhcontroller {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SwhService swhService;

    /**
     * @Title: getCaseInfo @Description: 获取个案基本信息 @param @param
     * caseId @param @return @param @throws UnsupportedEncodingException
     * 参数 @return String 返回类型 @throws
     */
    @RequestMapping(value = "/getCaseInfo", method = RequestMethod.GET)
    public String getCaseInfo(@RequestParam String caseId) throws UnsupportedEncodingException {
        System.out.println("个案信息获取："+caseId);
        return swhService.GetCaseInfo(caseId);
    }

    /**
     * @Title: getCaseList @Description: 根据时间后去之后的更新案件列表 @param @param
     * latestSynchTime @param @param ajzt @param @param
     * pageNum @param @return @param @throws
     * UnsupportedEncodingException @param @throws DocumentException 参数 @return
     * String 返回类型 @throws
     */
    @RequestMapping(value = "/getCaseList", method = RequestMethod.GET)
    public String getCaseList(@RequestParam String latestSynchTime, @RequestParam String ajzt,
            @RequestParam String pageNum) throws UnsupportedEncodingException, DocumentException {
        String data = swhService.GetCaseList(latestSynchTime, ajzt, pageNum);
        Document document = DocumentHelper.parseText(Constant.base64Tosrc(data));
        Element node = document.getRootElement();
        Element CaseList = node.element("CaseList");
        List<Element> CaseLists = CaseList.elements();
        for (Element Case : CaseLists) {
            // Element CaseInfo = Case.element("CaseInfo");
            Element eaj = Case.element("EAJ");
            System.out.println(Constant.base64Tosrc(eaj.attribute("AHDM").getText()));
//            swhService.GetCaseInfo(Constant.base64Tosrc(eaj.attribute("AHDM").getText()));
        }
        return data;
    }

    /**
     * @throws FileNotFoundException 
     * @Title: getLb @Description: 根据案件标识及序号获取blob文件 @param @param
     * caseId @param @param xh @param @return @param @throws
     * UnsupportedEncodingException 参数 @return Object 返回类型 @throws
     */
    @RequestMapping(value = "/getLb", method = RequestMethod.GET)
    public Object getLb(@RequestParam String caseId, @RequestParam String xh) throws Exception, FileNotFoundException {
        System.out.println("BLOB数据获取，案件标识："+caseId+"序号："+xh);
//        Object nr = swhService.getLb(caseId, xh);
//        FileOutputStream FileOutputStream = new FileOutputStream(new File("D:\\1.doc"));
//        FileOutputStream.write((byte[])nr);
//        FileOutputStream.close();
        return swhService.getLb(caseId, xh);
    }

    /**
     * @Title: getWj @Description: 获取目录文件信息及存储位置和目录 @param @param
     * caseId @param @return @param @throws UnsupportedEncodingException
     * 参数 @return Object 返回类型 @throws
     */
    @RequestMapping(value = "/getWj", method = RequestMethod.GET)
    public Object getWj(@RequestParam String caseId) throws UnsupportedEncodingException {
        System.out.println("获取文件信息："+caseId);
        return swhService.getwj(caseId);
    }

    /**
     * @Title: setDbxx @Description: 文书回填（督办信息） @param @param
     * dbxx @param @return 参数 @return String 返回类型 @throws
     */
    @RequestMapping(value = "/reviewResults", method = RequestMethod.GET)
    public String reviewResults(@RequestParam String xml) {
        System.out.println("回写督办信息"+xml);
        return swhService.setDbxx(xml);
    }
}
