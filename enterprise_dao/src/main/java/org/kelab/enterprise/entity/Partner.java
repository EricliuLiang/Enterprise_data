package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 16:02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("partner")
public class Partner {
	//公司名字
	private String companyName;
	//股东名字
	@MGKey
	private String partner_name;
	//时间
	private String sub_date;
	//份额
	private String hole_ratio;
	//资金
	private String sub_cap;
	//投资类型
	private String partner_type;
}
