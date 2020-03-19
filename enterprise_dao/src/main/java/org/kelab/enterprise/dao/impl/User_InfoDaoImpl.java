package org.kelab.enterprise.dao.impl;

import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.kelab.enterprise.dao.User_InfoDao;
import org.kelab.enterprise.entity.User_Info;
import org.springframework.stereotype.Repository;
/**
 * Create by WzyGenerator
 * on Mon Aug 13 16:04:12 CST 2018
 * 不短不长八字刚好
 */

@Repository
public class User_InfoDaoImpl extends BaseDaoImpl<User_Info> implements User_InfoDao {
    @Override
    public String getNameSpace() {
        return "org.kelab.enterprise.dao.User_InfoMapper";
    }
}