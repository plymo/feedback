package cz.akineta.feedback.config;

import cz.akineta.feedback.config.data.TestingDataConfig;
import cz.akineta.feedback.config.preference.TestingPreferences;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author Jakub Mahdal,jakub.mahdal@akineta.cz
 */
@Configuration
@Import(value = {TestingPreferences.class, TestingDataConfig.class})
public class TestingInfrastructureConfig
{
}
