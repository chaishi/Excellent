package edu.swust.cs.excellent.service.inter;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Note;

public interface IEditNote extends IBase<Note>{
	/**
	 * 留言点赞 
	 */
	public boolean up_Note();
	
	/**
	 * 取消赞
	 */
	public boolean end_up_Note();

	/**
	 * 获取所有留言
	 */
	public Page<Note> getAllNoteList(int numPage, int numPerPage); 
}
