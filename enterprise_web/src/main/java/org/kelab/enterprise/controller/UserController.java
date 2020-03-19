package org.kelab.enterprise.controller;

import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.cn.wzy.util.TokenUtil;
import org.kelab.enterprise.entity.User_Info;
import org.kelab.enterprise.model.LoginResult;
import org.kelab.enterprise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.cn.wzy.model.ResultModel.SUCCESS;
import static org.kelab.enterprise.constant.LoginConstant.LOGIN_SUCCESS;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/1 10:43
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value="/selectByPrimaryKey.do",method = RequestMethod.GET)
	public ResultModel insertList(Integer id){
		User_Info result=userService.selectByPrimaryKey(id);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}

	@ResponseBody
	@RequestMapping(value="/updateByPrimaryKeySelective.do",method = RequestMethod.PUT)
	public ResultModel updateByPrimaryKeySelective(@RequestBody User_Info record) {
		Integer result=userService.updateByPrimaryKeySelective(record);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}


	@ResponseBody
	@RequestMapping(value="/insertSelective.do",method = RequestMethod.POST)
	public ResultModel insertSelective(@RequestBody User_Info record) {
		Integer result=userService.insertSelective(record);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}


	/*
	* 增加一个用户
	* */
	@ResponseBody
	@RequestMapping(value="/insert.do",method = RequestMethod.POST)
	public ResultModel insert(@RequestBody User_Info record) {
		Integer result=userService.insert(record);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}

	/*
	* 通过主键删除
	* */
	@ResponseBody
	@RequestMapping(value="/deleteByPrimaryKey.do",method = RequestMethod.DELETE)
	public ResultModel deleteByPrimaryKey(Integer id){
		Integer result=userService.deleteByPrimaryKey(id);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}
	/*
	* 通过主键更新
	* */
	@ResponseBody
	@RequestMapping(value="/updateByPrimaryKey.do",method = RequestMethod.PUT)
	public ResultModel updateByPrimaryKey(@RequestBody User_Info record){
		Integer result=userService.updateByPrimaryKey(record);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}



	/*
	* 删除用户
	*
	* */
	@ResponseBody
	@RequestMapping(value="/deleteByIdsList.do",method = RequestMethod.DELETE)
	public ResultModel deleteByIdsList(@RequestBody List<Integer> ids){
		Integer result=userService.deleteByIdsList(ids);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}

  /*
  * 插入用户
  * */
	@ResponseBody
	@RequestMapping(value="/insertList.do",method = RequestMethod.POST)
	public ResultModel insertList(@RequestBody List<User_Info> list){
		Integer result=userService.insertList(list);
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}

/*
* 选择用户
* */
	@ResponseBody
	@RequestMapping(value="/selectCountByCondition.do",method = RequestMethod.GET)
	public ResultModel selectCountByCondition(){
		Integer result=userService.selectCountByCondition();
		return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}
/*
* 通过id选择用户
* */
	@ResponseBody
	@RequestMapping(value="/selectByIds.do",method = RequestMethod.POST)
	public ResultModel selectByIds(@RequestBody List<Integer> ids){
		return ResultModel.builder()
				.data(userService.selectByIds(ids))
				.code(SUCCESS)
				.build();
	}

	/*
	*
	* 查询所有用户
	* */
    @ResponseBody
	@RequestMapping(value="/query.do",method = RequestMethod.GET)
	public ResultModel query(){
    	List<User_Info> result=userService.query();
    	return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.build();
	}

	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ResultModel login(User_Info user, String verify, String code) {
		LoginResult result = userService.login(user, verify, code);
		if (result.getStatus() == LOGIN_SUCCESS) {
			Map<String, Object> claims = new HashMap<>();
			claims.put("roleId", result.getUser().getRoleId());
			claims.put("username", result.getUser().getUsername());
			claims.put("userId", result.getUser().getId());
			return ResultModel.builder()
				.data(result)
				.code(SUCCESS)
				.token(TokenUtil.tokens(claims))
				.build();
		}
		return ResultModel.builder()
			.data(result)
			.code(SUCCESS)
			.build();
	}
}
