package data.exchange.center.service.parse.xml.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;
import data.exchange.center.service.parse.xml.mapper.GetTableMetaMapper;
import data.exchange.center.service.parse.xml.service.GetTableMetaService;

/**
 * <p>Title: DataVerificationServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月3日 上午12:10:08</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@Service
public class GetTableMetaServiceImpl implements GetTableMetaService{

    @Autowired
    private GetTableMetaMapper        getTableMetaMapper;

    /** (non-Javadoc)
     * @see org.data.governance.provider.parse.dataverification.service.GetTableMetaService#getTableMeta()
     */
    @Override
    @Transactional
    public AjlxMeta getAjlxMeta(String ajlxChName) throws Exception {
        return getTableMetaMapper.getAjlxMeta(ajlxChName);
    }

    /** (non-Javadoc)
     * @see org.data.governance.provider.parse.dataverification.service.GetTableMetaService#getTableMeta(java.lang.String)
     */
    @Override
    @Transactional
    public List<TableMeta> getTableMeta(String ajlxChName) throws Exception {
        return getTableMetaMapper.getTableMeta(ajlxChName);
    }

    /** (non-Javadoc)
     * @see org.data.governance.provider.parse.dataverification.service.GetTableMetaService#getColMeta(java.lang.String)
     */
    @Override
    @Transactional
    public List<ColMeta> getColMeta(String ajlxChName) throws Exception {
        return getTableMetaMapper.getColMeta(ajlxChName);
    }

}
