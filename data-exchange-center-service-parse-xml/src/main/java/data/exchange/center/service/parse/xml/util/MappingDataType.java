/**
 * data-governance-api
 * created by yuguang at 2017年3月19日
 */
package data.exchange.center.service.parse.xml.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年3月19日 上午11:50:07</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class MappingDataType {

    public final static ConcurrentMap<String, String> dataType = new ConcurrentHashMap<String, String>();
    static{
        dataType.put("FLOAT", "FLOAT");
        dataType.put("TIMESTAMP", "TIMESTAMP");
        dataType.put("NVARCHAR2", "VARCHAR");
        dataType.put("NUMBER", "DOUBLE");
        dataType.put("CHAR", "CHAR");
        dataType.put("CLOB", "CLOB");
        dataType.put("DATE", "TIMESTAMP");
        dataType.put("VARCHAR2", "VARCHAR");
        dataType.put("BLOB", "BLOB");
    }
    /**
     * 
     * @function jdbcType和数据库字段类型转换
     * @author wenyuguang
     * @creaetime 2017年3月19日 下午12:48:41
     * @return
     */
    public static ConcurrentMap<String, String> getDataType() {
        return dataType;
    }
}
