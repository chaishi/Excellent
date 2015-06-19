package edu.swust.cs.excellent.service.inter;

import com.jfinal.plugin.activerecord.Page;

public interface IBase<T> {
   public boolean add(T t);
   public boolean delete(int id);
   public T merge(T t);
   public Page<T> getList();
   public Page<T> getList(int numPage,int numPerPage);
   public T getDetail(int id);
   public  String getLastError();
}
