package edu.swust.cs.excellent.service.impl;

import org.springframework.stereotype.Service;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.Note;
import edu.swust.cs.excellent.service.inter.IEditNote;

@Service("editNoteImpl")
public class EditNoteImpl extends BaseImpl implements IEditNote {

	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");
	
	@Override
	public boolean add(Note t) {
		try {
			t.save();
			logger_disk.info("新增留言:id"+t.getInt("id"));
			return true;
		} catch (Exception e) {
			logger_disk.error("新增留言:"+"失败");
            logger_disk_we.error("新增留言:失败");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			Note.dao.deleteById(id);
			logger_disk.info("删除留言:id"+id);
			return true;
		} catch (Exception e) {
			logger_disk.info("删除留言:id"+id+"失败");
			logger_disk_we.error("删除留言:id"+id+"失败");
		}
		return false;
	}

	@Override
	public Note merge(Note t) {
		try {
			t.set("is_passed", 1).update();
			logger_disk.info("通过留言:id"+t.getInt("id"));
			return t;
		} catch (Exception e) {
			logger_disk.info("通过留言:id"+t.getInt("id")+"失败");
			logger_disk_we.error("通过留言:id"+t.getInt("id")+"失败");
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
		return Note.dao.paginate(numPage, numPerPage, "select id,noter_id,content,timestamp", "from notes "
				//+ "where is_passed=1 "
				+ "order by timestamp desc");
	}
	
	/**
	 * 暂时按时间顺序排序
	 */
	@Override
	public Page<Note> getAllNoteList(int numPage, int numPerPage){
		return Note.dao.paginate(numPage, numPerPage,"select id,noter_id,content,timestamp" , " from notes order by is_passed asc,timestamp desc");
	}

		

}
