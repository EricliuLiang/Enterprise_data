package org.kelab.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.Recruit;
import org.kelab.enterprise.service.RecruitService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:07
 */
@Service
public class RecruitServiceImpl extends BaseServiceImpl<Recruit> implements RecruitService {


	@Override
	public List<Recruit> query(BaseQuery<Recruit> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query,new BasicDBObject("hiring_time",-1));
	}

	@Override
	public String querySalaryDistribution(String companyName, boolean cache) {
		if (cache && !StringUtil.notBlank(companyName)) {
			String res = getCache("RecruitCache");
			if (res != null) {
				return res;
			}
		}
		BaseQuery<Recruit> query = new BaseQuery<>(Recruit.class);
		query.getQuery().setCompany_name(companyName);
		List<Integer> values = new ArrayList<>();
		values.add(mongoDao.queryCoditionCount(query,getParam(1000,3000)));
		values.add(mongoDao.queryCoditionCount(query,getParam(3000,6000)));
		values.add(mongoDao.queryCoditionCount(query,getParam(6000,8000)));
		values.add(mongoDao.queryCoditionCount(query,getParam(8000,10000)));
		values.add(mongoDao.queryCoditionCount(query,getParam(10000,15000)));
		values.add(mongoDao.queryCoditionCount(query,getParam(15000,20000)));
		values.add(mongoDao.queryCoditionCount(query,getParam(20000,9999999)));
		String res = JSON.toJSONString(values);
		saveCache("RecruitCache",res);
		return res;
	}

	private BasicDBObject getParam(double floor, double top) {
		BasicDBObject list = new BasicDBObject("$gte", floor);
		list.append("$lte", top);
		BasicDBObject param = new BasicDBObject("aver_salary",list);
		return param;
	}

	@Override
	public List<Distribution> queryRequireDistribution(String companyName) {
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<Recruit> query = new BaseQuery<>(Recruit.class);
			query.getQuery().setCompanyName(companyName);
			List<Recruit> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (Recruit recruit: list) {
				Integer val = dis.get(recruit.getRequire());
				if (val == null) {
					dis.put(recruit.getRequire(),1);
				} else {
					dis.put(recruit.getRequire(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
		return getDistribution("recruit","require");
	}

	@Override
	public List<Distribution> queryAddressDistribution(String companyName) {
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<Recruit> query = new BaseQuery<>(Recruit.class);
			query.getQuery().setCompany_name(companyName);
			List<Recruit> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (Recruit recruit: list) {
				Integer val = dis.get(recruit.getAddress());
				if (val == null) {
					dis.put(recruit.getAddress(),1);
				} else {
					dis.put(recruit.getAddress(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
		return getDistribution("recruit","address");
	}

}
