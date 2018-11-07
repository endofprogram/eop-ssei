package org.eop.ssei.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinjie
 * @since 2018-11-05
 */
@Configuration
@ComponentScan(basePackages = {"org.eop.ssei.config.*", "org.eop.ssei.**.service"})
public class RootConfig {

}
