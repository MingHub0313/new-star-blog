package com.zmm.spring.boot.blog.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmm.spring.boot.blog.domain.User;
import com.zmm.spring.boot.blog.repository.UserRepository;

/**
 * @author 1805783671
 * @version UserServiceImpl-1.0
 * @time 2018年12月31日 下午7:11:57
 * @Desc 描述
 */
@Service
public class UserServiceImpl implements UserService ,UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public User saveOrUpdateUser(User user) {
		return userRepository.save(user);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public User registerUser(User user) {
		return userRepository.save(user);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeUser(Long id) {
		userRepository.delete(id);
	}

	
	@Override
	public void removeUsersInBatch(List<User> users) {
		userRepository.deleteInBatch(users);

	}

	
	@Override
	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}

	
	@Override
	public List<User> listUsers() {
		return userRepository.findAll();
	}

	
	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
		// 模糊查询
		name = "%" + name + "%";
		Page<User> users = userRepository.findByNameLike(name, pageable);
		return users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		return userRepository.findByUsernameIn(usernames);
	}

	

}
