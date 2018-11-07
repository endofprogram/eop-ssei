package org.eop.ssei.web.config.security.access.authority;

import org.eop.ssei.web.config.security.metadatasource.attribute.RoleConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author lixinjie
 * @since 2018-10-22
 */
public class RoleGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String role;

	public RoleGrantedAuthority(String role) {
		this.role = role;
	}

	public String getAuthority() {
		return role;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof RoleGrantedAuthority) {
			return role.equals(((RoleGrantedAuthority)obj).role);
		}
		if (obj instanceof RoleConfigAttribute) {
			return role.equals(((RoleConfigAttribute)obj).getAttribute());
		}
		return false;
	}

	public int hashCode() {
		return role.hashCode();
	}

	public String toString() {
		return role;
	}

}
