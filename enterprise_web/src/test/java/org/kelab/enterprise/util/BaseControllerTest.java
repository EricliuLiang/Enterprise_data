package org.kelab.enterprise.util;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Wzy
 * on 2018/5/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseControllerTest {
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext control;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(control).build();
        System.out.println("==========start=========");
    }

    @After
    public void after() {
        System.out.println("===========end==========");
    }
}
