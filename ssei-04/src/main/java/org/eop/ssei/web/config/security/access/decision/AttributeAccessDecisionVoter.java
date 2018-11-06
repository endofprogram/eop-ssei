package org.eop.ssm.web.config.security.access.decision;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * @author lixinjie
 * @since 2018-10-31
 */
public class AttributeAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
	
	private static final String permitAll = "permitAll";
	private static final String authenticated = "authenticated";
	
	@Override
	public int vote(Authentication authentication, FilterInvocation fi,
			Collection<ConfigAttribute> attributes) {
		for (ConfigAttribute attribute : attributes) {
			if (attribute.toString().equals(permitAll)) {
				return ACCESS_GRANTED;
			}
		}
		for (ConfigAttribute attribute : attributes) {
			if (attribute.toString().equals(authenticated)) {
				if (authentication == null || "anonymousUser".equals(authentication.getName())) {
					return ACCESS_DENIED;
				}
				return ACCESS_GRANTED;
			}
		}
		return ACCESS_ABSTAIN;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
