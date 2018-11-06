package org.eop.ssei.web.config.mvc;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lixinjie
 * @since 2018-08-08
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		int xmlIndex = 0;
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof StringHttpMessageConverter) {
				((StringHttpMessageConverter)converter).setDefaultCharset(Charset.forName("UTF-8"));
			}
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				((MappingJackson2HttpMessageConverter)converter).getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			}
			if (converter instanceof MappingJackson2XmlHttpMessageConverter) {
				((MappingJackson2XmlHttpMessageConverter)converter).getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				xmlIndex = converters.indexOf(converter);
			}
		}
		removeXmlConverter(converters, xmlIndex);
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/", ".jsp");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	
	private void removeXmlConverter(List<HttpMessageConverter<?>> converters, int xmlIndex) {
		converters.remove(xmlIndex);
	}
}
