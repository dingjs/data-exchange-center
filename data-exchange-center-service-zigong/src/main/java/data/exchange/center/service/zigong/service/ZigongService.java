package data.exchange.center.service.zigong.service;

public interface ZigongService {

	Object getAjbsList(String startDateTime, String endDateTime) throws Exception;

	Object getTqtj(String ajbs, String ajlx) throws Exception;
	
}
