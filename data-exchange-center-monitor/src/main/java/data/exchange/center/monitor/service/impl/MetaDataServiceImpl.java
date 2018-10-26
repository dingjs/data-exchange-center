package data.exchange.center.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.domain.MainMetaNode;
import data.exchange.center.monitor.domain.MenuNode;
import data.exchange.center.monitor.domain.MetaInfoclassify;
import data.exchange.center.monitor.domain.MetaTable;
import data.exchange.center.monitor.repository.MenuRepository;
import data.exchange.center.monitor.repository.MetaDataRepository;
import data.exchange.center.monitor.repository.RoleRepository;
import data.exchange.center.monitor.service.MetaDataService;
import data.exchange.center.monitor.domain.MainMetaNode;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月20日 上午10:55:54</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class MetaDataServiceImpl implements MetaDataService {

	@Autowired
    protected MetaDataRepository metaDataRepository;
    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected RoleRepository roleRepository;
	@Override
	public void clear() {

	}

	@Override
	public Object getTableCol(String nodeId) {
		return metaDataRepository.getTableCol(nodeId);
	}

	@Override
	public Object getMetadata(String treeid) {
	   	
//	        @SuppressWarnings("unchecked")
//			List<MainMetaNode> listMainMetaNode = (List<MainMetaNode>)map.get("node");
//	        for(MainMetaNode mainMetaNode:listMainMetaNode) {
//	        	Map<String, Object> ajlxdata = new HashMap<>();
//	        	ajlxdata.put("name", mainMetaNode.getcTbName());
//	        	ajlxdata.put("id",mainMetaNode.getAjlx());
//	        	list.add(ajlxdata);
//	        	List<MetaTable> metaTableList = menuRepository.getTables(mainMetaNode.getAjlx());
//	        	for(MetaTable metaTable:metaTableList) {
//	        		Map<String, Object> tabledata = new HashMap<>();
//	        		tabledata.put("pId", mainMetaNode.getAjlx());
//	        		tabledata.put("id", metaTable.getcTableId());
//	        		tabledata.put("name", metaTable.getcCtbName());
//	        		list.add(tabledata);
//	        	}
//	        	
//	        }
//	   	List<MetaInfoclassify> metaInfotree = metaDataRepository.getMetaInfotree();
//	    for (MetaInfoclassify metaInfoclassify:metaInfotree) {
//	   		Map<String, Object> tabledata = new HashMap<>();
//    		tabledata.put("id",  metaInfoclassify.getId());
//    		tabledata.put("name", metaInfoclassify.getName());
//    		tabledata.put("type", metaInfoclassify.getType());
//    		tabledata.put("treeid", metaInfoclassify.getTreeid());
//    		list.add(tabledata);
//	    }
		 List<Object> list =new ArrayList<>();
	   	List<MetaInfoclassify> metaInfoclassifyList = metaDataRepository.getMetaInfoclassify(treeid);
	   	 for (MetaInfoclassify metaInfoclassify:metaInfoclassifyList) {
	   		Map<String, Object> tabledata = new HashMap<>();

	   		tabledata.put("pId", metaInfoclassify.getPid());
	   		tabledata.put("treeid", metaInfoclassify.getTreeid());
    		tabledata.put("id",  metaInfoclassify.getId());
    		tabledata.put("name", metaInfoclassify.getName());
    		tabledata.put("type", metaInfoclassify.getType());
    		list.add(tabledata);
		}
		return list;
	}
}
