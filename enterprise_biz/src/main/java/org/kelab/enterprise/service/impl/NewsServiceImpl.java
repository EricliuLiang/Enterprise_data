package org.kelab.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.News;
import org.kelab.enterprise.entity.Recruit;
import org.kelab.enterprise.service.NewsService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 19:59
 */
@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {

	@Autowired
	private BaseMongoDao dao;

	@Override
	public List<News> query(BaseQuery<News> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query,new BasicDBObject("publish_time",-1));
	}


	@Override
	public List<Distribution> querySourceDistribution(String companyName) {
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<News> query = new BaseQuery<>(News.class);
			query.getQuery().setCompanyName(companyName);
			List<News> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (News recruit: list) {
				Integer val = dis.get(recruit.getSource());
				if (val == null) {
					dis.put(recruit.getSource(),1);
				} else {
					dis.put(recruit.getSource(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
		return getDistribution("news","source");
	}

	@Override
	public String queryTimeDistribution(String companyName, boolean cache) {
		String res;
		if (!StringUtil.notBlank(companyName) && cache) {
			res = getCache("newsCache");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		res = JSON.toJSONString(timeTotal("news",companyName,"publish_time"));
		if (!StringUtil.notBlank(companyName)) {
			saveCache("newsCache", res);
		}
		return res;
	}
}
