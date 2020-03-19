package org.kelab.enterprise.dao;

import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.kelab.enterprise.entity.Recruit;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RecruitDaoTest extends BaseDaoTest {

	@Autowired
	private BaseMongoDao dao;

	@Test
	public void test() {
		Recruit record = new Recruit();
		BaseQuery<Recruit> query = new BaseQuery<>(null,null, record);
		List<Recruit> list = dao.queryByCondition(query,null);
		List<Recruit> res = new ArrayList<>(list.size());
		for (Recruit recruit: list) {
			if (recruit.getSalary() == null) {
				System.out.println("null");
				continue;
			}
			try{
				String[] salarys = recruit.getSalary().split("-");
				double aver = (Integer.parseInt(salarys[0]) + Integer.parseInt(salarys[1])) / 2;
				recruit.setAver_salary(aver);
			} catch (Exception e) {
				e.printStackTrace();
			}
			res.add(recruit);
		}
		dao.delete(record,false);
		dao.insertList(res);
	}
}
