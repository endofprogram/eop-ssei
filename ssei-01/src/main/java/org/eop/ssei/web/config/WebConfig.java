package org.eop.ssei.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinjie
 * @since 2018-01-08
 */
@Configuration
@ComponentScan(basePackages = {"org.eop.ssei.web.config.*", "org.eop.ssei.**.controller"})
public class WebConfig {

}
