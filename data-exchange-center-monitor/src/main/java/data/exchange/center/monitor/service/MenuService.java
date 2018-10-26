package data.exchange.center.monitor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import data.exchange.center.monitor.domain.MainMeta;
import data.exchange.center.monitor.domain.MainMetaNode;
import data.exchange.center.monitor.domain.MenuNode;
import data.exchange.center.monitor.domain.modle.Menu;
import data.exchange.center.monitor.domain.modle.TreeModel;
import data.exchange.center.monitor.repository.MenuRepository;
import data.exchange.center.monitor.repository.RoleRepository;
import data.exchange.center.monitor.security.SecurityUtil;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:58:43</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
@CacheConfig(cacheNames = "menulist")
public class MenuService {

    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected RoleRepository roleRepository;

    /*cache操作相关的注解中key都是spel表达式，字符串需要用''*/
    @Caching(
            put = @CachePut(key = "#menu.id"),
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public Menu create(Menu menu) {
        validate(menu);
        menuRepository.add(menu);
        return menu;
    }

    @Caching(
            put = @CachePut(key = "#menu.id"),
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public Menu modify(Menu menu) {
        validate(menu);
        menuRepository.update(menu);
        return menu;
    }


    @Cacheable
    public Menu get(String code) {
        return menuRepository.get(code);
    }

    @Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#code"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public void delete(String code) {
        roleRepository.removeRoleMenuByMenuId(code);
        menuRepository.remove(code);
    }

    @Cacheable(key = "'list'")
    public List<Menu> list() {
        List<Menu> list = menuRepository.list();
        TreeModel.sortByTree(list);
        return list;
    }

    @Caching(
            evict = {@CacheEvict(key = "'list'"), @CacheEvict(key = "#menu"), @CacheEvict(value = "user-nav-menu", allEntries = true)}
    )
    public void switchStatus(String menu, boolean disable) {
        menuRepository.switchStatus(menu, disable);
    }

    @SuppressWarnings("unchecked")
	@Cacheable(value = "user-nav-menu")
    public List<Menu> getNavMenuss(String uid) {
        List<Menu> list = null;
        if (SecurityUtil.isRoot()) {
            list = menuRepository.list();
        } else {
            list = menuRepository.getNavMenus(uid);
        }
        return (List<Menu>) TreeModel.buildTree(list);
    }
    
    @Cacheable(value = "user-nav-menu")
	public Object getNavMenus(String uid) {
        
        List<MenuNode> listMenu = new ArrayList<MenuNode>();
        MenuNode metaData = new MenuNode();
        metaData.setLabel("数据管理");
        List<Object> listSecondMenu = new ArrayList<Object>();
    	MenuNode secondMenuNode = new MenuNode();
        secondMenuNode.setLabel("元数据管理");
        secondMenuNode.setUrl("/metadata/1");
    	MenuNode secondMenuNode1 = new MenuNode();
        secondMenuNode1.setLabel("业务分类树");
        secondMenuNode1.setUrl("/regappclassify");
        listSecondMenu.add(secondMenuNode);
        listSecondMenu.add(secondMenuNode1);
        metaData.setChildNodes(listSecondMenu);
        //交换数据信息注册
        MenuNode exchgreg = new MenuNode();
        List<Object> regAppMenu = new ArrayList<Object>();
        exchgreg.setLabel("交换业务注册信息");
//        List<MainMeta> mainList = menuRepository.getMainMeta();
    	MenuNode exchgRegNode = new MenuNode();
    	exchgRegNode.setLabel("节点注册信息");
    	exchgRegNode.setUrl("/exchgreg/regNode");
    	regAppMenu.add(exchgRegNode);
    	MenuNode exchgregApp = new MenuNode();
    	exchgregApp.setLabel("应用系统信息");
    	exchgregApp.setUrl("/exchgreg/regApp");
    	regAppMenu.add(exchgregApp);
    	MenuNode exchgregFtp = new MenuNode();
    	exchgregFtp.setLabel("FTP注册信息");
    	exchgregFtp.setUrl("/exchgreg/regFtp");
    	regAppMenu.add(exchgregFtp);
    	MenuNode exchgregService = new MenuNode();
    	exchgregService.setLabel("服务注册信息");
    	exchgregService.setUrl("/exchgreg/regService");
    	regAppMenu.add(exchgregService);
      	MenuNode exchgregSoaToken = new MenuNode();
      	exchgregSoaToken.setLabel("服务鉴权注册信息");
      	exchgregSoaToken.setUrl("/exchgreg/regSoaToken");
    	regAppMenu.add(exchgregSoaToken);
    	MenuNode exchgregExchg = new MenuNode();
    	exchgregExchg.setLabel("交换业务注册信息");
    	exchgregExchg.setUrl("/exchgreg/regExchg");
    	regAppMenu.add(exchgregExchg);
    	exchgreg.setChildNodes(regAppMenu);
    	
        MenuNode service = new MenuNode();
        List<Object> serviceList = new ArrayList<Object>();
        service.setLabel("业务调用信息");
       	MenuNode serviceNode = new MenuNode();
       	serviceNode.setLabel("业务调用统计一览");
       	serviceNode.setUrl("/peloxinVoke");
       	serviceList.add(serviceNode);
       	service.setChildNodes(serviceList);
//        for(MainMeta mainMeta:mainList) {
//        	//List<Object> listThirdMenu = new ArrayList<Object>();
//        	MenuNode secondMenuNode = new MenuNode();
//            secondMenuNode.setLabel(mainMeta.getName());
//            secondMenuNode.setUrl("/metadata/"+mainMeta.getTreeId());
//            

        //    Map<String, Object> map = menuRepository.getMainMetaNode(mainMeta.getTreeId());
          //  List<MainMetaNode> listMainMetainfo = (List<MainMetaNode>)map.get("info");
//            for(MainMetaNode mainMetaNode:listMainMetainfo) {
//            	MenuNode thirdMenuNode = new MenuNode();
//            	thirdMenuNode.setLabel(mainMetaNode.getcTbName());
//            	thirdMenuNode.setUrl("/metadata/"+mainMetaNode.geteTbName());
//            	
//            	listThirdMenu.add(thirdMenuNode);
//            	secondMenuNode.setChildNodes(listThirdMenu);
//            }
//            List<MainMetaNode> listMainMetaNode = (List<MainMetaNode>)map.get("node");
            
//            for(MainMetaNode mainMetaNode:listMainMetaNode) {
//            	MenuNode thirdMenuNode = new MenuNode();
//            	thirdMenuNode.setLabel(mainMetaNode.getcTbName());
////            	thirdMenuNode.setUrl("/metadata/"+mainMetaNode.geteTbName());
//            	
//            	
//            	List<MetaTable> metaTableList = menuRepository.getTables(mainMetaNode.getAjlx());
//            	
//            	List<Object> listFourMenu = new ArrayList<Object>();
//            	for(MetaTable metaTable:metaTableList) {
//            		MenuNode fourMenuNode = new MenuNode();
//            		fourMenuNode.setLabel(metaTable.getcCtbName());
//            		fourMenuNode.setUrl("/metadata/"+metaTable.getcTableId());
//            		listFourMenu.add(fourMenuNode);
//            	}
//            	thirdMenuNode.setChildNodes(listFourMenu);
//            	
//            	listThirdMenu.add(thirdMenuNode);
//            	secondMenuNode.setChildNodes(listThirdMenu);
//            }    listSecondMenu.add(secondMenuNode);
           /* listSecondMenu.add(secondMenuNode);
            listSecondMenu.add(secondMenuNode1);*/
     //   }
        
        
//        MenuNode secondMenuNode = new MenuNode();
//        secondMenuNode.setLabel("二级菜单");
//        
//        
//        List<Object> l = new ArrayList<Object>();
//        MenuNode t = new MenuNode();
//        t.setLabel("三级菜单");
//        t.setUrl("/servicelog");
//        l.add(t);
        
        
//        secondMenuNode.setChildNodes(l);
//        listSecondMenu.add(secondMenuNode);
         
       
        
        /**
         * 第二个菜单
         */
        List<Object> systemMgMenuList = new ArrayList<Object>();
        
        MenuNode systemMgMenu = new MenuNode();
        systemMgMenu.setLabel("系统管理");
        String[] systemMgMenuNode = {"菜单管理","系统监控","资源管理","角色管理","系统日志","用户管理"};
        for(String node:systemMgMenuNode) {
        	MenuNode systemMgMenuNo = new MenuNode();
        	switch(node) {
        		case "菜单管理":
        			systemMgMenuNo.setLabel(node);
        			systemMgMenuNo.setUrl("/menu");
        			systemMgMenuList.add(systemMgMenuNo);
        			break;
        		case "系统监控":
        			systemMgMenuNo.setLabel(node);
        			systemMgMenuNo.setUrl("/admin");
        			systemMgMenuList.add(systemMgMenuNo);
        			break;
        		case "资源管理":
        			systemMgMenuNo.setLabel(node);
        			systemMgMenuNo.setUrl("/resource");
        			systemMgMenuList.add(systemMgMenuNo);
        			break;
        		case "角色管理":
        			systemMgMenuNo.setLabel(node);
        			systemMgMenuNo.setUrl("/role");
        			systemMgMenuList.add(systemMgMenuNo);
        			break;
        		case "系统日志":
        			systemMgMenuNo.setLabel(node);
        			systemMgMenuNo.setUrl("");
        			systemMgMenuList.add(systemMgMenuNo);
        			break;
        		case "用户管理":
        			systemMgMenuNo.setLabel(node);
        			systemMgMenuNo.setUrl("/user");
        			systemMgMenuList.add(systemMgMenuNo);
        			break;
        	}
        }
        
        systemMgMenu.setChildNodes(systemMgMenuList);
        
        /**
         * 第三个菜单
         */
        List<Object> serviceMgMenuList = new ArrayList<Object>();
        
        MenuNode serviceMgMenu = new MenuNode();
        serviceMgMenu.setLabel("服务管理");
        
        MenuNode serviceMgMenuNode = new MenuNode();
        serviceMgMenuNode.setLabel("服务日志管理");
        serviceMgMenuNode.setUrl("/servicelog");
        
        MenuNode serviceMgMenuNode1 = new MenuNode();
        serviceMgMenuNode1.setLabel("服务节点信息");
        serviceMgMenuNode1.setUrl("/node");
        serviceMgMenuList.add(serviceMgMenuNode);
        serviceMgMenuList.add(serviceMgMenuNode1);
        
        serviceMgMenu.setChildNodes(serviceMgMenuList);;
        
        
        /**
         * 第四个菜单
         */
        List<Object> serviceCaseInfoMenuList = new ArrayList<Object>();
        MenuNode serviceCaseInfoMenu = new MenuNode();
        serviceCaseInfoMenu.setLabel("案件管理");
        MenuNode serviceCaseInfoMenuNode = new MenuNode();
        serviceCaseInfoMenuNode.setLabel("全省各市数据明细");
        serviceCaseInfoMenuNode.setUrl("/casemanage");
        
        MenuNode serviceCaseInfoMenuNode1 = new MenuNode();
        serviceCaseInfoMenuNode1.setLabel("案件同步跟踪");
        serviceCaseInfoMenuNode1.setUrl("/casetrack");
        
        MenuNode serviceCaseInfoMenuNode2 = new MenuNode();
        serviceCaseInfoMenuNode2.setLabel("卷宗调阅");
        serviceCaseInfoMenuNode2.setUrl("/caselook");
        
        serviceCaseInfoMenuList.add(serviceCaseInfoMenuNode);
        serviceCaseInfoMenuList.add(serviceCaseInfoMenuNode1);
        serviceCaseInfoMenuList.add(serviceCaseInfoMenuNode2);
        serviceCaseInfoMenu.setChildNodes(serviceCaseInfoMenuList);
        
        /**
         * 添加到主菜单
         */
        listMenu.add(metaData);
        listMenu.add(service);
        listMenu.add(exchgreg);
        listMenu.add(systemMgMenu);
        listMenu.add(serviceMgMenu);
        listMenu.add(serviceCaseInfoMenu);
        
        return listMenu;
    }

    @SuppressWarnings("deprecation")
	private void validate(Menu menu) {
        Assert.hasText(menu.getId());
        Assert.hasText(menu.getLabel());
    }
}
