package org.kelab.enterprise.dao;

import org.junit.Test;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzy
 * @date 2018/8/13 16:12
 * @不短不长 八字刚好
 */
public class User_InfoDaoTest extends BaseDaoTest {

    @Autowired
    private User_InfoDao dao;

    @Test
    public void test() {
        System.out.println(dao.selectByPrimaryKey(1));
    }
}
