package com.moe.bootsetting.fragment;
import android.view.*;

import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.moe.bootsetting.R;
import com.moe.bootsetting.app.FloatFragment;
import com.moe.bootsetting.utils.ImageCache;
import com.moe.bootsetting.widget.SlideLayout;
import java.lang.reflect.Field;
import android.widget.ImageView;
import android.util.TypedValue;

public class PackageInfoFragment extends FloatFragment implements ImageCache.Callback
{
private SlideLayout sl;
private Toolbar toolbar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.package_info,container,false);
	}

	@Override
	public SlideLayout getSlideLayout(View parent)
	{
		toolbar=(Toolbar) parent.findViewById(R.id.toolbar);
		this.sl=(SlideLayout)parent;
		return sl;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onActivityCreated(savedInstanceState);
		onHiddenChanged(false);
	}

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(getArguments()!=null){
			PackageInfo pi=getArguments().getParcelable("package");
			toolbar.setTitle(pi.applicationInfo.loadLabel(getActivity().getPackageManager()));
			ImageCache.loadIcon(toolbar,pi.packageName,this);
		}
	}

	@Override
	public void onReady(Bitmap bit)
	{
		toolbar.setLogo(new BitmapDrawable(bit));
		try
		{
			Field logo=toolbar.getClass().getDeclaredField("mLogoView");
			logo.setAccessible(true);
			try
			{
				ImageView icon=(ImageView) logo.get(toolbar);
				ViewGroup.LayoutParams vl=icon.getLayoutParams();
				int size=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,46,getResources().getDisplayMetrics());
				vl.width=size;
				vl.height=size;
				((Toolbar.LayoutParams)vl).setMarginEnd((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics()));
			}
			catch (IllegalAccessException e)
			{}
			catch (IllegalArgumentException e)
			{}
		}
		catch (NoSuchFieldException e)
		{}
	}

	
}
