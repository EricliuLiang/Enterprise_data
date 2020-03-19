package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.util.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static org.cn.wzy.model.ResultModel.SUCCESS;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/1 10:24
 */
@Controller
@RequestMapping("/pic")
public class PicController extends BaseController {

	@ResponseBody
	@RequestMapping(value = "/getPic.do",method = RequestMethod.GET)
	public ResultModel getPic(Integer width,Integer height) throws IOException {
		if (width == null || height == null) {
			width = 80;
			height = 30;
		}
		return ResultModel.builder()
			.data(VerifyCodeUtils.VerifyCode(width,height,4))
			.code(SUCCESS)
			.build();
	}
}
