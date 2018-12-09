package org.eop.ssei.web.rootac.initializer;

import org.eop.ssei.web.env.EnvUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * <p>执行一些和Profile相关的配置
 * <p>原来需要配置的-D系统参数
 * <p>现在直接通过程序添加到spring的Environment中
 * <p>减少开发人员的参数配置
 * @author lixinjie
 * @since 2018-08-08
 */
@Order(-100)
public class ProfileApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		EnvUtils.activeProfile(applicationContext.getEnvironment());
	}

}
