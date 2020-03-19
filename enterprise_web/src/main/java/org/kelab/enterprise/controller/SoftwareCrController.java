package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.SoftwareCr;
import org.kelab.enterprise.service.SoftwareCrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static org.cn.wzy.model.ResultModel.SUCCESS;

@Controller
@RequestMapping("/software")
public class SoftwareCrController extends BaseController {
	
	@Autowired
	private SoftwareCrService service;

	@ResponseBody
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public ResultModel query(BaseQuery<SoftwareCr> query, SoftwareCr record) {
		List<SoftwareCr> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(BaseQuery<SoftwareCr> query, SoftwareCr record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel total(@RequestBody SoftwareCr record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(SoftwareCr record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody SoftwareCr record) {
		return ResultModel.builder()
			.data(service.insert(record))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 软件著作权时间分布
	 * @param companyName
	 * @param cache
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/timeDistribution.do", method = RequestMethod.GET)
	public ResultModel queryTimeDistribution(String companyName, boolean cache) {
		return ResultModel.builder()
			.data(service.queryTimeDistribution(companyName,cache))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 查找著作权关键字关联的公司有哪些并统计著作权的数量
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCompany.do",method = {RequestMethod.GET})
	public ResultModel searchCompany(BaseQuery<SoftwareCr> query,String keyword){
		Map<String,Long>result=service.searchCompanyByKeyword(query,keyword);
		int total=result.get("total").intValue();
		result.remove("total");
		return ResultModel.builder()
				.data(result)
				.total(total)
				.code(SUCCESS)
				.build();
	}

	/**
	 *  统计著作权关键字所关联的著作权类型以及数量
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countType.do",method = {RequestMethod.GET})
	public ResultModel countType(String keyword){
		Map<String,Long>result=service.countCopyrightType(keyword);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}

	/**
	 * 根据关键字查找对应的软件著作权
	 * @param query
	 * @param record
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryByKeyword",method = {RequestMethod.GET})
	public ResultModel queryByKeyword(BaseQuery<SoftwareCr> query,SoftwareCr record,String keyword){
		query.setQuery(record);
		List<SoftwareCr> result= service.queryByKeyword(query,keyword);
		int total=service.queryConditionCount(query,keyword);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.total(total)
				.build();
	}
}
