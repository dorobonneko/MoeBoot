package com.moe.bootsetting.services;
import android.app.*;
import android.os.*;
import android.content.*;
import java.lang.Process;
import java.io.*;

public class Cmd extends Service
{
	Process process;
	BufferedWriter bw;
	BufferedReader br;
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		try
		{
			process = Runtime.getRuntime().exec("su");
			bw=new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			br=new BufferedReader(new InputStreamReader(process.getInputStream()));
		}
		catch (IOException e)
		{}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		try
		{
			bw.write("echo start;\n");
			bw.write(intent.getStringExtra("cmd"));
			bw.write("\necho end;\n");
			bw.flush();
		}
		catch (IOException e)
		{}
		handler.sendEmptyMessageDelayed(0,10000);
		return START_NOT_STICKY;
	}
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			String line=null;
			try
			{
				while ((line = br.readLine()) != null)
				{
					if(line.equals("end")){
						stopService(new Intent(getApplicationContext(), Cmd.class));
						break;
					}else if(!br.ready()&&line.equals("start")){
						sendEmptyMessageDelayed(0,10000);
						break;
					}
				}
			}
			catch (IOException e)
			{}
		}
		
	};
	@Override
	public void onDestroy()
	{
		try
		{
			if (bw != null){
				bw.write("\nexit;\n");
				bw.flush();
				bw.close();
				}
		}
		catch (IOException e)
		{}
		if (process != null)
			process.destroy();
		super.onDestroy();
	}
	
}
