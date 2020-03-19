package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 16:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("news")
public class News {
	//公司名字
	private String companyName;
	//发布时间
	private String publish_time;
	//来源
	private String source;
	//关键字
	private String keys;
	//题目
	@MGKey
	private String title;
	//链接
	private String url;
	//公司
	private String query_name;
	//摘录时间
	private String crawl_time;
	//内容
	private String context;
}
