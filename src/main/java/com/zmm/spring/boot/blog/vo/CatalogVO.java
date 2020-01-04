package com.zmm.spring.boot.blog.vo;

import java.io.Serializable;

import com.zmm.spring.boot.blog.domain.Catalog;

/**
 * @author 1805783671
 * @version CatalogVO-1.0
 * @time 2019年1月4日 下午8:02:36
 * @Desc 描述 Catalog VO.
 */
public class CatalogVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private Catalog catalog;
	
	public CatalogVO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}
