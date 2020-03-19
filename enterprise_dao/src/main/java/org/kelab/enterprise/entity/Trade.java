package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 17:06
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("trade")
public class Trade {
	private String companyName;
	//状态
	private String status;
	//代理名
	private String agent_name;
	//贸易类型
	private String trade_category;
	//公私贸易
	private String is_public_trade;
	//注册号
	@MGKey
	private String regist_num;
	//服务表
	private String service_list;
	//贸易名字
	private String trade_name;
	//链接
	private String url;
	//应用地址
	private String applicant_address;
	//贸易类型
	private String trade_type;
	//应用时间
	private String applicent_time;
	//摘录时间
	private String crawl_time;
	//贸易路径
	private String trade_path;
	//贸易图
	private String trade_image;
	//时间单位
	private String time_linit;
	//应用名
	private String applicant_name;
	//登记时间
	private String regist_time;
}
