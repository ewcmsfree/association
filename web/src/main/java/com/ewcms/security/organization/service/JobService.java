package com.ewcms.security.organization.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseTreeableService;
import com.ewcms.security.organization.entity.Job;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class JobService extends BaseTreeableService<Job, Long> {

    /**
     * 过滤仅获取可显示的数据
     *
     * @param jobIds
     * @param organizationJobIds
     */
    public void filterForCanShow(Set<Long> jobIds, Set<Long[]> organizationJobIds) {

        Iterator<Long> iter1 = jobIds.iterator();

        while (iter1.hasNext()) {
            Long id = iter1.next();
            Job o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter1.remove();
            }
        }

        Iterator<Long[]> iter2 = organizationJobIds.iterator();

        while (iter2.hasNext()) {
            Long id = iter2.next()[1];
            Job o = findOne(id);
            if (o == null || Boolean.FALSE.equals(o.getShow())) {
                iter2.remove();
            }
        }

    }

	@Override
	public Job initRoot() {
		Job job = new Job();
		job.setName("工作职务");
		job.setParentId(0L);
		job.setParentIds("0/");
		job.setShow(Boolean.TRUE);
		
		return save(job);
	}

	@Override
	public Map<String, Object> treeAttributes(Job m) {
		return null;
	}
}
