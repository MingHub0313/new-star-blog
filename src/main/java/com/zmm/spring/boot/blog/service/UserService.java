package com.zmm.spring.boot.blog.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zmm.spring.boot.blog.domain.User;

/**
 * @author 1805783671
 * @version UserService-1.0
 * @time 2018年12月31日 下午7:09:52
 * @Desc 描述
 */
public interface UserService {
	
	/**
	 * 描述---新增、编辑,保存用户
	 * @Desc
	 * @param user
	 * @return
	 */
	User saveOrUpdateUser(User user);
	
	/**
	 * 描述---注册用户
	 * 
	 * @Desc
	 * @方法返回类型 User
	 * @author 1805783671
	 * @时间 2018年12月31日 下午7:18:17
	 * @param user
	 * @return
	 */
	User registerUser(User user);
	
	/**
	 * 描述---删除用户
	 * @Desc
	 * @param id
	 * @return
	 */
	void removeUser(Long id);
	
	/**
	 * 描述---删除列表里面的用户
	 * @Desc
	 * @param users
	 * @return
	 */
	void removeUsersInBatch(List<User> users);
	
	
	/**
	 * 描述---根据id获取用户
	 * @Desc
	 * @param id
	 * @return
	 */
	User getUserById(Long id);
	
	/**
	 * 描述---获取用户列表
	 * @Desc
	 * @param
	 * @return
	 */
	List<User> listUsers();
	
	/**
	 * 描述---根据用户名进行分页模糊查询
	 * @Desc
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<User> listUsersByNameLike(String name, Pageable pageable);

	
	/**
	 * 描述---根据用户名集合，查询用户详细信息列表
	 * 
	 * @Desc
	 * @方法返回类型 List<User>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午8:07:38
	 * @param userNames
	 * @return
	 */
    List<User> listUsersByUsernames(Collection<String> userNames);
}
