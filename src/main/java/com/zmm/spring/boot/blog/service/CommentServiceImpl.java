package com.zmm.spring.boot.blog.service;

import com.zmm.spring.boot.blog.domain.Comment;
import com.zmm.spring.boot.blog.repository.CommentRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 1805783671
 * @version CommentServiceImpl-1.0
 * @time 2019年1月4日 下午5:00:53
 * @Desc 描述 Comment Service接口实现.
 */
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
    private CommentRepository commentRepository;


	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.findOne(id);
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void removeComment(Long id) {
		commentRepository.delete(id);

	}

}
