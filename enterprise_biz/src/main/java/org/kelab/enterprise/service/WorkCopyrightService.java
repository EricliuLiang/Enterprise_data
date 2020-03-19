package org.kelab.enterprise.service;

import org.cn.wzy.query.BaseQuery;
import org.kelab.enterprise.common.BaseService;
import org.kelab.enterprise.entity.Distribution;
import org.kelab.enterprise.entity.WorkCopyright;

import java.util.List;

public interface WorkCopyrightService extends BaseService<WorkCopyright> {

	List<Distribution> queryTypeDistribution(String companyName);
}
