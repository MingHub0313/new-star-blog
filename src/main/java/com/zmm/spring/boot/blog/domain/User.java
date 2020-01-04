package com.zmm.spring.boot.blog.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 1805783671
 * @version User-1.0
 * @时间 2018年12月24日 下午9:33:32 User 实体
 */
@Entity
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	// 自增长策略
	/**
	 * 用户的唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	/**
	 * 映射为字段，值不能为空
	 */
	@NotEmpty(message = "姓名不能为空")
	@Size(min = 2, max = 20)
	@Column(nullable = false, length = 20)
	private String name;

	@NotEmpty(message = "邮箱不能为空")
	@Size(max = 50)
	@Email(message = "邮箱格式不对")
	@Column(nullable = false, length = 50, unique = true)
	private String email;


	/**
	 * 用户账号，用户登录时的唯一标识
	 */
	@NotEmpty(message = "账号不能为空")
	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20, unique = true)
	private String username;


	/**
	 * 登录时密码
	 */
	@NotEmpty(message = "密码不能为空")
	@Size(max = 100)
	@Column(length = 100)
	private String password;


	/**
	 * 头像图片地址
	 */
	@Column(length = 200)
	private String avatar; //

	/**
	 * 用户与权限的关系 ---一对多的关系
	 */
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = 	@JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @Desc 描述--- 直接设置
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月3日 下午5:27:14
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * 
	 * @Desc 描述--- 加密密码
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月3日 下午5:26:48
	 * @param password
	 */
	public void setEncodePassword(String password) {
		PasswordEncoder  encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(password);
		this.password = encodePasswd;
	}

	protected User() {
		// 设为protected ，防止直接使用
	}

	public User(Long id, String name, String email, String username) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return String.format("User[id=%d,name='%s',email='%s',username='%s']", id, name, email, username);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 需将 List<Authority> 转成 List<SimpleGrantedAuthority>[字符串角色信息],否则前端拿不到角色列表名称
		List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
		for (GrantedAuthority authority : this.authorities) {
			simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
		return simpleAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		// 账号不过期
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		// 账号没有锁
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		// 账号证书没有过期
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		// 账号是否未启用
		return true;
	}
}
