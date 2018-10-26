package data.exchange.center.service.listener.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.listener.domain.Msg;

@Mapper
public interface HandlerMapper {

	/**
	 * 
	 * @function 查询出还需要FTP同步的案件标识
	 * @author Tony
	 * @creaetime 2018年5月7日 上午9:57:32
	 * @return
	 * @throws Exception
	 */
	public List<Msg> getAjbs_tfp() throws Exception;

}
