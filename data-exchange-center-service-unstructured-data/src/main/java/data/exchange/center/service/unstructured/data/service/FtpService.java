package data.exchange.center.service.unstructured.data.service;

public interface FtpService {

	public Object handleFtpFile(String ajbs, String ajlx, String fydm) throws Exception;

	public Object handleTempUnstructureData(String ajbs, String ajlx, String fydm, String uuid) throws Exception;

	public Object handlePath(String ajbs, String ajlx, String fydm, String uuid) throws Exception;

}
