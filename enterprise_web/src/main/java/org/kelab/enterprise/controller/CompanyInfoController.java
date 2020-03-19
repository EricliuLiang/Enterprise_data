package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.CompanyProfile;
import org.kelab.enterprise.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/7/31 18:43
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/company")
public class CompanyInfoController extends BaseController {

  @Autowired
  private CompanyService service;

  /**
   * 条件查询公司基本信息(单字段模糊查询)
   *
   * @param query
   * @param companyInfo
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/query.do", method = RequestMethod.GET)
  public ResultModel query(BaseQuery<CompanyProfile> query, CompanyProfile companyInfo) {
    List<CompanyProfile> result = service.queryCondition(query.setQuery(companyInfo));
    return ResultModel.builder()
      .data(result)
      .total(result == null ? 0 : result.size())
      .code(SUCCESS)
      .build();
  }

  @ResponseBody
  @RequestMapping(value = "/queryIndustry.do",method = {RequestMethod.GET})
  public ResultModel queryIndustryCount(BaseQuery<CompanyProfile> query,CompanyProfile companyProfile,String condition) {
    if(condition != null){
      companyProfile.setCompanyName(condition);
      companyProfile.setLegalPerson(condition);
    }
    query.setQuery(companyProfile);
    Map<String,Integer> result = service.queryIndustryDistribution(query);
    return ResultModel.builder()
            .data(result)
            .code(SUCCESS)
            .build();
  }

  @ResponseBody
  @RequestMapping(value = "/queryCity.do",method = {RequestMethod.GET})
  public ResultModel queryCityCount(BaseQuery<CompanyProfile> query,CompanyProfile companyProfile,String condition){
    if(condition != null){
      companyProfile.setCompanyName(condition);
      companyProfile.setLegalPerson(condition);
    }
    query.setQuery(companyProfile);
    Map<String,Integer> result = service.queryCityDistribution(query);
    return ResultModel.builder()
            .data(result)
            .code(SUCCESS)
            .build();
  }

  /**
   * 条件查询公司信息条数(单字段模糊查询)
   *
   * @param query
   * @param companyInfo
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/total.do", method = RequestMethod.GET)
  public ResultModel total(BaseQuery<CompanyProfile> query, CompanyProfile companyInfo) {
    return ResultModel.builder()
      .data(service.totalProfile(query.setQuery(companyInfo)))
      .total(1)
      .code(SUCCESS)
      .build();
  }

  /**
   * 通过所有字段模糊查询公司信息（多字段模糊查询）
   *
   * @param condition
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/conditions.do", method = RequestMethod.GET)
  public ResultModel AllCondition(BaseQuery<CompanyProfile> query, CompanyProfile companyInfo, String condition) {
    List<CompanyProfile> result = service.queryAllCondition(query.setQuery(companyInfo), condition);
    return ResultModel.builder()
      .data(result)
      .total(result == null ? 0 : result.size())
      .code(SUCCESS)
      .build();
  }

  /**
   * 所有条件模糊查询条数（多字段模糊查询）
   *
   * @param condition
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/conditionsTotal.do", method = RequestMethod.GET)
  public ResultModel conditionTotal(BaseQuery<CompanyProfile> query, CompanyProfile companyInfo, String condition) {
    return ResultModel.builder()
      .data(service.conditionTotal(query.setQuery(companyInfo), condition))
      .code(SUCCESS)
      .build();
  }

  /**
   * 查出排行榜
   *
   * @param num
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/rank.do", method = RequestMethod.GET)
  public ResultModel ranklist(Integer num) {
    List<CompanyProfile> result = service.ranklist(num);
    return ResultModel.builder()
      .data(result)
      .code(SUCCESS)
      .total(result == null ? 0 : result.size())
      .build();
  }

  /**
   * 修改公司信息
   *
   * @param record
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/update.do", method = RequestMethod.PUT)
  public ResultModel update(@RequestBody CompanyProfile record) {
    return ResultModel.builder()
      .data(service.update(record))
      .code(SUCCESS)
      .build();
  }

  /**
   * 资本区间的数量与创新值的分布
   *
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/analysis.do", method = RequestMethod.GET)
  public ResultModel analysis() {
    return ResultModel.builder()
      .data(service.AnalysisTheDistribution())
      .code(SUCCESS)
      .build();
  }


  /**
   * 公司类型分布
   *
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/companyTypeDistribution.do", method = RequestMethod.GET)
  public ResultModel companyTypeDistribution() {
    return ResultModel.builder()
      .data(service.companyTypeDistribution())
      .code(SUCCESS)
      .build();
  }

  /**
   * 公司行业分布
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/industryDistribution.do", method = RequestMethod.GET)
  public ResultModel industryDistribution() {
    return ResultModel.builder()
      .data(service.industryDistribution())
      .code(SUCCESS)
      .build();
  }
}
