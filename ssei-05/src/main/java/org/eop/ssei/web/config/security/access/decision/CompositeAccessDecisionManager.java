package org.eop.ssei.web.config.security.access.decision;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * @author lixinjie
 * @since 2018-10-22
 */
public class CompositeAccessDecisionManager implements AccessDecisionManager {

	private List<AccessDecisionVoter<FilterInvocation>> decisionVoters;
	
	public CompositeAccessDecisionManager(List<AccessDecisionVoter<FilterInvocation>> decisionVoters) {
		this.decisionVoters = decisionVoters;
	}
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		for (AccessDecisionVoter<FilterInvocation> decisionVoter : decisionVoters) {
			int result = decisionVoter.vote(authentication, (FilterInvocation)object, configAttributes);
			switch (result) {
				case AccessDecisionVoter.ACCESS_GRANTED:
					return;
				case AccessDecisionVoter.ACCESS_DENIED:
					String user = authentication.getName();
					String url = ((FilterInvocation)object).getRequestUrl();
					throw new AccessDeniedException("user '" + user + "' attempts to access url '" + url + "' was denied.");
				default:
					break;
			}
		}
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		for (AccessDecisionVoter<FilterInvocation> decisionVoter : decisionVoters) {
			if (decisionVoter.supports(attribute)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		for (AccessDecisionVoter<FilterInvocation> decisionVoter : decisionVoters) {
			if (!decisionVoter.supports(clazz)) {
				return false;
			}
		}
		return true;
	}

}
