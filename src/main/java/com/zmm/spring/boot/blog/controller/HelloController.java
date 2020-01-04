package com.zmm.spring.boot.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 1805783671
 * @version HelloController-1.0
 * @时间 2018年12月24日 下午2:37:02
 *
 */
@RestController
public class HelloController {
	
	/**
	 * @方法返回类型 String
	 * @时间 2018年12月24日 下午3:13:39
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
	
	/**
	 * @方法返回类型 int
	 * @时间 2018年12月24日 下午3:13:27
	 * @param number
	 * @return
	 */
	public int say(int number){
		number=number+1;
		return number;
	}

}
