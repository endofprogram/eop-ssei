package org.eop.ssei.web.config.security.metadatasource.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author lixinjie
 * @since 2018-10-31
 */
@Mapper
public interface MetadataSourceMapper {

	Date getLastModified();
	
	List<Map<String, Object>> getAllUrlRoleMappings();
}
