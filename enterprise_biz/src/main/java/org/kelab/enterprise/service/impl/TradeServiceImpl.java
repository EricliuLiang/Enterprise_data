package org.kelab.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Trade;
import org.kelab.enterprise.service.TradeService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.apache.log4j.spi.Configurator.NULL;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:11
 */
@Service
public class TradeServiceImpl extends BaseServiceImpl<Trade> implements TradeService {
	@Override
	public List<Trade> query(BaseQuery<Trade> query) {
		return this.mongoDao.queryByCondition(query,new BasicDBObject("trade_name",-1));
	}

	@Override
	public String queryTypeDistribution(String companyName, boolean cache) {
		String res;
		if(!StringUtil.notBlank(companyName)&&cache){
			res=getCache("tradeTypeDistribution");
			if(StringUtil.notBlank(res)){
				return res;
			}
		}
		MongoDatabase mongo= MongoUtils.getMongo(mongoDao);
		FindIterable<Document> documents;
		if(StringUtil.notBlank(companyName)){
			documents=mongo.getCollection("trade")
					.find(new BasicDBObject("companyName",companyName));
		}else{
			documents=mongo.getCollection("trade").find();
		}
		MongoCursor<Document> iterator =documents.iterator();
		Map<String,Integer> tradeType=new TreeMap<String,Integer>();
		while(iterator.hasNext()) {
			Document document = iterator.next();
			String trade_type = document.getString("trade_type");
			if (trade_type != null&&!trade_type.equals("NULL")) {
				Integer val = tradeType.get(trade_type);
				if (val == null) {
					tradeType.put(trade_type, 1);
				} else {
					tradeType.put(trade_type, val + 1);
				}
			}
		}
		res = JSON.toJSONString(tradeType);
		if (!StringUtil.notBlank(companyName)) {
			saveCache("tradeTypeDistribution",res);
		}
		return res;
	}

	@Override
	public String queryCategoryDistribution(String companyName, boolean cache) {
		String res;
		if(!StringUtil.notBlank(companyName)&&cache) {
			res = getCache("tradeCategoryDistribution");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		MongoDatabase mongo= MongoUtils.getMongo(mongoDao);
		FindIterable<Document> documents;
		if(StringUtil.notBlank(companyName)){
			documents=mongo.getCollection("trade")
					.find(new BasicDBObject("companyName",companyName));
		}else{
			documents=mongo.getCollection("trade").find();
		}
		MongoCursor<Document> iterator =documents.iterator();
		Map<String,Integer> tradeCategory=new TreeMap<String,Integer>();
		while(iterator.hasNext()){
			Document document = iterator.next();
			String trade_category = document.getString("trade_category");
			if(trade_category!=null) {
				Integer val = tradeCategory.get(trade_category);
				if (val == null) {
					tradeCategory.put(trade_category, 1);
				} else {
					tradeCategory.put(trade_category, val + 1);
				}
			}
		}
		res = JSON.toJSONString(tradeCategory);
		if (!StringUtil.notBlank(companyName)) {
			saveCache("tradeCategoryDistribution",res);
		}
		return res;
	}

	@Override
	public String queryStatusDistribution(String companyName, boolean cache) {
		String res;
		if(!StringUtil.notBlank(companyName)&&cache) {
			res = getCache("tradeStatusDistribution");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		MongoDatabase mongo= MongoUtils.getMongo(mongoDao);
		FindIterable<Document> documents;
		if(StringUtil.notBlank(companyName)){
			documents=mongo.getCollection("trade")
					.find(new BasicDBObject("companyName",companyName));
		}else{
			documents=mongo.getCollection("trade").find();
		}
		MongoCursor<Document> iterator =documents.iterator();
		Map<String,Integer> tradeStatus=new TreeMap<String,Integer>();
		while(iterator.hasNext()){
			Document document = iterator.next();
			String trade_status = document.getString("status");
			if(trade_status!=null) {
				judgeStatus(tradeStatus,trade_status);
			}
		}
		res = JSON.toJSONString(tradeStatus);
		if (!StringUtil.notBlank(companyName)) {
			saveCache("tradeStatusDistribution",res);
		}
		return res;
	}

	void judgeStatus(Map tradeStatus,String trade_status) {
		String []status=new String[]{"商标已注册","商标续展","商标无效","商标变更完成","期满未续展","商标续展完成","商标注册申请","去国际注册中"};
		for(int i=0;i<status.length;i++){
			if(trade_status.indexOf(status[i])!=-1){
				Integer val=(Integer)tradeStatus.get(status[i]);
				if(val==null) tradeStatus.put(status[i],1);
				else tradeStatus.put(status[i],val+1);
			}
		}
	}
}
