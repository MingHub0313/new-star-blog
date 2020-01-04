package com.zmm.spring.boot.blog.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmm.spring.boot.blog.domain.Vote;
import com.zmm.spring.boot.blog.repository.VoteRepository;

/**
 * @author 1805783671
 * @version VoteServiceImpl-1.0
 * @time 2019年1月4日 下午7:25:00
 * @Desc 描述 Vote 服务实现
 */
@Service
public class VoteServiceImpl implements VoteService {
	
	@Autowired
	private VoteRepository voteRepository;

	@Override
	public Vote getVoteById(Long id) {
		return voteRepository.findOne(id);
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeVote(Long id) {
		voteRepository.delete(id);
	}

}
