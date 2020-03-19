package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.MapUtil;
import org.kelab.enterprise.entity.CompanyInfo;
import org.kelab.enterprise.entity.Statistics;
import org.kelab.enterprise.service.StatisticsService;
import org.kelab.enterprise.util.MongoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Wzy
 * on 2018/8/5 17:56
 * 不短不长八字刚好
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private BaseMongoDao dao;


    @Override
    public List<Statistics> query(BaseQuery<Statistics> query) {
        saveStatistics();
        return dao.queryByCondition(query, new BasicDBObject("data", -1));
    }


    //将数据存入静态文件
    public void saveStatistics() {
        MongoDatabase mongo = MongoUtils.getMongo(dao);
        MongoCollection<Document> collection = mongo.getCollection("statistics");
        ObjectId object = new ObjectId("5b69634a4542464984b89f50");
        collection.updateMany(Filters.eq("_id",object),new Document("$set",statiDocument()));

    }
    public Document statiDocument(){
        Document document = new Document("company",number("companyInfo"));
        document.append("literature",number("company_literature"));
        document.append("news",number("news"));
        document.append("patent",number("patent"));
        document.append("recruit",number("recruit"));
        document.append("trade",number("trade"));
        document.append("copyright",number("software_copyright")+number("work_copyright"));
        return document;
    }
    public long number(String colName){
        MongoDatabase mongo = MongoUtils.getMongo(dao);
        return mongo.getCollection(colName).countDocuments();
    }

}
