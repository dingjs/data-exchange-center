package data.exchange.center.service.elasticsearch.service.impl;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import data.exchange.center.service.elasticsearch.dao.LogRepository;
import data.exchange.center.service.elasticsearch.domain.LogObject;
import data.exchange.center.service.elasticsearch.service.ElasticsearchService;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月6日 上午11:18:22</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

	private static Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);  
	
	@Autowired
	private LogRepository logRepository;
	
	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.elasticsearch.service.ElasticsearchService#findByLevel(java.lang.String, int, int)
	 */
	@Override
	public List<LogObject> findByLevel(String searchContent, int pageNumber, int pageSize) throws Exception {
//        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(level);
//        Iterable<LogObject> searchResult = logRepository.search(builder);
//        Iterator<LogObject> iterator = searchResult.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        /**
         * select * from table where active=1 and ( name like '%？%' or code like '%?%' )
         * QueryBuilder orQuery = QueryBuilders.boolQuery()
.should(QueryBuilders.matchQuery("name", 参数))
.should(QueryBuilders.matchQuery("code", 参数));

QueryBuilder qb = QueryBuilders.boolQuery()
.must(new QueryStringQueryBuilder("1").field("active"))
.must(orQuery);
         */
		
		
		/**
		 * 模糊查询
		 */
		QueryBuilder orQuery = QueryBuilders.boolQuery()
				.should(QueryBuilders.matchQuery("systemName", "service-download"));
				//.should(QueryBuilders.matchQuery("methodName", "start"));

		/**
		 * 精确查询
		 */
		QueryBuilder qb = QueryBuilders.boolQuery().must(new QueryStringQueryBuilder(searchContent).field("level"))
				.must(orQuery);
		// 分页参数
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        
		
		Page<LogObject> log = logRepository.search(qb, pageable);
		log.getTotalPages();//页数
		log.getContent();//list
		log.getSize();
		log.getNumber();
		log.getNumberOfElements();
		log.getTotalElements();//总条数
		
		
        
        // Function Score Query
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("level", searchContent)),
                    ScoreFunctionBuilders.weightFactorFunction(1000))
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("message", searchContent)),
                        ScoreFunctionBuilders.weightFactorFunction(100));

        // 创建搜索 DSL 查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();

        logger.info("\n searchLog(): searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());

//        Page<LogObject> searchPageResults = logRepository.search(searchQuery);
        return log.getContent();
	}

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.elasticsearch.service.ElasticsearchService#findById(java.lang.String, int, int)
	 */
	@Override
	public List<LogObject> findById(String id, int pageNumber, int pageSize) throws Exception {
		/**
		 * 精确查询
		 */
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(new QueryStringQueryBuilder(id).field("uuid"));
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		Page<LogObject> log = logRepository.search(queryBuilder, pageable);
		return log.getContent();
	}

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.elasticsearch.service.ElasticsearchService#findBySystemName(java.lang.String, int, int)
	 */
	@Override
	public List<LogObject> findBySystemName(String systemName, int pageNumber, int pageSize) throws Exception {
		/**
		 * 精确查询
		 */
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(new QueryStringQueryBuilder(systemName).field("systemName"));
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		Page<LogObject> log = logRepository.search(queryBuilder, pageable);
		return log.getContent();
	}

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.elasticsearch.service.ElasticsearchService#deleteBySystemName(java.lang.String)
	 */
	@Override
	public void deleteBySystemName(String systemName) throws Exception {
		LogObject logObject = new LogObject();
		logRepository.delete(logObject);
	}

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.elasticsearch.service.ElasticsearchService#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String id) throws Exception {
		logRepository.delete(id);
	}

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.elasticsearch.service.ElasticsearchService#deleteByLevel(java.lang.String)
	 */
	@Override
	public void deleteByLevel(String level) throws Exception {
		logRepository.delete(level);
	}

	@Override
	public Object findLog(String ip, String level, String systemName, String searchDate, int pageNumber, int pageSize) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(new QueryStringQueryBuilder(ip).field("ip"))
				.should(new QueryStringQueryBuilder(level).field("level"))
				.should(new QueryStringQueryBuilder(systemName).field("systemName"))
				.should(new QueryStringQueryBuilder(searchDate).field("time"));
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		Page<LogObject> log = logRepository.search(queryBuilder, pageable);
		return log.getContent();
	}

	@Override
	public Object getCaseTrack(String ajbs, int pageNumber, int pageSize) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(new QueryStringQueryBuilder(ajbs).field("message"));
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		Page<LogObject> log = logRepository.search(queryBuilder, pageable);
		return log.getContent();
	}
}
