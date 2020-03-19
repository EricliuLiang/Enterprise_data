package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.CompanyInfo;
import org.kelab.enterprise.entity.CompanyProfile;
import org.kelab.enterprise.model.Total_Score;
import org.kelab.enterprise.service.CompanyService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.min;
import static java.lang.Integer.numberOfLeadingZeros;
import static org.kelab.enterprise.util.MongoUtils.getMongo;

/**
 * Create by Wzy
 * on 2018/7/31 18:47
 * 不短不长八字刚好
 */
@Service
public class CompanyServiceImpl implements CompanyService {

  @Autowired
  private BaseMongoDao dao;

  private WeakReference<Total_Score> cahce;

  @Override
  public Integer totalProfile(BaseQuery<CompanyProfile> query) {

      return  dao.queryCoditionCount(query, addConditions(query.getQuery()));

  }

  @Override
  public List<Document> companyTypeDistribution() {
    return groupBy("companyType");
  }

  @Override
  public List<Document> industryDistribution() {
    return groupBy("industry");
  }

  private List<Document> groupBy(String fieldName) {
    List<Bson> list = new ArrayList<>();
    BasicDBObject _id = new BasicDBObject("_id", "$" + fieldName);
    _id.append("value", new BasicDBObject("$sum", 1));
    BasicDBObject group = new BasicDBObject("$group", _id);
    list.add(group);
    BasicDBObject result = new BasicDBObject();
    result.append("_id", 0);
    result.append("name", "$_id");
    result.append("value", "$value");
    BasicDBObject project = new BasicDBObject("$project", result);
    list.add(project);

    AggregateIterable<Document> iterable = getMongo(dao).getCollection("company_profile").aggregate(list);
    MongoCursor<Document> set = iterable.iterator();
    List<Document> res = new ArrayList<>();
    while (set.hasNext()) {
      Document document = set.next();
      if (!document.get("name").equals("-")) {
        res.add(document);
      }
    }
    Collections.sort(res, new Comparator<Document>() {
      @Override
      public int compare(Document o1, Document o2) {
        return (Integer) o2.get("value") - (Integer) o1.get("value");
      }
    });
    return res.subList(0, Math.min(15, res.size()));
  }

  @Override
  public Total_Score AnalysisTheDistribution() {
    if (cahce != null && cahce.get() != null) {
      return cahce.get();
    }
    List<Integer> nums = new ArrayList<>();
    List<Double> scores = new ArrayList<>();
    Map<String, Object> param = new HashMap<>();
    BaseQuery<CompanyProfile> query = new BaseQuery<>(CompanyProfile.class);
    query.getQuery().setFrom(0d).setEnd(100d);
    handlerCapital(param, query.getQuery());
    List<CompanyProfile> list = dao.queryByCondition(query, null, param);
    nums.add(list.size());
    scores.add(average(list));
    list.clear();
    query.getQuery().setFrom(100d).setEnd(500d);
    handlerCapital(param, query.getQuery());
    list = dao.queryByCondition(query, null, param);
    nums.add(list.size());
    scores.add(average(list));
    list.clear();
    query.getQuery().setFrom(500d).setEnd(1000d);
    handlerCapital(param, query.getQuery());
    list = dao.queryByCondition(query, null, param);
    nums.add(list.size());
    scores.add(average(list));
    list.clear();
    query.getQuery().setFrom(1000d).setEnd(5000d);
    handlerCapital(param, query.getQuery());
    list = dao.queryByCondition(query, null, param);
    nums.add(list.size());
    scores.add(average(list));
    list.clear();
    query.getQuery().setFrom(5000d).setEnd(999999999999d);
    handlerCapital(param, query.getQuery());
    list = dao.queryByCondition(query, null, param);
    nums.add(list.size());
    scores.add(average(list));
    list.clear();
    Total_Score total_score = new Total_Score(nums, scores);
    cahce = new WeakReference<>(total_score);
    return total_score;
  }

  @Override
  public List<CompanyProfile> queryCondition(BaseQuery<CompanyProfile> query) {
    checkPage(query);
    return dao.queryByCondition(query, new BasicDBObject("value", -1), addConditions(query.getQuery()));
  }

  @Override
  public Map<String,Integer> queryIndustryDistribution(BaseQuery<CompanyProfile>query){
    List<Document>list = this.industryDistribution();
    CompanyProfile companyProfile = query.getQuery();
    BasicDBObject cond = new BasicDBObject();
    Map<String,Integer> result = new HashMap<>();
    Map<String,Object> condMap = addConditions(companyProfile);
    cond.putAll(condMap);
    if(companyProfile.getCity() != null){
      cond.put("businessInfo.city",companyProfile.getCity());
    }
    if(companyProfile.getIndustry() != null){
      cond.put("businessInfo.industry",companyProfile.getIndustry());
      // TODO change the collection name
      int count = (int) getMongo(dao).getCollection("companyInfo_backup").countDocuments(cond);
      result.put(companyProfile.getIndustry(),count);
      return result;
    }
    for (Document document:list){
      String industryName = document.getString("name");
      cond.put("businessInfo.industry",industryName);
      System.out.println(cond);
      long count = getMongo(dao).getCollection("companyInfo_backup").countDocuments(cond);
      System.out.println(industryName+":"+count);
      result.put(industryName,(int)count);
    }
    return result;
  }

