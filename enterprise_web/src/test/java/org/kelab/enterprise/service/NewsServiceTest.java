package org.kelab.enterprise.service;

import org.junit.Test;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

public class NewsServiceTest extends BaseDaoTest {

	@Autowired
	private NewsService service;

	@Test
	public void test() {
		System.out.println(service.querySourceDistribution("东方电气集团东方汽轮机有限公司"));
	}
}
