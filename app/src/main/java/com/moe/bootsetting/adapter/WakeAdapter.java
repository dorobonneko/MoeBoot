package com.moe.bootsetting.adapter;
import android.content.pm.PackageInfo;
import java.util.List;
import com.moe.bootsetting.adapter.BootAdapter.ViewHolder;
import android.content.pm.PackageManager;
import com.moe.bootsetting.utils.ImageCache;

public class WakeAdapter extends BootAdapter
{
	private List<PackageInfo> list;
	public WakeAdapter(List<PackageInfo> list){
		super(list);
		this.list=list;
	}

	@Override
	public void onBindViewHolder(BootAdapter.ViewHolder p1, int p2)
	{
		p1.title.setText(list.get(p2).applicationInfo.loadLabel(p1.itemView.getContext().getPackageManager()));
		p1.summary.setText(list.get(p2).packageName);
		ImageCache.setLogo(p1.logo,list.get(p2).packageName);
		p1.state.setChecked(p1.itemView.getContext().getPackageManager().checkPermission("android.permission.WAKE_LOCK", list.get(p2).packageName)==PackageManager.PERMISSION_GRANTED);
		
	}
	
}
