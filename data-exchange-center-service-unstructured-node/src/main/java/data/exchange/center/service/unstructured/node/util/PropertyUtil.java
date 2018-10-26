package data.exchange.center.service.unstructured.node.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PropertyUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;
    static{
        loadProps();
    }
    synchronized static private void loadProps(){
        props = new Properties();
        InputStream in = null;
        try {
            System.out.println(System.getProperty("applicationKey"));
            in = PropertyUtil.class.getResourceAsStream("/application-"+System.getProperty("applicationKey")+".properties");//如果修改下面还有一个
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("jdbc.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("jdbc.properties文件流关闭出现异常");
            }
        }
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
    public static void setProper(String key,String value){
        if(null == props) {
            loadProps();
        }
        props.setProperty(key, value);
        //保存文件
        try {
            URL fileUrl = PropertyUtil.class.getClassLoader().getResource("/application.properties");//如果修改上面还有一个
            FileOutputStream fos = new FileOutputStream(new File(fileUrl.toURI()));
            props.store(fos, "the primary key of article table");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        }
}
