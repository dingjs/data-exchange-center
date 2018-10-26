package data.exchange.center.service.parse.ftpzip;

public class TestPattern {

	public static void main(String[] args) {
		String str = "解析出错误了，f86b397275344899b1dabd8993d40048xml解析入库出错：nested exception is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property='MLSXH', mode=IN, javaType=class java.lang.Object, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #5 with JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: java.sql.SQLException: 无效的列类型: 1111";  
        String reg = "[^\u4e00-\u9fa5]";  
        str = str.replaceAll(reg, "");  
        System.out.println(str);
	}
}
