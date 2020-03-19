package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.Recruit;
import org.kelab.enterprise.entity.WorkCopyright;
import org.kelab.enterprise.service.WorkCopyrightService;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkCopyrightServiceImpl extends BaseServiceImpl<WorkCopyright> implements WorkCopyrightService {

	@Override
	public List<WorkCopyright> query(BaseQuery<WorkCopyright> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query, new BasicDBObject("companyName", 1));
	}

	@Override
	public List<Distribution> queryTypeDistribution(String companyName) {
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<WorkCopyright> query = new BaseQuery<>(WorkCopyright.class);
			query.getQuery().setCompanyName(companyName);
			List<WorkCopyright> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (WorkCopyright recruit: list) {
				Integer val = dis.get(recruit.getRegistrationCategory());
				if (val == null) {
					dis.put(recruit.getRegistrationCategory(),1);
				} else {
					dis.put(recruit.getRegistrationCategory(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
		return getDistribution("work_copyright","registrationCategory");
	}
}
