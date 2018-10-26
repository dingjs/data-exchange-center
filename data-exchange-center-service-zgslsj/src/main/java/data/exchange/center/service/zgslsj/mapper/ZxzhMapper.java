package data.exchange.center.service.zgslsj.mapper;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.zgslsj.domain.ZxzhTemporary;



/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月15日 上午11:17:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface ZxzhMapper {
 int pushZzzhTemporary(ZxzhTemporary zxzhTemporary);
}
