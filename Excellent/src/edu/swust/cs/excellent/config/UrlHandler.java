package edu.swust.cs.excellent.config;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jfinal.handler.Handler;
/**
 * @author MONEY
 */
public class UrlHandler extends Handler{
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled){
	     
		HttpSession session=request.getSession();
		String url=request.getRequestURI();
		if(url.indexOf("Mngr")>0){
			String usertype=(String) session.getAttribute("userType");
			if(usertype==null||
					(usertype!=null&&!usertype.equals(Constant.ADMIN))){
					try {
						response.sendRedirect(request.getContextPath()+File.separator+"pages"+File.separator+"login.html");
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		nextHandler.handle(target, request, response, isHandled);
	}
}
