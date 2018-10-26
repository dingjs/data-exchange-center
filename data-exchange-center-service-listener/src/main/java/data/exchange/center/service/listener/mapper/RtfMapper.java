package data.exchange.center.service.listener.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.listener.domain.Msg;

@Mapper
public interface RtfMapper {

	public List<Msg> getRtfToDocAjbs();

}
