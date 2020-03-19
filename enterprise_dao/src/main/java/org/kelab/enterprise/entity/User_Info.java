package org.kelab.enterprise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Create by WzyGenerator
 * on Mon Aug 13 16:04:12 CST 2018
 * 不短不长八字刚好
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User_Info {
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private Integer roleId;

    private String mailbox;

    private String realname;

}