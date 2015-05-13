package edu.swust.cs.excellent.service.impl;

import com.jfinal.plugin.activerecord.Page;

import edu.swust.cs.excellent.model.News;
import edu.swust.cs.excellent.service.inter.IEditNews;

public class EditNewsImpl implements IEditNews {

	@Override
	public boolean add(News t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public News merge(News t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<News> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News getDetail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean up_News() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean end_up_News() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commentNews(int newId, String comment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteComment(int commentId, boolean p) {
		// TODO Auto-generated method stub
		return false;
	}

}
