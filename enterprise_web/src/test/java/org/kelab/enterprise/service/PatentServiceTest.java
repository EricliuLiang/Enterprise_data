package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.kelab.enterprise.entity.Patent;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;


public class PatentServiceTest extends BaseDaoTest {

	@Autowired
	private PatentService service;

	@Test
	public void test() {
		String map = service.queryDistribution(null, false);
		System.out.println(map);
	}

	@Test
	public void queryTypeDistribution() {
		String res = service.queryTypeDistribution(null, false);
		System.out.println(res);
	}

	@Test
	public void queryByKeyAndCount(){
		BaseQuery<Patent> query=new BaseQuery();
		Patent patent=new Patent();
		query.setQuery(patent);
		query.setStart(1);
		query.setRows(10);
		System.out.println(service.queryByKeyword(query,"一种"));
	}

	@Test
	public void countType(){
//		Map<String,Long> res = service.countPatentType("电力");
		BaseQuery query=new BaseQuery();
		service.countPatentType("一种");
	}
}
