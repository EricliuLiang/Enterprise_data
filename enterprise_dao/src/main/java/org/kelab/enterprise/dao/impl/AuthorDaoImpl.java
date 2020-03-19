package org.kelab.enterprise.dao.impl;

import org.kelab.enterprise.dao.AuthorDao;
import org.kelab.enterprise.entity.Author;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/10/10 16:45
 */
@Repository
public class AuthorDaoImpl extends SqlSessionDaoSupport implements AuthorDao {

	private String getNamespace(){
		return "org.kelab.enterprise.dao.AuthorDao";
	}

	@Override
	public boolean verify(Author record) {
		return (Integer)getSqlSession().selectOne(getNamespace() + ".verify",record) > 0;
	}
}
