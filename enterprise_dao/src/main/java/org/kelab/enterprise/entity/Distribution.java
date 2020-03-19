package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;

/**
 * Create by Wzy
 * on 2018/8/7 14:29
 * 不短不长八字刚好
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@MGColName("distribution")
public class Distribution {

    private String name;

    private Integer value;
}
