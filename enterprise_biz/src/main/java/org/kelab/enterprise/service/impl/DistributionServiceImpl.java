package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.service.DistributionService;
import org.kelab.enterprise.util.MongoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/8/7 14:32
 * 不短不长八字刚好
 */
@Service
public class DistributionServiceImpl extends BaseServiceImpl<Distribution> implements DistributionService {

	@Autowired
	private BaseMongoDao dao;

	@Override
	public List<Distribution> query(BaseQuery<Distribution> query) {
		return dao.queryByCondition(query, new BasicDBObject("name", 1));
	}

	@Override
	public boolean refresh() {
		List<Distribution> distribution = getDistribution("company_profile", "city");
		if (distribution.size() > 0) {
			MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
			mongo.getCollection("distribution").drop();
			mongoDao.insertList(distribution);
		}
		return true;
	}
}
