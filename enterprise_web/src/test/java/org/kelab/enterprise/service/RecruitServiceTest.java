package org.kelab.enterprise.service;

import org.junit.Test;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RecruitServiceTest extends BaseDaoTest {

	@Autowired
	private RecruitService service;

	@Test
	public void test() {
		System.out.println(service.queryAddressDistribution(null));
	}
}
