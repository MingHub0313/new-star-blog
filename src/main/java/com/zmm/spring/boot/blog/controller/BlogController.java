package com.zmm.spring.boot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.domain.es.EsBlog;
import com.zmm.spring.boot.blog.service.EsBlogService;
import com.zmm.spring.boot.blog.vo.TagVO;

/**
 * @author 1805783671
 * @version BlogController-1.0
 * @time 2018年12月27日 上午11:30:41
 * @Desc Blog控制器
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
    private EsBlogService esBlogService;

	private static final String   HOT   = "hot";

	private static final String   NEW   = "new";
	
	/**
	 * 
	 * @Desc 描述--- 博客首页
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月7日 下午8:14:19
	 * @param order
	 * @param keyword
	 * @param async
	 * @param pageIndex
	 * @param pageSize
	 * @param model
	 * @return
	 */
    @GetMapping
    public String listEsBlog(
            @RequestParam(value="order",required=false,defaultValue="new") String order,
            @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
            @RequestParam(value="async",required=false) boolean async,
            @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
            @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
            Model model) {

        Page<EsBlog> page = null;
        List<EsBlog> list = null;
		// 系统初始化时,没有博客数据
        boolean isEmpty = true;
        try {
            if (HOT.equals(order)) {
				// 最热查询
                Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime"); 
                Pageable pageable =new PageRequest(pageIndex, pageSize, sort);
                page = esBlogService.listHotestEsBlogs(keyword, pageable);
            } else if (NEW.equals(order)) {
				// 最新查询
                Sort sort = new Sort(Direction.DESC,"createTime"); 
                Pageable pageable =new PageRequest(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }
            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable =new PageRequest(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }

		// 当前所在页面数据列表
        list = page.getContent();


        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载(查询第几页不会刷新)
        if (!async && !isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);
            List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
            model.addAttribute("hotest", hotest);
            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);
            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async==true?"/index :: #mainContainerRepleace":"/index");
    }
	
}
