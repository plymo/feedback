package cz.akineta.feedback.config;

import cz.akineta.feedback.config.data.DevboxDataConfig;
import cz.akineta.feedback.config.data.OpenShiftDataConfig;
import cz.akineta.feedback.config.preference.DevboxPreferences;
import cz.akineta.feedback.config.preference.OpenShiftPreferences;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@Import(value = {
		OpenShiftPreferences.class, DevboxPreferences.class,
		OpenShiftDataConfig.class, DevboxDataConfig.class
})
public class InfrastructureConfig
{
}
