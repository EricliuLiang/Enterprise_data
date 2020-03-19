package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Trade;
import org.kelab.enterprise.service.TradeService;
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
@RequestMapping("/trade")
public class TradeController extends BaseController {
	@Autowired
	private TradeService service;

	@ResponseBody
	@RequestMapping(value = "/query.do", method = RequestMethod.GET)
	public ResultModel query(BaseQuery<Trade> query, Trade record) {
		List<Trade> result = service.query(query.setQuery(record));
		return ResultModel.builder()
			.data(result)
			.total(result == null ? 0 : result.size())
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/total.do", method = RequestMethod.GET)
	public ResultModel total(BaseQuery<Trade> query, Trade record) {
		return ResultModel.builder()
			.data(service.total(query.setQuery(record)))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.PUT)
	public ResultModel total(@RequestBody Trade record) {
		return ResultModel.builder()
			.data(service.update(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
	public ResultModel delete(Trade record) {
		return ResultModel.builder()
			.data(service.delete(record))
			.code(SUCCESS)
			.build();
	}

	@ResponseBody
	@RequestMapping(value = "/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody Trade record) {
		return ResultModel.builder()
			.data(service.insert(record))
			.code(SUCCESS)
			.build();
	}
	@ResponseBody
	@RequestMapping(value="/queryTypeDistribution.do",method=RequestMethod.GET)
	public ResultModel queryTypeDistribution(String companyName,boolean cache){
		return ResultModel.builder()
				.data(service.queryTypeDistribution(companyName,cache))
				.code(SUCCESS)
				.build();
	}
	@ResponseBody
	@RequestMapping(value="/queryCategoryDistribution.do",method=RequestMethod.GET)
	public ResultModel queryCategoryDistribution(String companyName,boolean cache){
		return ResultModel.builder()
				.data(service.queryCategoryDistribution(companyName,cache))
				.code(SUCCESS)
				.build();
	}

	@ResponseBody
	@RequestMapping(value="/queryStatusDistribution.do",method=RequestMethod.GET)
	public ResultModel queryStatusDistribution(String companyName,boolean cache){
		return ResultModel.builder()
				.data(service.queryStatusDistribution(companyName,cache))
				.code(SUCCESS)
				.build();
	}
}
