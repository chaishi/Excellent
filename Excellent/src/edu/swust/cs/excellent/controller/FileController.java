package edu.swust.cs.excellent.controller;

import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

import edu.swust.cs.excellent.config.Constant;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class FileController extends CommonController{
	/**
	 * JFinal文件上传
	 */
	public  void  uploadImg() {
		UploadFile file = null;
		file = getFile("imgFile",PathKit.getWebRootPath() + Constant.FILE_TEMPORARY_SVAE_DIR);
		if (file==null){
			renderError("文件源无效");
			return ;
		}
		File source = file.getFile();
		String fileName = file.getFileName();
		String extension = fileName.substring(fileName.lastIndexOf("."));

		boolean flag = false;
		for (String  p:Constant.UPLOAD_IMGAE_EXTENSION){
			if (p.equalsIgnoreCase(extension)){
				flag = true;
			}
		}
		if (!flag){
		    renderError("文件类型错误");
			return;
		}

		try {
			FileInputStream fis = new FileInputStream(source);
			fileName = generateTimeString() + extension;
			File targetDir = new File(PathKit.getWebRootPath() + Constant.UPLOAD_IMGAGE_PATH);
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			File target = new File(targetDir, fileName);
			if (!target.exists()) {
				target.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(target);
			byte[] bts = new byte[512];
			while (fis.read(bts, 0, 512) != -1) {
				fos.write(bts, 0, 512);
			}
			fos.close();
			fis.close();
			source.delete();
			setAttr("url", Constant.BASE_PATH + Constant.UPLOAD_IMGAGE_PATH+fileName);
			setAttr("error", 0);
		} catch (FileNotFoundException e) {
			setAttr("error", 1);
			setAttr("message", "上传出现错误，请稍后再上传");
		} catch (IOException e) {
			setAttr("error", 1);
			setAttr("message", "文件写入服务器出现错误，请稍后再上传");
		}
		renderJson();
	}


	public  void  uploadfile() {
		UploadFile file = null;
		file = getFile("file",Constant.FILE_TEMPORARY_SVAE_DIR);
		if (file==null){
			renderError("文件源无效");
			return ;
		}
		File source = file.getFile();
		String fileName = file.getFileName();
		String extension = fileName.substring(fileName.lastIndexOf("."));

		boolean flag = false;
		for (String  p:Constant.UPLOAD_FILE_EXTENSION){
			if (p.equalsIgnoreCase(extension)){
				flag = true;
			}
		}
		if (!flag){
		    renderError("文件类型错误");
			return;
		}

		try {
			FileInputStream fis = new FileInputStream(source);
			fileName =  generateTimeString() + extension;
			File targetDir = new File(PathKit.getWebRootPath()  + Constant.UPLOAD_FILE_PATH );
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			File target = new File(targetDir, fileName);
			if (!target.exists()) {
				target.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(target);
			byte[] bts = new byte[512];
			while (fis.read(bts, 0, 512) != -1) {
				fos.write(bts, 0, 512);
			}
			fos.close();
			fis.close();
			source.delete();
			setAttr("url", Constant.BASE_PATH + Constant.UPLOAD_FILE_PATH+fileName);
			setAttr("error", 0);
		} catch (FileNotFoundException e) {
			setAttr("error", 1);
			setAttr("message", "上传出现错误，请稍后再上传");
		} catch (IOException e) {
			setAttr("error", 1);
			setAttr("message", "文件写入服务器出现错误，请稍后再上传");
		}
		renderJson();
	}
	private  String generateTimeString() {
		Date t = Calendar.getInstance().getTime();
		SimpleDateFormat df=new SimpleDateFormat("yyMMddHHmmss"); 
		return df.format(t);
	}

}
