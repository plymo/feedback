package cz.akineta.feedback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@Profile("testing")
public class TestingThymeLeafViewResolverConfig
{

	@Bean
	public ThymeleafViewResolver viewResolver()
	{
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		viewResolver.setCharacterEncoding("UTF-8");

		return viewResolver;
	}

	@Bean
	public FileTemplateResolver templateResolver()
	{
		FileTemplateResolver templateResolver = new FileTemplateResolver();
		templateResolver.setPrefix("src/main/webapp/WEB-INF/templates/thymeleaf/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setTemplateMode("HTML5");

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine()
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}

}
