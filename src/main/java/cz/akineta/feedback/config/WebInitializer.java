package cz.akineta.feedback.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class[]{ApplicationConfig.class, InfrastructureConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[]{MvcDispatcherConfig.class, ThymeLeafViewResolverConfig.class};
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[]{"/"};
	}

	@Override
	protected String getServletName()
	{
		return "feedback";
	}

	@Override
	protected Filter[] getServletFilters()
	{
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");

		return new Filter[]{characterEncodingFilter};
	}

}
