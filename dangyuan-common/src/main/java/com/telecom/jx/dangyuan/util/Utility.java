package com.telecom.jx.dangyuan.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Utility {
	
	public static void makeDirectory(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	public static boolean isNotEmpty(String str){
		return null != str && str.trim().length() > 0;
	}

	public static void write(HttpServletResponse response,String msg) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
}
