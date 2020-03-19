package org.kelab.enterprise.common;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.MGCache;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 19:45
 */
public class BaseServiceImpl<Q> implements BaseService<Q> {

	@Autowired
	protected BaseMongoDao mongoDao;

	protected void checkPage(BaseQuery<?> query) {
		if (query.getQuery() == null || query.getStart() == null) {
			query.setStart(1).setRows(10);
		}
	}
	@Override
	public List<Q> query(BaseQuery<Q> query) {
		checkPage(query);
		return mongoDao.queryByCondition(query, null);
	}

	@Override
	public int total(BaseQuery<Q> query) {
		return mongoDao.queryCoditionCount(query);
	}

	@Override
	public boolean update(Q record) {
		return mongoDao.updateByFeild(record);
	}

	@Override
	public boolean insert(Q record) {
		return mongoDao.insertOne(record);
	}

	@Override
	public boolean delete(Q record) {
		return mongoDao.delete(record,true);
	}

	@Override
	public Map<String, Integer> timeTotal(String collection, String companyName, String key) {
		MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
		FindIterable<Document> documents;
		if (StringUtil.notBlank(companyName)) {
			documents = mongo.getCollection(collection)
				.find(new BasicDBObject("companyName", companyName));
		} else {
			documents = mongo.getCollection(collection)
				.find();
		}
		MongoCursor<Document> iterator = documents.iterator();
		Map<String, Integer> distr = new TreeMap<>();//年份和数量分布
		Pattern p = Pattern.compile("[0-9]{4}");
		while (iterator.hasNext()) {
			Document document = iterator.next();
			String time = document.getString(key);
			if (time == null) {
				continue;
			}
			Matcher m = p.matcher(time);
			if (m.find()) {//提取出年份
				String year = m.group().substring(0, 4);
				Integer tmp = distr.get(year);
				if (tmp == null) {
					distr.put(year, 1);
				} else {
					distr.put(year, tmp + 1);
				}
			}
		}
		return distr;
	}

	@Override
	public String getCache(String key) {
		BaseQuery<MGCache> query = new BaseQuery<>(MGCache.class);
		query.getQuery().setKey(key);
		List<MGCache> caches = mongoDao.queryByCondition(query,null);
		if (caches == null || caches.size() == 0) {
			return null;
		} else {
			return caches.get(0).getValue();
		}
	}



	@Override
	public void saveCache(String key, String value) {
		MGCache record = new MGCache(key, null);
		mongoDao.delete(record,false);
		record.setValue(value);
		mongoDao.insertOne(record);
	}

	@Override
	public List<Distribution> getDistribution(String collection, String key) {
		MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
		List<Bson> list = new ArrayList<>();
		BasicDBObject _id = new BasicDBObject("_id", "$" + key);
		_id.append("value", new BasicDBObject("$sum", 1));
		BasicDBObject group = new BasicDBObject("$group", _id);
		list.add(group);
		BasicDBObject result = new BasicDBObject();
		result.append("_id", 0);
		result.append("name", "$_id");
		result.append("value", "$value");
		BasicDBObject project = new BasicDBObject("$project", result);
		list.add(project);
		AggregateIterable<Document> iterable = mongo.getCollection(collection).aggregate(list);
		MongoCursor<Document> set = iterable.iterator();
		List<Distribution> res = new ArrayList<>();
		while (set.hasNext()) {
			Document document = set.next();
			res.add(new Distribution(document.getString("name"), document.getInteger("value")));
		}
		return res;
	}

	public int saveStatic(BaseQuery<Q> query){
		Q rend = query.getQuery();
		MGColName mgColName = (MGColName) rend.getClass().getAnnotation(MGColName.class);
		String name = mgColName.value();
		MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
		MongoCollection<Document> collection = mongo.getCollection("statistics");
		Integer total = mongoDao.queryCoditionCount(query);
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		Integer oldVal =0;
		while(mongoCursor.hasNext()){
			Document document = mongoCursor.next();
			oldVal = (Integer)document.get(name);
			System.out.println(oldVal);
		}
		BasicDBObject basic = new BasicDBObject(name,oldVal);
		BasicDBObject update = new BasicDBObject("$set",new BasicDBObject(name,total));
		collection.updateOne(basic,update);
		return total;
	}

}
