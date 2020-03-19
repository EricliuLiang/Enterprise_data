package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Distribution;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/8/7 14:31
 * 不短不长八字刚好
 */
public interface DistributionService extends BaseService<Distribution> {
	/**
	 * 查地区分布信息
	 *
	 * @param query
	 * @return
	 */
	List<Distribution> query(BaseQuery<Distribution> query);

	boolean refresh();
}
