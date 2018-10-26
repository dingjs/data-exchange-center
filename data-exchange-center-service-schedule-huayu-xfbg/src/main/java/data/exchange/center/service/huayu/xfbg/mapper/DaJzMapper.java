package data.exchange.center.service.huayu.xfbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.huayu.xfbg.domain.Ajxx;
import data.exchange.center.service.huayu.xfbg.domain.Daj;
import data.exchange.center.service.huayu.xfbg.domain.Gcj;

@Mapper
public interface DaJzMapper {

    int pushDajToSGY(Daj daj) throws Exception;

    int pushGcjToSGY(Gcj gcj) throws Exception;

    List<Ajxx> getAllAjxx() throws Exception;
}
