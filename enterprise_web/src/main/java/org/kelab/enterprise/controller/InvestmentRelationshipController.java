
package org.kelab.enterprise.controller;


import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.entity.Literature;
import org.kelab.enterprise.service.InvestmentRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.cn.wzy.model.ResultModel.SUCCESS;

@Controller
@RequestMapping("/relationship")
public class InvestmentRelationshipController extends BaseController {
    @Autowired
    private InvestmentRelationshipService service;
    @ResponseBody
    @RequestMapping(value="/queryInvestmentRelationship.do",method=RequestMethod.GET)
    public ResultModel queryTypeDistribution(String companyName){
        return ResultModel.builder()
                .data(service.queryInvestmentRelationshipService(companyName))
                .code(SUCCESS)
                .build();
    }

}
