package data.exchange.center.service.sfgk.service;

import java.util.List;
import java.util.Map;

import data.exchange.center.service.sfgk.domain.Aj;
import data.exchange.center.service.sfgk.domain.Fydm;

public interface SfgkService {
	/**
	 * 
	 * @function 开庭公告信息
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:45:15
	 * @return
	 * @throws Exception
	 */
	public Object ktgg(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 案件信息
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:45:33
	 * @return
	 * @throws Exception
	 */
	public Object ajxx(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 法院公开方式
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:44:56
	 * @return
	 * @throws Exception
	 */
	public Object fygkfs(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 案件公开效果信息
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:44:46
	 * @return
	 * @throws Exception
	 */
	public Object ajck(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 网站公开效果信息
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:44:36
	 * @return
	 * @throws Exception
	 */
	public Object wzfwl(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 用户登录信息
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:44:26
	 * @return
	 * @throws Exception
	 */
	public Object yhdl(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 案件撤销记录
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:44:06
	 * @return
	 * @throws Exception
	 */
	public Object ajcx(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	/**
	 * 
	 * @function 用户登录信息
	 * @author Tony
	 * @param VERSION 
	 * @param FYBH 
	 * @param GXKSSJ 
	 * @param GXJSSJ 
	 * @param YWLX 
	 * @creaetime 2018年4月25日 上午10:43:48
	 * @return
	 * @throws Exception
	 */
	public Object dx(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception;
	
	/**
	 * 
	 * @function 根据案件标识和序号查找姓名
	 * @author Tony
	 * @creaetime 2018年4月26日 上午10:54:01
	 * @param ajbs
	 * @param xh
	 * @return
	 * @throws Exception
	 */
	public String getYastmlMc(String ajbs, String xh) throws Exception;
	/**
	 * 
	 * @function 获取开庭信息
	 * @author Tony
	 * @creaetime 2018年4月26日 上午11:36:58
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getKtgkxx(String ajbs, String ajlx) throws Exception;
	/**
	 * 
	 * @function 获取当前日期的前一天案件信息
	 * @author Tony
	 * @creaetime 2018年4月26日 下午4:00:15
	 * @return
	 * @throws Exception
	 */
	public List<Aj> selectAj() throws Exception;
	/**
	 * 
	 * @function 增加已经生成的zip包信息
	 * @author Tony
	 * @creaetime 2018年4月26日 下午6:53:40
	 * @param zipname
	 * @throws Exception
	 */
	public void addSfgkFile(String zipname) throws Exception;
	/**
	 * 
	 * @function 查询要下载的文件
	 * @author Tony
	 * @creaetime 2018年4月26日 下午8:20:58
	 * @param zipname
	 * @return
	 * @throws Exception
	 */
	public String selectDownFilePath(String zipname) throws Exception;
	/**
	 * 
	 * @function 获取全省法院信息
	 * @author Tony
	 * @creaetime 2018年4月27日 下午7:49:17
	 * @return
	 * @throws Exception
	 */
	public List<Fydm> getFydm() throws Exception;
	/**
	 * 
	 * @function 获取案件公开案件列表
	 * @author Tony
	 * @creaetime 2018年6月22日 上午9:45:52
	 * @return
	 * @throws Exception
	 */
	public List<Aj> selectAjgkAj() throws Exception;
	/**
	 * 
	 * @function 获取刑事案件公开信息
	 * @author Tony
	 * @creaetime 2018年6月22日 上午9:56:37
	 * @param ajbs
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getXsAjgkxx(String ajbs, String ajlx) throws Exception;
	/**
	 * 
	 * @function 获取民事案件公开信息
	 * @author Tony
	 * @creaetime 2018年6月22日 上午9:56:37
	 * @param ajbs
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMsAjgkxx(String ajbs, String ajlx) throws Exception;
	/**
	 * 
	 * @function 获取行政案件公开信息
	 * @author Tony
	 * @creaetime 2018年6月22日 上午9:56:37
	 * @param ajbs
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getXzAjgkxx(String ajbs, String ajlx) throws Exception;
	/**
	 * 
	 * @function 获取赔偿案件公开信息
	 * @author Tony
	 * @creaetime 2018年6月22日 上午9:56:37
	 * @param ajbs
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPcAjgkxx(String ajbs, String ajlx) throws Exception;
	public void addAjgkFile(String string) throws Exception;
	/**
	 * 获取网站访问量
	 * @function 
	 * @author Tony
	 * @creaetime 2018年6月26日 上午10:47:35
	 * @return
	 * @throws Exception
	 */
	public void addWzfwlFile(String string) throws Exception;
	public void addDxFile(String string) throws Exception;
	public void addYhdlFile(String string) throws Exception;
	public void addFygkfsFile(String string) throws Exception;
	public void addAjcxFile(String string) throws Exception;
	public void addAjckFile(String string) throws Exception;
}
