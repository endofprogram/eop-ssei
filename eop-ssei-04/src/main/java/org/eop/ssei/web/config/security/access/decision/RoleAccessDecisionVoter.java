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
			String expression = attribute.toString();
			if (expression.startsWith("hasRole") ||
					expression.startsWith("hasAnyRole")) {
				if (containsRoles(authentication, expression)) {
					return ACCESS_GRANTED;
				}
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
	
	private boolean containsRoles(Authentication authentication, String expression) {
		String[] roles = getRoles(expression);
		for (String role : roles) {
			if (authentication.getAuthorities().contains(new RoleGrantedAuthority(role))) {
				return true;
			}
		}
		return false;
	}
	
	private String[] getRoles(String expression) {
		int begin = expression.indexOf("(");
		int end = expression.lastIndexOf(")");
		String expr = expression.substring(begin + 1, end);
		String[] exprs = expr.split(",");
		String[] roles = new String[exprs.length];
		for (int i = 0; i < exprs.length; i++) {
			roles[i] = exprs[i].substring(1, exprs[i].length() - 1);
		}
		return roles;
	}
}
