package data.exchange.center.service.parse.xml.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;
import data.exchange.center.service.parse.xml.domain.XmlContent;

@Mapper
public interface ParseStorageJxjsMapper {

	public XmlContent getXML(@Param("map")Map<String, Object> map);

	public List<ColMeta> getColMeta(String ajlxEname);

	public List<TableMeta> getTableMeta(String ajlxEname);

}
