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
public class LAAYMappingData {

    public final static ConcurrentMap<String, String> mappingData = new ConcurrentHashMap<String, String>();
    static{
        mappingData.put("11", "XSYS_SAHLAXX.QSZZM");
        mappingData.put("12", "XSES_SAHLAXX.ZZM");
        mappingData.put("13", "XSFH_SAHLAXX.FHZM");
        mappingData.put("14", "XSZS_SAHLAXX.ZM");
        mappingData.put("15", "XFBG_SAHLAXX.ZZM");
        mappingData.put("16", "QZYL_SAHLAXX.SAZXW");
        mappingData.put("17", "QZYLFY_SAHLAXX.SAZXW");
        mappingData.put("18", "JCQZYL_SAHLAXX.WFZXW");
        mappingData.put("19", "QZYLJD_SAXX.SAZXW");
        mappingData.put("1A", "MSWFSDSQSC_SAXX.SAZM");
        mappingData.put("1D", "TZZXSX_SAXX.ZZM");
        mappingData.put("1E", "XFYZXBGJD_SAXX.ZZM");
        mappingData.put("1F", "XFYZXBGBA_SAXX.ZZM");
        mappingData.put("1G", "SQAZJYSC_SAXX.SAZM");
        mappingData.put("1Z", "QTXS_SAXX.ZZM");
        mappingData.put("21", "MSYS_SAHLAXX.LAAY");
        mappingData.put("22", "MSES_SAHLAXX.LAAY");
        mappingData.put("23", "MSZS_SAHLAXX.LAAY");
        mappingData.put("24", "MSTS_SAHLAXX.LAAY");
        mappingData.put("25", "PC_SAHLAXX.LAAY");
        mappingData.put("26", "DSRCXZS_SAXX.AY");
        mappingData.put("31", "XZYS_SAHLAXX.LAAY");
        mappingData.put("32", "XZES_SAHLAXX.LAAY");
        mappingData.put("33", "XZZS_SAHLAXX.LAAY");
        mappingData.put("34", "XZFS_SAHLAXX.LAAY");
        mappingData.put("41", "PCQR_SAHLAXX.LAAY");
        mappingData.put("42", "SFPC_SAHLAXX.LAAY");
        mappingData.put("51", "ZXAJ_SAHLAXX.LAAY");
        mappingData.put("61", "SSZSYSPJD_SAHLAXX.YSXCPAY");
        mappingData.put("81", "SQBQ_SAHLAXX.LAAY");
        mappingData.put("D1", "XSGX_SAXX.LAAY");
        mappingData.put("D2", "MSGX_SAXX.LAAY");
        mappingData.put("D3", "XZGX_SAXX.LAAY");
        mappingData.put("D4", "XZPCGX_SAXX.LAAY");
        /**
         * 以下为不需要处理的案件类型
         */
        mappingData.put("1B", "");
        mappingData.put("1C", "");
        mappingData.put("1H", "");
        mappingData.put("1I", "");
        mappingData.put("1J", "");
        mappingData.put("2A", "");
        mappingData.put("2B", "");
        mappingData.put("2C", "");
        mappingData.put("2D", "");
        mappingData.put("2E", "");
        mappingData.put("2F", "");
        mappingData.put("2Z", "");
        mappingData.put("27", "");
        mappingData.put("28", "");
        mappingData.put("29", "");
        mappingData.put("3Z", "");
        mappingData.put("43", "");
        mappingData.put("44", "");
        mappingData.put("45", "");
        mappingData.put("46", "");
        mappingData.put("47", "");
        mappingData.put("48", "");
        mappingData.put("49", "");
        mappingData.put("4Z", "");
        mappingData.put("5Z", "");
        mappingData.put("71", "");
        mappingData.put("82", "");
        mappingData.put("91", "");
        mappingData.put("A1", "");
        mappingData.put("AZ", "");
        mappingData.put("B1", "");
        mappingData.put("B2", "");
        mappingData.put("B3", "");
        mappingData.put("B4", "");
        mappingData.put("B5", "");
        mappingData.put("B6", "");
        mappingData.put("B7", "");
        mappingData.put("B8", "");
        mappingData.put("B9", "");
        mappingData.put("BA", "");
        mappingData.put("C1", "");
        mappingData.put("ZZ", "");
    }
    /**
     * 
     * @function 入案件主表时，对应的映射关系
     * </p>--           自行解析部份：
</p>--          LAAY        案件类型 11     XSYS_SAHLAXX.QSZZM
</p>--                                      12      XSES_SAHLAXX.ZZM
</p>--                                      14      XSFH_SAHLAXX.FHZM
</p>--                                      13      XSZS_SAHLAXX.ZM
</p>--                                      15      XFBG_SAHLAXX.ZZM
</p>--                                      16      QZYL_SAHLAXX.SAZXW
</p>--                                      17      QZYLFY_SAHLAXX.SAZXW
</p>--                                      18      JCQZYL_SAHLAXX.WFZXW
</p>--                                      19      QZYLJD_SAXX.SAZXW
</p>--                                      1A      MSWFSDSQSC_SAXX.SAZM
</p>--                                      1B  -1C     不处理
</p>--                                      1D      TZZXSX_SAXX.ZZM
</p>--                                      1E      XFYZXBGJD_SAXX.ZZM
</p>--                                      1F      XFYZXBGBA_SAXX.ZZM
</p>--                                      1G      SQAZJYSC_SAXX.SAZM
</p>--                                      1H-1I-1J        不处理
</p>--                                      1Z      QTXS_SAXX.ZZM
</p>--                                      2A-2B-2C-2D-2E-2F-2Z        不处理
</p>--                                      21      MSYS_SAHLAXX.LAAY
</p>--                                      22      MSES_SAHLAXX.LAAY
</p>--                                      23      MSZS_SAHLAXX.LAAY
</p>--                                      24      MSTS_SAHLAXX.LAAY
</p>--                                      25      PC_SAHLAXX.LAAY
</p>--                                      26      DSRCXZS_SAXX.AY
</p>--                                      27  -28-29      不处理
</p>--                                      31      XZYS_SAHLAXX.LAAY
</p>--                                      32      XZES_SAHLAXX.LAAY
</p>--                                      33      XZZS_SAHLAXX.LAAY
</p>--                                      34      XZFS_SAHLAXX.LAAY
</p>--                                      3Z      不处理
</p>--                                      41      PCQR_SAHLAXX.LAAY
</p>--                                      42      SFPC_SAHLAXX.LAAY
</p>--                                      43-44-45-46-47-48-49-4Z     不处理
</p>--                                      51      ZXAJ_SAHLAXX.LAAY
</p>--                                      5Z      不处理
</p>--                                      61      SSZSYSPJD_SAHLAXX.YSXCPAY
</p>--                                      71      不处理
</p>--                                      81      SQBQ_SAHLAXX.LAAY
</p>--                                      82-91-A1-AZ     不处理
</p>--                                      B1-B2-B3-B4-B5-B6-B7-B8-B9-BA-C1    不处理
</p>--                                      D1      XSGX_SAXX.LAAY
</p>--                                      D2      MSGX_SAXX.LAAY
</p>--                                      D3      XZGX_SAXX.LAAY
</p>--                                      D4      XZPCGX_SAXX.LAAY
</p>--                                      ZZ      不处理
     * @author wenyuguang
     * @creaetime 2017年3月25日 下午9:57:13
     * @return
     */
    public static ConcurrentMap<String, String> getMappingData() {
        return mappingData;
    }
}
