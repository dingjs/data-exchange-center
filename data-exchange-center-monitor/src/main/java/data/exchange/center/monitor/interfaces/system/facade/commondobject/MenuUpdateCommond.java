package data.exchange.center.monitor.interfaces.system.facade.commondobject;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:55:18</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MenuUpdateCommond {

    private String label;
    private String url;
    private int order;
    private int type;//扩展字段。菜单类型，供不同业务区分

    private String style;//样式，方便ui展现

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
