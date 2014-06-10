package cz.akineta.feedback.config.data;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@Profile("openshift")
public class OpenShiftDataConfig
{

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		String host = env.getRequiredProperty("OPENSHIFT_MYSQL_DB_HOST");
		String port = env.getRequiredProperty("OPENSHIFT_MYSQL_DB_PORT");
		String appName = env.getRequiredProperty("OPENSHIFT_APP_NAME");
		String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + appName;

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(env.getRequiredProperty("OPENSHIFT_MYSQL_DB_USERNAME"));
		dataSource.setPassword(env.getRequiredProperty("OPENSHIFT_MYSQL_DB_PASSWORD"));

		return dataSource;
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter()
	{
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		vendorAdapter.setDatabase(Database.MYSQL);

		return vendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("cz.akineta.feedback.domain");
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPersistenceUnitName("feedbackPU");
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager()
	{
		JpaDialect jpaDialect = new HibernateJpaDialect();

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		txManager.setJpaDialect(jpaDialect);

		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator exceptionTranslator()
	{
		return new HibernateExceptionTranslator();
	}

}
