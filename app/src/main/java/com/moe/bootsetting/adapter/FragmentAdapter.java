package com.moe.bootsetting.adapter;
import android.support.v4.app.FragmentManager;
import java.util.*;
import com.moe.bootsetting.fragment.*;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter
{
	private List<Fragment> list;
	public FragmentAdapter(FragmentManager fm,List<Fragment> list){
		super(fm);
		this.list=list;
	}
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return list.size();
	}

	@Override
	public Fragment getItem(int p1)
	{
		// TODO: Implement this method
		return list.get(p1);
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		// TODO: Implement this method
		return list.get(position).getFTag();
	}

}
