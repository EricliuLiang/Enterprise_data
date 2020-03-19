package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.News;

import java.util.List;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 19:58
 */
public interface NewsService extends BaseService<News> {

	List<Distribution> querySourceDistribution(String companyName);

	String queryTimeDistribution(String companyName, boolean cache);
}
