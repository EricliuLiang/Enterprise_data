package org.kelab.enterprise.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.dao.impl.BaseMongoDao;
import org.kelab.enterprise.common.BaseServiceImpl;
import org.kelab.enterprise.entity.Partner;
import org.kelab.enterprise.service.InvestmentRelationshipService;
import org.kelab.enterprise.util.MongoUtils;
import org.kelab.enterprise.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class InvestmentRelationshipServiceImpl extends BaseServiceImpl<Partner> implements InvestmentRelationshipService {
    @Autowired
    private BaseMongoDao dao;
    @Override
    public Map<String, List<String>> queryInvestmentRelationshipService(String companyName) {
        if(StringUtil.notBlank(companyName)) {
            List<String> result = queryRelation("partner", "companyName", companyName, "partner_name");
            Map<String, List<String>> investCompany = new HashMap<>();
            for (int i = 0; i < result.size(); i++) {
                String partner_name = result.get(i);
                List<String> list = queryRelation("partner", "partner_name", partner_name, "companyName");
                for(int j=0;j<list.size();j++){
                    String str=list.get(j);
                    if(str.equals(companyName)){
                        list.remove(j);
                    }
                }
                if(!list.isEmpty()) {
                    investCompany.put(partner_name, list);
                }
            }
            if(investCompany.isEmpty()){
                return null;
            }else{
                return investCompany;
            }

        }else{
            return null;
        }
    }

    //collection代表集合名,key代表集合里的元素名,value代表要查询的元素名,result代表想要获得的元素值
    public List<String> queryRelation(String collection,String key,String value,String result){
        List<String> results=new ArrayList<>();
        MongoDatabase mongo= MongoUtils.getMongo(mongoDao);
        FindIterable<Document> documents=null;
        if(StringUtil.notBlank(value)){
            documents=mongo.getCollection(collection)
                    .find(new BasicDBObject(key,value));
        }
        MongoCursor<Document> mongoCursor=documents.iterator();
        while(mongoCursor.hasNext()){
            Document document=mongoCursor.next();
            String res=document.getString(result);
            results.add(res);
        }
        return results;
    }
}


