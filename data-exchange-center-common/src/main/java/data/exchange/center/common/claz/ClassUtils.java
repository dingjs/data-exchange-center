package data.exchange.center.common.claz;

public class ClassUtils {

	/**
	 * 
	 * @function 获取当前代码行号
	 * @author wenyuguang
	 * @creaetime 2018年2月7日 下午3:35:57
	 * @return
	 */
	public static String getLineInfo() {
		return "第" + new Throwable().getStackTrace()[1].getLineNumber() + "行";
	}

	/**
	 * 
	 * @function 获取当前类名称,包含了包名
	 * @author wenyuguang
	 * @creaetime 2018年2月7日 下午3:36:17
	 * @return
	 */
	public static String getClassFileName() {
		return new Throwable().getStackTrace()[1].getClassName();
	}

	/**
	 * 
	 * @function 获取当前方法名称
	 * @author wenyuguang
	 * @creaetime 2018年2月7日 下午3:55:38
	 * @return
	 */
	public static String getMethodName() {
		return new Throwable().getStackTrace()[1].getMethodName();
	}

	public static void main(String[] args) {

	}
}
