package cz.akineta.feedback.config.preference;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@PropertySource("classpath:/properties/config.devbox.properties")
@Profile("devbox")
public class DevboxPreferences
{
}
