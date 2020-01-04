package com.zmm.spring.boot.blog.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zmm.spring.boot.blog.domain.es.EsBlog;

/**
 * @author 1805783671
 * @version EsBlogRepository-1.0
 * @time 2019年1月7日 下午7:25:40
 * @Desc 描述
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String>{
	
	 /**
	  *
	  * 描述---模糊查询(去重)
	  * @Desc
	  * @方法返回类型 Page<EsBlog>
	  * @author 1805783671
	  * @时间 2019年1月7日 下午7:26:27
	  * @param title
	  * @param summary
	  * @param content
	  * @param tags
	  * @param pageable
	  * @return
	  */
    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(
    		String title,String summary,String content,String tags,Pageable pageable);

    /**
     *
	 * 描述---根据 Blog 的id 查询 EsBlog
     * @Desc
     * @方法返回类型 EsBlog
     * @author 1805783671
     * @时间 2019年1月7日 下午7:26:33
     * @param blogId
     * @return
     */
    EsBlog findByBlogId(Long blogId);

}
