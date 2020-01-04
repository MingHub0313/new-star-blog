package com.zmm.spring.boot.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.domain.es.EsBlog;
import com.zmm.spring.boot.blog.vo.TagVO;

/**
 * @author 1805783671
 * @version EsBlogService-1.0
 * @time 2019年1月7日 下午7:29:54
 * @Desc 描述 EsBlog 服务接口
 */
public interface EsBlogService {
	
	/**
	 * 描述---删除EsBlog
	 * 
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:30:22
	 * @param id
	 */
	void removeEsBlog(String id);
	
	/**
	 *
	 * 描述---更新 EsBlog
	 * @Desc
	 * @方法返回类型 EsBlog
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:30:33
	 * @param esBlog
	 * @return
	 */
	EsBlog updateEsBlog(EsBlog esBlog);
	
	/**
	 * 描述---根据Blog的Id获取EsBlog
	 * 
	 * @Desc
	 * @方法返回类型 EsBlog
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:30:44
	 * @param blogId
	 * @return
	 */
	EsBlog getEsBlogByBlogId(Long blogId);
 
	/**
	 *
	 * 描述---最新博客列表,分页
	 * @Desc
	 * @方法返回类型 Page<EsBlog>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:00
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);
 
	/**
	 *
	 * 描述---最热博客列表,分页
	 * @Desc
	 * @方法返回类型 Page<EsBlog>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:13
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);
	
	/**
	 *
	 * 描述---博客列表,分页
	 * @Desc
	 * @方法返回类型 Page<EsBlog>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:20
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> listEsBlogs(Pageable pageable);
	
	/**
	 * 描述---最新前5
	 * 
	 * @Desc
	 * @方法返回类型 List<EsBlog>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:32
	 * @return
	 */
	List<EsBlog> listTop5NewestEsBlogs();
	
	/**
	 * 描述---最热前5
	 * 
	 * @Desc
	 * @方法返回类型 List<EsBlog>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:41
	 * @return
	 */
	List<EsBlog> listTop5HotestEsBlogs();
	
	/**
	 *
	 * 描述---最热前 30 标签
	 * @Desc
	 * @方法返回类型 List<TagVO>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:46
	 * @return
	 */
	List<TagVO> listTop30Tags();

	/**
	 *
	 * 描述---最热前12用户
	 * @Desc
	 * @方法返回类型 List<User>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午7:31:54
	 * @return
	 */
	List<User> listTop12Users();
}
