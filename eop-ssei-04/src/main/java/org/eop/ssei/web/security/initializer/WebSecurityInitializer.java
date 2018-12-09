package org.eop.ssei.web.security.initializer;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author lixinjie
 * @since 2018-08-08
 */
public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	//该方法返回null或非null决定了WebSecurityConfig类是加入
	//RootConfig还是ServletConfig
	@Override
	protected String getDispatcherWebApplicationContextSuffix() {
		return "dispatcher";
	}
}
