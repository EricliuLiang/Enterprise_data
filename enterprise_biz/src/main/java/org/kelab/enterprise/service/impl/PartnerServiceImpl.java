package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Partner;
import org.kelab.enterprise.service.PartnerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:02
 */
@Service
public class PartnerServiceImpl extends BaseServiceImpl<Partner> implements PartnerService {
	@Override
	public List<Partner> query(BaseQuery<Partner> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query,new BasicDBObject("partner_name",-1));
	}
}
