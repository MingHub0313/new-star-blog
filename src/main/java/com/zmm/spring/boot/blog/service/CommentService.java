package com.zmm.spring.boot.blog.service;

import com.zmm.spring.boot.blog.domain.Comment;

/**
 * @author 1805783671
 * @version CommentService-1.0
 * @time 2019年1月4日 下午4:59:00
 * @Desc 描述 Comment Service接口.
 */
public interface CommentService {
	
	/**
	 *
	 * 描述---根据id获取 Comment
	 * @Desc
	 * @方法返回类型 Optional<Comment>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午4:59:55
	 * @param id
	 * @return
	 */
	Comment getCommentById(Long id);
	
	
    /**
     * 描述---删除评论
     * @Desc
     * @方法返回类型 void
     * @author 1805783671
     * @时间 2019年1月4日 下午5:00:05
     * @param id
     */
    void removeComment(Long id);

}
