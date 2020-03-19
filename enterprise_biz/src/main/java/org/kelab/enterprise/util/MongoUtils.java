package org.kelab.enterprise.util;

import com.mongodb.client.MongoDatabase;
import org.cn.wzy.dao.impl.BaseMongoDao;

import java.lang.reflect.Field;

public class MongoUtils {

  public static MongoDatabase getMongo(BaseMongoDao dao) {
    Field database = null;
    try {
      database = BaseMongoDao.class.getDeclaredField("mongo");
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
      return null;
    }
    database.setAccessible(true);
    MongoDatabase mongo = null;
    try {
      return (MongoDatabase) database.get(dao);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
  }
}
