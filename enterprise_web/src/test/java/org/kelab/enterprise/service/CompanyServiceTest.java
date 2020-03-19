package org.kelab.enterprise.service;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.kelab.enterprise.entity.CompanyInfo;
import org.kelab.enterprise.entity.CompanyProfile;
import org.kelab.enterprise.util.BaseDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by Wzy
 * on 2018/7/31 19:18
 * 不短不长八字刚好
 */
public class CompanyServiceTest extends BaseDaoTest {

  @Autowired
  private CompanyService service;


  @Autowired
  private SoftwareCrService softwareCrService;
  @Test
  public void test() {
    for (Document document: service.companyTypeDistribution()) {
      System.out.println(document);
    }

    for (Document document: service.industryDistribution()) {
      System.out.println(document);
    }
  }

  @Test
  public void test1() {
    System.out.println(softwareCrService.queryTimeDistribution("成都雅途生物技术有限公司", false));

  }

  @Test
  public void searchTest(){
    BaseQuery<CompanyProfile> query = new BaseQuery<>();
    CompanyProfile companyInfo  = new CompanyProfile();
    companyInfo.setLegalPerson("黄");
    query.setQuery(companyInfo);
    service.queryIndustryDistribution(query);
  }

  @Test
  public void test2(){
    BaseQuery<CompanyProfile> query = new BaseQuery<>();
    CompanyProfile companyInfo  = new CompanyProfile();
    companyInfo.setCity("成都市");
    query.setQuery(companyInfo);
    service.queryCityDistribution(query);
  }
}
