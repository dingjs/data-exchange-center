package data.exchange.center.service.listener.domain;


public class Msg {

	private String ajbs;
	private String fydm;
	private String ajlx;
	public String getAjbs() {
		return ajbs;
	}
	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}
	public String getFydm() {
		return fydm;
	}
	public void setFydm(String fydm) {
		this.fydm = fydm;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	@Override
	public String toString() {
		return "Msg [ajbs=" + ajbs + ", fydm=" + fydm + ", ajlx=" + ajlx + "]";
	}
}
