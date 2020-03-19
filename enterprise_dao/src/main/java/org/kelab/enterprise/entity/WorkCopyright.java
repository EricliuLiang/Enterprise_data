package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("work_copyright")
public class WorkCopyright {
	private String companyName;
	private String workName;
	private String firstPublishedDate;
	private String completionDate;
	@MGKey
	private String registrationNumber;
	private String registrationDate;
	private String registrationCategory;
}
