package com.zmm.spring.boot.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zmm.spring.boot.blog.domain.Blog;
import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.Comment;
import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.domain.Vote;
import com.zmm.spring.boot.blog.domain.es.EsBlog;
import com.zmm.spring.boot.blog.repository.BlogRepository;

/**
 * @author 1805783671
 * @version BlogServiceImpl-1.0
 * @time 2019年1月4日 下午1:59:51
 * @Desc 描述 Blog实现
 */
@Service
public class BlogServiceImpl implements BlogService {

	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private EsBlogService esBlogService;
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public Blog saveBlog(Blog blog) {
		//判断是新建还是更新
		boolean isNew = (blog.getId() == null);
		EsBlog esBlog = null;
		
		Blog returnBlog = blogRepository.save(blog);
		if (isNew) {
			esBlog = new EsBlog(returnBlog);
		} else {
			esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
			esBlog.update(returnBlog);
		}
		esBlogService.updateEsBlog(esBlog);
		return returnBlog;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public void removeBlog(Long id) {
		blogRepository.delete(id);
		EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
		esBlogService.removeEsBlog(esblog.getId());
	}


	@Override
	public Blog updateBlog(Blog blog) {
		return blogRepository.save(blog);
	}


	@Override
	public Blog getBlogById(Long id) {
		return blogRepository.findOne(id);
	}

	
	@Override
	public Page<Blog> listBlogsByTitleLikeVote(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		String tags = title;
		return blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, 
				user, tags, user, pageable);
	}

	
	@Override
	public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		return blogRepository.findByUserAndTitleLike(user, title, pageable);
	}

	
	@Override
	public void readingIncrease(Long id) {
		Blog blog = blogRepository.findOne(id);
		blog.setReadSize(blog.getReadSize()+1);
		blogRepository.save(blog);

	}

	@Override
	public Blog createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogRepository.findOne(blogId);
		//获取用户信息
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Comment comment = new Comment(user, commentContent);
		originalBlog.addComment(comment);
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeComment(commentId);
		this.saveBlog(originalBlog);
	}

	@Override
	public Blog createVote(Long blogId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		Vote vote = new Vote(user);
		boolean isExist = originalBlog.addVote(vote);
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeVote(voteId);
		this.saveBlog(originalBlog);
		
	}

	@Override
	public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
		return blogRepository.findByCatalog(catalog, pageable);
	}

}
