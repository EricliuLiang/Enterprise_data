package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Statistics;
import org.kelab.enterprise.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * Create by Wzy
 * on 2018/8/5 18:01
 * 不短不长八字刚好
 */
@Controller
@RequestMapping("/static")
public class StatisticsController extends BaseController {

    @Autowired
    private StatisticsService service;

    /**
     * 查询系统收集情况
     * @param query
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/query.do")
    public ResultModel query(BaseQuery<Statistics> query, Statistics info) {
        List<Statistics> result = service.query(query.setQuery(info));
        return ResultModel.builder()
                .code(SUCCESS)
                .data(result)
                .total(result == null ? 0 : result.size())
                .build();
    }
}
