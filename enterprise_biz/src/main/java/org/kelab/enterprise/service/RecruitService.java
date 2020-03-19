package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.Recruit;

import java.util.List;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:07
 */
public interface RecruitService extends BaseService<Recruit> {

	String querySalaryDistribution(String companyName, boolean cache);

	List<Distribution> queryRequireDistribution(String companyName);

	List<Distribution> queryAddressDistribution(String companyName);
}
