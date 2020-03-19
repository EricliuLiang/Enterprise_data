package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;

/**
 * Create by Wzy
 * on 2018/8/5 17:02
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@MGColName("statistics")
public class Statistics {
	//文献数量
	private Integer literature;
	//企业数量
	private Integer company;
	//新闻
	private Integer news;
	//专利数量
	private Integer patent;
	//招聘
	private Integer recruit;
	//商标
	private Integer trade;
	//评选时间
	private Long date;
	//综合值
	private Integer value;
	//    招投标数量
	private Integer bid;
	//著作权
	private Integer copyright;
}
