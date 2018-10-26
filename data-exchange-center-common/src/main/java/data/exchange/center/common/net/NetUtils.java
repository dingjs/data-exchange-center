package data.exchange.center.common.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

	/**
	 * 
	 * @function 获取主机名称
	 * @author wenyuguang
	 * @creaetime 2018年2月7日 下午5:37:43
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}
	
	/**
	 * 获取服务器ip
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年2月7日 下午5:37:51
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(getHostName()+getIp());
	}
}
