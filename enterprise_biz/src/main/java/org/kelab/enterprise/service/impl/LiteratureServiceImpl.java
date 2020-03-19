package org.kelab.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.MapUtil;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.Literature;
import org.kelab.enterprise.entity.News;
import org.kelab.enterprise.entity.Patent;
import org.kelab.enterprise.service.LiteratureService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 19:37
 */
@Service
public class LiteratureServiceImpl extends BaseServiceImpl<Literature> implements LiteratureService {

	@Autowired
	private BaseMongoDao dao;

	@Override
	public List<Literature> query(BaseQuery<Literature> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query, new BasicDBObject("public_time", -1));
	}

	@Override
	public String queryDistribution(String companyName, boolean cache) {
		String res;
		if (!StringUtil.notBlank(companyName) && cache) {
			res = getCache("LiteratureTimeDistribution");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		res = JSON.toJSONString(timeTotal("company_literature",companyName,"public_time"));
		if (!StringUtil.notBlank(companyName)) {
			saveCache("LiteratureTimeDistribution", res);
		}
		return res;
	}

	@Override
	public List<Distribution> queryTypeDistribution(String companyName) {
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<Literature> query = new BaseQuery<>(Literature.class);
			query.getQuery().setCompanyName(companyName);
			List<Literature> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (Literature literature: list) {
				Integer val = dis.get(literature.getClass_number());
				if (val == null) {
					dis.put(literature.getClass_number(),1);
				} else {
					dis.put(literature.getClass_number(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
		return getDistribution("company_literature","class_number");
	}


   /*
   * 论文来源分布
   *
   * */
	public List<Distribution> querySourceDistribution(String companyName){
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<Literature> query = new BaseQuery<>(Literature.class);
			query.getQuery().setCompanyName(companyName);
			List<Literature> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (Literature literature: list) {
				Integer val = dis.get(literature.getSource());
				if (val == null) {
					dis.put(literature.getSource(),1);
				} else {
					dis.put(literature.getSource(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
     return getDistribution("company_literature","source");
	}

	/*
	* 判断论文属于哪个方面
	*
	* */
	public List<Distribution> queryCategoryDistribution(String companyName){
		if (StringUtil.notBlank(companyName)) {
			BaseQuery<Literature> query = new BaseQuery<>(Literature.class);
			query.getQuery().setCompanyName(companyName);
			List<Literature> list = mongoDao.queryByCondition(query,null);
			Map<String, Integer> dis = new HashMap<>();
			for (Literature literature: list) {
				Integer val = dis.get(literature.getCLC());
				if (val == null) {
					dis.put(literature.getCLC(),1);
				} else {
					dis.put(literature.getCLC(),val + 1);
				}
			}
			List<Distribution> res = new ArrayList<>(dis.size());
			for (String key: dis.keySet()) {
				res.add(new Distribution(key, dis.get(key)));
			}
			return res;
		}
		return getDistribution("company_literature","CLC");
	}

	@Override
	public Map<String, Integer> queryAndCount(BaseQuery<Literature> query, String keyword) {
		if(query.getStart()==null||query.getRows()==null){
			query.setStart(1);
			query.setRows(10);
		}
		Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("word",pattern);
		BasicDBObject sort=new BasicDBObject();
		sort.put("frequency",-1);
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		MongoCollection collection=mongo.getCollection("literature_keywords_by_company");
		FindIterable<Document>documents=collection.find(cond).sort(sort);
		MongoCursor<Document>iterators=documents.iterator();
		Map<String,Integer>res=new HashMap<>();
		int index=0;
		int rows=query.getRows();
		int skipNum=(query.getStart()-1)*rows;
		while (iterators.hasNext()){
			Document document=iterators.next();
			index++;

			if(index<skipNum)continue;

			if(index>=skipNum+rows)break;

			String companyName=document.getString("companyName");
			Integer frequency=document.getInteger("frequency");
			if(res.containsKey(companyName)){
				res.put(companyName,frequency+res.get(companyName));
				index--;
			}else{
				res.put(companyName,frequency);
			}
		}
		int total = (int) collection.countDocuments(cond);
		res.put("total",total);
		return res;
	}

	@Override
	public Map<String, Integer> countLiteratureType(String keyword) {
		Map<String,Integer>result=new HashMap<>();
		if(StringUtil.notBlank(keyword)){
			Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
			BasicDBObject cond1=new BasicDBObject();
			cond1.append("document_name",pattern);
			BasicDBObject cond2=new BasicDBObject();
			cond2.append("summary",pattern);
			BasicDBList condList=new BasicDBList();
			//对document_name 或者 summary进行模糊查询
			condList.add(cond1);
			condList.add(cond2);
			BasicDBObject cond=new BasicDBObject();
			cond.put("$or",condList);
			MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
			MongoCollection collection=mongo.getCollection("company_literature");
			FindIterable<Document> documents = collection.find(cond);
			MongoCursor<Document>iterator=documents.iterator();
			String otherType="其它";
			while (iterator.hasNext()){
				Document document=iterator.next();
				String key=document.getString("CLC");
				if(key==null){
					Integer value = result.get(otherType);
					if(value!=null){
						result.put(otherType,value+1);
					}else{
						result.put(otherType,1);
					}
				}else{
					if(result.containsKey(key)){
						Integer value=result.get(key);
						result.put(key,value+1);
					}else{
						result.put(key,1);
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<Literature> queryByKeyword(BaseQuery<Literature> query, String keyword) {
		if(query.getRows()==null||query.getStart()==null){
			query.setRows(10);
			query.setStart(1);
		}
		Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		BasicDBList condList = new BasicDBList();
		condList.add(new BasicDBObject("document_name",pattern));
		condList.add(new BasicDBObject("keyword",pattern));
		cond.put("$or",condList);
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		MongoCollection collection=mongo.getCollection("literature");
		FindIterable<Document> findIterable = collection.find(cond).skip(query.getStart()).limit(query.getRows());
		MongoCursor<Document> cursor = findIterable.iterator();
		ArrayList result = new ArrayList();
		while (cursor.hasNext()){
			Document document = (Document)cursor.next();
			result.add(MapUtil.castToEntity(document, Literature.class));
		}
		return result;
	}

	@Override
	public Integer queryConditionCount(BaseQuery<Literature> query, String keyword) {
		Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("document_name",pattern);
		Integer total=mongoDao.queryCoditionCount(query,cond);
		return total;
	}

}
