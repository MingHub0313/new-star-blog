package com.zmm.spring.boot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zmm.spring.boot.blog.domain.Authority;

/**
 * @author 1805783671
 * @version AuthorityRepository-1.0
 * @time 2019年1月2日 下午5:55:45
 * @Desc 描述  Authority仓库
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	/*
	 * @param 
	 * @return 
	 * AuthorityRepository
	*/
}
