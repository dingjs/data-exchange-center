package data.exchange.center.service.businessdata.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 扫描 Mapper 接口并容器管理
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月11日 上午11:11:59</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
@MapperScan(basePackages = TdhDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "tdhSqlSessionFactory")
public class TdhDataSourceConfig {
 
    // 精确到 tdh 目录，以便跟其他数据源隔离
    static final String PACKAGE = "data.exchange.center.service.businessdata.mapper.tdh";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/tdh/*.xml";
 
    @Value("${tdh.datasource.url}")
    private String url;
 
    @Value("${tdh.datasource.username}")
    private String user;
 
    @Value("${tdh.datasource.password}")
    private String password;
 
    @Value("${tdh.datasource.driverClassName}")
    private String driverClass;
 
    @Bean(name = "tdhDataSource")
    public DataSource tdhDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
 
    @Bean(name = "tdhTransactionManager")
    public DataSourceTransactionManager tdhTransactionManager() {
        return new DataSourceTransactionManager(tdhDataSource());
    }
 
    @Bean(name = "tdhSqlSessionFactory")
    public SqlSessionFactory tdhSqlSessionFactory(@Qualifier("tdhDataSource") DataSource tdhDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(tdhDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(TdhDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
