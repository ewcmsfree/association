package com.ewcms.system.icon.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.icon.entity.Icon;

/**
 * @author wu_zhijun
 */
public interface IconRepository extends BaseRepository<Icon, Long> {
    Icon findByIdentity(String identity);
}
