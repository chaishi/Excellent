package edu.swust.cs.excellent.util;

import java.io.File;


import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;

import edu.swust.cs.excellent.config.Constant;

public class CleanLogJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		File directory=new File(PathKit.getWebRootPath()+Constant.LOG4JDIR);
		if (directory.isDirectory()){
			File[] files=directory.listFiles();
			for (File p:files){
				//warn and error
				if (p.getName().equals("WE")){
					if (getSize(p)/1024/1024>Constant.WARN_ERROR_LOG_LIMIT){
						Logger logger=Logger.getLogger("MAIL");
						logger.error("系统出现了异常,可能遭受攻击或者负载过大,请及时处理");
						//备份数据库以防万一
						new DBBackUpJob().execute(null);
					}
					dealFile(p,"we");
				}
				else if (p.getName().equals("INFO")){
					dealFile(p,"info");
				}
			}
		}
	}

	private void dealFile(File f,String type){
		if (f.isDirectory()){
			dealFile(f,type);
		}
		Date lastM=new Date(f.lastModified());
		Date curD=new Date();
		Long ms=curD.getTime()-lastM.getTime();
		int days=(int) (ms/(1000*24*60*60));
		if (type.equals("we")){
			if (days>Constant.WARN_ERROR_LOG_LIFETIME){
				f.delete();
			}
		}else if (type.equals("info")){
			if (days>Constant.INFO_LOG_LIFETIME){
				f.delete();
			}
		}
	}

	private long getSize(File d){
		long sum=0;
		if (!d.isDirectory()){
			return 0;
		}
		File[] files=d.listFiles();
		for (File p:files){
			if (p.isDirectory()){
				sum+=getSize(p);
			}else{
				sum+=p.length();
			}
		}
		return sum;
	}

}
