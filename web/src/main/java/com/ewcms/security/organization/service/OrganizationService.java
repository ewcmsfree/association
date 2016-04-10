package com.ewcms.security.organization.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseTreeableService;
import com.ewcms.security.organization.entity.Organization;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class OrganizationService extends BaseTreeableService<Organization, Long> {

    /**
     * 过滤仅获取可显示的数据
     *
     * @param organizationIds
     * @param organizationJobIds
     */
    public void filterForCanShow(Set<Long> organizationIds, Set<Long[]> organizationJobIds) {

        Iterator<Long> iter1 = organizationIds.iterator();

        while (iter1.hasNext()) {
            Long id = iter1.next();
            Organization o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter1.remove();
            }
        }

        Iterator<Long[]> iter2 = organizationJobIds.iterator();

        while (iter2.hasNext()) {
            Long id = iter2.next()[0];
            Organization o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter2.remove();
            }
        }

    }

	@Override
	public Organization initRoot() {
		Organization organization = new Organization();
		organization.setName("组织机构");
		organization.setParentId(0L);
		organization.setParentIds("0/");
		organization.setShow(Boolean.TRUE);
		return save(organization);
	}

	@Override
	public Map<String, Object> treeAttributes(Organization m) {
		return null;
	}
}
