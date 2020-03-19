package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/8/7 14:34
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/distribution")
public class DistributionController extends BaseController {

	@Autowired
	private DistributionService service;

	/**
	 * 根据地区查企业个数
	 *
	 * @param query
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public ResultModel query(BaseQuery<Distribution> query, Distribution record) {
		List<Distribution> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.code(SUCCESS)
			.total(result == null ? 0 : result.size())
			.build();
	}

	/**
	 * refresh the distribution.
	 * @return boolean result
	 */
	@ResponseBody
	@RequestMapping(value = "/refresh.do", method = RequestMethod.GET)
	public ResultModel refresh() {
		return ResultModel.builder()
			.data(service.refresh())
			.code(SUCCESS)
			.build();
	}
}
