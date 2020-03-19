package org.kelab.enterprise.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.cn.wzy.controller.BaseController;
import org.cn.wzy.model.ResultModel;
import org.kelab.enterprise.dao.AuthorDao;
import org.kelab.enterprise.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static org.kelab.enterprise.constant.RequestConstant.ILLEGAL_ACCESS_ERROR;
import static org.kelab.enterprise.constant.UserConstant.VISITOR;

/**
 * Created by Wzy
 * on 2018/5/8
 */
public class AccessAspect {

	@Autowired
	private AuthorDao authorDao;

	public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
		BaseController controller = (BaseController) joinPoint.getTarget();
		HttpServletRequest request = controller.getRequest();
		Integer roleId = (Integer) controller.getClaimsValue("roleId");
		roleId = roleId == null ? VISITOR : roleId;
		String api = request.getRequestURI().replaceAll(request.getContextPath(), "");
		while (api.contains("//")) {
			api = api.replace("//","/");
		}
		String methodName = request.getMethod();
		if ("OPTIONS".equals(methodName)) {
			return joinPoint.proceed();
		}
		String url = methodName + ":" + api;
		Author record = new Author(roleId, url);
		if (authorDao.verify(record)) {
			return joinPoint.proceed();
		} else {
			return ResultModel.builder().code(ILLEGAL_ACCESS_ERROR).build();
		}
	}
}
