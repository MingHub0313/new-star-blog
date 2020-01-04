package com.zmm.spring.boot.blog.service;

import com.zmm.spring.boot.blog.domain.Authority;

/**
 * @author 1805783671
 * @version AuthorityService-1.0
 * @time 2019年1月2日 下午5:58:51
 * @Desc 描述
 */
public interface AuthorityService {
	/**
	 *
	 * 描述---根据id获取 Authority
	 * @Desc
	 * @方法返回类型 Authority
	 * @author 1805783671
	 * @时间 2019年1月2日 下午5:59:05
	 * @param id
	 * @return
	 */
	Authority getAuthorityById(Long id);
}
