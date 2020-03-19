package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.SoftwareCr;

import java.util.List;
import java.util.Map;

public interface SoftwareCrService extends BaseService<SoftwareCr> {

	String queryTimeDistribution(String companyName, boolean cache);

	Map<String,Long> searchCompanyByKeyword(BaseQuery<SoftwareCr>query,String keyword);

	Map<String,Long> countCopyrightType(String keyword);

	List<SoftwareCr> queryByKeyword(BaseQuery<SoftwareCr> query,String keyword);

	Integer queryConditionCount(BaseQuery<SoftwareCr> query,String keyword);
}
