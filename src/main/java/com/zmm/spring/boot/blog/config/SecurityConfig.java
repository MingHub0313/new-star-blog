package com.zmm.spring.boot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Name EnableGlobalMethodSecurity
 * @Author 1805783671
 * @version SecurityConfig-1.0
 * @time 2019年1月2日 下午7:24:33
 * @Desc 描述 Spring Security 配置类.
 * 启用方法安全设置
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String KEY = "zmm.com";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Bean  
    public PasswordEncoder passwordEncoder() {
		// 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }

	
	@Bean  
    public AuthenticationProvider authenticationProvider() {  
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		// 设置密码加密方式
		authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }  
	
	
	/**
	 * 自定义配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 都可以访问
				.antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
				// 都可以访问---H2的数据库控制台
				.antMatchers("/h2-console/**").permitAll()
				// 需要相应的角色才能访问
				.antMatchers("/admins/**").hasRole("ADMIN")
				// 基于 Form 表单登录验证
				.and().formLogin()
				// 自定义登录界面
				.loginPage("/login").failureUrl("/login-error")
				// 启用 remember me
				.and().rememberMe().key(KEY)
				// 处理异常，拒绝访问就重定向到 403 页面
				.and().exceptionHandling().accessDeniedPage("/403");
		// 禁用 H2 控制台的 CSRF 防护--跨站点请求伪造
		http.csrf().ignoringAntMatchers("/h2-console/**");
		// 允许来自同一来源的H2 控制台的请求
		http.headers().frameOptions().sameOrigin();
		
		//CSRF防御
		/*
		 * 通过 referer、token 或者 验证码 来检测用户提交
		 * 尽量不要在页面的链接中暴露用户隐私信息
		 * 对于用户修改删除等操作最好都使用post 操作
		 * 避免全站通用的cookie，严格设置cookie的域
		 */
	}
	
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
}
