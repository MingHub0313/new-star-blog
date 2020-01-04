package com.zmm.spring.boot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zmm.spring.boot.blog.domain.Comment;

/**
 * @author 1805783671
 * @version CommentRepository-1.0
 * @time 2019年1月4日 下午4:58:11
 * @Desc 描述 Comment Repository 接口
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
