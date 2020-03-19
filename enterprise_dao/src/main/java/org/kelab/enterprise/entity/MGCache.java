package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cn.wzy.annotation.MGColName;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MGColName("global_cache")
public class MGCache {
	private String key;
	private String value;
}
