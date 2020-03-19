package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.Document;
import org.cn.wzy.annotation.MGColName;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/31 13:02
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@MGColName("companyInfo")
public class CompanyInfo {//汇总表信息，不用于查询

	private Integer value;

	private String companyName;

	private String companyProfile;

	private BusinessInfo businessInfo;

	private List<Document> literature;

	private List<Document> patent;

	private List<Document> news;
	//
	private List<Document> trade;
	//招聘
	private List<Document> recruit;
	//股东
	private List<Document> partners;
}
