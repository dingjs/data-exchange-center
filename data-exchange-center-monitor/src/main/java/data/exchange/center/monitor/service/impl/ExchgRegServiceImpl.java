package data.exchange.center.monitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.monitor.domain.ExchgReg.RegApp;
import data.exchange.center.monitor.domain.ExchgReg.RegExchg;
import data.exchange.center.monitor.domain.ExchgReg.RegFtp;
import data.exchange.center.monitor.domain.ExchgReg.RegNode;
import data.exchange.center.monitor.domain.ExchgReg.RegService;
import data.exchange.center.monitor.domain.ExchgReg.RegSoaToken;
import data.exchange.center.monitor.repository.ExchgRegRepository;
import data.exchange.center.monitor.service.ExchgRegService;
@Service
public class ExchgRegServiceImpl implements ExchgRegService{
	@Autowired
	ExchgRegRepository exchgRegRepository;
	@Override
	public Object getRegNode() {
		List<RegNode> list = exchgRegRepository.getRegNode();
		return list;
	}
	@Override
	public Object getRegApp() {
		List<RegApp> list =exchgRegRepository.getRegAPP();
		return list;
	}
	@Override
	public Object getRegFtp() {
		List<RegFtp> list =exchgRegRepository.getRegFtp();
		return list;
	}
	@Override
	public Object getRegService() {
		List<RegService> list =exchgRegRepository.getRegService();
		return list;
	}
	@Override
	public Object getRegSoaToken() {
		List<RegSoaToken> list =exchgRegRepository.getRegSoaToken();
		return list;
	}
	@Override
	public Object getRegExchg() {
		List<RegExchg> list =exchgRegRepository.getRegExchg();
		return list;
	}
}
