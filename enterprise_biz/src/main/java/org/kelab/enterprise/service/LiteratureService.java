package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.Literature;
import org.kelab.enterprise.entity.Patent;

import java.util.List;
import java.util.Map;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 19:36
 */
public interface LiteratureService extends BaseService<Literature> {

	/**
	 * 论文年份分布
	 * @param companyName
	 * @return
	 */
	String queryDistribution(String companyName, boolean cache);

	/**
	 * 论文类型分布
	 * @param companyName
	 * @return
	 */
	 List<Distribution> queryTypeDistribution(String companyName);
	 List<Distribution> querySourceDistribution(String companyName);
	 List<Distribution> queryCategoryDistribution(String companyName);

	/**
	 *  查找哪些公司拥有关键字所关联的文献
	 * @param query
	 * @param keyword
	 * @return
	 */
	 Map<String,Integer> queryAndCount(BaseQuery<Literature> query,String keyword);

	/**
	 * 统计这些文献的类型
	 * @param keyword
	 * @return
	 */
	 Map<String,Integer> countLiteratureType(String keyword);

	 List<Literature> queryByKeyword(BaseQuery<Literature>query,String keyword);

	 Integer queryConditionCount(BaseQuery<Literature>query,String keyword);
}
