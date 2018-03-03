package com.moe.bootsetting;
import android.service.quicksettings.*;
import com.moe.bootsetting.utils.*;
import com.moe.bootsetting.services.*;
import android.content.*;

public class Adb extends TileService
{

	@Override
	public void onStartListening()
	{
		if(PropUtils.getprop("service.adb.tcp.port","-1").equals("5555"))
		getQsTile().setState(Tile.STATE_ACTIVE);
		else
		getQsTile().setState(Tile.STATE_INACTIVE);
		getQsTile().updateTile();
	}

	@Override
	public void onClick()
	{
		if(PropUtils.getprop("service.adb.tcp.port","-1").equals("5555")){
			getQsTile().setState(Tile.STATE_INACTIVE);
			startService(new Intent(this,Cmd.class).putExtra("cmd","setprop service.adb.tcp.port -1;"));
				
		}else{
			getQsTile().setState(Tile.STATE_ACTIVE);
			startService(new Intent(this,Cmd.class).putExtra("cmd","setprop service.adb.tcp.port 5555;"));
			
			}
		getQsTile().updateTile();
	}
	
}
