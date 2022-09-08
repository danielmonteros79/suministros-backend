package com.sa.su.configuration;

import java.io.File;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ar.org.bbva.util.CipherUtils;


@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.sa.su.configuration" })
public class HibernateConfiguration {
	Logger log = Logger.getLogger(HibernateConfiguration.class);
	
	@Autowired
    private ServletContext context;
	
	@Autowired
	private ConfigurableEnvironment environment;

	@PostConstruct
	public void init() throws Exception {
		try {
			String path = getSysConfPathResource("/suministros.properties");
			log.info("PATH:    "  + path);
			ResourcePropertySource propertySource = new ResourcePropertySource("resource", "file:" + path);
			environment.getPropertySources().addFirst(propertySource);
		} catch (Exception e) {
			log.error("Error al cargar properties", e);
		}
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.sa.su.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));//Local - descomentar en desa
//		try { //comentar en desa - descomentar en was
//		Properties propPass = CipherUtils.desencriptarProperties((Properties)environment.getPropertySources().get("resource").getSource());
//		dataSource.setPassword(propPass.getProperty("jdbc.password")); 
//	} catch (Exception e) {
//		log.error("",e);
//	}
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
//		properties.put("hibernate.hbm2ddl.auto", "update");
//		properties.put("hibernate.hbm2ddl.auto", "create");
//		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
	
	private String getSysConfPathResource(String fileName) {
		File f = new File(context.getRealPath(fileName));
		if (fileName.startsWith("/"))
			fileName = fileName.substring(1);

		String pathfinal = f.getParentFile().getParentFile().getAbsolutePath().concat("/syscfg/".concat(fileName));
		return pathfinal;
	}
}
