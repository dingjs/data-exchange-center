package data.exchange.center.monitor.domain.modle;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:52:53</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class SelectResource {

    private String rid;//resource id

    private String label;

    private boolean checked;

    public SelectResource() {
    }

    public SelectResource(String rid, String label, boolean checked) {
        this.rid = rid;
        this.label = label;
        this.checked = checked;
    }

    public String getLabel() {
        return label;
    }

    public String getRid() {
        return rid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
