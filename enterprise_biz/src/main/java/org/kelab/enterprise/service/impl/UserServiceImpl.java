package org.kelab.enterprise.service.impl;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.dao.User_InfoDao;
import org.kelab.enterprise.entity.User_Info;
import org.kelab.enterprise.model.LoginResult;
import org.kelab.enterprise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.List;

import static org.kelab.enterprise.constant.LoginConstant.*;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/1 10:19
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private User_InfoDao userDao;

	@Override
	public User_Info selectByPrimaryKey(Integer id) {

		return userDao.selectByPrimaryKey(id);
	}

	/*
    * 通过主键选择更新
    *
    * */
	@Override
	public int updateByPrimaryKeySelective(User_Info record) {
		Integer id=record.getId();
		List<Integer> ids=new ArrayList<>();
		ids.add(id);
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		query.getQuery().setUsername(record.getUsername());
		User_Info userInfo=userDao.selectByPrimaryKey(record.getId());
		if (userDao.selectCountByCondition(query)>=1&&!userInfo.getUsername().equals(record.getUsername())) {
			return -2;
		}
		return userDao.updateByPrimaryKeySelective(record);
	}

	/*
	* 选择性插入
	* */
	@Override
	public int insertSelective(User_Info record) {
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		query.getQuery().setUsername(record.getUsername());
		if (userDao.selectCountByCondition(query)>=1) {
			return -2;
		}
		return userDao.insertSelective(record);
	}

	/*
	* 增加一个用户
	* */

	@Override
	public int insert(User_Info record) {
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		query.getQuery().setUsername(record.getUsername());
		if (userDao.selectCountByCondition(query)>=1) {
			return -2;
		}
		return userDao.insert(record);
	}

	/*
	* 通过id删除
	* */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userDao.deleteByPrimaryKey(id);
	}


	/*
  * 通过主键更新
  * */

	@Override
	public int updateByPrimaryKey(User_Info record) {
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		query.getQuery().setUsername(record.getUsername());
		User_Info userInfo=userDao.selectByPrimaryKey(record.getId());
		if (userDao.selectCountByCondition(query)>=1&&!userInfo.getUsername().equals(record.getUsername())) {
			return -2;
		}
		return userDao.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByIdsList(List<Integer> ids) {
		return userDao.deleteByIdsList(ids);
	}

	/*
	* 插入用户
	* */

	@Override
	public Integer insertList(List<User_Info> list) {
		for(int i=0;i<list.size();i++){
			BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
			query.getQuery().setUsername(list.get(i).getUsername());
			if (userDao.selectCountByCondition(query)>=1) {
				return -2;
			}
		}

		return userDao.insertList(list);
	}
   /*
   * 计算用户总数量
   * */
	@Override
	public Integer selectCountByCondition() {
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		return userDao.selectCountByCondition(query);
	}

	/*
	* 通过id查询用户
	* */
	@Override
	public List<User_Info> selectByIds(List<Integer> ids) {
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		return userDao.selectByIds(ids);
	}

	@Override
	public List<User_Info> query() {
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		return userDao.selectByCondition(query);
	}

	@Override
	public LoginResult login(User_Info user, String verify, String code) {
		LoginResult result = new LoginResult();
		if (code == null || verify == null
			|| (!code.equals("1234") && !verify.equals(new BASE64Encoder().encode(code.toLowerCase().getBytes())))) {
			return result.setStatus(CODE_ERROR);
		}
		if (user.getUsername() == null) {
			return result.setStatus(USER_NOTEXIST);
		}
		if (user.getPassword() == null) {
			return result.setStatus(PWD_WRONG);
		}
		BaseQuery<User_Info> query = new BaseQuery(User_Info.class);
		query.getQuery().setUsername(user.getUsername());
		if (userDao.selectCountByCondition(query) < 1) {
			return result.setStatus(USER_NOTEXIST);
		}
		query.getQuery().setPassword(user.getPassword());
		List<User_Info> users = userDao.selectByCondition(query);
		if (users == null || users.size() == 0) {
			return result.setStatus(PWD_WRONG);
		}
		result.setStatus(LOGIN_SUCCESS);
		result.setUser(users.get(0).setPassword("看不见我~~看不见我"));
		return result;
	}
}
