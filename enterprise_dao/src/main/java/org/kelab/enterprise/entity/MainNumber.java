package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;

/**
 * @author hw 不短不长八字刚好.
 * @since 2018/9/19 16:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
@MGColName("mainmember")
public class MainNumber {
    private String companyName;
    //公司名字
    private String name;
    //主要人物
    private String job;
    //人物职位
}
