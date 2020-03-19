package org.kelab.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.bson.Document;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Patent;
import org.kelab.enterprise.service.PatentService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import javax.print.Doc;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:05
 */
@Service
public class PatentServiceImpl extends BaseServiceImpl<Patent> implements PatentService {

	private static Map<String, String> type;


	//初始化各个类型
	static {
		type = new HashMap<>();
		type.put("1", "发明专利");
		type.put("2", "实用新型专利");
		type.put("3", "外观设计专利");
		type.put("8", "进入中国国家阶段的PCT发明专利");
		type.put("9", "进入中国国家阶段的PCT实用新型专利");
		type.put("A", "人类生活必需（农、轻、医）");
		type.put("B", "作业、运输");
		type.put("C", "化学、冶金");
		type.put("D", "纺织、造纸");
		type.put("E", "固定建筑物（建筑、采矿）");
		type.put("F", "机械工程");
		type.put("G", "物理");
		type.put("H", "电学");
	}

	@Override
	public List<Patent> query(BaseQuery<Patent> query) {
		checkPage(query);
		return this.mongoDao.queryByCondition(query, new BasicDBObject("crawl_time", 1));
	}

	@Override
	public String queryDistribution(String companyName, boolean cache) {
		String res;
		if (!StringUtil.notBlank(companyName) && cache) {
			res = getCache("PatentTimeDistribution");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		res = JSON.toJSONString(timeTotal("patent",companyName,"public_time"));
		if (!StringUtil.notBlank(companyName)) {
			saveCache("PatentTimeDistribution", res);
		}
		return res;
	}

	@Override
	public String queryTypeDistribution(String companyName, boolean cache) {
		String res;
		if (!StringUtil.notBlank(companyName) && cache) {
			res = getCache("PatentTypeDistribution");
			if (StringUtil.notBlank(res)) {
				return res;
			}
		}
		MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
		FindIterable<Document> documents;
		if (StringUtil.notBlank(companyName)) {
			documents = mongo.getCollection("patent")
				.find(new BasicDBObject("companyName", companyName));
		} else {
			documents = mongo.getCollection("patent")
				.find();
		}
		MongoCursor<Document> iterator = documents.iterator();
		Map<String, Integer> bigType = new TreeMap<>();
		Map<String, Integer> contentType = new TreeMap<>();
		while (iterator.hasNext()) {
			Document document = iterator.next();
			String appli_num =document.getString("appli_num");
			if (appli_num != null) {
				String key;
				if (appli_num.length() == 16) {
					key = type.get("" + appli_num.charAt(6));
				} else {
					key = type.get("" + appli_num.charAt(4));
				}
				if (key != null) {
					addMapValue(bigType, key);
				}
			}
			String mainClassify = document.getString("mainClassify");
			if (mainClassify != null && mainClassify.length() > 3) {
				String key = type.get("" + mainClassify.charAt(0));
				if (key != null) {
					addMapValue(contentType, key);
				}
			}
		}
		res = JSON.toJSONString(Arrays.asList(bigType,contentType));
		if (!StringUtil.notBlank(companyName)) {
			saveCache("PatentTypeDistribution",res);
		}
		return res;
	}

	@Override
	public Map<String,Integer> queryCompanyFrequency(BaseQuery<Patent> query,String keyword) {
		if(query==null){
			return null;
		}
		if(query.getStart()==null||query.getRows()==null){
			query.setStart(1);
			query.setRows(10);
		}
		Pattern pattern = Pattern.compile("^.*"+filter(keyword)+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("word",pattern);
		BasicDBObject sort=new BasicDBObject();
		sort.put("frequency",-1);
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		MongoCollection collection=mongo.getCollection("patent_keywords_by_company");
		FindIterable<Document>documents=collection.find(cond)
				.sort(sort)
				.skip((query.getStart()-1)*query.getRows())
				.limit(query.getRows());
		MongoCursor<Document>iterators=documents.iterator();
		Map<String,Integer>res=new HashMap<>();
		while (iterators.hasNext()){
			Document document=iterators.next();
			res.put(document.getString("companyName"),document.get("frequency",Integer.class));
		}
		int total=(int)collection.countDocuments(cond);
		res.put("total",total);
		return res;
	}

	@Override
	public Map<String, Long> countPatentType(String keyword) {
		BasicDBObject cond=new BasicDBObject();
		cond.put("word",keyword);
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		MongoCollection collection=mongo.getCollection("patent_keywords");
		FindIterable<Document>patentKeywords=collection.find(cond);
		MongoCursor<Document>iterator=patentKeywords.iterator();
		Map<String,Long>result=new HashMap<>();
		if (iterator.hasNext()){
			Document patentKeyword=iterator.next();
			if (patentKeyword==null){
				return null;
			}
			// 获取该关键字的出现频率
			Integer frequency=patentKeyword.get("frequency",Integer.class);
			if(frequency==null){
				return null;
			}
			result=this.queryByFrequency(frequency,keyword);
		}
		return result;
	}

	@Override
	public List<Patent> queryByKeyword(BaseQuery<Patent> query, String keyword) {
		if(query.getRows()==null||query.getStart()==null){
			query.setRows(10);
			query.setStart(1);
		}
		Pattern pattern = Pattern.compile("^.*"+filter(keyword)+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("keywords",pattern);
		List<Patent>list=mongoDao.queryByCondition(query,new BasicDBObject(),cond);
		return list;
	}

	@Override
	public Integer queryConditionCount(BaseQuery<Patent> query, String keyword) {
		Pattern pattern = Pattern.compile("^.*"+filter(keyword)+".*$",Pattern.CASE_INSENSITIVE);
		BasicDBObject cond=new BasicDBObject();
		cond.put("keywords",pattern);
		Integer total=mongoDao.queryCoditionCount(query,cond);
		return total;
	}

	private Map<String,Long> queryByFrequency(Integer frequency, String keyword){
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		Map<String,Long>res=new HashMap<>();
		// 如果频率大于两百，可以直接从表patent_keywords_by_type中进行查询
		if(frequency>200){
			MongoCollection collection=mongo.getCollection("patent_keywords_by_type");
			BasicDBObject cond=new BasicDBObject();
			cond.put("word",keyword);
			FindIterable<Document>documents=collection.find(cond);
			MongoCursor<Document>iterator=documents.iterator();
			if (iterator.hasNext()){
				Document document=iterator.next();
				for(Map.Entry<String,String> entry:type.entrySet()){
					String key=entry.getValue();
					Long value=document.get(key,Long.class);
					if(value!=null){
						res.put(key,value);
					}
				}
			}
		}else{
			//频率小于两百可以直接进行统计
			MongoCollection collection=mongo.getCollection("patent");
			Pattern pattern = Pattern.compile("^.*"+filter(keyword)+".*$",Pattern.CASE_INSENSITIVE);
			BasicDBObject cond=new BasicDBObject();
			cond.put("keywords",pattern);
			FindIterable<Document>documents=collection.find(cond);
			MongoCursor<Document>iterator=documents.iterator();
			//遍历查到的专利进行统计
			while (iterator.hasNext()){
				Document document = iterator.next();
				String appli_num =document.getString("appli_num");
				if (appli_num != null) {
					int index=appli_num.length()==16 ? 6 : 4;
					String key = type.get(""+appli_num.charAt(index));
					if(key!=null){
						long value = res.containsKey(key) ? res.get(key)+1 : 1;
						res.put(key,value);
					}
				}

			}
		}
		return res;
	}

	private void addMapValue(Map<String, Integer> map, String key) {
		Integer val = map.get(key);
		if (val == null) {
			map.put(key, 1);
		} else {
			map.put(key, val + 1);
		}
	}
	private String filter(String str) {
		if (StringUtil.notBlank(str)) {
			String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
			for (String key : fbsArr) {
				if (str.contains(key)) {
					str = str.replace(key, "\\" + key);
				}
			}
		}
		return str;
	}

	/**
	 * 统计出现频率大于200的专利关键字所关联的专利类型。将结果存入数据库(patent_keywords_by_type)中
	 * 目的是便于后期的查找，提高查询效率。如果数据没有发生改动，请勿随意调用这个函数。
	 */
	public void countHighFrequencyKeyword(){
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		MongoCollection collection=mongo.getCollection("patent_keywords");
		//查找频率大于200次的关键词
		BasicDBObject cond1=new BasicDBObject();
		cond1.put("frequency",new BasicDBObject("$gt",200));
		FindIterable<Document>patentKeywords=collection.find(cond1);
		System.out.println("频率大于200的关键字查询完毕，开始统计类型。时间可能会有点长，请耐心等待...");
		//依次遍历这些关键字，查询他们所对应的专利，并统计类型
		MongoCursor<Document>iterator=patentKeywords.iterator();
		int i=1;
		while (iterator.hasNext()){
			System.out.println("正在统计第"+i+"个关键字所对应的专利的类型...");
			Document patentKeyword = iterator.next();
			String word=patentKeyword.getString("word");
			//将关键字转化为完全匹配模式进行模糊查询
			Pattern pattern = Pattern.compile("^.*"+filter(word)+".*$",Pattern.CASE_INSENSITIVE);
			BasicDBObject cond=new BasicDBObject();
			cond.put("keywords",pattern);
			MongoCollection collection1=mongo.getCollection("patent");
			//模糊查询该关键字所对应的的专利
			FindIterable<Document>patents=collection1.find(cond);
			MongoCursor<Document>patentIterator=patents.iterator();
			//统计专利中都有哪些类型 并返回结果
			Document patentKeyType=this.countType(patentIterator);
			patentKeyType.append("word",word);
			this.refresh(patentKeyType);
			System.out.println("第"+i+"个关键字统计完毕...");
			i++;
		}
		System.out.println("统计结束");
	}

	/**
	 * 遍历专利列表，根据申请号统计这些专利的类型，具体转换规则如下：
	 * 专利申请号（如果有CN前缀，则除去CN），1-4位申请为年份，第5位数字代表专利类型：
	 *  1--发明专利
	 *  2--实用新型专利
	 *  3--外观设计专利
	 *  8--进入中国国家阶段的PCT发明专利
	 *  9--进入中国国家阶段的PCT实用新型专利
	 * @param iterator
	 * @return
	 */
	private Document countType(MongoCursor<Document>iterator){
		Map<String,Object>res=new HashMap<>();
		while (iterator.hasNext()){
			Document patent=iterator.next();
			String appli_num =patent.getString("appli_num");
			if (appli_num != null) {
				int index=appli_num.length()==16 ? 6 : 4;
				String key = type.get(""+appli_num.charAt(index));
				if(key!=null){
					long value = res.containsKey(key) ? (long)res.get(key)+1 : 1;
					res.put(key,value);
				}
			}
		}
		Document document=new Document(res);
		return document;
	}

	/**
	 * 更新 patent_keywords_by_type 表
	 * 如果没有就直接添加，否则替换原有内容
	 * @param document
	 */
	private void refresh(Document document){
		MongoDatabase mongo=MongoUtils.getMongo(mongoDao);
		MongoCollection collection=mongo.getCollection("patent_keywords_by_type");
		BasicDBObject cond=new BasicDBObject();
		cond.put("word",document.getString("word"));
		long count=collection.countDocuments(cond);
		if(count>0){
			collection.replaceOne(cond,document);
		}else{
			collection.insertOne(document);
		}
	}
}
