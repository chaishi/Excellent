package edu.swust.cs.excellent.config;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

import edu.swust.cs.excellent.util.CachedResponseWrapper;
import edu.swust.cs.excellent.util.GzipUtil;
public class GzipHandler extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		
		try {
			CachedResponseWrapper responseWrapper = null ;
			responseWrapper = new CachedResponseWrapper(response);
			
			//把数据传给action
			nextHandler.handle(target, request, responseWrapper, isHandled);
			
			byte[] responseData = responseWrapper.getResponseData();
			System.out.println(responseData.length);
			
			//压缩数据
			responseData=compress(request, responseWrapper, responseData);
			
			System.out.println(responseData.length);
			//发送数据
			response.setHeader("Content-Encoding", "gzip");
			response.setContentLength(responseData.length);
			ServletOutputStream output = response.getOutputStream();
			output.write(responseData);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public byte[] compress(HttpServletRequest request,
			HttpServletResponse response, byte[] responseData){
		
		String acceptEncoding = request.getHeader("Accept-Encoding");
		System.out.println(acceptEncoding);
		if (acceptEncoding == null
				||!acceptEncoding.contains("gzip")) {
			return responseData;
		}
		response.setHeader("Content-Encoding", "gzip");
		return GzipUtil.gzip(responseData);		
	}

}
