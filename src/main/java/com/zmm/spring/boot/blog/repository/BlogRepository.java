package com.zmm.spring.boot.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zmm.spring.boot.blog.domain.Blog;
import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;

/**
 * @author 1805783671
 * @version BlogRepository-1.0
 * @time 2019年1月4日 下午1:53:40
 * @Desc 描述
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

	
	
	/**
	 *
	 * 描述--- 根据用户名、博客标题分页查询博客列表
	 * @Desc
	 * @方法返回类型 Page<Blog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午1:54:44
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);
	
	
	
	
	/**
	 *
	 * 描述---根据用户名、博客查询博客列表（时间逆序
	 * @Desc
	 * @方法返回类型 Page<Blog>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午4:43:14
	 * @param title
	 * @param user
	 * @param tags
	 * @param user2
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc( String title, User user, String tags, User user2 , Pageable pageable);
	
	
	
	/**
	 *
	 * 描述---根据分类查询博客列表
	 * @Desc
	 * @方法返回类型 Page<Blog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:05:42
	 * @param catalog
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByCatalog(Catalog catalog,Pageable pageable);
}
