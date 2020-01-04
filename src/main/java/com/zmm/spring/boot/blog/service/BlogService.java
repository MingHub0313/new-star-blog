package com.zmm.spring.boot.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zmm.spring.boot.blog.domain.Blog;
import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;

/**
 * @author 1805783671
 * @version BlogService-1.0
 * @time 2019年1月4日 下午1:57:22
 * @Desc 描述 Blog 接口
 */
public interface BlogService {
	
	/**
	 * 描述--- 保存Blog
	 * 
	 * @Desc
	 * @方法返回类型 Blog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:58:10
	 * @param blog
	 * @return
	 */
	Blog saveBlog(Blog blog);
	
	/**
	 *
	 * 描述---删除Blog
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:58:18
	 * @param id
	 */
	void removeBlog(Long id);
	
	/**
	 *
	 * 描述---更新Blog
	 * @Desc
	 * @方法返回类型 Blog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:58:27
	 * @param blog
	 * @return
	 */
	Blog updateBlog(Blog blog);
	
	/**
	 *
	 * 描述---根据id获取Blog
	 * @Desc
	 * @方法返回类型 Blog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:58:39
	 * @param id
	 * @return
	 */
	Blog getBlogById(Long id);
	
	/**
	 *
	 * 描述---根据用户名进行分页模糊查询(最新)
	 * @Desc
	 * @方法返回类型 Page<Blog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:58:56
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByTitleLikeVote(User user, String title, Pageable pageable);
 
	/**
	 * 描述---根据用户名进行分页模糊查询(最热)
	 * @Desc
	 * @方法返回类型 Page<Blog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:59:10
	 * @param suser
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByTitleLikeAndSort(User suser, String title, Pageable pageable);
	
	/**
	 * 描述---阅读量递增
	 * 
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:59:23
	 * @param id
	 */
	void readingIncrease(Long id);
	
	/**
	 *
	 * 描述---发表评论
	 * @Desc
	 * @方法返回类型 Blog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午6:06:01
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	Blog createComment(Long blogId,String commentContent);
	
	/**
	 * 描述---删除评论
	 * 
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午6:06:52
	 * @param blogId 博客id
	 * @param commentId 评论id
	 */
	void removeComment(Long blogId,Long commentId);
	
	/**
	 * 描述---点赞
	 * 
	 * @Desc
	 * @方法返回类型 Blog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午7:28:14
	 * @param blogId
	 * @return
	 */
	Blog createVote(Long blogId);
	
	/**
	 *
	 * 描述---取消点赞
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午7:28:20
	 * @param blogId
	 * @param voteId
	 */
	void removeVote(Long blogId, Long voteId);
	
	/**
	 *
	 * 描述--- 根据分类进行查询
	 * @Desc
	 * @方法返回类型 Page<Blog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:12:53
	 * @param catalog
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByCatalog(Catalog catalog,  Pageable pageable);
}
