package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MGColName("software_copyright")
public class SoftwareCr {

	private String companyName;

	private String softwareName;

	private String versionNumber;

	private String releaseDate;

	@MGKey
	private String registrationNumber;

	private String softwareAbbreviation;

	private String registrationApprovalDate;
}
