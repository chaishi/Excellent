package edu.swust.cs.excellent.service.impl;

import com.jfinal.log.Logger;

public class BaseImpl {
	Logger logger_disk = Logger.getLogger("Disk"); 
	Logger logger_mail = Logger.getLogger("MAIL");
	Logger logger_disk_we = Logger.getLogger("DiskWE");


	protected String lastError="";
	public String getLastError(){
		return lastError;
	}
}
