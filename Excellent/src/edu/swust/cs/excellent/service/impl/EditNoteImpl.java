package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Note;
import edu.swust.cs.excellent.service.inter.IEditNote;

@Service("editNoteImpl")
public class EditNoteImpl implements IEditNote {

	@Override
	public boolean add(Note t) {
		try {
			t.save();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			Note.dao.deleteById(id);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public Note merge(Note t) {
		try {
			t.set("is_passed", 1).update();
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<Note> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note getDetail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean up_Note() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean end_up_Note() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 暂时按时间顺序排列
	 */
	@Override
	public Page<Note> getList(int numPage, int numPerPage) {
		return Note.dao.paginate(numPage, numPerPage, "select id,noter_id,content,timestamp", "from notes where is_passed=1 order by timestamp");
	}
	
	/**
	 * 暂时按时间顺序排序
	 */
	@Override
	public Page<Note> getAllNoteList(int numPage, int numPerPage){
		return Note.dao.paginate(numPage, numPerPage,"select id,noter_id,content,timestamp" , " from notes order by is_passed asc,timestamp desc");
	}
	
	

}
