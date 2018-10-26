package data.exchange.center.monitor.repository;

import java.util.List;

import data.exchange.center.monitor.domain.ExchgReg.RegApp;
import data.exchange.center.monitor.domain.ExchgReg.RegExchg;
import data.exchange.center.monitor.domain.ExchgReg.RegFtp;
import data.exchange.center.monitor.domain.ExchgReg.RegNode;
import data.exchange.center.monitor.domain.ExchgReg.RegService;
import data.exchange.center.monitor.domain.ExchgReg.RegSoaToken;

public interface ExchgRegRepository {
	
 List<RegNode> getRegNode();
 
 List<RegApp> getRegAPP();
 
 List<RegFtp> getRegFtp();
 
 List<RegService> getRegService();
 
 List<RegSoaToken> getRegSoaToken();
 
 List<RegExchg> getRegExchg();
 }
