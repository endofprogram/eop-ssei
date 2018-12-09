package org.eop.ssei.web.config.security.user.service;

import java.util.Arrays;

import org.eop.ssei.web.config.security.access.authority.RoleGrantedAuthority;
import org.eop.ssei.web.config.security.access.authority.UriGrantedAuthority;
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
			return new User("admin", "123", Arrays.asList(new RoleGrantedAuthority("ROLE_Admin")));
		}
		if ("user".equals(username)) {
			return new User("user", "123", Arrays.asList(new RoleGrantedAuthority("ROLE_User")));
		}
		if ("ua".equals(username)) {
			return new User("ua", "123", Arrays.asList(new RoleGrantedAuthority("ROLE_A")));
		}
		if ("ub".equals(username)) {
			return new User("ub", "123", Arrays.asList(new RoleGrantedAuthority("ROLE_B")));
		}
		if ("uc".equals(username)) {
			return new User("uc", "123", Arrays.asList(new UriGrantedAuthority("/example/rolea")));
		}
		throw new UsernameNotFoundException("username '" + username + "' not found");
	}

}
