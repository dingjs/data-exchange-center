package data.exchange.center.monitor.interfaces.system.facade.assembler;

import data.exchange.center.monitor.domain.modle.Menu;
import data.exchange.center.monitor.infrastructure.BeanUtil;
import data.exchange.center.monitor.interfaces.system.facade.commondobject.MenuCreateCommand;
import data.exchange.center.monitor.interfaces.system.facade.commondobject.MenuUpdateCommond;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:55:05</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MenuAssembler {

    public static Menu updateCommendToDomain(String id, MenuUpdateCommond updateCommond) {
        Menu menu=new Menu();
        BeanUtil.copeProperties(updateCommond,menu);
        menu.setId(id);
        return menu;
    }

    public static Menu createCommendToDomain(MenuCreateCommand creteCommand){
        Menu menu=new Menu();
        BeanUtil.copeProperties(creteCommand,menu);
        return menu;
    }
}
