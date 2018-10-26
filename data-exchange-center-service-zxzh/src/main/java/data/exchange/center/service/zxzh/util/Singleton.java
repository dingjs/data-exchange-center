package data.exchange.center.service.zxzh.util;

import javax.xml.rpc.ServiceException;

import data.exchange.center.service.zxzh.webservice.DataTransService;
import data.exchange.center.service.zxzh.webservice.DataTransServiceService;
import data.exchange.center.service.zxzh.webservice.DataTransServiceServiceLocator;

public class Singleton {
    private static Singleton instance;
    static DataTransService stub = null;

    private Singleton() throws ServiceException {
        DataTransServiceService caseEvidenceWebServiceService = new DataTransServiceServiceLocator();
        stub = caseEvidenceWebServiceService.getDataTransServicePort();
    }

    public static synchronized DataTransService getInstance() throws ServiceException {

        if (instance == null) {
            instance = new Singleton();
        }
        return stub;
    }
    // public static DataTransService getDataTransService() throws
    // ServiceException{
    // DataTransServiceService caseEvidenceWebServiceService = new
    // DataTransServiceServiceLocator();
    // DataTransService stub =
    // caseEvidenceWebServiceService.getDataTransServicePort();
    //// return stub;
    // }
}
