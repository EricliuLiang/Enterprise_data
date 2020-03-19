package org.kelab.enterprise.common;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Distribution;

import java.util.List;
import java.util.Map;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 19:39
 */
public interface BaseService<Q> {

	List<Q> query(BaseQuery<Q> query);

	int total(BaseQuery<Q> query);

	boolean update(Q record);

	boolean insert(Q record);

	boolean delete(Q record);

	Map<String, Integer> timeTotal(String collection, String companyName, String key);

	String getCache(String key);

	void saveCache(String key, String value);

	List<Distribution> getDistribution(String collection, String key);
}
