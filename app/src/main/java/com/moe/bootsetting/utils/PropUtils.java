package com.moe.bootsetting.utils;
import java.lang.reflect.*;
import java.io.*;

public class PropUtils
{
	public static String getprop(String key, String defaultValue) {
		String value = defaultValue;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class );
			value = (String)(   get.invoke(c, key, defaultValue ));
		} catch (Exception e) {
			}
		return value;
	}
	public static void setprop(String key,String value){
		try
		{
			Process p=Runtime.getRuntime().exec("/system/bin/sh");
			OutputStream os=p.getOutputStream();
			os.write("setprop ".getBytes());
			os.write(key.getBytes());
			os.write(" ".getBytes());
			os.write(value.getBytes());
			os.write(";\nexit;".getBytes());
			os.flush();
			
			os.close();
			p.destroy();
		}
		
		catch (IOException e)
		{}
	}
}
