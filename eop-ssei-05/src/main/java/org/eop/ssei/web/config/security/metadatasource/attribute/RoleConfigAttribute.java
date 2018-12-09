package org.eop.ssei.web.config.security.metadatasource.attribute;

import org.eop.ssei.web.config.security.access.authority.RoleGrantedAuthority;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author lixinjie
 * @since 2018-11-05
 */
public class RoleConfigAttribute implements ConfigAttribute {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	private String roleAttribute;
	
	public RoleConfigAttribute(String roleAttribute) {
		this.roleAttribute = roleAttribute;
	}
	
	@Override
	public String getAttribute() {
		return roleAttribute;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof RoleConfigAttribute) {
			return roleAttribute.equals(((RoleConfigAttribute)obj).roleAttribute);
		}
		if (obj instanceof RoleGrantedAuthority) {
			return roleAttribute.equals(((RoleGrantedAuthority)obj).getAuthority());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return roleAttribute.hashCode();
	}

	@Override
	public String toString() {
		return roleAttribute;
	}
}