  @Override
  public Map<String, Integer> queryCityDistribution(BaseQuery<CompanyProfile> query) {
    List<Document> list =groupBy("city");
    CompanyProfile companyProfile = query.getQuery();
    BasicDBObject cond = new BasicDBObject();
    Map<String,Integer> result = new HashMap<>();
    Map<String,Object> condMap = addConditions(companyProfile);
    cond.putAll(condMap);
    if(companyProfile.getIndustry() != null){
      cond.put("businessInfo.industry",companyProfile.getIndustry());
    }
    if(companyProfile.getCity() != null){
      cond.put("businessInfo.city",companyProfile.getCity());
      int count = (int) getMongo(dao).getCollection("companyInfo_backup").countDocuments(cond);
      result.put(companyProfile.getCity(),count);
      return result;
    }
    for (Document document:list){
      String city = document.getString("name");
      cond.put("businessInfo.city",city);
      // TODO change the collection name
      long count = getMongo(dao).getCollection("companyInfo_backup").countDocuments(cond);
      System.out.println(city+":"+count);
      result.put(city,(int)count);
    }
    return result;
  }

  @Override
  public List<CompanyProfile> ranklist(Integer num) {
    num = num == null ? 5 : num;
    BaseQuery<CompanyProfile> query = new BaseQuery<>(CompanyProfile.class);
    query.setStart(1).setRows(num);
    List<CompanyProfile> reslut = dao.queryByCondition(query, new BasicDBObject("value", -1));
    return reslut;
  }

  @Override
  public Integer conditionTotal(BaseQuery<CompanyProfile> query, String condition) {
    return dao.queryCoditionCount(query, addConditions(query.getQuery(), condition));
  }

  @Override
  public List<CompanyProfile> queryAllCondition(BaseQuery<CompanyProfile> query, String condition) {
    checkPage(query);
    return dao.queryByCondition(query, new BasicDBObject("companyName", -1), addConditions(query.getQuery(), condition));
  }

  @Override
  public boolean update(CompanyProfile record) {
    return dao.updateByFeild(record);
  }

  private void handlerCapital(Map<String, Object> add, CompanyProfile record) {
    if (record.getEnd() != null && record.getFrom() != null) {
      BasicDBObject list = new BasicDBObject("$gte", record.getFrom());
      list.append("$lte", record.getEnd());
      add.put("registeredCapital", list);
      record.setFrom(null).setEnd(null);
      record.setRegisteredCapital(null);
    }
    if (StringUtil.notBlank(record.getEstablishDate())) {
      Pattern pattern = Pattern.compile("^" + filter(record.getEstablishDate()) + ".*$");
      add.put("establishDate", pattern);
      record.setEstablishDate(null);
    }
    if (StringUtil.notBlank(record.getCompanyType())) {
      Pattern pattern = Pattern.compile("^" + filter(record.getCompanyType()) + ".*$");
      add.put("companyType", pattern);
      record.setCompanyType(null);
    }
  }


  private Map<String, Object> addConditions(CompanyProfile record, String condition) {
    if (condition == null) {
      condition = "";
    }
    Map<String, Object> addtion = new HashMap<>();
    handlerCapital(addtion, record);
    BasicDBList list = new BasicDBList();
    Pattern pattern = Pattern.compile("^.*" + filter(condition) + ".*$");
    list.add(new BasicDBObject("companyName", pattern));
    list.add(new BasicDBObject("legalPerson", pattern));
    list.add(new BasicDBObject("address", pattern));
    addtion.put("$or", list);
    return addtion;
  }

  private Map<String, Object> addConditions(CompanyProfile record) {
    Map<String, Object> addtion = new HashMap<>();
    handlerCapital(addtion, record);
    BasicDBList list = new BasicDBList();
    if (StringUtil.notBlank(record.getCompanyName())) {
      Pattern pattern = Pattern.compile("^.*" + filter(record.getCompanyName()) + ".*$");
      list.add(new BasicDBObject("companyName", pattern));
    }
    if (StringUtil.notBlank(record.getLegalPerson())) {
      Pattern pattern = Pattern.compile("^.*" + filter(record.getLegalPerson()) + ".*$");
      list.add(new BasicDBObject("legalPerson", pattern));
    }
    if (StringUtil.notBlank(record.getAddress())) {
      Pattern pattern = Pattern.compile("^.*" + filter(record.getAddress()) + ".*$");
      list.add(new BasicDBObject("address", pattern));
    }
    if (list.size() > 0) {
      addtion.put("$or", list);
    }
    record.setCompanyName(null)
      .setLegalPerson(null)
      .setAddress(null);
    return addtion;
  }

  private void checkPage(BaseQuery<?> query) {
    if (query.getQuery() == null || query.getStart() == null) {
      query.setStart(1).setRows(10);
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

  private double average(List<CompanyProfile> list) {
    double total = 0;
    for (CompanyProfile companyProfile : list) {
      total += companyProfile.getValue();
    }
    if (total == 0) {
      return 0;
    }
    return total / list.size();
  }
}
