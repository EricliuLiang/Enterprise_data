package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.MainNumber;
import org.kelab.enterprise.service.MainNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author hw 不短不长八字刚好.
 * @since 2018/9/19 19:37
 */
@Service
public class MainNumberServiceImpl extends BaseServiceImpl<MainNumber> implements MainNumberService {

    @Override
    public List<MainNumber> query(BaseQuery<MainNumber> query) {
        checkPage(query);
        return this.mongoDao.queryByCondition(query,new BasicDBObject("companyName",1));
    }

}
