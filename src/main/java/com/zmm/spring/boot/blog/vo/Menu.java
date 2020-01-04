package com.zmm.spring.boot.blog.vo;

import java.io.Serializable;

/**
 * @author 1805783671
 * @version Menu-1.0
 * @time 2018年12月31日 下午8:56:35
 * @Desc 描述 菜单 值对象.
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String url;

	public Menu(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
