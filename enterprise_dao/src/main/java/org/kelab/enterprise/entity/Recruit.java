package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.cn.wzy.annotation.MGColName;
import org.cn.wzy.annotation.MGKey;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/19 17:17
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@MGColName("recruit")
public class Recruit {
  private String companyName;
  //薪水
  private String salary;
  //规模
  private String scale;
  //雇佣时间
  @MGKey
  private String hiring_time;
  //公司类型
  private String company_nature;
  //链接
  private String url;
  //要求
  private String require;
  //职位
  private String posi_type;
  //公司名
  private String company_name;
  //地址
  private String address;
  //位置
  private String position;
  //平均工资
  private Double aver_salary;
}
