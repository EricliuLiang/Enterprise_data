package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.SoftwareCr;
import org.kelab.enterprise.entity.WorkCopyright;
import org.kelab.enterprise.service.WorkCopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

@Controller
@RequestMapping("/workCopyright")
public class WorkCopyrightController extends BaseController {
	@Autowired
	private WorkCopyrightService service;

	@ResponseBody
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public ResultModel query(BaseQuery<WorkCopyright> query, WorkCopyright record) {
		List<WorkCopyright> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(BaseQuery<WorkCopyright> query, WorkCopyright record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel total(@RequestBody WorkCopyright record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(WorkCopyright record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody WorkCopyright record) {
		return ResultModel.builder()
			.data(service.insert(record))
			.code(SUCCESS)
			.build();
	}

	/**
	 * 软件类型分布
	 * @param companyName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/typeDistribution.do", method = RequestMethod.GET)
	public ResultModel queryTypeDistribution(String companyName) {
		return ResultModel.builder()
			.data(service.queryTypeDistribution(companyName))
			.code(SUCCESS)
			.build();
	}

}
