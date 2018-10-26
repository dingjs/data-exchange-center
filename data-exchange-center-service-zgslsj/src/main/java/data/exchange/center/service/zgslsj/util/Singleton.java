package data.exchange.center.service.zgslsj.util;

import javax.xml.rpc.ServiceException;

import data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccess;
import data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccessService;
import data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccessServiceLocator;



public class Singleton {
    private static Singleton instance;
    static TmriJaxRpcOutAccess stub = null;

    private Singleton() throws ServiceException {
        TmriJaxRpcOutAccessService caseEvidenceWebServiceService = new TmriJaxRpcOutAccessServiceLocator();
        stub = caseEvidenceWebServiceService.getTmriOutAccess();
    }

    public static synchronized TmriJaxRpcOutAccess getInstance() throws ServiceException {

        if (instance == null) {
            instance = new Singleton();
        }
        return stub;
    }
}
