package data.exchange.center.service.zxzh.controller;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.zxzh.service.ZxzhService;

@RestController
public class EvidenceGuidanceController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ZxzhService zxzhServiceService;

    @RequestMapping(value = "/getZzjg", method = RequestMethod.GET)
    public String getZzjg(@RequestParam("xml") String xml) throws UnsupportedEncodingException {
        System.out.println("getZzjg");
        return zxzhServiceService.getZzjg(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getCaseList", method = RequestMethod.GET)
    public String getCaseList(@RequestParam("xml") String xml) {
        System.out.println("getCaseList");
        return zxzhServiceService.getCaseList(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getDailyUpdateCaseList", method = RequestMethod.GET)
    public String getDailyUpdateCaseList(String xml) {
        System.out.println("getDailyUpdateCaseList");
        return zxzhServiceService.getDailyUpdateCaseList(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getCaseXml", method = RequestMethod.GET)
    public String getCaseXml(String xml) {
        System.out.println("getCaseXml");
        return zxzhServiceService.getCaseXml(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getCaseZip", method = RequestMethod.GET)
    public String getCaseZip(String xml) {
        System.out.println("getCaseZip");
        return zxzhServiceService.getCaseZip(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getWsData", method = RequestMethod.GET)
    public String getWsData(String xml) {
        System.out.println("getWsData");
        return zxzhServiceService.getWsData(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getCaseXml09", method = RequestMethod.GET)
    public String getCaseXml09(String xml) {
        return zxzhServiceService.getCaseXml09(xml.toString().replace(" ", "+"));
    }

    @RequestMapping(value = "/getDelZip", method = RequestMethod.GET)
    public String getDelZip(String xml) {
        System.out.println("getDelZip");
        return zxzhServiceService.getDelZip(xml.toString().replace(" ", "+"));
    }
}
