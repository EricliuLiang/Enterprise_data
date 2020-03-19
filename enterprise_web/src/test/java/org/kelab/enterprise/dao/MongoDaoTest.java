package org.kelab.enterprise.dao;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.MapUtil;
import org.junit.Test;
import org.kelab.enterprise.entity.*;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/31 13:06
 * 不短不长八字刚好
 */
public class MongoDaoTest extends BaseDaoTest {

  @Autowired
  private BaseMongoDao dao;

  @Test
  public void exportToProfile() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      List<CompanyProfile> records = new ArrayList<>(list.size());
      for (CompanyInfo companyInfo : list) {
        CompanyProfile record = new CompanyProfile();
        record.setCompanyName(companyInfo.getCompanyName());
        record.setValue(companyInfo.getValue());
        record.setCompanyProfile(companyInfo.getCompanyProfile());
        BeanUtils.copyProperties(companyInfo.getBusinessInfo(), record, "registeredCapital");
        String registeredCapital = companyInfo.getBusinessInfo().getRegisteredCapital();
        String num = registeredCapital.equals("-") ? "-1" : registeredCapital.substring(0, registeredCapital.indexOf("万"));
        Double capital = Double.parseDouble(num);
        record.setRegisteredCapital(capital);
        records.add(record);
        System.out.println(record);
      }
      dao.insertList(records);
    }
  }

  @Test
  public void exportToPartner() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      List<Partner> records = new ArrayList<>(list.size());
      for (CompanyInfo companyInfo : list) {
        List<Document> partners = companyInfo.getPartners();
        if (partners == null) {
          continue;
        } else {
          for (Document document : partners) {
            Partner record = MapUtil.castToEntity(document, Partner.class);
            records.add(record);
            record.setCompanyName(companyInfo.getCompanyName());
            System.out.println(record);
          }
        }
      }
      dao.insertList(records);
    }
  }

  @Test
  public void exportToNews() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    List<News> records = new ArrayList<>(50);
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      for (CompanyInfo companyInfo : list) {
        List<Document> partners = companyInfo.getNews();
        if (partners == null) {
          continue;
        } else {
          for (Document document : partners) {
            News news = MapUtil.castToEntity(document, News.class);
            news.setCompanyName(companyInfo.getCompanyName());
            records.add(news);
            System.out.println(news);
          }
        }
      }
      dao.insertList(records);
      records.clear();
    }
  }

  @Test
  public void exportToPatent() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    List<Patent> records = new ArrayList<>(50);
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      for (CompanyInfo companyInfo : list) {
        List<Document> partners = companyInfo.getPatent();
        if (partners == null) {
          continue;
        } else {
          for (Document document : partners) {
            Patent record = MapUtil.castToEntity(document, Patent.class);
            record.setCompanyName(companyInfo.getCompanyName());
            record.setSummary((String) document.get("abstract"));
            records.add(record);
            System.out.println(record);
          }
        }
      }
      dao.insertList(records);
      records.clear();
    }
  }

  @Test
  public void exportToLiterature() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    List<Literature> records = new ArrayList<>(50);
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      for (CompanyInfo companyInfo : list) {
        List<Document> partners = companyInfo.getLiterature();
        if (partners == null) {
          continue;
        } else {
          for (Document document : partners) {
            Literature record = MapUtil.castToEntity(document, Literature.class);
            record.setCompanyName(companyInfo.getCompanyName());
            record.setSummary((String) document.get("abstract"));
            records.add(record);
            System.out.println(record);
          }
        }
      }
      if (records.size() != 0) {
        dao.insertList(records);
      }
      records.clear();
    }
  }

  @Test
  public void exportToTrade() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    List<Trade> records = new ArrayList<>(50);
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      for (CompanyInfo companyInfo : list) {
        List<Document> trades = companyInfo.getTrade();
        if (trades == null || trades.size() == 0) {
          continue;
        } else {
          for (Document document : trades) {
            Trade record = MapUtil.castToEntity(document, Trade.class);
            record.setCompanyName(companyInfo.getCompanyName());
            records.add(record);
            System.out.println(record);
          }
        }
      }
      if (records.size() != 0) {
        dao.insertList(records);
      }
      records.clear();
    }
  }

  @Test
  public void exportToRecruit() {
    BaseQuery<CompanyInfo> query = new BaseQuery<>();
    query.setQuery(new CompanyInfo());
    List<Recruit> records = new ArrayList<>(50);
    for (int i = 1; i <= 36; i++) {
      query.setStart(i).setRows(100);
      List<CompanyInfo> list = dao.queryByCondition(query, new BasicDBObject("companyName", 1));
      System.out.println(list.size());
      for (CompanyInfo companyInfo : list) {
        List<Document> partners = companyInfo.getRecruit();
        if (partners == null || partners.size() == 0) {
          continue;
        } else {
          for (Document document : partners) {
            Recruit record = MapUtil.castToEntity(document, Recruit.class);
            record.setCompanyName(companyInfo.getCompanyName());
            records.add(record);
            System.out.println(record);
          }
        }
      }
      if (records.size() != 0) {
        dao.insertList(records);
      }
      records.clear();
    }
  }


}
