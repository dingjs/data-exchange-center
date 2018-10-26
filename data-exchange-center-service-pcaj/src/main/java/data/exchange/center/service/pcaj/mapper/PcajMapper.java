package data.exchange.center.service.pcaj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import data.exchange.center.service.pcaj.domain.ColMeta;
import data.exchange.center.service.pcaj.domain.TableMeta;

@Mapper
public interface PcajMapper {

	List<ColMeta> getColMeta(String ajlx) throws Exception;

	int saveCase(@Param("params")Map<String, Object> params) throws Exception;

	void deleteTableRecord(@Param("deleteSql")String deleteSql) throws Exception;

	List<TableMeta> getTableMeta(String ajlxChName) throws Exception;

	int insertTable_uf_eaj(@Param("map")Map<String, Object> mainTableParams) throws Exception;
}
