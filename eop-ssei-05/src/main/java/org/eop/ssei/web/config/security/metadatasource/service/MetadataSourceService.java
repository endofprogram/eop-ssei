package org.eop.ssei.web.config.security.metadatasource.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author lixinjie
 * @since 2018-10-31
 */
public interface MetadataSourceService {

	Date getLastModified();
	
	LinkedHashMap<String, List<String>> getAllUrlRolesMappings();
}
