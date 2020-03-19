package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Partner;
import org.kelab.enterprise.service.PartnerService;
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
 * @since 2018/9/19 20:15
 */
@Controller
@RequestMapping("/partner")
public class PartnerController extends BaseController {

	@Autowired
	private PartnerService service;

	@ResponseBody
	@RequestMapping(value = "/query.do",method = RequestMethod.GET)
	public ResultModel query(BaseQuery<Partner> query, Partner record) {
		List<Partner> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do",method = RequestMethod.GET)
	public ResultModel total(BaseQuery<Partner> query, Partner record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do",method = RequestMethod.PUT)
	public ResultModel total(@RequestBody Partner record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(Partner record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody Partner record) {
		return ResultModel.builder()
			.data(service.insert(record))
			.code(SUCCESS)
			.build();
	}
}
