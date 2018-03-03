package com.moe.bootsetting;
import android.service.quicksettings.*;
import com.moe.bootsetting.utils.*;
import android.content.*;
import com.moe.bootsetting.services.*;
import com.moe.bootsetting.database.ModeDatabase;
import com.moe.bootsetting.entity.Mode;
import java.util.List;

public class BatterySetting extends TileService implements ModeDatabase.Callback
{
	private List<Mode> list;
	private SharedPreferences moe;
	private Mode mode;
	@Override
	public void onCreate()
	{
		super.onCreate();
		ModeDatabase md=ModeDatabase.getInstance(this);
		list=md.query();
		md.setCallback(this);
		moe=getSharedPreferences("moe",0);
		mode=md.query(moe.getString("mode",null));
	}

	@Override
	public void onTileRemoved()
	{
		getQsTile().setState(Tile.STATE_UNAVAILABLE);
		getQsTile().updateTile();
	}

	@Override
	public void onStartListening()
	{
		if(mode==null)
			getQsTile().setLabel("Unknow");
		else
			getQsTile().setLabel(mode.getName());
		//getQsTile().setState(Tile.STATE_ACTIVE);
		getQsTile().updateTile();
	}

	@Override
	public void onTileAdded()
	{
		getQsTile().setState(Tile.STATE_ACTIVE);
		getQsTile().updateTile();
	}
	
	@Override
	public void onClick()
	{
		if(list.size()==0)return;
		int index=list.indexOf(mode);
			index++;
		if(index>=list.size())
			index=0;
		mode=list.get(index);
		startService(new Intent(this,Cmd.class).putExtra("cmd","setprop persist.moe.cpu.governor "+mode.getCpu_governor()
		+"\nsetprop persist.moe.gpu.governor "
		+mode.getGpu_governor()
		+"\nsetprop persist.moe.cpu.size "
		+mode.getCpu_size()
		+"\nsetprop persist.moe.cpu.max_freq "
		+mode.getCpu_max_freq()
		+"\nsetprop persist.moe.cpu.growth "
		+mode.getGrowth()
		));
		moe.edit().putString("mode",mode.getName()).commit();
		getQsTile().setLabel(mode.getName());
		getQsTile().updateTile();
	}

	@Override
	public void onAdded(Mode mode)
	{
		list.add(mode);
	}

	@Override
	public void onDelete(Mode mode)
	{
		list.remove(mode);
	}

	@Override
	public void onDestroy()
	{
		ModeDatabase.getInstance(this).setCallback(null);
		super.onDestroy();
	}


	
}
