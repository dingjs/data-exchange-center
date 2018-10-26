//package data.exchange.center.service.unstructured.node.service.impl;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import data.exchange.center.service.unstructured.node.domain.TempEajJz;
//import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
//import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
//import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
//import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
//import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;
//import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
//import data.exchange.center.service.unstructured.node.service.AgentPushDataService;
//
///**
// * Description:
// * <p>Company: pelox </p>
// * <p>Date:2017年5月24日 下午5:06:55</p>
// * @author Wen.Yuguang
// * @version 1.0
// **/
//@Service
//public class AgentPushDataServiceImpl implements AgentPushDataService {
//
//    @Autowired
//    AgentPushDataMapper agentPushDataMapper;
//    
//    /** (non-Javadoc)
//     * @see org.data.governance.unstructured.agent.push.service.AgentPushDataService#pushEajSsjcyxToSGY(org.data.governance.unstructured.agent.push.domain.tdh.TempEajSsjcyx)
//     */
//    @Override
//    public int pushEajSsjcyxToSGY(TempEajSsjcyx tempEajSsjcyx) throws Exception {
//        int status = agentPushDataMapper.pushEajSsjcyxToSGY(tempEajSsjcyx);
//        return status;
//    }
//
//    /** (non-Javadoc)
//     * @see org.data.governance.unstructured.agent.push.service.AgentPushDataService#pushEajMlxxToSGY(org.data.governance.unstructured.agent.push.domain.tdh.TempEajMlxx)
//     */
//    @Override
//    public int pushEajMlxxToSGY(TempEajMlxx tempEajMlxx) throws Exception {
//        int status = agentPushDataMapper.pushEajMlxxToSGY(tempEajMlxx);
//        return status;
//    }
//
//    /** (non-Javadoc)
//     * @see org.data.governance.unstructured.agent.push.service.AgentPushDataService#pushEajMlxxGcToSGY(org.data.governance.unstructured.agent.push.domain.tdh.TempEajMlxxGc)
//     */
//    @Override
//    public int pushEajMlxxGcToSGY(TempEajMlxxGc tempEajMlxxGc) throws Exception {
//        int status = agentPushDataMapper.pushEajMlxxGcToSGY(tempEajMlxxGc);
//        return status;
//    }
//
//    /** (non-Javadoc)
//     * @see org.data.governance.unstructured.agent.push.service.AgentPushDataService#pushEajJzwjAllToSGY(org.data.governance.unstructured.agent.push.domain.tdh.TempEajJzwjAll)
//     */
//    @Override
//    public int pushEajJzwjAllToSGY(TempEajJzwjAll tempEajJzwjAll) throws Exception {
//        int status = agentPushDataMapper.pushEajJzwjAllToSGY(tempEajJzwjAll);
//        return status;
//    }
//
//    /** (non-Javadoc)
//     * @see org.data.governance.unstructured.agent.push.service.AgentPushDataService#pushEajJzToSGY(org.data.governance.unstructured.agent.push.domain.tdh.TempEajJz)
//     */
//    @Override
//    public int pushEajJzToSGY(TempEajJz tempEajJz) throws Exception {
//        int status = agentPushDataMapper.pushEajJzToSGY(tempEajJz);
//        return status;
//    }
//
//    /** (non-Javadoc)
//     * @see org.data.governance.unstructured.agent.push.service.AgentPushDataService#pushEajJzNewToSGY(org.data.governance.unstructured.agent.push.domain.tdh.TempEajJzwjAllNew)
//     */
//    @Override
//    public int pushEajJzNewToSGY(TempEajJzwjAllNew tempEajJzwjAllNew) throws Exception {
//        int status = agentPushDataMapper.pushEajJzwjAllNewToSGY(tempEajJzwjAllNew);
//        return status;
//    }
//
//	@Override
//	public int pushEajAll(Map<String, Object> map) throws Exception {
//	    int status = agentPushDataMapper.pushEajAll(map);
//		return status;
//	}
//
//
//}
