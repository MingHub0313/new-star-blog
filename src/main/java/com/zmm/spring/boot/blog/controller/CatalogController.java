package com.zmm.spring.boot.blog.controller;

import java.util.List;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.service.CatalogService;
import com.zmm.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import com.zmm.spring.boot.blog.vo.CatalogVO;
import com.zmm.spring.boot.blog.vo.Response;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 1805783671
 * @version CatalogController-1.0
 * @time 2019年1月4日 下午8:11:42
 * @Desc 描述 分类 控制器.
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {
	
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 
	 * @Desc 描述---获取分类列表
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:16:46
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping
	public ModelAndView listComments(@RequestParam(value="username",required=true) String username, Model model) {
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		// 判断操作用户是否是分类的所有者
		boolean isOwner = false;
		
		if (SecurityContextHolder.getContext().getAuthentication() !=null 
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				 .equals("anonymousUser")) {
			User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			if (principal !=null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			} 
		} 
		
		model.addAttribute("isCatalogsOwner", isOwner);
		model.addAttribute("catalogs", catalogs);
		//return "/userspace/u :: #catalogRepleace";
		return new ModelAndView( "userspace/u :: #catalogRepleace");
	}
	
	/**
	 * 
	 * @Desc 描述---创建分类
	 * @方法返回类型 ResponseEntity<Response>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:16:51
	 * @param catalogVO
	 * @return
	 */
	@PostMapping
	@PreAuthorize("authentication.name.equals(#catalogVO.username)")// 指定用户才能操作方法
	public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {
		
		String username = catalogVO.getUsername();
		Catalog catalog = catalogVO.getCatalog();
		
		User user = (User)userDetailsService.loadUserByUsername(username);
		
		try {
			catalog.setUser(user);
			catalogService.saveCatalog(catalog);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, 
					ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 
	 * @Desc 描述---删除分类
	 * @方法返回类型 ResponseEntity<Response>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:17:09
	 * @param username
	 * @param id
	 * @return
	 * // 指定用户才能操作方法
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> delete(String username, @PathVariable("id") Long id) {
		try {
			catalogService.removeCatalog(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, 
					ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 
	 * @Desc 描述---获取分类编辑界面
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:17:23
	 * @param model
	 * @return
	 */
	@GetMapping("/edit")
	public ModelAndView getCatalogEdit(Model model) {
		Catalog catalog = new Catalog(null, null);
		model.addAttribute("catalog",catalog);
		//return "/userspace/catalogedit";
		return new ModelAndView("userspace/catalogedit");
	}
	
	/**
	 * 
	 * @Desc 描述---根据 Id 获取编辑界面
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:17:43
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public ModelAndView getCatalogById(@PathVariable("id") Long id, Model model) {
		Catalog catalog = catalogService.getCatalogById(id);
		model.addAttribute("catalog",catalog);
		//return "/userspace/catalogedit";
		return new ModelAndView("userspace/catalogedit");
	}

}
