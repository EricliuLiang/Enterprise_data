package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.kelab.enterprise.entity.Literature;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

public class LiteratureServiceTest extends BaseDaoTest {

	@Autowired
	private LiteratureService service;


//	@Test
//	public void test() {
//		System.out.println(service.queryTypeDistribution("东方日立(成都)电控设备有限公司"));
//
//
//	}

	@Test
	public void countTest(){
		BaseQuery<Literature> query = new BaseQuery<>();
		query.setStart(1);
		query.setRows(10);
		query.setQuery(new Literature());
		System.out.println(service.queryByKeyword(query,"施工技术"));
	}
}
