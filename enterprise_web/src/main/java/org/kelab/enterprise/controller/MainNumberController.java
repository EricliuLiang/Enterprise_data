package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.MainNumber;
import org.kelab.enterprise.service.MainNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * @author hw 不短不长八字刚好.
 * @since 2018/9/19 20:15
 */
@Controller
@RequestMapping("/mainNumber")
public class MainNumberController extends BaseController {

    @Autowired
    private MainNumberService service;

    @ResponseBody
    @RequestMapping(value="/query.do",method = RequestMethod.GET)
    public ResultModel query(BaseQuery<MainNumber> query,MainNumber record){
        List<MainNumber> result=service.query(query.setQuery(record));
        return ResultModel.builder()
                .data(result)
                .total(result==null?0:result.size())
                .code(SUCCESS)
                .build();
    }

    @ResponseBody
    @RequestMapping(value="/total.do",method=RequestMethod.GET)
    public ResultModel total(BaseQuery<MainNumber> query,MainNumber record){
        return ResultModel.builder()
                .data(service.total(query.setQuery(record)))
                .code(SUCCESS)
                .build();
    }
}
