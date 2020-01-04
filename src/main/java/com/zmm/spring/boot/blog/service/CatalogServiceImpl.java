package com.zmm.spring.boot.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmm.spring.boot.blog.domain.Catalog;
import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.repository.CatalogRepository;

/**
 * @author 1805783671
 * @version CatalogServiceImpl-1.0
 * @time 2019年1月4日 下午8:07:33
 * @Desc 描述 Catalog 服务接口实现.
 */
@Service
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository;


	@Override
	public Catalog saveCatalog(Catalog catalog) {
		 // 判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(),catalog.getName());
        if(list !=null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
		return catalogRepository.save(catalog);
	}


	@Override
	public void removeCatalog(Long id) {
		catalogRepository.delete(id);

	}


	@Override
	public Catalog getCatalogById(Long id) {
		return catalogRepository.findOne(id);
	}


	@Override
	public List<Catalog> listCatalogs(User user) {
		return catalogRepository.findByUser(user);
	}

}
