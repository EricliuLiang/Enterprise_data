package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 16:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("company_literature")
public class Literature {

	private String source;

	private String CLC;

	private String literature_class;

	private String companyName;
	//作者
	private String author;
	//链接
	private String url;
	//总结
	private String summary;
	//发布时间
	private String public_time;
	//出版方
	private String author_unit;
	//类型码
	private String class_number;
	//文件名字
	@MGKey
	private String document_name;
	//摘录时间
	private String crawl_date;

    private String mo_literature;

    private String conference_name;

    private String metting_time;

    private String venue;

    private String sponsor;

    private String language;

}
