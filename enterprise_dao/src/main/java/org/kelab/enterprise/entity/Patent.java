package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 16:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("patent")
public class Patent {
	private String companyName;
	//摘要
	private String summary;
	//代理人
	private String agent;
	//关键字
	private String keywords;
	//申请号
	private String appli_num;
	//城市
	private String city;
	//代理公司
	private String agent_company;
	//申请时间
	private String appli_time;
	//专利号
	private String patent_num;
	//解释
	private String main_clain;
	//涉及领域
	private String instructions;
	//状态
	private String status;
	//类型号
	private String mainClassify;
	//发表公司
	private String applicent;
	//发表时间
	private String public_time;
	//摘录时间
	private String crawl_time;
	//地址
	private String address;
	//发明者
	private String inventor;
	//生粉码
	private String province_code;
	//链接
	private String url;
	//申明
	private String claims;
	//专利名字
	@MGKey
	private String patent_name;
}
