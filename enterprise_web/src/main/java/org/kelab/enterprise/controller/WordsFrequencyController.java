package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.kelab.enterprise.service.WordsFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.cn.wzy.model.ResultModel.SUCCESS;

@Controller
@RequestMapping("/words")
public class WordsFrequencyController extends BaseController {

  @Autowired
  private WordsFrequencyService service;

	/**
	 * 查询所有的热词
	 * @param key
	 * @return
	 */
  @ResponseBody
  @RequestMapping(value = "/query.do", method= RequestMethod.GET)
  public ResultModel query(String key, String companyName) {
    return ResultModel.builder()
      .data(service.query(key,companyName))
      .code(SUCCESS).build();
  }
}
