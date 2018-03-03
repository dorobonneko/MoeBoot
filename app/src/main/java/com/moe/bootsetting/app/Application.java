package com.moe.bootsetting.app;
import android.app.*;
import android.content.*;
import com.moe.bootsetting.*;
import android.os.*;

public class Application extends android.app.Application implements Thread.UncaughtExceptionHandler
{

	@Override
	public void uncaughtException(Thread p1, Throwable p2)
	{
		StringBuilder sb=new StringBuilder(p2.getMessage());
		
		for(StackTraceElement ste:p2.getStackTrace())
		sb.append("\n").append(ste.toString());
		startActivity(new Intent(this,CrashActivity.class).putExtra("error",sb.toString()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		Thread.currentThread().setDefaultUncaughtExceptionHandler(this);
	}
	
}
