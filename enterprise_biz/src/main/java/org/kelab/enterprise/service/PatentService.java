package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Patent;

import java.util.List;
import java.util.Map;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:04
 */
public interface PatentService extends BaseService<Patent> {
	/**
	 * 专利年份分布
	 * @param companyName
	 * @return
	 */
	String queryDistribution(String companyName, boolean cache);


	String queryTypeDistribution(String companyName, boolean cache);

	/**
	 * 根据关键字搜索哪些公司拥有项专利并统计数量
	 * @param query
	 * @return
	 */
	Map<String,Integer> queryCompanyFrequency(BaseQuery<Patent>query,String keyword);

	/**
	 * 根据关键字搜索专利，统计这些专利的类型
	 * @param keyword
	 * @return
	 */
	Map<String,Long> countPatentType(String keyword);

	/**
	 * 根据专利关键字查找专利
	 * @param query
	 * @param keyword
	 * @return
	 */
	List<Patent> queryByKeyword(BaseQuery<Patent> query,String keyword);

	Integer queryConditionCount(BaseQuery<Patent> query,String keyword);
}
