package com.zmm.spring.boot.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmm.spring.boot.blog.domain.Authority;
import com.zmm.spring.boot.blog.repository.AuthorityRepository;

/**
 * @author 1805783671
 * @version AuthorityServiceImpl-1.0
 * @time 2019年1月2日 下午5:59:45
 * @Desc 描述
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findOne(id);
	}

}
