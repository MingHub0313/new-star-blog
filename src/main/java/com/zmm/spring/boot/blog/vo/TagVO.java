package com.zmm.spring.boot.blog.vo;

/**
 * @author 1805783671
 * @version TagVO-1.0
 * @time 2019年1月7日 下午7:29:02
 * @Desc 描述 Tag 值对象
 */
public class TagVO {
	
	private String name;
	private Long count;
	
	public TagVO(String name, Long count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
