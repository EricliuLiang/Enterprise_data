package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Statistics;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/8/5 17:56
 * 不短不长八字刚好
 */
public interface StatisticsService {

    /**
     * 查询系统收集情况
     * @param query
     * @return
     */
    List<Statistics> query(BaseQuery<Statistics> query);
}
