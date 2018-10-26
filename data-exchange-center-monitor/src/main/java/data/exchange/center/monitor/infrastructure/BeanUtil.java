package data.exchange.center.monitor.infrastructure;


import org.springframework.beans.BeanUtils;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:53:57</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class BeanUtil {

    public static void copeProperties(Object from,Object dest){
        try {
            BeanUtils.copyProperties(from, dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
