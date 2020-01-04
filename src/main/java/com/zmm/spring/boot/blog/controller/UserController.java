package com.zmm.spring.boot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zmm.spring.boot.blog.domain.Authority;
import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.service.AuthorityService;
import com.zmm.spring.boot.blog.service.UserService;
import com.zmm.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import com.zmm.spring.boot.blog.vo.Response;

/**
 * @author 1805783671
 * @version UserController-1.0
 * @time 2018年12月24日 下午10:03:01
 * @Desc User 控制器
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService  authorityService;


	/**
	 * 查询所用用户
	 * @return
	 * GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)的缩写
	 */
	@GetMapping
	public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			@RequestParam(value="name",required=false,defaultValue="") String name,
			Model model) {
	 
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<User> page=null;
		page=userService.listUsersByNameLike(name, pageable);
		/*if(name.isEmpty()) {
			System.out.println("全部查询");
			page = userRepository.findAll(pageable);
		}else{
			System.out.println("名称查询");
			page = userRepository.findByNameLike(name, pageable);
		}*/
		// 当前所在页面数据列表
		List<User> list = page.getContent();
		
		model.addAttribute("page", page);
		model.addAttribute("userList",list);
		return new ModelAndView(async==true?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
	}

	/**
	 * 
	 * @Desc 描述---根据id查询用户
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2018年12月24日 下午10:12:17
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Long id,Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user",user);
		model.addAttribute("title","查看用户");
		return  new ModelAndView("users/view","userModel",model);
	}
	
	/**
	 * 
	 * @Desc 描述---获取创建form表单页面
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2018年12月24日 下午10:14:15
	 * @param model
	 * @return
	 */
	@GetMapping("/add")
	public ModelAndView createForm(Model model) {
		model.addAttribute("user",new User(null, null, null, null));
		return  new ModelAndView("users/add","userModel",model);
	}
	
	@PostMapping
	public ResponseEntity<Response> saveOrUpdateUser(User user,Long authorityId) {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(authorityId));
		user.setAuthorities(authorities);
		try {
			user=userService.saveOrUpdateUser(user);
		} catch (ConstraintViolationException e) {	
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		}
		
		return  ResponseEntity.ok().body(new Response(true, "处理成功", user));
	}
	
	/**
	 * 
	 * @Desc 描述---根据id删除用户
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2018年12月24日 下午10:19:50
	 * @param id
	 * @param model
	 * @return
	 */
	@DeleteMapping("/{id}")
	public  ResponseEntity<Response> delete(@PathVariable("id") Long id,Model model) {
		try {
			userService.removeUser(id);
		} catch (Exception e) {
			return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
		}
		return  ResponseEntity.ok().body( new Response(true, "处理成功"));
	}
	
	/**
	 * 
	 * @Desc 描述---获取修改用户的界面
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2018年12月24日 下午10:22:09
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public ModelAndView modify(@PathVariable("id") Long id,Model model) {
		User user =userService.getUserById(id);
		model.addAttribute("user",user);
		model.addAttribute("title","修改用户");
		return  new ModelAndView("users/edit","userModel",model);
	}
}
