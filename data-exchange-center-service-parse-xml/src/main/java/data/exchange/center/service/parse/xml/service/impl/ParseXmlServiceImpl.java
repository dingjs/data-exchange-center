package data.exchange.center.service.parse.xml.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.exchange.center.service.parse.xml.domain.XmlContent;
import data.exchange.center.service.parse.xml.mapper.ParseXmlMapper;
import data.exchange.center.service.parse.xml.service.ParseXmlService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年1月24日 上午10:58:52</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ParseXmlServiceImpl implements ParseXmlService {
    @Autowired
    private ParseXmlMapper parseXmlMapper;
    
    /** (non-Javadoc)
     * @see org.data.governance.provider.parse.parsestorage.service.ParseXmlService#getXMLFromDb()
     */
    @Override
    @Transactional
    public XmlContent getXML(Map<String, Object> map) throws Exception {
        return parseXmlMapper.getXML(map);
    }

    /** (non-Javadoc)
     * @see org.data.governance.provider.parse.parsestorage.service.ParseXmlService#saveCase(java.lang.String)
     */
    @Override
    @Transactional
    public int saveCase(String sql, Map<String, Object> params) throws Exception {
        params.put("insertSql", sql);
        return parseXmlMapper.saveCase(params);
    }

    /** (non-Javadoc)
     * @see org.data.governance.provider.parse.parsestorage.service.ParseXmlService#deleteTableRecord(java.lang.String)
     */
    @Override
    public int deleteTableRecord(String deleteSql) throws Exception {
        return parseXmlMapper.deleteTableRecord(deleteSql);
    }
}
