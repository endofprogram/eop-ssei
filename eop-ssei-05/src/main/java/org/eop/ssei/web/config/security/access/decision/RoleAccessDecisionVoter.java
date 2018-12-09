package org.eop.ssei.web.config.security.access.decision;

import java.util.Collection;

import org.eop.ssei.web.config.security.access.authority.RoleGrantedAuthority;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * @author lixinjie
 * @since 2018-10-22
 */
public class RoleAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

	@Override
	public int vote(Authentication authentication, FilterInvocation fi,
			Collection<ConfigAttribute> attributes) {
		if (authentication.getAuthorities().contains(new RoleGrantedAuthority("ROLE_Admin"))) {
			return ACCESS_GRANTED;
		}
		for (ConfigAttribute attribute : attributes) {
			if (authentication.getAuthorities().contains((Object)attribute)) {
				return ACCESS_GRANTED;
			}
		}
		return ACCESS_ABSTAIN;
	}
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
