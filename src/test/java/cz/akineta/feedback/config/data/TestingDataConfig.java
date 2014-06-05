package cz.akineta.feedback.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cz.akineta.feedback.domain")
@Profile("testing")
public class TestingDataConfig
{

	@Bean
	public DataSource dataSource()
	{
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.DERBY)
				.build();
	}

	@Bean
	public EntityManager entityManager()
	{
		SharedEntityManagerBean entityManager = new SharedEntityManagerBean();
		entityManager.setEntityManagerFactory(entityManagerFactory());

		return entityManager.getObject();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory()
	{
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.DerbyTenSevenDialect");
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.TRUE);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan("cz.akineta.feedback.domain");
		entityManagerFactory.setJpaProperties(jpaProperties);
		entityManagerFactory.afterPropertiesSet();

		return entityManagerFactory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager()
	{
		EntityManagerFactory factory = entityManagerFactory();

		return new JpaTransactionManager(factory);
	}

	@Bean
	public HibernateExceptionTranslator exceptionTranslator()
	{
		return new HibernateExceptionTranslator();
	}

}
