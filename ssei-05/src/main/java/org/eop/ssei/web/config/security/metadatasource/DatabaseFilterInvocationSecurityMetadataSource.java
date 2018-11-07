package org.eop.ssei.web.config.security.metadatasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.eop.common.date.DateUtils;
import org.eop.ssei.web.config.security.metadatasource.attribute.RoleConfigAttribute;
import org.eop.ssei.web.config.security.metadatasource.service.MetadataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @author lixinjie
 * @since 2018-10-31
 */
public class DatabaseFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource,
			FilterInvocationSecurityMetadataSourceLoader {

	private static final Logger log = LoggerFactory.getLogger(DatabaseFilterInvocationSecurityMetadataSource.class);
	
	private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME = "mvcHandlerMappingIntrospector";
	
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	
	private Date lastModified;
	private Date lastLoaded;
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private MetadataSourceService metadataSourceService;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		Map<RequestMatcher, Collection<ConfigAttribute>> myRequestMap = requestMap;
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : myRequestMap
				.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		Map<RequestMatcher, Collection<ConfigAttribute>> myRequestMap = requestMap;
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : myRequestMap
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	@Override
	public Date getLastModified() {
		return lastModified;
	}
	
	@Override
	public Date getLastLoaded() {
		return lastLoaded;
	}
	
	@Override
	public void load() {
		lastModified = metadataSourceService.getLastModified();
		lastLoaded = new Date();
		requestMap = loadRequestMap();
	}
	
	@Override
	public void reload() {
		lastModified = metadataSourceService.getLastModified();
		log.info("security metadata lastModified at {}, lastLoaded at {}",
				DateUtils.getDateStr19(lastModified), lastLoaded == null ?
						"null" : DateUtils.getDateStr19(lastLoaded));
		if (lastLoaded == null || lastLoaded.before(lastModified)) {
			lastLoaded = new Date();
			requestMap = loadRequestMap();
			log.info("security metadata reloaded at {}", DateUtils.getDateStr19());
		}
	}
	
	private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> loadRequestMap() {
		LinkedHashMap<String, List<String>> urlRolesMappings = metadataSourceService.getAllUrlRolesMappings();
		return buildRequestAttributesMappings(urlRolesMappings);
	}
	
	private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>
		buildRequestAttributesMappings(LinkedHashMap<String, List<String>> urlRolesMappings) {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestAttributesMappings =
				new LinkedHashMap<>(urlRolesMappings.size());
		for (Map.Entry<String, List<String>> entry : urlRolesMappings.entrySet()) {
			requestAttributesMappings.put(getRequestMatcher(entry.getKey()), getConfigAttributes(entry.getValue()));
		}
		return requestAttributesMappings;
	}
	
	private RequestMatcher getRequestMatcher(String urlPattern) {
		if ("/**".equals(urlPattern)) {
			return anyRequest();
		}
		if (urlPattern.endsWith("/**")) {
			return antMatchers(urlPattern).get(0);
		}
		return mvcMatchers(urlPattern).get(0);
	}
	
	private List<ConfigAttribute> getConfigAttributes(List<String> roles) {
		List<ConfigAttribute> attributes = new ArrayList<>(roles.size());
		for (String role : roles) {
			attributes.add(new RoleConfigAttribute(role));
		}
		return attributes;
	}

	public RequestMatcher anyRequest() {
		return AnyRequestMatcher.INSTANCE;
	}
	
	public List<RequestMatcher> antMatchers(String... antPatterns) {
		return antMatchers(null, antPatterns);
	}
	
	public List<RequestMatcher> antMatchers(HttpMethod httpMethod,
			String... antPatterns) {
		String method = httpMethod == null ? null : httpMethod.toString();
		List<RequestMatcher> matchers = new ArrayList<>();
		for (String pattern : antPatterns) {
			matchers.add(new AntPathRequestMatcher(pattern, method));
		}
		return matchers;
	}
	
	public List<RequestMatcher> regexMatchers(String... regexPatterns) {
		return regexMatchers(null, regexPatterns);
	}
	
	public List<RequestMatcher> regexMatchers(HttpMethod httpMethod,
			String... regexPatterns) {
		String method = httpMethod == null ? null : httpMethod.toString();
		List<RequestMatcher> matchers = new ArrayList<>();
		for (String pattern : regexPatterns) {
			matchers.add(new RegexRequestMatcher(pattern, method));
		}
		return matchers;
	}
	
	public List<RequestMatcher> mvcMatchers(String... mvcPatterns) {
		return mvcMatchers(null, mvcPatterns);
	}
	
	@SuppressWarnings("unchecked")
	public List<RequestMatcher> mvcMatchers(HttpMethod method,
			String... mvcPatterns) {
		boolean isServlet30 = ClassUtils.isPresent("javax.servlet.ServletRegistration",
				getClass().getClassLoader());
		ObjectPostProcessor<Object> opp = this.context.getBean(ObjectPostProcessor.class);
		if(!this.context.containsBean(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME)) {
			throw new NoSuchBeanDefinitionException("A Bean named " + HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME
				+" of type " + HandlerMappingIntrospector.class.getName()
				+ " is required to use MvcRequestMatcher. "
				+ "Please ensure Spring Security & Spring MVC are configured in a shared ApplicationContext.");
		}
		HandlerMappingIntrospector introspector = this.context.getBean(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME,
			HandlerMappingIntrospector.class);
		List<RequestMatcher> matchers = new ArrayList<>(mvcPatterns.length);
		for (String mvcPattern : mvcPatterns) {
			MvcRequestMatcher matcher = new MvcRequestMatcher(introspector, mvcPattern);
			if (isServlet30) {
				opp.postProcess(matcher);
			}
			if (method != null) {
				matcher.setMethod(method);
			}
			matchers.add(matcher);
		}
		return matchers;
	}
}
