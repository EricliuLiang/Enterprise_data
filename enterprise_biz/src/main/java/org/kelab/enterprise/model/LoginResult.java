package org.kelab.enterprise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.kelab.enterprise.entity.User_Info;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/1 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginResult {

	private Integer status;

	private User_Info user;
}
