package org.kelab.enterprise.util;

import org.cn.wzy.util.DaoAndEntityGenerator;
import org.cn.wzy.util.MapperingGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author wzy
 * @date 2018/8/13 15:59
 * @不短不长 八字刚好
 */
public class GeneratorUtil extends BaseDaoTest {

    @Autowired
    private MapperingGenerator mapperingGenerator;

    @Autowired
    private DaoAndEntityGenerator entityGenerator;

    @Test
    public void mapper() {
        mapperingGenerator.oldPath = "G:\\enterprise_data\\enterprise_dao\\src\\main\\resources\\mapper";
        mapperingGenerator.sql = mapperingGenerator.oldPath + "\\sql";
        mapperingGenerator.implPath = mapperingGenerator.oldPath + "\\impl";
        mapperingGenerator.run();
    }
    @Test
    public void dao() throws IOException {
        entityGenerator.entityPath = "G:\\enterprise_data\\bin\\mybatis\\src\\main\\java\\org\\kelab\\enterprise\\entity";
        entityGenerator.packagePath = "G:\\enterprise_data\\enterprise_dao\\src\\main\\java\\org\\kelab\\enterprise";
        DaoAndEntityGenerator.entityGenerator();
        DaoAndEntityGenerator.daoGenerator();
    }
}
