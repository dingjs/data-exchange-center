package data.exchange.center.service.sfgk.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.sfgk.domain.Aj;
import data.exchange.center.service.sfgk.domain.Fydm;
import data.exchange.center.service.sfgk.domain.Ktgkxx;
import data.exchange.center.service.sfgk.mapper.AjgkMsMapper;
import data.exchange.center.service.sfgk.mapper.AjgkPcMapper;
import data.exchange.center.service.sfgk.mapper.AjgkXsMapper;
import data.exchange.center.service.sfgk.mapper.AjgkXzMapper;
import data.exchange.center.service.sfgk.mapper.SfgkMapper;
import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.util.Code;
import data.exchange.center.service.sfgk.util.FileInfoUtil;
import data.exchange.center.service.sfgk.util.rsa.RSAUtils;

@Service
public class SfgkServiceImpl implements SfgkService {

	private static final Logger logger = LoggerFactory.getLogger(SfgkServiceImpl.class);
	
	@Autowired
	private SfgkMapper   sfgkMapper;
	@Autowired
	private AjgkXsMapper ajgkXsMapper;
	@Autowired
	private AjgkMsMapper ajgkMsMapper;
	@Autowired
	private AjgkXzMapper ajgkXzMapper;
	@Autowired
	private AjgkPcMapper ajgkPcMapper;

	@Override
	public Object ktgg(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getKtgg(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object ajxx(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getAjxx(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object fygkfs(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getFygkfs(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object ajck(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getAjck(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object wzfwl(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getWzfwl(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object yhdl(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getYhdl(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object ajcx(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getAjcx(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object dx(String YWLX, String GXJSSJ, String GXKSSJ, String FYBH, String VERSION) throws Exception {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("YWLX",    YWLX);
			params.put("GXJSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXJSSJ)));
			params.put("GXKSSJ",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(GXKSSJ)));
			params.put("FYBH",    FYBH);
			params.put("VERSION", VERSION);
			List<String> zipList = sfgkMapper.getKtgg(params);
			if(!StringUtils.isEmpty(zipList)&&zipList.size()>0) {
				
				List<String> listb =  new ArrayList<String>();;  
				for (String zip : zipList) {  
					listb.add("\""+zip+"\"");  
				}
				
				String url = "{'URL' : " + listb + "}";
				System.out.println(url);
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_200);
				retMap.put("MESSAGE", "成功");
				byte[] encryptedBytes = RSAUtils.encrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
						url.getBytes("UTF-8"));
				String base = Base64.encodeBase64String(encryptedBytes);
				retMap.put("DATA", base);
				String json = JSON.toJSONString(retMap);
				System.out.println(json);
				return json;
			}else {//没有记录有两种情况  1、确实没得；2、还没生产。
				Map<String, Object> retMap = new HashMap<>();
				retMap.put("CODE", Code.CODE_OK_100);
				retMap.put("MESSAGE", "准备中");
				
				return JSON.toJSONString(retMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	
	
	
	
	@Override
	public String getYastmlMc(String ajbs, String xh) throws Exception {
		Map<String, String> params = new HashMap<>();
		try {
			params.put("ajbs", ajbs);
			params.put("xh", xh);
			String mcList = sfgkMapper.getYastmlMc(params);
			return mcList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> getKtgkxx(String ajbs, String ajlx) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ajbs", ajbs);
			params.put("ajlx", ajlx);
			sfgkMapper.getKtgkxx(params);
			int flag = (int) params.get("v_cursor1");
			@SuppressWarnings("unchecked")
			List<Ktgkxx> ktgxxxList = (List<Ktgkxx>) params.get("v_cursor2");
			if(flag == 0) {
				retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
				retMap.put(CodeUtil.RETURN_MSG, ktgxxxList);
			}else {
				retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				retMap.put(CodeUtil.RETURN_MSG, ktgxxxList);
			}
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
		}
		return retMap;
	}

	@Override
	public List<Aj> selectAj() throws Exception {
		return sfgkMapper.selectAj();
	}

	@Override
	public void addSfgkFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addSfgkFile(params);
	}

	@Override
	public String selectDownFilePath(String zipname) throws Exception {
		if(zipname.contains(FileInfoUtil.ZIP_LX_AJCK)) {
			return sfgkMapper.selectAjckDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_AJCX)) {
			return sfgkMapper.selectAjcxDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_AJXX)) {
			return sfgkMapper.selectAjxxDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_DX)) {
			return sfgkMapper.selectDxDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_FYGKFS)) {
			return sfgkMapper.selectFygkfsDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_KTGG)) {
			return sfgkMapper.selectDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_WZFWL)) {
			return sfgkMapper.selectWzfwlDownFilePath(zipname);
		}
		if(zipname.contains(FileInfoUtil.ZIP_LX_YHDL)) {
			return sfgkMapper.selectYhdlDownFilePath(zipname);
		}
		return null;
	}

	@Override
	public List<Fydm> getFydm() throws Exception {
		
		return sfgkMapper.getFydm();
	}

	@Override
	public List<Aj> selectAjgkAj() throws Exception {
		return sfgkMapper.selectAjgkAj();
	}

	@Override
	public Map<String, Object> getXsAjgkxx(String ajbs, String ajlx) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("ajbs", ajbs);
			params.put("ajlx", ajlx);
			ajgkXsMapper.getXsAjgkxx(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	@Override
	public Map<String, Object> getMsAjgkxx(String ajbs, String ajlx) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("ajbs", ajbs);
			params.put("ajlx", ajlx);
			ajgkMsMapper.getMsAjgkxx(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	@Override
	public Map<String, Object> getXzAjgkxx(String ajbs, String ajlx) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("ajbs", ajbs);
			params.put("ajlx", ajlx);
			ajgkXzMapper.getXzAjgkxx(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	@Override
	public Map<String, Object> getPcAjgkxx(String ajbs, String ajlx) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("ajbs", ajbs);
			params.put("ajlx", ajlx);
			ajgkPcMapper.getPcAjgkxx(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	@Override
	public void addAjgkFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addAjgkFile(params);
	}

	@Override
	public void addWzfwlFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addWzfwlFile(params);
	}

	@Override
	public void addDxFile(String zipname) {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addDxFile(params);
	}

	@Override
	public void addYhdlFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addYhdlFile(params);
	}

	@Override
	public void addFygkfsFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addFygkfsFile(params);
	}

	@Override
	public void addAjcxFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addAjcxFile(params);
	}

	@Override
	public void addAjckFile(String zipname) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String zipurl = "http://118.122.95.223:8888/api/service-sfgk/download/"
				+zipname.substring(zipname.lastIndexOf("/")+1);
		params.put("uuid",    UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("zipurl",  zipurl);
		params.put("zipname", zipname.substring(zipname.lastIndexOf("/")+1));
		params.put("zippath", zipname);
		sfgkMapper.addAjckFile(params);
	}

}
