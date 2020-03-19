package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Patent;
import org.kelab.enterprise.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:15
 */
@Controller
@RequestMapping("/patent")
public class PatentController extends BaseController {

	@Autowired
	private PatentService service;

	@ResponseBody
	@RequestMapping(value = "/query.do",method = RequestMethod.GET)
	public ResultModel query(BaseQuery<Patent> query, Patent record) {
		List<Patent> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do",method = RequestMethod.GET)
	public ResultModel total(BaseQuery<Patent> query, Patent record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do",method = RequestMethod.PUT)
	public ResultModel total(@RequestBody Patent record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(Patent record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

  @ResponseBody
  @RequestMapping(value = "/insert.do",method = RequestMethod.POST)
  public ResultModel insert(@RequestBody Patent record) {
    return ResultModel.builder()
      .data(service.insert(record))
      .code(SUCCESS)
      .build();
  }

	/**
	 * 查询专利年份分布
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDistribution.do",method = RequestMethod.GET)
	public ResultModel queryDistribution(String companyName, boolean cache) {
		return ResultModel.builder()
			.data(service.queryDistribution(companyName, cache))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 查询专利类型分类
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTypeDistribution.do",method = RequestMethod.GET)
	public ResultModel queryTypeDistribution(String companyName, boolean cache) {
		return ResultModel.builder()
			.data(service.queryTypeDistribution(companyName,cache))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 查询哪些公司拥有该关键字关联的专利
	 * @param query
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCompany.do",method = {RequestMethod.GET})
	public ResultModel queryCompany(BaseQuery<Patent> query,String keyword){
		Map<String,Integer> result=service.queryCompanyFrequency(query,keyword);
		int total=result.get("total");
		result.remove("total");
		return ResultModel.builder()
				.data(result)
				.total(total)
				.build();
	}

	/**
	 * 统计该关键字所关联的专利有哪些类型
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countType.do",method = {RequestMethod.GET})
	public ResultModel countPatentType(String keyword){
		Map<String,Long> result=this.service.countPatentType(keyword);
		return ResultModel.builder()
				.data(result)
				.total(result.size())
				.code(SUCCESS)
				.build();
	}

	/**
	 * 根据关键字查找对应的专利
	 * @param query
	 * @param record
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryByKeyword",method = {RequestMethod.GET})
	public ResultModel queryByKeyword(BaseQuery<Patent> query,Patent record,String keyword){
		query.setQuery(record);
		List<Patent> result=service.queryByKeyword(query,keyword);
		int total=service.queryConditionCount(query,keyword);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.total(total)
				.build();
	}
}
