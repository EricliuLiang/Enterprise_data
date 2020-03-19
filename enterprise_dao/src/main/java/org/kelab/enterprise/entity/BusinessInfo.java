package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Create by Wzy
 * on 2018/7/31 13:03
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BusinessInfo {//汇总表信息，不用于查询

	//法人
	private String legalPerson;
	//注册资本
	private String registeredCapital;
	//
	private String paidCapital;
	//企业状态
	private String businessStatus;
	//建立时间
	private String establishDate;
	//注册号
	private String registrationNum;
	//机构码
	private String organizationCode;
	//纳税编号
	private String taxpayerNum;
	//信用码
	private String creditCode;
	//公司类型
	private String companyType;
	//行业
	private String industry;
	//批准日期
	private String approvalDate;
	//登记处
	private String registrationAuth;
	//地区
	private String area;
	//英文名称
	private String enName;
	//使用名
	private String usedName;
	//经营时间
	private String operationMode;
	//员工规模
	private String staffSize;
	//经营时期
	private String operatingPeriod;
	//地区
	private String address;
	//经营范围
	private String businessScope;
	//城市
	private String city;
	//县，区
	private String county;
}
