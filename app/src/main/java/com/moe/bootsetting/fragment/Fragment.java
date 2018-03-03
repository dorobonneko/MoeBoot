package com.moe.bootsetting.fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.moe.bootsetting.R;

public class Fragment extends android.support.v4.app.Fragment
{
	private String tag;
	public void setTag(String tag){
		this.tag=tag;
	}
	public String getFTag(){
		return tag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.recyclerview,container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		view.setClickable(false);
	}
	
	public boolean onBackPressed(){
		return false;
	}
}
