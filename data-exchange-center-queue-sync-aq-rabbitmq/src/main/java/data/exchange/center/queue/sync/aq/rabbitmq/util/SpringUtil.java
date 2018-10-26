package data.exchange.center.queue.sync.aq.rabbitmq.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * Description:获取spring bean工具类
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午3:51:31</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

	/**
	 * 获取applicationContext
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年9月6日 下午3:51:05
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 通过name获取 Bean.
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年9月6日 下午3:51:10
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 通过class获取Bean.
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年9月6日 下午3:51:14
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 通过name,以及Clazz返回指定的Bean
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年9月6日 下午3:51:18
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

}