package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy
 * @date 2018/8/12 16:45
 * @不短不长 八字刚好
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("company_profile")
public class CompanyProfile {

	//创新值
	private Integer value;
	//公司名称
	@MGKey
	private String companyName;
	//简介
	private String companyProfile;
	//法人
	private String legalPerson;
	//注册资本
	private Double registeredCapital;
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

	private Double from;

	private Double end;
}
