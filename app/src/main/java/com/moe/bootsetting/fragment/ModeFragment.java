package com.moe.bootsetting.fragment;
import com.moe.bootsetting.app.FloatFragment;
import android.view.View;
import com.moe.bootsetting.widget.SlideLayout;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.moe.bootsetting.R;
import android.support.v7.widget.Toolbar;
import java.lang.reflect.Field;
import android.widget.ImageView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.content.res.TypedArray;

public class ModeFragment extends FloatFragment implements Toolbar.OnMenuItemClickListener,SwipeRefreshLayout.OnRefreshListener
{
	private Toolbar toolbar;
	private SwipeRefreshLayout refresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.mode_view,container,false);
	}

	@Override
	public SlideLayout getSlideLayout(View parent)
	{
		toolbar=(Toolbar) parent.findViewById(R.id.toolbar);
		toolbar.setTitle("省电模式");
		toolbar.setLogo(R.drawable.arrow_left);
		try
		{
			Field logo=toolbar.getClass().getDeclaredField("mLogoView");
			logo.setAccessible(true);
			try
			{
				ImageView icon=(ImageView) logo.get(toolbar);
				ViewGroup.LayoutParams vl=icon.getLayoutParams();
				int size=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,28,getResources().getDisplayMetrics());
				vl.width=size;
				vl.height=size;
				((Toolbar.LayoutParams)vl).setMarginEnd((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics()));
				icon.setOnClickListener(new View.OnClickListener(){

						@Override
						public void onClick(View p1)
						{
							hide();
						}
					});
				TypedArray ta=getContext().obtainStyledAttributes(new int[]{android.support.v7.appcompat.R.attr.selectableItemBackgroundBorderless});
				icon.setBackgroundDrawable(ta.getDrawable(0));
				ta.recycle();
			}
			catch (IllegalAccessException e)
			{}
			catch (IllegalArgumentException e)
			{}
		}
		catch (NoSuchFieldException e)
		{}
		toolbar.inflateMenu(R.menu.menu_mode);
		toolbar.setOnMenuItemClickListener(this);
		RecyclerView rv=(RecyclerView) parent.findViewById(R.id.recyclerview);
		//ViewCompat.setNestedScrollingEnabled(rv,false);
		refresh=(SwipeRefreshLayout) parent.findViewById(R.id.swiperefreshlayout);
		//ViewCompat.setNestedScrollingEnabled(refresh,false);
		//parent.setOnClickListener(null);
		return (SlideLayout)parent;
	}

	@Override
	public boolean onMenuItemClick(MenuItem p1)
	{
		switch(p1.getItemId()){
			case android.R.id.home:
				hide();
				break;
				case R.id.menu_plus:
				FloatFragment plus=(FloatFragment) getFragmentManager().findFragmentByTag("plus");
				if(plus==null)plus=new ModePlusFragment();
				if(plus.isAdded())
					getFragmentManager().beginTransaction().show(plus).commit();
				else
					getFragmentManager().beginTransaction().add(android.R.id.background,plus,"plus").commit();
				
					break;
		}
		return true;
	}

	@Override
	public void onRefresh()
	{
		
	}


	
}
