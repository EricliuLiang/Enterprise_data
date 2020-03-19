package org.kelab.enterprise.dao;

import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.kelab.enterprise.entity.News;
import org.kelab.enterprise.entity.Recruit;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NewsDaoTest extends BaseDaoTest {

	@Autowired
	private BaseMongoDao dao;

	@Test
	public void test() {
		News record = new News();
		BaseQuery<News> query = new BaseQuery<>(null, null, record);
		List<News> res = dao.queryByCondition(query,null);
		for (News news: res) {
			String time = news.getPublish_time();
			if (!time.startsWith("20")) {
				System.out.println(news.getPublish_time());
				if (!time.equals("NULL")) {
					String right = time.substring(time.indexOf("20"));
					System.out.println(right);
					news.setPublish_time(right);
					dao.updateByFeild(news);
				}
			}
		}
	}
}
