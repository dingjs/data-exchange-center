package data.exchange.center.service.sfgk.util.jdbc;

public class JdbcFactory {

	public static JdbcFactory factory;
	
	public static JdbcFactory newInstance() {
		synchronized (JdbcFactory.class) {
			if(factory == null)
				return new JdbcFactory();
		}
		return factory;
	}
	
	public static Object getClass(Class<? extends JdbcConnection> clazz) {
        Object obj = null;
        try {
            obj = Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
