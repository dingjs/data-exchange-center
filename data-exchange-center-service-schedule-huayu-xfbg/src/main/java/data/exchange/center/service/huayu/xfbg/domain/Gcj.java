package data.exchange.center.service.huayu.xfbg.domain;

import java.io.Serializable;
import java.util.Date;

public class Gcj implements Serializable{
	
	/**
     * 2017年7月1日下午2:24:32
     * yuguang
     */
    private static final long serialVersionUID = 4769200444973405375L;
    public Integer N_AJLB;
	public String C_WSMC;
	public Date DT_ZHBJSJ;
	public String C_DOC_PROTOCOL;
	public String N_AJBS;
	public String N_AJJZJD;
	public String C_HTM_PROTOCOL;
	public String C_AH;
	public String C_BH;
	private String C_PATH;
    private String C_FILENAME;
    private Integer C_WJDX;
    
	public String getC_PATH() {
        return C_PATH;
    }
    public void setC_PATH(String c_PATH) {
        C_PATH = c_PATH;
    }
    public String getC_FILENAME() {
        return C_FILENAME;
    }
    public void setC_FILENAME(String c_FILENAME) {
        C_FILENAME = c_FILENAME;
    }
    public Integer getC_WJDX() {
        return C_WJDX;
    }
    public void setC_WJDX(Integer c_WJDX) {
        C_WJDX = c_WJDX;
    }
    public Integer getN_AJLB() {
		return N_AJLB;
	}
	public void setN_AJLB(Integer n_AJLB) {
		N_AJLB = n_AJLB;
	}
	public String getC_WSMC() {
		return C_WSMC;
	}
	public void setC_WSMC(String c_WSMC) {
		C_WSMC = c_WSMC;
	}
	public Date getDT_ZHBJSJ() {
		return DT_ZHBJSJ;
	}
	public void setDT_ZHBJSJ(Date dT_ZHBJSJ) {
		DT_ZHBJSJ = dT_ZHBJSJ;
	}
	public String getC_DOC_PROTOCOL() {
		return C_DOC_PROTOCOL;
	}
	public void setC_DOC_PROTOCOL(String c_DOC_PROTOCOL) {
		C_DOC_PROTOCOL = c_DOC_PROTOCOL;
	}
	public String getN_AJBS() {
		return N_AJBS;
	}
	public void setN_AJBS(String n_AJBS) {
		N_AJBS = n_AJBS;
	}
	public String getN_AJJZJD() {
		return N_AJJZJD;
	}
	public void setN_AJJZJD(String n_AJJZJD) {
		N_AJJZJD = n_AJJZJD;
	}
	public String getC_HTM_PROTOCOL() {
		return C_HTM_PROTOCOL;
	}
	public void setC_HTM_PROTOCOL(String c_HTM_PROTOCOL) {
		C_HTM_PROTOCOL = c_HTM_PROTOCOL;
	}
	public String getC_AH() {
		return C_AH;
	}
	public void setC_AH(String c_AH) {
		C_AH = c_AH;
	}
	public String getC_BH() {
		return C_BH;
	}
	public void setC_BH(String c_BH) {
		C_BH = c_BH;
	}
}
