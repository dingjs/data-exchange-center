package service.sjsb.service;

import java.util.List;
import java.util.Map;

import service.sjsb.domain.AjxxInfo;

public interface SjsbService {

	/**
	 * 
	 * @function 获取案件信息
	 * @author Tony
	 * @creaetime 2018年5月11日 下午3:39:54
	 * @return
	 */
	public List<AjxxInfo> getAjbs();

	/**
	 * 
	 * @function 获取单个案件信息
	 * @author Tony
	 * @param ajlx 
	 * @param ajbs 
	 * @creaetime 2018年5月11日 下午3:40:05
	 * @return
	 */
	public Map<String, Object> getAjData(String ajbs, String ajlx, String fydm);

	/**
	 * 
	 * @function 生成的zip包绝对路径
	 * @author Tony
	 * @creaetime 2018年5月11日 下午5:20:58
	 * @param zipName
	 */
	public void addSjsbFile(String zipName);
	/**
	 * 
	 * @function 查询当天需要上报的zip文件列表
	 * @author Tony
	 * @creaetime 2018年5月11日 下午5:50:20
	 * @return
	 */
	public List<String> getSjsbFile();
	
	/**
	 * 
	 * @function 查询需要删除的zip文件列表
	 * @author Tony
	 * @creaetime 2018年6月19日 上午11:47:40
	 * @return
	 */
	public List<String> getDeleteSjsbFile();
	

}
