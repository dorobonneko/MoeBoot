package com.moe.bootsetting.fragment;
import com.moe.bootsetting.utils.PackagesUtils;
import com.moe.bootsetting.adapter.BootAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import android.content.pm.PackageInfo;
import android.view.View;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import com.moe.bootsetting.adapter.WakeAdapter;
import android.support.v4.view.ViewCompat;

public class WakeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
	private PackagesUtils pu;
	private SwipeRefreshLayout refresh;
	private BootAdapter ba;
	private ArrayList<PackageInfo> list;
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		pu=new PackagesUtils(getActivity().getPackageManager());
		refresh=(SwipeRefreshLayout) view;
		refresh.setOnRefreshListener(this);
		RecyclerView rv=(RecyclerView) refresh.getChildAt(1);
		rv.setLayoutManager(new LinearLayoutManager(getContext()));
		rv.setAdapter(ba=new WakeAdapter(list=new ArrayList<>()));
		ViewCompat.setNestedScrollingEnabled(rv,false);
		
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onActivityCreated(savedInstanceState);
		refresh.setRefreshing(true);
		onRefresh();
	}

	@Override
	public void onRefresh()
	{
		list.clear();
		ba.notifyDataSetChanged();
		new Thread(){
			public void run(){
				list.addAll(getActivity().getPackageManager().getInstalledPackages(0));
				pu.sort(list);
				pu.checkList(getActivity().getPackageManager(),list,"android.permission.WAKE_LOCK");
				getActivity().runOnUiThread(new Runnable(){

						@Override
						public void run()
						{
							ba.notifyDataSetChanged();
							refresh.setRefreshing(false);
						}
					});
			}
		}.start();}
	@Override
	public String getFTag()
	{
		// TODO: Implement this method
		return "唤醒";
	}
	
}
