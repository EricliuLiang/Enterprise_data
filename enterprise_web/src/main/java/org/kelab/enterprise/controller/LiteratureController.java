package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Literature;
import org.kelab.enterprise.entity.Patent;
import org.kelab.enterprise.service.LiteratureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:20
 */
@Controller
@RequestMapping("/literature")
public class LiteratureController extends BaseController {
	@Autowired
	private LiteratureService service;

	@ResponseBody
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public ResultModel query(BaseQuery<Literature> query, Literature record) {
		List<Literature> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(BaseQuery<Literature> query, Literature record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel total(@RequestBody Literature record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(Literature record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody Literature record) {
		return ResultModel.builder()
			.data(service.insert(record))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 查询论文年份分布
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDistribution.do",method = RequestMethod.GET)
	public ResultModel queryDistribution(String companyName, boolean cache) {
		return ResultModel.builder()
			.data(service.queryDistribution(companyName,cache))
			.code(SUCCESS)
			.build();
	}


	/**
	 * 查询类型分布
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTypeDistribution.do",method = RequestMethod.GET)
	public ResultModel queryTypeDistribution(String companyName) {
		return ResultModel.builder()
			.data(service.queryTypeDistribution(companyName))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value="/querySourceDistribution.do",method = RequestMethod.GET)
	  public ResultModel querySourceDistribution(String companyName){
		return ResultModel.builder()
				.data(service.querySourceDistribution(companyName))
				.code(SUCCESS)
				.build();
	}

	@ResponseBody
	@RequestMapping(value="/queryCategoryDistribution.do",method = RequestMethod.GET)
	public ResultModel queryCategoryDistribution(String companyName){
		 return ResultModel.builder()
				.data(service.queryCategoryDistribution(companyName))
				.code(SUCCESS)
				.build();
	}


	/**
	 * 查找哪些公司拥有这些关键字的文献以及统计他们的数量
	 * @param query
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCompany.do",method = {RequestMethod.GET})
	public ResultModel searchCompany(BaseQuery<Literature> query,String keyword){
		Map<String,Integer>result=this.service.queryAndCount(query,keyword);
		int total=result.get("total");
		result.remove("total");
		return ResultModel.builder()
				.data(result)
				.total(total)
				.code(SUCCESS)
				.build();
	}

	/**
	 * 统计关键字所对应的文献的类型数量
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/countType.do",method = {RequestMethod.GET})
	public ResultModel countLiteratureType(String keyword){
		Map<String,Integer> result= this.service.countLiteratureType(keyword);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}


	/**
	 * 根据关键字查找对应的文献
	 * @param query
	 * @param record
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryByKeyword.do",method = {RequestMethod.GET})
	public ResultModel queryByKeyword(BaseQuery<Literature> query,Literature record,String keyword){
		query.setQuery(record);
		List<Literature>result=service.queryByKeyword(query,keyword);
		int total=service.queryConditionCount(query,keyword);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.total(total)
				.build();
	}
}
