package org.kelab.enterprise.dao;

import com.mongodb.MongoClient;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.kelab.enterprise.entity.Trade;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class TradeTest extends BaseDaoTest {

	@Autowired
	private BaseMongoDao dao;

	@Test
	public void test() throws Exception {
		Field client = BaseMongoDao.class.getDeclaredField("mongoClient");
		client.setAccessible(true);
		MongoClient mongo_client = (MongoClient) client.get(dao);

		Field mongo = BaseMongoDao.class.getDeclaredField("mongo");
		mongo.setAccessible(true);
		Field modifiers = mongo.getClass().getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.setInt(mongo, mongo.getModifiers() & ~Modifier.FINAL);//fianl标志位置0
		mongo.set(dao, mongo_client.getDatabase("businfo"));


		BaseQuery<Trade> query = new BaseQuery<>(Trade.class);
		List<Trade> trades = dao.queryByCondition(query,null);
		for (Trade trade:trades) {
			trade.setCompanyName(trade.getAgent_name());
		}
		mongo.set(dao, mongo_client.getDatabase("qcj_companyInfo"));
		dao.insertList(trades);
		System.out.println(trades.size());
	}
}
