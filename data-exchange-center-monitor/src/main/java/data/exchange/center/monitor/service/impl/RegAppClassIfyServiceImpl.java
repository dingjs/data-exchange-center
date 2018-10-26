package data.exchange.center.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import data.exchange.center.monitor.domain.RegAppclassify;
import data.exchange.center.monitor.repository.RegAppClassIfyRepository;

@Service
public class RegAppClassIfyServiceImpl implements data.exchange.center.monitor.service.RegAppClassIfyService {
	@Autowired
	protected RegAppClassIfyRepository regAppClassIfyRepository;

	@Override
	public Object getRegAppClassIfy() {
		List<Object> list = new ArrayList<>();
		List<RegAppclassify> regappList = regAppClassIfyRepository.getRegApp();
		for (RegAppclassify regapp : regappList) {
			Map<String, Object> tabledata = new HashMap<>();
			tabledata.put("pId", regapp.getAppcode() + "" + regapp.getPid());
			tabledata.put("treeid", regapp.getTreeid());
			tabledata.put("id", regapp.getAppcode() + "" + regapp.getId());
			tabledata.put("name", regapp.getName());
			tabledata.put("type", regapp.getType());
			tabledata.put("appcode", regapp.getAppcode());
			tabledata.put("isFirstNode", true);
			list.add(tabledata);
		}
		List<RegAppclassify> regappclassifyList = regAppClassIfyRepository.getRegAppClassIfy();
		for (RegAppclassify regappclassifya : regappclassifyList) {
			Map<String, Object> tabledata = new HashMap<>();
			if (regappclassifya.getPid().endsWith("-1")) {
				tabledata.put("pId", regappclassifya.getAppcode() + "" + regappclassifya.getAppcode());
			} else {
				tabledata.put("pId", regappclassifya.getAppcode() + "" + regappclassifya.getPid());
			}
			tabledata.put("treeid", regappclassifya.getTreeid());
			tabledata.put("id", regappclassifya.getAppcode() + "" + regappclassifya.getId());
			tabledata.put("name", regappclassifya.getName());
			tabledata.put("type", regappclassifya.getType());
			tabledata.put("appcode", regappclassifya.getAppcode());
			tabledata.put("isFirstNode", false);
			list.add(tabledata);
		}
		return list;
	}

	@Override
	public Object setRegAppClassIfy(String idArrayList) {
		String id;
		String appcode;
		String sql = "";
		String[] list = idArrayList.split(",");
			for (int i = 0; i < list.length; i++) {
				String idSrc = list[i];
				appcode = idSrc.substring(0, 3);
				id = idSrc.substring(3, idSrc.length());
				sql += " insert into dcadm.dc_reg_appclassify(c_appcode,c_nodeid) values  ('" + appcode + "','" + id + "'); ";
			}
			regAppClassIfyRepository.setRegAppClassIfy(sql);

		return null;
	}

	@Override
	public Object deleteRegAppClassIfy(String nodesIdList) {
		String id;
		String appcode = null;
		String sql = "";
		String[] list = nodesIdList.split(",");
			for (int i = 0; i < list.length; i++) {
				String idSrc = list[i];
				appcode = idSrc.substring(0, 3);
				id = idSrc.substring(3, idSrc.length());
				sql += id+",";
			}
			sql = " c_nodeid in (" + sql.substring(0, sql.length()-1) + " ) and c_appcode =" + appcode;
			regAppClassIfyRepository.deleteRegAppClassIfy(sql);

		return null;
	}


}
