package com.zmm.spring.boot.blog.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 1805783671
 * @version Catalog-1.0
 * @time 2019年1月4日 下午7:59:34
 * @Desc 描述  分类实体
 */
@Entity
public class Catalog implements Serializable{
	
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
	@NotEmpty(message = "名称不能为空")
	@Size(min=2, max=30)
	@Column(nullable = false)
	private String name;

	/**
	 * 与用户一对一   (user_id)
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
 
	protected Catalog() {
		
	}
	
	public Catalog(User user, String name) {
		this.name = name;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
