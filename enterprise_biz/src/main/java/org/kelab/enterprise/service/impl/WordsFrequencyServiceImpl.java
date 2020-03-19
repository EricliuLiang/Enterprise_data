package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.kelab.enterprise.service.WordsFrequencyService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordsFrequencyServiceImpl implements WordsFrequencyService {

  @Autowired
  private BaseMongoDao mongoDao;

  @Override
  public List<Document> query(String key, String companyName) {
    MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
    if (!StringUtil.notBlank(key)) {
      return null;
    }
    if (StringUtil.notBlank(companyName)) {
    	return queryByCompany(key,companyName);
		} else {
			FindIterable<Document> documents = mongo.getCollection(key + "_keywords")
				.find()
				.sort(new BasicDBObject("frequency", -1))
				.limit(30);
			return getRes(documents);
		}
  }

	@Override
	public List<Document> queryByCompany(String key, String companyName) {
		MongoDatabase mongo = MongoUtils.getMongo(mongoDao);
		if (!(StringUtil.notBlank(key) && StringUtil.notBlank(companyName))) {
			return null;
		}
		FindIterable<Document> documents = mongo.getCollection(key + "_keywords_by_company")
			.find(new BasicDBObject("companyName",companyName))
			.sort(new BasicDBObject("frequency", -1))
			.limit(30);
		return getRes(documents);
	}


	private List<Document> getRes(FindIterable<Document> documents) {
		MongoCursor<Document> iterator = documents.iterator();
		List<Document> result = new ArrayList<>();
		while (iterator.hasNext()) {
			Document document = iterator.next();
			document.put("name",document.get("word"));
			document.put("value",document.get("frequency"));
			document.remove("word");
			document.remove("frequency");
			document.remove("companyName");
			document.remove("_id");
			result.add(document);
		}
		return result;
	}
}
