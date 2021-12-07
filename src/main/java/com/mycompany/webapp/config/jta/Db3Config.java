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
	basePackages="com.mycompany.webapp.dao.db3orders", 
	sqlSessionFactoryRef="db3SqlSessionFactory"
)
public class Db3Config {
    @Value("${spring.db3.datasource.xa-data-source-class-name}") 
    private String xaDataSourceClassName;
    
    @Value("${spring.db3.datasource.xa-properties.url}") 
    private String url;
    
    @Value("${spring.db3.datasource.xa-properties.user}") 
    private String user;
    
    @Value("${spring.db3.datasource.xa-properties.password}") 
    private String password; 
    
    public static final String DB3_DATASOURCE = "ds3DataSource";
	
	@Bean(name=DB3_DATASOURCE)
	public DataSource dataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName(DB3_DATASOURCE);
        ds.setXaDataSourceClassName(xaDataSourceClassName);
        
        Properties p = new Properties();
        p.setProperty("URL", url);
        p.setProperty("user", user);
        p.setProperty("password", password);
        ds.setXaProperties (p);
        
        return ds;
	}
	
    @Bean(name="db3SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
    		@Qualifier(DB3_DATASOURCE) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        PathMatchingResourcePatternResolver resover = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resover.getResources("classpath:mybatis/mapper/db3orders/*.xml"));
		sessionFactory.setConfigLocation(resover.getResource("classpath:mybatis/mapper-config.xml"));
        return sessionFactory.getObject();
    }
}
