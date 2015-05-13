package edu.swust.cs.excellent.service.inter;

import edu.swust.cs.excellent.model.News;

/**
 * 参与新闻活动
 * @author moulaotou
 *
 */
public interface IEditNews extends IBase<News> {
	/**
	 * 新闻点赞 
	 */
	public boolean up_News();
	
	/**
	 * 取消赞
	 */
	public boolean end_up_News();
	
	/**
	 * 评论新闻
	 * @param newsId 新闻Id
	 *        comment 评论
	 */
	public boolean commentNews(int newId,String comment);
	
	/**
	 * 删除新闻评论
	 * @param commentId 评论Id
	 *        p true 教师
	 *          false 学生
	 */
	public boolean deleteComment(int commentId,boolean p);
	
}
