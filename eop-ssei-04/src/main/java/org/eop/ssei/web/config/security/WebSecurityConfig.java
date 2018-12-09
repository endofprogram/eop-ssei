package org.eop.ssei.web.config.security;

import java.util.Arrays;

import org.eop.ssei.web.config.security.access.decision.CompositeAccessDecisionManager;
import org.eop.ssei.web.config.security.access.decision.RoleAccessDecisionVoter;
import org.eop.ssei.web.config.security.access.decision.SupportAccessDecisionVoter;
import org.eop.ssei.web.config.security.access.decision.UriAccessDecisionVoter;
import org.eop.ssei.web.config.security.access.handler.JsonAccessDeniedHandler;
import org.eop.ssei.web.config.security.login.handler.JsonAuthenticationFailureHandler;
import org.eop.ssei.web.config.security.login.handler.JsonAuthenticationSuccessHandler;
import org.eop.ssei.web.config.security.logout.handler.JsonLogoutSuccessHandler;
import org.eop.ssei.web.config.security.user.password.FakePasswordEncoder;
import org.eop.ssei.web.config.security.user.service.FakeUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lixinjie
 * @since 2018-11-05
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new FakeUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new FakePasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.accessDecisionManager(new CompositeAccessDecisionManager(
					Arrays.asList(new SupportAccessDecisionVoter(), new RoleAccessDecisionVoter(),
							new UriAccessDecisionVoter())))
			.mvcMatchers("/example/rolea").hasRole("A")
			.mvcMatchers("/example/roleb").hasRole("B")
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/example/login")
			.loginProcessingUrl("/example/loginProcess")
			//.successForwardUrl("/example/success")//转发
			//.failureForwardUrl("/example/failure")//转发
			//.defaultSuccessUrl("/example/success")//重定向
			//.failureUrl("/example/failure")//重定向
			.successHandler(new JsonAuthenticationSuccessHandler())
			.failureHandler(new JsonAuthenticationFailureHandler())
			.permitAll()
			.and().logout()
			.logoutUrl("/example/logoutProcess")
			//.logoutSuccessUrl("/example/logout")//重定向
			.logoutSuccessHandler(new JsonLogoutSuccessHandler())
			.permitAll()
			.and().exceptionHandling()
			//.accessDeniedPage("/example/deny")//转发
			.accessDeniedHandler(new JsonAccessDeniedHandler());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true)
			.ignoring()
			.antMatchers("/images/**", "/js/**", "/css/**");
	}
}
