package com.zmm.spring.boot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;

/**
 * @author 1805783671
 * @version CatalogRepository-1.0
 * @time 2019年1月4日 下午8:01:58
 * @Desc 描述
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
	
	/**
	 *
	 * 描述---根据用户查询
	 * @Desc
	 * @方法返回类型 List<Catalog>
	 * @author 1805783671
	 * @时间 2019年1月4日 下午8:03:23
	 * @param user
	 * @return
	 */
    List<Catalog> findByUser(User user);

    /**
     *
	 * 描述---根据用户、分类名称查询
     * @Desc
     * @方法返回类型 List<Catalog>
     * @author 1805783671
     * @时间 2019年1月4日 下午8:03:30
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user,String name);
}
