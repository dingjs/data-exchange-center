package data.exchange.center.service.zxzh.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 自定义拦截器根据ip过滤所有请求，限制请求频率
 * 
 * @author LKD
 *
 */
public class InterceptorConfig implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);

    /**
     * 进入controller层之前拦截请求
     * 
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }

    /**
     * servletContext查该用户ip如果没有， 就加入该ip，并把当前系统时间存入map的value中，
     * 如果servletContext有该ip，就去取map，取到map取上一次访问时间，
     * 如果时间是null说明上一次时间访问次数是0，清空map然后存入当前时间，然后在把map绑定到servletContext，
     * 如果map的value取到了上一次时间，说明在上一次时间有访问，将上一个时间和当前时间相减，是否时间大于2秒，如果不是拦截。
     */
    @Autowired
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        // 获取全局ServletContext
        ServletContext servletContext = request.getServletContext();
        // 获取ip
        String ip =getIpAddress(request);
        // 获取当前系统时间
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        // 返回状态码
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
    	Calendar cl = Calendar.getInstance();
    	//指定ip双休全天访问
    	if(doPeanIp(ip,cl)){
    		return checkTime(servletContext,ip,cl,date,writer,response);
    	}else{
	    	int hour = cl.get(Calendar.HOUR_OF_DAY);
	       	if(!(hour < 7 || hour >= 20)){
		       	 writer = response.getWriter();
		         Map<String, String> map = new HashMap<String, String>();
		         map.put("msg", "不在请求时间!");
		         JSONObject json = JSONObject.fromObject(map);
		         writer.print(json);
		         System.out.println("IP:"+ip+"不在请求时间!");
		         return false;
	       	}
	       	return checkTime(servletContext,ip,cl,date,writer,response);
    	}
    	
    }
    public static String getIpAddress(HttpServletRequest request){  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    } 
    
    
    /**
     * 周六周天放行指定ip
     * @param ip
     * @param c
     * @param writer
     * @return
     */
    public static Boolean doPeanIp(String ip,Calendar c){
    	int day = c.get(Calendar.DAY_OF_WEEK);
    	if(ip.equals("150.0.2.202")&&day==7||day==1){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * checkTime
     * @param servletContext
     * @param ip
     * @param cl
     * @param date
     * @param writer
     * @param response
     * @return
     * @throws IOException
     */
    public static Boolean checkTime(ServletContext servletContext,String ip,Calendar cl,Date date,PrintWriter writer,HttpServletResponse response) throws IOException{
    	Map<String, Date> timeRecord = new HashMap<String, Date>();
        // 获取serlvetContext中的ip如果没有就加入
        timeRecord = (Map<String, Date>) servletContext.getAttribute(ip);
        // 判断serlvetContext是否有ip如果没有加添加
        if (timeRecord == null) {
            Map<String, Date> fristtimeRecord = new HashMap<String, Date>();
            // 将当前时间存入map中
            fristtimeRecord.put("sysTime", date);
            // 将map绑定到servletContext
            servletContext.setAttribute(ip, fristtimeRecord);
            return true;
        } else {
            // 如果没有上一次时间为空则清空map加入当前时间
            if (timeRecord.get("sysTime") == null) {
                timeRecord.clear();
                timeRecord.put("sysTime", date);
                servletContext.setAttribute(ip, timeRecord);
                System.out.println("IP:"+ip+"初次请求");
                return true;
            } else {
                // 获取上一次请求时间
                Date oldsysTime = timeRecord.get("sysTime");
                int diff = (int) (date.getTime() - oldsysTime.getTime()) / 1000;
                System.out.println("date.getTime():"+date.getTime()/ 1000);
                System.out.println("oldsysTime.getTime():"+oldsysTime.getTime()/ 1000);
                // 时间间隔小于或等于两秒时拦截
                if (diff <= 2) {
                    writer = response.getWriter();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("msg", "请求频率过高");
                    JSONObject json = JSONObject.fromObject(map);
                    writer.print(json);
                    System.out.println("IP:"+ip+"请求频率过高");
                    return false;
                } else {
                    timeRecord.put("sysTime", date);
                    servletContext.setAttribute(ip, timeRecord);
                    System.out.println("IP:"+ip+"请求成功");
                    return true;
                }
            }
        }
    }
    
}
