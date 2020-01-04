package com.zmm.spring.boot.blog.service;

import com.zmm.spring.boot.blog.domain.Vote;

/**
 * @author 1805783671
 * @version VoteService-1.0
 * @time 2019年1月4日 下午7:24:12
 * @Desc 描述 Vote 服务接口
 */
public interface VoteService {
	
	/**
	 * 描述---根据id获取 Vote
	 * 
	 * @Desc
	 * @方法返回类型 Vote
	 * @author 1805783671
	 * @时间 2019年1月4日 下午7:24:29
	 * @param id
	 * @return
	 */
	Vote getVoteById(Long id);
	
	/**
	 *
	 * 描述---删除Vote
	 * @Desc
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午7:24:37
	 * @param id
	 */
	void removeVote(Long id);

}
