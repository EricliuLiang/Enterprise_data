package org.kelab.enterprise.util;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BaseDaoTest {

    @Before
    public void before() {
        System.out.println("==========start===========");
    }

    @After
    public void after() {
        System.out.println("===========end===========");
    }
}
