package org.eop.ssei.web.config.security.access.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author lixinjie
 * @since 2018-10-22
 */
public class UriGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String uri;

	public UriGrantedAuthority(String uri) {
		this.uri = uri;
	}

	public String getAuthority() {
		return uri;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof UriGrantedAuthority) {
			return uri.equals(((UriGrantedAuthority)obj).uri);
		}

		return false;
	}

	public int hashCode() {
		return this.uri.hashCode();
	}

	public String toString() {
		return this.uri;
	}

}
