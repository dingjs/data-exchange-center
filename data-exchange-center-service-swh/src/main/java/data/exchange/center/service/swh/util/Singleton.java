package data.exchange.center.service.swh.util;

import javax.xml.rpc.ServiceException;


import data.exchange.center.service.swh.webservice.ILdtService;
import data.exchange.center.service.swh.webservice.ILdtServiceService;
import data.exchange.center.service.swh.webservice.ILdtServiceServiceLocator;



public class Singleton {
    private static Singleton instance;
    static ILdtService stub = null;

    private Singleton() throws ServiceException {
        ILdtServiceService caseEvidenceWebServiceService = new ILdtServiceServiceLocator();
        stub = caseEvidenceWebServiceService.getILdtServicePort();
    }

    public static synchronized ILdtService getInstance() throws ServiceException {

        if (instance == null) {
            instance = new Singleton();
        }
        return stub;
    }
}
