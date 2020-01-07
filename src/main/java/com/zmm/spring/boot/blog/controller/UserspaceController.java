package com.zmm.spring.boot.blog.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zmm.spring.boot.blog.domain.Blog;
import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.domain.Vote;
import com.zmm.spring.boot.blog.service.BlogService;
import com.zmm.spring.boot.blog.service.CatalogService;
import com.zmm.spring.boot.blog.service.UserService;
import com.zmm.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import com.zmm.spring.boot.blog.vo.Response;

/**
 * @author 1805783671
 * @version UserspaceController-1.0
 * @time 2018年12月27日 上午11:40:51
 * @Desc 用户主页控制器
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CatalogService catalogService;

	@Value("${file.server.url}")
	private String fileServerUrl;

	private static final String   HOT   = "hot";

	private static final String   NEW   = "new";

	/**
	 * 
	 * @Desc 描述---访问url是用户名称时--进入用户主页
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:07:37
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username, Model model) {
		User user = (User) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return "redirect:u/" + username + "/blogs";
	}

	/**
	 * 
	 * @Desc 描述--- 获取个人设置页面
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2019年1月3日 下午5:15:59
	 * @param username
	 * @param model
	 * @return
	 */

	/**
	 * 权限认证 a用户只能访问a的页面.不能访问b用户的页面
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView profile(@PathVariable("username") String username, Model model) {
		User user = (User) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		// 文件服务器的地址返回给客户端
		model.addAttribute("fileServerUrl", fileServerUrl);
		return new ModelAndView("userspace/profile", "userModel", model);
	}

	/**
	 * 
	 * @Desc 描述--- 保存个人设置
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月3日 下午5:16:04
	 * @param username
	 * @param user
	 * @return
	 */
	@PostMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)")
	public String saveProfile(@PathVariable("username") String username, User user) {
		// 原始用户信息
		User originalUser = userService.getUserById(user.getId());
		originalUser.setEmail(user.getEmail());
		originalUser.setName(user.getName());

		// 判断密码是否做了变更
		String rawPassword = originalUser.getPassword();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassWord = encoder.encode(user.getPassword());
		boolean isMatch = encoder.matches(rawPassword, encodePassWord);
		if (!isMatch) {
			originalUser.setEncodePassword(user.getPassword());
		}

		userService.saveOrUpdateUser(originalUser);
		return "redirect:u/" + username + "/profile";
	}

	/**
	 * 
	 * @Desc 描述---获取编辑头像的界面
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2019年1月3日 下午5:28:59
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		User user = (User) userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("userspace/avatar", "userModel", model);
	}

	/**
	 * 
	 * @Desc 描述---保存头像
	 * @方法返回类型 ResponseEntity<Response>
	 * @author 1805783671
	 * @时间 2019年1月3日 下午5:29:08
	 * @param username
	 * @param user
	 * @return
	 */
	@PostMapping("/{username}/avatar")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
		String avatarUrl = user.getAvatar();

		User originalUser = userService.getUserById(user.getId());
		originalUser.setAvatar(avatarUrl);
		userService.saveOrUpdateUser(originalUser);

		return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
	}

	/**
	 * 
	 * @Desc 描述---获取用户的博客列表
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:09:04
	 * @param username
	 * @param order
	 * @param catalogId
	 * @param keyword
	 * @param async
	 * @param pageIndex
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(@PathVariable("username") String username,
			@RequestParam(value = "order", required = false, defaultValue = "new") String order,
			@RequestParam(value = "catalog", required = false) Long catalogId,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "async", required = false) boolean async,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {
		User user = (User) userDetailsService.loadUserByUsername(username);
		Page<Blog> page = null;

		// 分类查询
		if (catalogId != null && catalogId > 0) {
			Catalog catalog = catalogService.getCatalogById(catalogId);
			Pageable pageable =new  PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByCatalog(catalog, pageable);
			order = "";
		}else if(HOT.equals(order)) {
			// 最热查询
			Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize");
			Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
			page = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
		} else if (NEW.equals(order)) {
			// 最新查询
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByTitleLikeVote(user, keyword, pageable);
		}

		// 当前所在页面数据列表
		List<Blog> list = page.getContent();

		model.addAttribute("user", user);
		model.addAttribute("order", order);
		model.addAttribute("catalogId", catalogId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		return (async == true ? "userspace/u :: #mainContainerRepleace" : "userspace/u");
	}

	/**
	 * 
	 * @Desc 描述---查询某个Id的博客
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:17:14
	 * @param id
	 * @return
	 */
	@GetMapping("/{username}/blogs/{id}")
	public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		User principal = null;
		Blog blog = blogService.getBlogById(id);

		// 每次读取，简单的可以认为阅读量增加1次
		blogService.readingIncrease(id);

		// 判断操作用户是否是博客的所有者
		boolean isBlogOwner = false;
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null && username.equals(principal.getUsername())) {
				isBlogOwner = true;
			}
		}
		// 判断操作用户的点赞情况
		List<Vote> votes = blog.getVotes();
		// 当前用户的点赞情况
		Vote currentVote = null;

		if (principal != null) {
			for (Vote vote : votes) {
				vote.getUser().getUsername().equals(principal.getUsername());
				currentVote = vote;
				break;
			}
		}

		// 是否是博客本人
		model.addAttribute("isBlogOwner", isBlogOwner);
		model.addAttribute("blogModel", blog);
		//判断当前用户是否对博客点赞
		model.addAttribute("currentVote", currentVote);

		return "userspace/blog";
	}

	/**
	 * 
	 * @Desc 描述---获取新增\编辑博客
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:20:57
	 * @return
	 */
	@GetMapping("/{username}/blogs/edit")
	public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
		// 获取用户分类列表
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
				
		model.addAttribute("catalogs", catalogs);				
		model.addAttribute("blog", new Blog(null, null, null));
		// 文件服务器的地址返回给客户端
		model.addAttribute("fileServerUrl", fileServerUrl);
		return new ModelAndView("userspace/blogedit", "blogModel", model);
	}

	/**
	 * 
	 * @Desc 描述--- 获取编辑博客的界面
	 * @方法返回类型 ModelAndView
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:23:20
	 * @param username
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/edit/{id}")
	public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		// 获取用户分类列表
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
						
		model.addAttribute("catalogs", catalogs);
		model.addAttribute("blog", blogService.getBlogById(id));
		// 文件服务器的地址返回给客户端
		model.addAttribute("fileServerUrl", fileServerUrl);
		return new ModelAndView("userspace/blogedit", "blogModel", model);
	}

	/**
	 * 
	 * @Desc 描述--- 保存博客
	 * @方法返回类型 ResponseEntity<Response>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:24:10
	 * @param username
	 * @param blog
	 * @return
	 */
	@PostMapping("/{username}/blogs/edit")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
		// 对 Catalog 进行空处理 ----必须创建
		if (blog.getCatalog().getId() == null) {
			return ResponseEntity.ok().body(new Response(false,"未选择分类"));
		}
				
		try {
			// 判断是修改还是新增
			if (blog.getId() != null) {
				Blog originalBlog = blogService.getBlogById(blog.getId());
				originalBlog.setTitle(blog.getTitle());
				originalBlog.setContent(blog.getContent());
				originalBlog.setSummary(blog.getSummary());
				// 增加对分类的处理
				originalBlog.setCatalog(blog.getCatalog());
				// 增加对标签的处理
				originalBlog.setTags(blog.getTags());
				blogService.saveBlog(originalBlog);
			} else {
				User user = (User) userDetailsService.loadUserByUsername(username);
				blog.setUser(user);
				blogService.saveBlog(blog);
			}

		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}

	/**
	 * 
	 * @Desc 描述--- 删除博客
	 * @方法返回类型 ResponseEntity<Response>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午2:26:00
	 * @param username
	 * @param id
	 * @return
	 */

	@DeleteMapping("/{username}/blogs/{id}")
	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {

		try {
			blogService.removeBlog(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		String redirectUrl = "/u/" + username + "/blogs";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}

}
