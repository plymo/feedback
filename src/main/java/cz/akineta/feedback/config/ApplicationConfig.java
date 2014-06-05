package cz.akineta.feedback.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@ComponentScan(basePackages = "cz.akineta.feedback.domain")
@EnableJpaRepositories(basePackages = "cz.akineta.feedback.domain")
@EnableTransactionManagement
@PropertySource("classpath:/properties/config.properties")
public class ApplicationConfig
{
}
