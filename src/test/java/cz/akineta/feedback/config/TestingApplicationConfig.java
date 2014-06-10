package cz.akineta.feedback.config;

import cz.akineta.feedback.domain.FeedbackService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@Profile("testing")
public class TestingApplicationConfig
{

	@Bean
	public FeedbackService feedbackService()
	{
		return Mockito.mock(FeedbackService.class);
	}

}
