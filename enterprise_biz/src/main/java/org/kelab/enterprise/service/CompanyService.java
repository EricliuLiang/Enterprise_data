package org.kelab.enterprise.service;

import org.bson.Document;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.CompanyInfo;
import org.kelab.enterprise.entity.CompanyProfile;
import org.kelab.enterprise.model.Total_Score;

import java.util.List;
import java.util.Map;

/**
 * Create by Wzy
 * on 2018/7/31 18:46
 * 不短不长八字刚好
 */
public interface CompanyService {

  /**
   * 条件查询具体详细信息
   *
   * @param query
   * @return
   */
  List<CompanyProfile> queryCondition(BaseQuery<CompanyProfile> query);

  /**
   * 条件查询公司简单信息的条数
   *
   * @param query
   * @return
   */
  Integer totalProfile(BaseQuery<CompanyProfile> query);

  /**
   * 修改公司信息
   *
   * @param record
   * @return
   */
  boolean update(CompanyProfile record);

  /**
   * 查询前几个公司
   *
   * @param num
   * @return
   */
  List<CompanyProfile> ranklist(Integer num);

  /**
   * 通过所有条件查询公司信息
   *
   * @param condition
   * @return
   */
  List<CompanyProfile> queryAllCondition(BaseQuery<CompanyProfile> query, String condition);

  /**
   * 通过所有条件模糊查询公司信息条数
   *
   * @param condition
   * @return
   */
  Integer conditionTotal(BaseQuery<CompanyProfile> query, String condition);


  /**
   * 统计不同资本区间的数量分布和分数分布
   *
   * @return
   */
  Total_Score AnalysisTheDistribution();

  /**
   * 公司类型分组求和
   *
   * @return
   */
  List<Document> companyTypeDistribution();


  /**
   * 公司行业分组求和
   *
   * @return
   */
  List<Document> industryDistribution();


  Map<String,Integer> queryIndustryDistribution(BaseQuery<CompanyProfile>query);

  Map<String,Integer> queryCityDistribution(BaseQuery<CompanyProfile> query);
}
