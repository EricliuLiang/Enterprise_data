package org.kelab.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.SoftwareCr;
import org.kelab.enterprise.service.SoftwareCrService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class SoftwareCrServiceImpl extends BaseServiceImpl<SoftwareCr> implements SoftwareCrService {

	@Override
	public List<SoftwareCr> query(BaseQuery<SoftwareCr> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query,new BasicDBObject("releaseDate",1));
	}

	@Override
	public String queryTimeDistribution(String companyName, boolean cache) {
		String res;
		if (!StringUtil.notBlank(companyName) && cache) {
			res = getCache("softwareCache");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		res = JSON.toJSONString(timeTotal("software_copyright",companyName,"releaseDate"));
		if (!StringUtil.notBlank(companyName)) {
			saveCache("softwareCache", res);
		}
		return res;
	}

	@Override
	public Map<String, Long> searchCompanyByKeyword(BaseQuery<SoftwareCr>query,String keyword) {
		Map<String,Long>result=new HashMap<>();
		if (StringUtil.notBlank(keyword)) {
			if(query.getStart()==null||query.getRows()==null){
				query.setStart(1);
				query.setRows(10);
			}
			MongoDatabase mongo= MongoUtils.getMongo(mongoDao);
			MongoCollection collection=mongo.getCollection("software_copyright");
			MongoCollection work_collection=mongo.getCollection("work_copyright");

			Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
			BasicDBObject cond=new BasicDBObject();
			cond.append("softwareName",pattern);
			FindIterable<Document>documents=collection.find(cond);
			FindIterable<Document>selectDocument=documents;
			MongoCursor<Document>iterator=documents.iterator();
			//获取要查询的行数
			Integer rows=query.getRows();
			Integer skipNum=(query.getStart()-1)*rows;
			int index=0;
			while (iterator.hasNext()){
				Document document=iterator.next();
				index++;
				//是否超过忽略的数量
				if(index<skipNum) continue;
				//是否已经查找了足够的数量
				if(index>=skipNum+rows)break;

				String companyName=document.getString("companyName");
				if(result.containsKey(companyName)){
					//如果result里面已经包含该公司的数据，index序列号回退，否者得不到rows条数据
					index--;
					continue;
				}
				BasicDBObject cond1=new BasicDBObject();
				cond1.append("companyName",companyName);
				cond1.append("softwareName",pattern);
				long count1=collection.countDocuments(cond1);

				BasicDBObject cond2=new BasicDBObject();
				cond2.append("companyName",companyName);
				cond2.append("workName",pattern);
				long count2=work_collection.countDocuments(cond2);

				result.put(companyName,count1+count2);
			}
			BasicDBList list=new BasicDBList();
			Long total=collection.countDocuments(cond);
			result.put("total",total);
		}
		return result;
	}

	@Override
	public Map<String,Long> countCopyrightType(String keyword){
		Map<String,Long> result = new HashMap<>();
		if(StringUtil.notBlank(keyword)){
			MongoDatabase mongo= MongoUtils.getMongo(mongoDao);
			MongoCollection software_collection=mongo.getCollection("software_copyright");
			MongoCollection work_collection=mongo.getCollection("work_copyright");
			Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
			//统计软件著作权
			BasicDBObject cond1=new BasicDBObject();
			cond1.append("softwareName",pattern);
			long count1=software_collection.countDocuments(cond1);
			//统计作品著作权
			BasicDBObject cond2=new BasicDBObject();
			cond2.append("workName",pattern);
			long count2=work_collection.countDocuments(cond2);
			result.put("软件著作权",count1);
			result.put("作品著作权",count2);
		}
		return result;
	}

	@Override
	public List<SoftwareCr> queryByKeyword(BaseQuery<SoftwareCr> query, String keyword) {
		if(query.getRows()==null||query.getStart()==null){
			query.setRows(10);
			query.setStart(1);
		}
		Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("softwareName",pattern);
		List<SoftwareCr> list = mongoDao.queryByCondition(query, new BasicDBObject(), cond);
		return list;
	}

	@Override
	public Integer queryConditionCount(BaseQuery<SoftwareCr> query, String keyword) {
		Pattern pattern = Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("softwareName",pattern);
		Integer total=mongoDao.queryCoditionCount(query,cond);
		return total;
	}
}
