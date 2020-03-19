package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Recruit;
import org.kelab.enterprise.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 20:20
 */
@Controller
@RequestMapping("/recruit")
public class RecruitController extends BaseController {
	@Autowired
	private RecruitService service;

	@ResponseBody
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public ResultModel query(BaseQuery<Recruit> query, Recruit record) {
		List<Recruit> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(BaseQuery<Recruit> query, Recruit record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel total(@RequestBody Recruit record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(Recruit record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody Recruit record) {
		return ResultModel.builder()
			.data(service.insert(record))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 薪资分布
	 * @param companyName
	 * @param cache
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/salaryDistribution.do",method = RequestMethod.GET)
	public ResultModel querySalaryDistribution(String companyName, boolean cache) {
		return ResultModel.builder()
			.data(service.querySalaryDistribution(companyName,cache))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 要求分布
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/requireDistribution.do",method = RequestMethod.GET)
	public ResultModel requireDistribution(String companyName) {
		return ResultModel.builder()
			.data(service.queryRequireDistribution(companyName))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 地区分布
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addressDistribution.do",method = RequestMethod.GET)
	public ResultModel addressDistribution(String companyName) {
		return ResultModel.builder()
			.data(service.queryAddressDistribution(companyName))
			.code(SUCCESS)
			.build();
	}
}
