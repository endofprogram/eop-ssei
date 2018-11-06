package org.eop.ssei.web.config.security.user.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author lixinjie
 * @since 2018-10-30
 */
public class FakeUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("admin".equals(username)) {
			return new User("admin", "123", AuthorityUtils.createAuthorityList("ROLE_Admin"));
		}
		if ("user".equals(username)) {
			return new User("user", "123", AuthorityUtils.createAuthorityList("ROLE_User"));
		}
		if ("ua".equals(username)) {
			return new User("ua", "123", AuthorityUtils.createAuthorityList("ROLE_A"));
		}
		if ("ub".equals(username)) {
			return new User("ub", "123", AuthorityUtils.createAuthorityList("ROLE_B"));
		}
		throw new UsernameNotFoundException("username '" + username + "' not found");
	}

}
