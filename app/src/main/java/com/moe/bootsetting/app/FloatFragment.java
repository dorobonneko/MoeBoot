package com.moe.bootsetting.app;
import android.support.v4.app.Fragment;
import com.moe.bootsetting.widget.SlideLayout;
import android.os.Bundle;
import android.view.View;
import android.app.FragmentManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.animation.*;

public abstract class FloatFragment extends Fragment implements SlideLayout.Callback
{
	private Animation anime;
	private SlideLayout slideLayout;
	public abstract SlideLayout getSlideLayout(View parent);

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view,savedInstanceState);
		slideLayout = getSlideLayout(view);
		slideLayout.setAnimeEnter(true);
		slideLayout.setCallback(this);
		view.setClickable(false);
	}

	@Override
	public void onEnter(SlideLayout s)
	{
		// TODO: Implement this method
	}

	@Override
	public void onExit(SlideLayout sl)
	{
		try{
		getFragmentManager().beginTransaction().hide(this).commit();
		}catch(Exception e){}
	}
	public void hide()
	{
		if (!slideLayout.isClose())
			slideLayout.exit();
	}

	
	@Override
	public final Animation onCreateAnimation(int transit, boolean enter, int nextAnim)
	{
		return enter?anime==null?anime=new Anime():anime:null;
	}

	private class Anime extends Animation implements Animation.AnimationListener
	{

		@Override
		public void onAnimationStart(Animation p1)
		{
			if(slideLayout!=null)slideLayout.enter();
		}

		@Override
		public void onAnimationEnd(Animation p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void onAnimationRepeat(Animation p1)
		{
			// TODO: Implement this method
		}


		
		
		public Anime(){
			setAnimationListener(this);
		}
		

	}

}
