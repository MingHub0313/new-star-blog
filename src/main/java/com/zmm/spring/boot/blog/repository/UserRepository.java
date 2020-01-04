package com.zmm.spring.boot.blog.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zmm.spring.boot.blog.domain.User;

/**
 * @author 1805783671
 * @version UserRepository-1.0
 * @时间 2018年12月24日 下午9:38:06 UserResponsitory 接口
 * @Desc 继承JpaRepository,有各种实现
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 *
	 * 描述--- 根据用户姓名查询用户列表(分页)
	 * @Desc
	 * @方法返回类型 Page<User>
	 * @author 1805783671
	 * @时间 2018年12月31日 下午7:07:37
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<User> findByNameLike(String name, Pageable pageable);

	/**
	 *
	 * 描述---根据用户账号查询用户
	 * @Desc
	 * @方法返回类型 User
	 * @author 1805783671
	 * @时间 2018年12月31日 下午7:07:59
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 创建或者修改用户
	 *
	 * @param user
	 * @return
	 */
	//public User saveOrUpdateUser(User user);

	/**
	 * 删除用户
	 *
	 * @param id
	 */
	//public void deleteUser(Long id);

	/**
	 * 根据id查用户
	 *
	 * @param id
	 * @return
	 */
	//public User getUserById(Long id);

	/**
	 *
	 * 描述---根据名称列表查询用户列表
	 * @Desc
	 * @方法返回类型 List<User>
	 * @author 1805783671
	 * @时间 2019年1月7日 下午8:08:55
	 * @param userNames
	 * @return
	 */
	List<User> findByUsernameIn(Collection<String> userNames);

}
