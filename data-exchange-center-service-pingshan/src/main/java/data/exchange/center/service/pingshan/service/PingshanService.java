package data.exchange.center.service.pingshan.service;

public interface PingshanService {

	Object getAjbsList(String tjsj) throws Exception;

	Object getAjxx(String ajbs, String ajlx) throws Exception;

	Object getAjbmxx() throws Exception;

	Object getYhxx() throws Exception;

	Object getBmxx() throws Exception;

	Object getFybm() throws Exception;
	
	Object getDelAj(String tjsj) throws Exception;

}
