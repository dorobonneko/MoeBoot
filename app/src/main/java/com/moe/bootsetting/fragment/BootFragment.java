package com.moe.bootsetting.fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.widget.SwipeRefreshLayout;
import com.moe.bootsetting.adapter.BootAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import java.util.Collections;
import com.moe.bootsetting.utils.PackagesUtils;
import java.util.ArrayList;
import android.content.pm.PackageInfo;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import android.content.pm.PermissionInfo;
import android.content.pm.PackageManager;
import android.support.v4.view.ViewCompat;

public class BootFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
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
		rv.setAdapter(ba=new BootAdapter(list=new ArrayList<>()));
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
				pu.checkList(getActivity().getPackageManager(),list,"android.permission.RECEIVE_BOOT_COMPLETED");
				getActivity().runOnUiThread(new Runnable(){

						@Override
						public void run()
						{
							ba.notifyDataSetChanged();
							refresh.setRefreshing(false);
						}
					});
			}
		}.start();
		
	}

	
	@Override
	public String getFTag()
	{
		// TODO: Implement this method
		return "自启";
	}
	
}
