package data.exchange.center.service.pingshan.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.pingshan.mapper.PingshanMapper;
import data.exchange.center.service.pingshan.service.PingshanService;
import oracle.sql.BLOB;
import util.CommUtils;

@Service
public class PingshanServiceImpl implements PingshanService {

	@Autowired
	private PingshanMapper pingshanMapper;

	@Override
	public Object getAjbsList(String tjsj) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> paramsValue = new HashMap<String, Object>();
		params.put("tjsj", tjsj);
		try {
			List<Map<String, Object>> listMap = pingshanMapper.getAjbsList(params);
			paramsValue.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			paramsValue.put(CodeUtil.RETURN_MSG, listMap);
			return paramsValue;
		}catch(Exception e) {
			e.printStackTrace();
			paramsValue.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			paramsValue.put(CodeUtil.RETURN_MSG, e.getMessage());
			return paramsValue;
		}
	}

	@Override
	public Object getAjxx(String ajbs, String ajlx) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> paramsValue = new HashMap<String, Object>();
		Map<String, Object> returnValue = new HashMap<String, Object>();
		params.put("ajbs", ajbs);
		params.put("ajlx", ajlx);
		params.put("jalxqz", CommUtils.getAjlx(ajlx));
		try {
		if(ajlx.equals("11")) {
			List<Map<String, Object>> XSYSSAHLAXX  = pingshanMapper.getXSYSSAHLAXX(params);
			List<Map<String, Object>> XSYSAJSLQK  = pingshanMapper.getXSYSAJSLQK(params);
			List<Map<String, Object>> XSYSBAQX = pingshanMapper.getXSYSBAQX(params);
			List<Map<String, Object>> XSYSXSFDMSSSGK = pingshanMapper.getXSYSXSFDMSSSGK(params);
			List<Map<String, Object>> XSYSJAQK = pingshanMapper.getXSYSJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("XSYSSAHLAXX",  getAjxx(XSYSSAHLAXX));
			paramsValue.put("XSYSAJSLQK",  getAjxx(XSYSAJSLQK));
			paramsValue.put("XSYSBAQX",  getAjxx(XSYSBAQX));
			paramsValue.put("XSYSJAQK",  getAjxx(XSYSJAQK));
			paramsValue.put("XSYSXSFDMSSSGK",  getAjxx(XSYSXSFDMSSSGK));
		}else if(ajlx.equals("12")) {
			List<Map<String, Object>> XSESSAHLAXX = pingshanMapper.getXSESSAHLAXX(params);
			List<Map<String, Object>> XSESAJSLQK = pingshanMapper.getXSESAJSLQK(params);
			List<Map<String, Object>> XSERAJSLQK = pingshanMapper.getXSERAJSLQK(params);
			List<Map<String, Object>> XSESJAQK = pingshanMapper.getXSESJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("XSESSAHLAXX", getAjxx(XSESSAHLAXX));
			paramsValue.put("XSESAJSLQK", getAjxx(XSESAJSLQK));
			paramsValue.put("XSERAJSLQK", getAjxx(XSERAJSLQK));
			paramsValue.put("XSESJAQK", getAjxx(XSESJAQK));
		}else if(ajlx.equals("13")) {
			List<Map<String, Object>> XSZSSAHLAXX = pingshanMapper.getXSZSSAHLAXX(params);
			List<Map<String, Object>> XSZSAJSLQK = pingshanMapper.getXSZSAJSLQK(params);
			List<Map<String, Object>> XSZSJAFS = pingshanMapper.getXSZSJAFS(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("XSZSSAHLAXX", getAjxx(XSZSSAHLAXX));
			paramsValue.put("XSZSAJSLQK", getAjxx(XSZSAJSLQK));
			paramsValue.put("XSZSJAFS", getAjxx(XSZSJAFS));
		}else if(ajlx.equals("21")) {
			List<Map<String, Object>> MSYSSAHLAXX = pingshanMapper.getMSYSSAHLAXX(params);
			List<Map<String, Object>> MSYSAJSLQK = pingshanMapper.getMSYSAJSLQK(params);
			List<Map<String, Object>> MSYSQTHJTSSQK = pingshanMapper.getMSYSQTHJTSSQK(params);
			List<Map<String, Object>> MSYSBAQX = pingshanMapper.getMSYSBAQX(params);
			List<Map<String, Object>> MSYSJAQK = pingshanMapper.getMSYSJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getMSSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("MSYSSAHLAXX", getAjxx(MSYSSAHLAXX));
			paramsValue.put("MSYSAJSLQK", getAjxx(MSYSAJSLQK));
			paramsValue.put("MSYSQTHJTSSQK", getAjxx(MSYSQTHJTSSQK));
			paramsValue.put("MSYSBAQX", getAjxx(MSYSBAQX));
			paramsValue.put("MSYSJAQK", getAjxx(MSYSJAQK));
		}else if(ajlx.equals("22")) {
			List<Map<String, Object>> MSESSAHLAXX = pingshanMapper.getMSESSAHLAXX(params);
			List<Map<String, Object>> MSESAJSLQK = pingshanMapper.getMSESAJSLQK(params);
			List<Map<String, Object>> MSESJAQK  = pingshanMapper.getMSESJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getMSSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("MSESSAHLAXX", getAjxx(MSESSAHLAXX));
			paramsValue.put("MSESAJSLQK", getAjxx(MSESAJSLQK));
			paramsValue.put("MSESJAQK", getAjxx(MSESJAQK));
		}else if(ajlx.equals("23")) {
			List<Map<String, Object>> MSZSSAHLAXX = pingshanMapper.getMSZSSAHLAXX(params);
			List<Map<String, Object>> MSZSJAQK = pingshanMapper.getMSZSJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getMSSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("MSZSSAHLAXX", getAjxx(MSZSSAHLAXX));
			paramsValue.put("MSZSJAQK", getAjxx(MSZSJAQK));
		}else if(ajlx.equals("31")) {
			List<Map<String, Object>> XZYSSAHLAXX = pingshanMapper.getXZYSSAHLAXX(params);
			List<Map<String, Object>> XZYSAJSLQK = pingshanMapper.getXZYSAJSLQK(params);
			List<Map<String, Object>> XZYSJAQK = pingshanMapper.getXZYSJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("XZYSSAHLAXX", getAjxx(XZYSSAHLAXX));
			paramsValue.put("XZYSAJSLQK", getAjxx(XZYSAJSLQK));
			paramsValue.put("XZYSJAQK", getAjxx(XZYSJAQK));
		}else if(ajlx.equals("32")) {
			List<Map<String, Object>> XZESSAHLAXX = pingshanMapper.getXZESSAHLAXX(params);
			List<Map<String, Object>> XZESJAQK = pingshanMapper.getXZESJAQK(params);
			List<Map<String, Object>> XZZSSAHLAXX = pingshanMapper.getXZZSSAHLAXX(params);
			List<Map<String, Object>> XZZSJAQK = pingshanMapper.getXZZSSAHLAXX(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("XZESSAHLAXX", getAjxx(XZESSAHLAXX));
			paramsValue.put("XZESJAQK", getAjxx(XZESJAQK));
			paramsValue.put("XZZSSAHLAXX", getAjxx(XZZSSAHLAXX));
			paramsValue.put("XZZSJAQK", getAjxx(XZZSJAQK));
		}else if(ajlx.equals("33")) {
			List<Map<String, Object>> XZZSSAHLAXX = pingshanMapper.getXZZSSAHLAXX(params);
			List<Map<String, Object>> XZZSJAQK = pingshanMapper.getXZZSJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("XZZSSAHLAXX", getAjxx(XZZSSAHLAXX));
			paramsValue.put("XZZSJAQK", getAjxx(XZZSJAQK));
		}else if(ajlx.equals("42")) {
			List<Map<String, Object>> SFPCSAHLAXX = pingshanMapper.getSFPCSAHLAXX(params);
			List<Map<String, Object>> SFPCJAQK = pingshanMapper.getSFPCJAQK(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			List<Map<String, Object>> FTSYJL = pingshanMapper.getFTSYJL(params);
			paramsValue.put("FTSYJL",  getAjxx(FTSYJL));
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("SFPCSAHLAXX", SFPCSAHLAXX);
			paramsValue.put("SFPCJAQK", SFPCJAQK);
		}else if(ajlx.equals("51")) {
			List<Map<String, Object>>ZXAJSAHLAXX = pingshanMapper.getZXAJSAHLAXX(params);
			List<Map<String, Object>>ZXAJAJZXQK = pingshanMapper.getZXAJAJZXQK(params);
			List<Map<String, Object>>ZXAJWTZXYSTHF = pingshanMapper.getZXAJWTZXYSTHF(params);
			List<Map<String, Object>>ZXAJZDZX = pingshanMapper.getZXAJZDZX(params);
			List<Map<String, Object>>ZXAJBAQX = pingshanMapper.getZXAJBAQX(params);
			List<Map<String, Object>>ZXAJJAQK = pingshanMapper.getZXAJJAQK(params);
			List<Map<String, Object>>ZXAJDSR = pingshanMapper.getZXAJDSR(params);
			List<Map<String, Object>>ZXAJFTSYJL = pingshanMapper.getZXAJFTSYJL(params);
			List<Map<String, Object>>ZXAJCF = pingshanMapper.getZXAJCF(params);
			List<Map<String, Object>>ZXAJCQ = pingshanMapper.getZXAJCQ(params);
			List<Map<String, Object>>ZXAJDJ = pingshanMapper.getZXAJDJ(params);
			List<Map<String, Object>> YASTML = pingshanMapper.getYASTML(params);
			List<Map<String, Object>> DAJ = pingshanMapper.getAjDaj(params);
			List<Map<String, Object>> GCJ = pingshanMapper.getAjGcj(params);
			List<Map<String, Object>> SDJL = pingshanMapper.getSDJL(params);
			List<Map<String, Object>> SLJD = pingshanMapper.getSLJD(params);
			paramsValue.put("DAJ",  getAjxx(DAJ));
			paramsValue.put("GCJ",  getAjxx(GCJ));
			paramsValue.put("SDJL",  getAjxx(SDJL));
			paramsValue.put("SLJD",  getAjxx(SLJD));
			paramsValue.put("YASTML",  getAjxx(YASTML));
			paramsValue.put("ZXAJSAHLAXX", getAjxx(ZXAJSAHLAXX));
			paramsValue.put("ZXAJAJZXQK", getAjxx(ZXAJAJZXQK));
			paramsValue.put("ZXAJWTZXYWTHF", getAjxx(ZXAJWTZXYSTHF));
			paramsValue.put("ZXAJZDZX", getAjxx(ZXAJZDZX));
			paramsValue.put("ZXAJBAQX", getAjxx(ZXAJBAQX));
			paramsValue.put("ZXAJJAQK", getAjxx(ZXAJJAQK));
			paramsValue.put("ZXAJDSR", getAjxx(ZXAJDSR));
			paramsValue.put("ZXAJFTSYJL", getAjxx(ZXAJFTSYJL));
			paramsValue.put("ZXAJCF", getAjxx(ZXAJCF));
			paramsValue.put("ZXAJCQ", getAjxx(ZXAJCQ));
			paramsValue.put("ZXAJDJ", getAjxx(ZXAJDJ));
		}
		returnValue.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
		returnValue.put(CodeUtil.RETURN_MSG, paramsValue);
		return returnValue;
		}catch(Exception e) {
			e.printStackTrace();
			returnValue.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnValue.put(CodeUtil.RETURN_MSG, e.getMessage());
			return returnValue;
		}
	}

	@Override
	public Object getAjbmxx() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Map<String, Object> map = pingshanMapper.getAjbmxx();
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			params.put(CodeUtil.RETURN_MSG, map);
			return params;
		}catch(Exception e) {
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			params.put(CodeUtil.RETURN_MSG, e.getMessage());
			return params;
		}
	}

	@Override
	public Object getYhxx() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<Map<String, Object>>  map = pingshanMapper.getYhxx();
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			params.put(CodeUtil.RETURN_MSG, map);
			return params;
		}catch(Exception e) {
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			params.put(CodeUtil.RETURN_MSG, e.getMessage());
			return params;
		}
	}

	@Override
	public Object getBmxx() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<Map<String, Object>>  map = pingshanMapper.getBmxx();
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			params.put(CodeUtil.RETURN_MSG, map);
			return params;
		}catch(Exception e) {
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			params.put(CodeUtil.RETURN_MSG, e.getMessage());
			return params;
		}
	}

	@Override
	public Object getFybm() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			List<Map<String, Object>>  map = pingshanMapper.getFybm();
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			params.put(CodeUtil.RETURN_MSG, map);
			return params;
		}catch(Exception e) {
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			params.put(CodeUtil.RETURN_MSG, e.getMessage());
			return params;
		}
	}
	public List<Map<String, Object>> getAjxx(List<Map<String, Object>> ajxx) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < ajxx.size(); i++) {
			Map<String, Object> data = new HashMap<>();
			map = ajxx.get(i);
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue()))) {
					// 判断是否为blob字段
					if (entry.getValue() instanceof oracle.sql.BLOB) {
						InputStream in = ((BLOB) entry.getValue()).getBinaryStream();
						data.put(entry.getKey(), getBlob(in));
					} else {
						data.put(entry.getKey(), String.valueOf(entry.getValue()));
					}
				}
				
			}
			list.add(data);
		}
		return list;
	}
	public static String getBlob(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1)
			outStream.write(data, 0, count);

		data = null;
		String result = new String(outStream.toByteArray());
		return result;

	}

	@Override
	public Object getDelAj(String tjsj) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> paramsValue = new HashMap<String, Object>();
		try {
			params.put("tjsj", tjsj);
			List<Map<String, Object>>  map = pingshanMapper.getDelAj(params);
			paramsValue.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			paramsValue.put(CodeUtil.RETURN_MSG, map);
			return paramsValue;
		}catch(Exception e) {
			paramsValue.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			paramsValue.put(CodeUtil.RETURN_MSG, e.getMessage());
			return paramsValue;
		}
	}
}
