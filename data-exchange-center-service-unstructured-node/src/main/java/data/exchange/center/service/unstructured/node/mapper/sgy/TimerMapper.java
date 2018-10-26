package data.exchange.center.service.unstructured.node.mapper.sgy;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.unstructured.node.domain.SubTask;
import data.exchange.center.service.unstructured.node.domain.TaskInfo;


/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月21日 下午1:46:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface TimerMapper {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年11月20日 下午6:11:54
	 * @param fydm
	 * @return
	 */
	List<TaskInfo> getTask(String fydm) throws Exception;
	
	List<SubTask> getSubbTask(Integer taskId) throws Exception;
	
	
}
