package org.kelab.enterprise.service;

import org.kelab.enterprise.entity.User_Info;
import org.kelab.enterprise.model.LoginResult;

import java.util.List;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/1 10:19
 */
public interface UserService {


	LoginResult login(User_Info user, String verify, String code);
	List<User_Info> query();
	List<User_Info> selectByIds(List<Integer> ids);
	Integer selectCountByCondition();
	Integer insertList(List<User_Info> list);
	int deleteByIdsList(List<Integer> ids);
	int updateByPrimaryKey(User_Info record);
	int deleteByPrimaryKey(Integer id);
	int insert(User_Info record);
	int insertSelective(User_Info record);
	int updateByPrimaryKeySelective(User_Info record);
	User_Info selectByPrimaryKey(Integer id);
}
