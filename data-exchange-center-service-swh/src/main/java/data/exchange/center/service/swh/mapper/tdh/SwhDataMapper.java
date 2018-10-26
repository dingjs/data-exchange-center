package data.exchange.center.service.swh.mapper.tdh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.swh.domain.EaJDa;
import data.exchange.center.service.swh.domain.EaJJZMl;
import data.exchange.center.service.swh.domain.EaJz;
import data.exchange.center.service.swh.domain.EajDaMl;
import data.exchange.center.service.swh.domain.TempEajJz;

@Mapper
public interface SwhDataMapper {
    List<EaJz> getEaJz(Map<String, String> map) throws Exception;

    List<EaJJZMl> getEajJzMl(Map<String, String> map) throws Exception;

    List<EaJDa> getEajDa(Map<String, String> map) throws Exception;

    List<EajDaMl> getEaJDaMl(Map<String, String> map) throws Exception;
    
    TempEajJz getLb(Map<String, String> map) throws Exception;

}
