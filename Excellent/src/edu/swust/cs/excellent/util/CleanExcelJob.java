package edu.swust.cs.excellent.util;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.PathKit;

import edu.swust.cs.excellent.config.Constant;


/**
 * 
 * 清理产生的临时excel
 *
 */
public class CleanExcelJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		File root = new File(PathKit.getWebRootPath()  + Constant.FILE_TEMPORARY_SVAE_DIR);
		
		if (root==null){
			return ;
		}
		
		File[] fs = root.listFiles();
		for (int i=0;i<fs.length;i++) {
			fs[i].delete();
		}
	}

}
