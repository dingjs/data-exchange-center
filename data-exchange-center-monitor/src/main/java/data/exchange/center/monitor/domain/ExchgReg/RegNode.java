package data.exchange.center.monitor.domain.ExchgReg;
/**
 * 
 * @author bmj	
 *
 */
public class RegNode {
	private  String nodeid;
	private  String nodename;
	private  String nodetype;
	private  String nodeip;
	private  String deptcode;
	private  String distribute;
	private  String kafka;
	private  String redis;
	private  String elastic;
	private  String remote;
	private  String diststatus;
	private  String kafkstatus;
	private  String redistatus;
	private  String elastatus;
	private  String retstatus;
	
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getNodetype() {
		return nodetype;
	}
	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}
	public String getNodeip() {
		return nodeip;
	}
	public void setNodeip(String nodeip) {
		this.nodeip = nodeip;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getDistribute() {
		return distribute;
	}
	public void setDistribute(String distribute) {
		this.distribute = distribute;
	}
	public String getKafka() {
		return kafka;
	}
	public void setKafka(String kafka) {
		this.kafka = kafka;
	}
	public String getRedis() {
		return redis;
	}
	public void setRedis(String redis) {
		this.redis = redis;
	}
	public String getElastic() {
		return elastic;
	}
	public void setElastic(String elastic) {
		this.elastic = elastic;
	}
	public String getRemote() {
		return remote;
	}
	public void setRemote(String remote) {
		this.remote = remote;
	}
	public String getDiststatus() {
		return diststatus;
	}
	public void setDiststatus(String diststatus) {
		this.diststatus = diststatus;
	}
	public String getKafkstatus() {
		return kafkstatus;
	}
	public void setKafkstatus(String kafkstatus) {
		this.kafkstatus = kafkstatus;
	}
	public String getRedistatus() {
		return redistatus;
	}
	public void setRedistatus(String redistatus) {
		this.redistatus = redistatus;
	}
	public String getElastatus() {
		return elastatus;
	}
	public void setElastatus(String elastatus) {
		this.elastatus = elastatus;
	}
	public String getRetstatus() {
		return retstatus;
	}
	public void setRetstatus(String retstatus) {
		this.retstatus = retstatus;
	}

}
