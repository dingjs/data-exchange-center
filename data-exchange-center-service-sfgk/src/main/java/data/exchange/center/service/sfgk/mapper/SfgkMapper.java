package data.exchange.center.service.sfgk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.sfgk.domain.Aj;
import data.exchange.center.service.sfgk.domain.Fydm;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月25日 上午10:13:34</p>
 * @author Tony
 * @version 1.0
 *
 */
@Mapper
public interface SfgkMapper {

	List<String> getKtgg(Map<String, Object> params) throws Exception;

	String getYastmlMc(Map<String, String> params) throws Exception;

	void getKtgkxx(Map<String, Object> params) throws Exception;

	List<Aj> selectAj() throws Exception;

	void addSfgkFile(Map<String, Object> params) throws Exception;

	String selectDownFilePath(String zipname)throws Exception;

	List<Fydm> getFydm()throws Exception;

	List<Aj> selectAjgkAj()throws Exception;

	void addAjgkFile(Map<String, Object> params) throws Exception;

	void addWzfwlFile(Map<String, Object> params);

	void addDxFile(Map<String, Object> params);

	void addAjckFile(Map<String, Object> params);

	void addAjcxFile(Map<String, Object> params);

	void addFygkfsFile(Map<String, Object> params);

	void addYhdlFile(Map<String, Object> params);

	List<String> getAjcx(Map<String, Object> params);

	List<String> getYhdl(Map<String, Object> params);

	List<String> getWzfwl(Map<String, Object> params);

	List<String> getAjck(Map<String, Object> params);

	List<String> getFygkfs(Map<String, Object> params);

	List<String> getAjxx(Map<String, Object> params);

	String selectAjckDownFilePath(String zipname);

	String selectAjcxDownFilePath(String zipname);

	String selectAjxxDownFilePath(String zipname);

	String selectDxDownFilePath(String zipname);

	String selectFygkfsDownFilePath(String zipname);

	String selectWzfwlDownFilePath(String zipname);

	String selectYhdlDownFilePath(String zipname);

}
