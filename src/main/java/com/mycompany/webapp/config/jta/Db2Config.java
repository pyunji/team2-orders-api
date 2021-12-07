package com.mycompany.webapp.config.jta;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@MapperScan(
	basePackages="com.mycompany.webapp.dao.db2product", 
	sqlSessionFactoryRef="db2SqlSessionFactory"
)
public class Db2Config {
    @Value("${spring.db2.datasource.xa-data-source-class-name}") 
    private String xaDataSourceClassName;
    
    @Value("${spring.db2.datasource.xa-properties.url}") 
    private String url;
    
    @Value("${spring.db2.datasource.xa-properties.user}") 
    private String user;
    
    @Value("${spring.db2.datasource.xa-properties.password}") 
    private String password; 
    
    public static final String DB2_DATASOURCE = "ds2DataSource";
	
	@Bean(name=DB2_DATASOURCE)
	public DataSource dataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName(DB2_DATASOURCE);
        ds.setXaDataSourceClassName(xaDataSourceClassName);
        
        Properties p = new Properties();
        p.setProperty("URL", url);
        p.setProperty("user", user);
        p.setProperty("password", password);
        ds.setXaProperties (p);
        
        return ds;
	}
	
    @Bean(name="db2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
    		@Qualifier(DB2_DATASOURCE) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        PathMatchingResourcePatternResolver resover = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resover.getResources("classpath:mybatis/mapper/db2product/*.xml"));
		sessionFactory.setConfigLocation(resover.getResource("classpath:mybatis/mapper-config.xml"));
        return sessionFactory.getObject();
    }
}
