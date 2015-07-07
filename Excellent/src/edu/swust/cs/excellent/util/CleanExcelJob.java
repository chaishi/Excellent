package edu.swust.cs.excellent.util;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;

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

		if (!root.exists())
			root.mkdir();

		if (root==null ||  !root.canWrite()){
			Logger.getLogger("DiskWE").error(Constant.FILE_TEMPORARY_SVAE_DIR+"文件暂存路径无法打开");
			System.out.println("文件暂存路径无法打开");

		}
		File[] fs = root.listFiles();
		for (int i=0;i<fs.length;i++) {
			fs[i].delete();
		}
	}

}
