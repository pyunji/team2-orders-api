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
	basePackages="com.mycompany.webapp.dao.db1member", 
	sqlSessionFactoryRef="db1SqlSessionFactory"
)
public class Db1Config {
	@Value("${spring.db1.datasource.xa-data-source-class-name}") 
	private String xaDataSourceClassName;
	
	@Value("${spring.db1.datasource.xa-properties.url}") 
	private String url;
    
	@Value("${spring.db1.datasource.xa-properties.user}") 
	private String user;
    
	@Value("${spring.db1.datasource.xa-properties.password}") 
	private String password;
    
    public static final String DB1_DATASOURCE = "db1DataSource";
	
	@Bean(name=DB1_DATASOURCE)
	public DataSource dataSource() {
		AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
		ds.setUniqueResourceName(DB1_DATASOURCE);
		ds.setXaDataSourceClassName(xaDataSourceClassName);

		Properties p = new Properties();
		p.setProperty("URL", url);
		p.setProperty("user", user);
		p.setProperty("password", password);
		ds.setXaProperties(p);

		return ds;
	}
	
	@Bean(name="db1SqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(
			@Qualifier(DB1_DATASOURCE) DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		PathMatchingResourcePatternResolver resover = new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resover.getResources("classpath:mybatis/mapper/db1member/*.xml"));
		sessionFactory.setConfigLocation(resover.getResource("classpath:mybatis/mapper-config.xml"));
		return sessionFactory.getObject();
	}
}
