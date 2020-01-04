package com.zmm.spring.boot.blog.service;

import java.util.List;

import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;

/**
 * @author 1805783671
 * @version CatalogService-1.0
 * @time 2019年1月4日 下午8:04:26
 * @Desc 描述 Catalog 服务接口
 */
public interface CatalogService {


	/**
	 *
	 * 描述---保存Catalog
	 * @Desc
	 * @方法返回类型 Catalog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:06:45
	 * @param catalog
	 * @return
	 */
	Catalog saveCatalog(Catalog catalog);
	
	/**
	 * 描述---删除Catalog
	 * 
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:06:50
	 * @param id
	 */
	void removeCatalog(Long id);

	/**
	 *
	 * 描述---根据id获取Catalog
	 * @Desc
	 * @方法返回类型 Catalog
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:06:57
	 * @param id
	 * @return
	 */
	Catalog getCatalogById(Long id);
	
	/**
	 *
	 * 描述---获取Catalog列表
	 * @Desc
	 * @方法返回类型 List<Catalog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:07:01
	 * @param user
	 * @return
	 */
	List<Catalog> listCatalogs(User user);
}
