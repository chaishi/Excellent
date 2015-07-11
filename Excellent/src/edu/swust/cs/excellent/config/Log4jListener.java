package edu.swust.cs.excellent.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.jfinal.core.Const;
import com.jfinal.kit.PathKit;

public class Log4jListener implements ServletContextListener {
	 public static final String log4jDirKey=Constant.LOG4JDIRKEY;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
          System.getProperties().remove(log4jDirKey);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	  // String log4jDir = Constant.LOG4JDIR;
	   String log4jDir = PathKit.getRootClassPath()+Constant.LOG4JDIR;
	 //  System.out.println("log4jdir:"+log4jDir);
	   System.setProperty(log4jDirKey,log4jDir );
	}

}
