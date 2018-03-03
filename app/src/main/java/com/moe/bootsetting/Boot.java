package com.moe.bootsetting;
import android.content.*;
import com.moe.bootsetting.services.*;

public class Boot extends BroadcastReceiver
{

	@Override
	public void onReceive(Context p1, Intent p2)
	{
		StringBuilder sb=new StringBuilder();
		sb.append("setprop persist.moe.cpu.size $(getprop persist.moe.cpu.size);\n");
		sb.append("setprop persist.moe.cpu.growth $(getprop persist.moe.cpu.growth);\n");
		sb.append("setprop persist.moe.lowmemorykiller.adj $(getprop persist.moe.lowmemorykiller.adj);\n");
		sb.append("setprop persist.moe.lowmemorykiller.minfree $(getprop persist.moe.lowmemorykiller.minfree);\n");
		sb.append("setprop persist.moe.brevent $(getprop persist.moe.brevent);\n");
		
		p1.startService(new Intent(p1,Cmd.class).putExtra("cmd",sb.toString()));
	}
	
}
