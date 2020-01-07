package com.zmm.spring.boot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zmm.spring.boot.blog.domain.Authority;
import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.service.AuthorityService;
import com.zmm.spring.boot.blog.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 1805783671
 * @version MainController-1.0
 * @time 2018年12月27日 上午11:24:05
 * @Desc主页控制器
 *
 */
@Controller
public class MainController {
	
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService  authorityService;
	
	
	@RequestMapping("/")
	public ModelAndView root() {
		return new ModelAndView("redirect:index");
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("redirect:blogs");
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登录失败,用户或者密码错误!");
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public ModelAndView register(User user) {
		List<Authority> authorities = new ArrayList<>();
		//默认注册是博主
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		userService.saveOrUpdateUser(user);
		return new ModelAndView("redirect:login");
		//return "redirect:/login";
	}
	
	@GetMapping("/search")
	public String search() {
		return "search";
	}

}
