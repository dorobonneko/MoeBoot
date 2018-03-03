package com.moe.bootsetting.fragment;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import java.util.List;
import java.util.ArrayList;
import com.moe.bootsetting.adapter.PackageAdapter;
import java.util.Collections;
import java.util.Comparator;
import com.moe.bootsetting.utils.PackagesUtils;
import com.moe.bootsetting.adapter.AbstractAdapter;
import com.moe.bootsetting.adapter.AbstractAdapter.ViewHolder;
import com.moe.bootsetting.R;
import android.support.v4.view.ViewCompat;

public class PackageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,AbstractAdapter.OnItemClickListener
{
	private PackagesUtils pu;
	private List<PackageInfo> list;
	private SwipeRefreshLayout refresh;
	private PackageAdapter pa;
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		pu=new PackagesUtils(getActivity().getPackageManager());
		list=new ArrayList<>();
		refresh=(SwipeRefreshLayout) view;
		refresh.setOnRefreshListener(this);
		RecyclerView rv=(RecyclerView) refresh.getChildAt(1);
		rv.setLayoutManager(new LinearLayoutManager(getContext()));
		rv.setAdapter(pa=new PackageAdapter(list));
		pa.setOnItemClickListener(this);
		ViewCompat.setNestedScrollingEnabled(rv,false);
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		refresh.setRefreshing(true);
		onRefresh();
	}

	@Override
	public void onRefresh()
	{
		list.clear();
		pa.notifyDataSetChanged();
		new Thread(){
			public void run(){
				list.addAll(getActivity().getPackageManager().getInstalledPackages(0));
				pu.sort(list);
				getActivity().runOnUiThread(new Runnable(){

						@Override
						public void run()
						{
							pa.notifyDataSetChanged();
							refresh.setRefreshing(false);
						}
					});
			}
		}.start();
	}


	@Override
	public String getFTag()
	{
		return "禁用";
	}

	@Override
	public void onItemClick(AbstractAdapter aa, AbstractAdapter.ViewHolder vh)
	{
		PackageInfoFragment pif=(PackageInfoFragment) getFragmentManager().findFragmentByTag(PackageInfoFragment.class.getName());
		if(pif==null)pif=new PackageInfoFragment();
		if(pif.getArguments()==null){
			Bundle b=new Bundle();
			pif.setArguments(b);
		}
		pif.getArguments().putParcelable("package",list.get(vh.getAdapterPosition()));
		if(pif.isAdded())
		getFragmentManager().beginTransaction().show(pif).commit();
			else
		getFragmentManager().beginTransaction().add(android.R.id.background,pif,PackageInfoFragment.class.getName()).commit();
	}
	
}
