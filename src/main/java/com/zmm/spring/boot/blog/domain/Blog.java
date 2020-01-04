package com.zmm.spring.boot.blog.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.rjeschke.txtmark.Processor;

/**
 * @author 1805783671
 * @version Blog-1.0
 * @time 2019年1月4日 下午1:45:06
 * @Desc 描述 博客实体
 */
@Entity // 实体
public class Blog implements Serializable{

	private static final long serialVersionUID = 1L;

	// 主键
	@Id
	// 自增长策略
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 用户的唯一标识
	 */
	private Long id;

	/**
	 *  映射为字段,值不能为空
	 */
	@NotEmpty(message = "标题不能为空")
	@Size(min=2, max=50)
	@Column(nullable = false, length = 50)
	private String title;


	/**
	 * 映射为字段，值不能为空
	 */
	@NotEmpty(message = "摘要不能为空")
	@Size(min=2, max=300)
	@Column(nullable = false)
	private String summary;

	/**
	 * 映射为字段,值不能为空
	 *  大对象,映射 MySQL 的 Long Text 类型
	 */
	@Lob
	@Basic(fetch=FetchType.LAZY) // 懒加载
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)
	@Column(nullable = false)
	private String content;

	/**
	 * 将 md 转为 html
	 * 大对象,映射 MySQL 的 Long Text 类型
	 * 懒加载
	 * 映射为字段，值不能为空
	 */
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)
	@Column(nullable = false)
	private String htmlContent;

	/**
	 * 与用户表一对一   (user_id)
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	/**
	 * 由数据库自动创建时间
	 * 映射为字段,值不能为空
	 */
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createTime;


	/**
	 * 访问量、阅读量
	 */
	@Column(name="readSize")
	private Integer  readSize = 0;


	/**
	 * 评论量
	 */
	@Column(name="commentSize")
	private Integer  commentSize = 0;


	/**
	 * 点赞量
	 */
	@Column(name="voteSize")
	private Integer  voteSize = 0;

	/**
	 * 标签
	 */
	@Column(name="tags",length=100)
	private String tags;

	/**
	 * 与评论一对多  (comment_id)
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
	private List<Comment> comments;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "blog_vote", 
		joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
	private List<Vote> votes;

	/**
	 * 与分类一对一 (catalog_id)
	 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="catalog_id")
	private Catalog catalog;
	
	
	protected Blog() {
		
	}
	public Blog(String title, String summary,String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		//将 Markdown 内容转换为HTML 格式
		this.htmlContent = Processor.process(content);
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
 
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}
	
	public Integer  getReadSize() {
		return readSize;
	}
	
	public void setReadSize(Integer  readSize) {
		this.readSize = readSize;
	}

	public Integer  getCommentSize() {
		return commentSize;
	}

	public void setCommentSize(Integer  commentSize) {
		this.commentSize = commentSize;
	}

	public Integer  getVoteSize() {
		return voteSize;
	}

	public void setVoteSize(Integer voteSize) {
		this.voteSize = voteSize;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
		this.commentSize=this.comments.size();
	}
	
	/**
	 * @Desc添加评论
	 * @param comment
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		this.commentSize = this.comments.size();
	}

	/**
	 * @Desc删除评论
	 * @param commentId
	 */
	public void removeComment(Long commentId) {
		for (int index=0; index < this.comments.size(); index ++ ) {
			if (comments.get(index).getId().equals(commentId)) {
				this.comments.remove(index);
				break;
			}
		}
		this.commentSize = this.comments.size();
	}
	
	/**
	 * 
	 * @Desc 描述---点赞
	 * @方法返回类型 boolean
	 * @author 1805783671
	 * @时间 2019年1月4日 下午7:21:15
	 * @param vote
	 * @return
	 */
	public boolean addVote(Vote vote) {
		boolean isExist = false;
		// 判断重复
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getUser().getId().equals(vote.getUser().getId())) {
				isExist = true;
				break;
			}
		}
		
		if (!isExist) {
			this.votes.add(vote);
			this.voteSize = this.votes.size();
		}

		return isExist;
	}
	/**
	 * 
	 * @Desc 描述---取消点赞
	 * @方法返回类型 void
	 * @author 1805783671
	 * @时间 2019年1月4日 下午7:21:21
	 * @param voteId
	 */
	public void removeVote(Long voteId) {
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getId().equals(voteId)) {
				this.votes.remove(index);
				break;
			}
		}
		
		this.voteSize = this.votes.size();
	}
	public List<Vote> getVotes() {
		return votes;
	}
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
		this.voteSize = this.votes.size();
	}
	
	public Catalog getCatalog() {
		return catalog;
	}
	
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	

}
