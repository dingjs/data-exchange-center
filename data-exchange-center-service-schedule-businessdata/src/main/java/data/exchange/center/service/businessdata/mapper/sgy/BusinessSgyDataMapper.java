package data.exchange.center.service.businessdata.mapper.sgy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.businessdata.domain.DCMonXmlOutDel;
import data.exchange.center.service.businessdata.domain.DCMonXmlOutZzjg;
import data.exchange.center.service.businessdata.domain.DcMonXmlOutAll;

@Mapper
public interface BusinessSgyDataMapper {
    // 排除案件或者缓存表中是否存在
    int getPcaj(Map<String, Object> params) throws Exception;

    // 查看是否案件标识不同，案号相同的案件
    int getAhcf(Map<String, Object> params) throws Exception;

    // 把案件数据写入队列缓存表
    void pushExternal2xmlToSGY(DcMonXmlOutAll dcMonXmlOutAll);

    // 写入减刑假释缓存表
    void pushTempExternal2xmlToSGY(DcMonXmlOutAll dcMonXmlOutAll);

    // 删除时排除案件或者ajsc缓存表，案件缓存表，中是否存在
    int getDelPcaj(Map<String, Object> params) throws Exception;

    // 把删除数据写入缓存表
    void pushAjsc2xmlToSGY(DCMonXmlOutDel dCMonXmlOutDel);

    // 删除时排除案件或组织人员表案件缓存表，中是否存在
    int getzzjgPcaj(Map<String, Object> params) throws Exception;

    // 把组织人员数据写入缓存表
    void pushZzjg2xmlToSGY(DCMonXmlOutZzjg dCMonXmlOutZzjg);

    // 获取法院代码
    String getFydm(String dm);

    // 写入减刑假释缓存表
    void dlTempExternal2xmlToSGY(String ajbs);

    List<DcMonXmlOutAll> getTemp(Map<String, Object> params) throws Exception;

    int setTempFlag(Map<String, Object> params) throws Exception;
}
