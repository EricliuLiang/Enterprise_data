package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Trade;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:10
 */
public interface TradeService extends BaseService<Trade> {
      String queryTypeDistribution(String companyName, boolean cache);
      String queryCategoryDistribution(String companyName,boolean cache);
      String queryStatusDistribution(String companyName,boolean cache);
}
