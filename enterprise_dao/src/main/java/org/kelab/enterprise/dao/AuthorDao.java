package org.kelab.enterprise.dao;

import org.kelab.enterprise.entity.Author;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/10/10 16:45
 */
public interface AuthorDao {

	boolean verify(Author record);
}
