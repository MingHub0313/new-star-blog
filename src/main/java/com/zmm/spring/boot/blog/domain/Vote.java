package com.zmm.spring.boot.blog.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

/**
 * @author 1805783671
 * @version Vote-1.0
 * @time 2019年1月4日 下午7:17:50
 * @Desc 描述 点赞实体
 */
@Entity
public class Vote implements Serializable{

	private static final long serialVersionUID = 1L;


	// 主键
	// 自增长策略
	/**
	 *  用户的唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;


	/**
	 * 映射为字段，值不能为空
	 * 由数据库自动创建时间
	 */
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createTime;
 
	protected Vote() {
		
	}
	
	public Vote(User user) {
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
 
	public Timestamp getCreateTime() {
		return createTime;
	}
}
