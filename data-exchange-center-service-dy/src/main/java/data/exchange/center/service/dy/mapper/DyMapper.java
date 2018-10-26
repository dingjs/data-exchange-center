package data.exchange.center.service.dy.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName:  DyMapper
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: BaiMaojun
 * @Date:   2018年5月14日 下午3:15:09
 */
@Mapper
public interface DyMapper {
    Map<String, Object> getFileName( Map<String, Object> param );
	
}
