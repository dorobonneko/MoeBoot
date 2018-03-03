package com.moe.bootsetting.adapter;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import com.moe.bootsetting.R;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Switch;
import android.content.pm.PackageInfo;
import java.util.List;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import com.moe.bootsetting.utils.ImageCache;

public class BootAdapter extends AbstractAdapter<BootAdapter.ViewHolder>
{
	private List<PackageInfo> list;
public BootAdapter(List<PackageInfo> list){
	this.list=list;
}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method
		return new ViewHolder(LayoutInflater.from(p1.getContext()).inflate(R.layout.package_item,p1,false));
	}

	@Override
	public void onBindViewHolder(ViewHolder p1, int p2)
	{
		p1.title.setText(list.get(p2).applicationInfo.loadLabel(p1.itemView.getContext().getPackageManager()));
		p1.summary.setText(list.get(p2).packageName);
		ImageCache.setLogo(p1.logo,list.get(p2).packageName);
		p1.state.setChecked(p1.itemView.getContext().getPackageManager().checkPermission("android.permission.RECEIVE_BOOT_COMPLETED", list.get(p2).packageName)==PackageManager.PERMISSION_GRANTED);
		
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return list.size();
	}
	public class ViewHolder extends AbstractAdapter.ViewHolder{
		TextView title,summary;
		ImageView logo;
		Switch state;
		public ViewHolder(View v){
			super(BootAdapter.this,v);
			title=(TextView) v.findViewById(R.id.title);
			summary=(TextView) v.findViewById(R.id.summary);
			logo=(ImageView) v.findViewById(R.id.icon);
			state=(Switch) v.findViewById(R.id.switchCompat);
		}
	}
}
