package data.exchange.center.service.shehong.service;

public interface ShehongService {

	Object getAjbsInfo(String ajbs, String fydm, String ajlx) throws Exception;

	Object getAjcl(String ajbs, String fydm) throws Exception;

	Object getAjclList(String ajbs, String fydm, String ajlx) throws Exception;

	Object getUserInfo(String fydm) throws Exception;

	Object getBmInfo() throws Exception;

	Object getAjbsList(String startDate, String endDate, String fydm) throws Exception;

}
