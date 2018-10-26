package service.sjsb;

import java.io.File;

import com.thunisoft.exchangeplatform.epclient.client.EPClient;
import com.thunisoft.exchangeplatform.epclient.client.clientcallers.HttpSendCaller;
import com.thunisoft.exchangeplatform.protocol.EPProtocol;
import com.thunisoft.exchangeplatform.service.call.ServiceAddress;

public class Test {

	public static void main(String[] args) {
//		try {
//			String url = "http://192.168.0.1:8080";
//			EPClient client = new EPClient(ServiceAddress.valueOf(url));
//			EPProtocol protocol = new EPProtocol("案件传输", "最高数据平台-1");
//			File file = new File("D:\\DL_0012_20140101233720_01_3_2017.zip ");
//			HttpSendCaller caller = new HttpSendCaller(file, protocol, file.getName());
//			client.call(caller);
//		} catch (Throwable e) {
//
//			
//		}
		File f = new File("e://test//test//test");
		if(!f.exists()) {
			f.mkdirs();
		}
	}
}
