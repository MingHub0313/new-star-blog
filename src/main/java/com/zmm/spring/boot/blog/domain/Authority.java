package com.zmm.spring.boot.blog.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author 1805783671
 * @version Authority-1.0
 * @time 2019年1月2日 下午5:43:47
 * @Desc 描述  权限实体
 */
@Entity // 实体
public class Authority implements GrantedAuthority,Serializable{

	private static final long serialVersionUID = 1L;

	// 主键
	@Id
	// 自增长策略
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 用户的唯一标识
	 */
	private Long id;

	/**
	 * 映射为字段,值不能为空
	 */
	@Column(nullable = false)
	private String name;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getAuthority() {
		return name;
	}

}
