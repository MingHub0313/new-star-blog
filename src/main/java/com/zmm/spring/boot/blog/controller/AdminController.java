package com.zmm.spring.boot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zmm.spring.boot.blog.vo.Menu;

/**
 * @author 1805783671
 * @version AdminController-1.0
 * @time 2018年12月27日 上午11:57:55
 * @Desc 后台管理控制器
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

	/**
	 * 
	 * @Desc 描述---获取后台管理主页
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2018年12月27日 下午12:00:01
	 * @param model
	 * @return
	 */
	@GetMapping
	public ModelAndView listUsers(Model model) {
		List<Menu> list = new ArrayList<>();
		list.add(new Menu("用户管理", "/users"));
		list.add(new Menu("角色管理", "/roles"));
		list.add(new Menu("博客管理", "/blogs"));
		list.add(new Menu("评论管理", "/commits"));
		model.addAttribute("list", list);
		return new ModelAndView("admins/index","model",model);
	}
}
