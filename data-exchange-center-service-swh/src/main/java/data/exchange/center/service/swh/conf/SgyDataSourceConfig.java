package data.exchange.center.service.swh.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 扫描 Mapper 接口并容器管理
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月11日 上午11:14:53</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
@MapperScan(basePackages = SgyDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sgySqlSessionFactory")
public class SgyDataSourceConfig {
 
    // 精确到 sgy 目录，以便跟其他数据源隔离
    static final String PACKAGE = "data.exchange.center.service.swh.mapper.sgy";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/sgy/*.xml";
 
    @Value("${sgy.datasource.url}")
    private String url;
 
    @Value("${sgy.datasource.username}")
    private String user;
 
    @Value("${sgy.datasource.password}")
    private String password;
 
    @Value("${sgy.datasource.driverClassName}")
    private String driverClass;
 
    @Bean(name = "sgyDataSource")
    @Primary
    public DataSource sgyDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
 
    @Bean(name = "sgyTransactionManager")
    @Primary
    public DataSourceTransactionManager sgyTransactionManager() {
        return new DataSourceTransactionManager(sgyDataSource());
    }
 
    @Bean(name = "sgySqlSessionFactory")
    @Primary
    public SqlSessionFactory sgySqlSessionFactory(@Qualifier("sgyDataSource") DataSource sgyDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(sgyDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SgyDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
