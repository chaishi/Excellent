package edu.swust.cs.excellent.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.coyote.http11.filters.GzipOutputFilter;

public class GzipUtil {
	public static byte[] gzip(byte[] data){
		ByteArrayOutputStream byteOutput=new ByteArrayOutputStream(10240);
		GZIPOutputStream output =null;
		try {
			output = new GZIPOutputStream(byteOutput);
			output.write(data);
		} catch (IOException e) {
			throw new RuntimeException("Gzip failed.", e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
		return byteOutput.toByteArray();//将字节流转换为byte数组
	}
}
