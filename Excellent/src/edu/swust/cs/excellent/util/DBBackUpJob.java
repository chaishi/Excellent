package edu.swust.cs.excellent.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.spi.LoggerRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;

import edu.swust.cs.excellent.config.Constant;

public class DBBackUpJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		backup();

		clean();
	}

	
	private void backup(){
		try {
			Runtime rt=Runtime.getRuntime();
			String cmd="cmd /c mysqldump -u"+ Constant.DB_USER
					+ " -p"+Constant.DB_PSWD
					+ " --set-charset=utf8 excellent";
			Process child = rt.exec(cmd);
			InputStream inputStream = child.getInputStream();
			InputStreamReader ips = new InputStreamReader(inputStream); 

			String inStr=null;
			StringBuffer sb=new StringBuffer("");
			String outStr;

			BufferedReader br = new BufferedReader(ips);
			while ((inStr=br.readLine())!=null){
				//System.out.println(inStr+"\r\n");
				sb.append(inStr+"\r\n");
			}
			outStr=sb.toString();


			Date t = Calendar.getInstance().getTime();
			SimpleDateFormat df=new SimpleDateFormat("yy_MM_dd"); 

			if (Constant.DB_IS_TOSENDMAIL){
				Logger.getLogger("MAIL").error("并不是一场\r\n数据库备\r\n"+"发送时间:"+df.format(t)+"\r\n"+outStr);
			}

			String fileName=PathKit.getWebRootPath()+"\\"+Constant.DB_BACKUP_PATH+"\\DB_"+df.format(t)+".sql";
			File file=new File(fileName);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			FileOutputStream fout=new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(fout);

			writer.write(outStr);
			writer.flush();

			inputStream.close();
			ips.close();
			br.close();
			writer.close();
			fout.close();
		} catch (IOException e) {
			Logger.getLogger("DiskWE").error("数据库备份异常",e);
			Logger.getLogger("MAIL").error("数据库备份异常",e);
		}
	}
	
//	private   void backup(){
//		try {
//			
//			Date t = Calendar.getInstance().getTime();
//			
//			SimpleDateFormat df=new SimpleDateFormat("yy_MM_dd"); 
//			String fileName=PathKit.getWebRootPath()+"\\"+Constant.DB_BACKUP_PATH+"\\DB_"+df+".sql";
//			
//			File file  = new File(fileName);
//
//			Runtime rt=Runtime.getRuntime();
//			String cmd="cmd /c mysqldump -u "+ Constant.DB_USER
//					+ " -p"+Constant.DB_PSWD
//					+ " --default-character-set=utf8 excellent>"+fileName;
//			Process child = rt.exec(cmd);
//		
//		} catch (IOException e) {
//			Logger.getLogger("DiskWE").error("数据库备份异常",e);
//			Logger.getLogger("MAIL").error("数据库备份异常",e);
//		}
//	}


	private class FileComparable implements  Comparable<FileComparable>{

		public String fn;
		public File file;
		public  FileComparable() {
			// TODO Auto-generated constructor stub
		}
		public FileComparable(File file){
			this.fn=file.getName();
			this.file=file;
		}
		//按时间升序排列
		@Override
		public int compareTo(FileComparable o) {
			return this.fn.compareTo(o.fn);
		}
		public boolean delete(){
			return file.delete();
		}

	}

	private void clean(){
		File file=new File(PathKit.getWebRootPath()+"\\"+Constant.DB_BACKUP_PATH);
		if (!file.isDirectory()){
			Logger.getLogger("DiskWE").error("数据库备份文件夹异常");
			Logger.getLogger("MAIL").error("数据库备份文佳佳异常");
		}else{
			File[] fs=file.listFiles();

			if (fs.length<=Constant.DB_BACKUP_COPIES)
				return;
			FileComparable[] fcs=new FileComparable[fs.length];
			for  (int i=0;i<fs.length;i++){
				fcs[i]=new FileComparable(fs[i]);
			}
			Arrays.sort(fcs);

			/**
			 * 保留最近的若干份备份,出现异常最多影响两份备份
			 */
			for (int i=fcs.length-1-Constant.DB_BACKUP_COPIES;i>=0;i--){
				fcs[i].delete();
			}
		}
	}

}
