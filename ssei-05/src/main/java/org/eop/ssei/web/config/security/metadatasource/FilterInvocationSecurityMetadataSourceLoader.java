package org.eop.ssei.web.config.security.metadatasource;

import java.util.Date;

/**
 * @author lixinjie
 * @since 2018-10-31
 */
public interface FilterInvocationSecurityMetadataSourceLoader {

	Date getLastModified();
	
	Date getLastLoaded();
	
	void load();
	
	void reload();
}
