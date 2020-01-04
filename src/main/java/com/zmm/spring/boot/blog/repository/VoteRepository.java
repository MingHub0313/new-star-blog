package com.zmm.spring.boot.blog.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zmm.spring.boot.blog.domain.Vote;

/**
 * @author 1805783671
 * @version VoteRepository-1.0
 * @time 2019年1月4日 下午7:22:41
 * @Desc 描述 Vote Repository 接口.
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

	
}
