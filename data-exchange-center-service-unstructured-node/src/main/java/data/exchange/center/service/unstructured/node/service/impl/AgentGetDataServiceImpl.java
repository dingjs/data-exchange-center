package data.exchange.center.service.unstructured.node.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;
import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
import data.exchange.center.service.unstructured.node.service.AgentGetDataService;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年5月24日 下午12:57:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@Service
public class AgentGetDataServiceImpl implements AgentGetDataService {

    @Autowired
    AgentGetDataMapper  agentGetDataMapper;
    
    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getEajJzFromTDH()
     */
    @Override
    public List<TempEajJz> getEajJzFromTDH(String ajbs) throws Exception {
        return null;
    }

    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getEajJzWjAllFromTDH()
     */
    @Override
    public List<TempEajJzwjAll> getEajJzWjAllFromTDH(String ajbs) throws Exception {
        List<TempEajJzwjAll> list = agentGetDataMapper.getEajJzWjAllFromTDH(ajbs);
        return list;
    }

    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getEajJzNewFromTDH()
     */
    @Override
    public List<TempEajJzwjAllNew> getEajJzwjAllNewFromTDH(String ajbs) throws Exception {
        List<TempEajJzwjAllNew> list = agentGetDataMapper.getEajJzwjAllNewFromTDH(ajbs);
        return list;
    }

    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getEajMlxxFromTDH()
     */
    @Override
    public List<TempEajMlxx> getEajMlxxFromTDH(String ajbs) throws Exception {
        List<TempEajMlxx> list = agentGetDataMapper.getEajMlxxFromTDH(ajbs);
        return list;
    }

    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getEajMlxxGcFromTDH()
     */
    @Override
    public List<TempEajMlxxGc> getEajMlxxGcFromTDH(String ajbs) throws Exception {
        List<TempEajMlxxGc> list = agentGetDataMapper.getEajMlxxGcFromTDH(ajbs);
        return list;
    }

    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getEajSsjcyxFromTDH()
     */
    @Override
    public List<TempEajSsjcyx> getEajSsjcyxFromTDH(String ajbs) throws Exception {
        List<TempEajSsjcyx> list = agentGetDataMapper.getEajSsjcyxFromTDH(ajbs);
        return list;
    }

    /** (non-Javadoc)
     * @see org.data.governance.unstructured.agent.push.service.AgentGetDataService#getAllAjxxFromTDH()
     */
    @Override
    public List<Ajxx> getAllAjxxFromTDH(Map<String, Object> params) throws Exception {
        List<Ajxx> ajbsList = agentGetDataMapper.getAllAjxxFromTDH(params);
        return ajbsList;
    }
}
