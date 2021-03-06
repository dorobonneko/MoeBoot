package com.moe.bootsetting.widget;
import android.view.ViewTreeObserver;
import android.content.Context;
import android.app.Activity;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import com.moe.bootsetting.utils.BitmapUtils;

public abstract class AutoBackground implements ViewTreeObserver.OnGlobalLayoutListener
{
	private View v,bl;
	public AutoBackground(View v,BackgroundLayout activity){
		this.v=v;
		this.bl=activity;
	}
	public AutoBackground(View v,Activity activity){
		this(v,(BackgroundLayout)activity.findViewById(android.R.id.background));
	}
	public View getView(){
		return v;
	}
	public void start(){
		v.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}
	public void stop(){
		v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	@Override
	public void onGlobalLayout()
	{
		if(bl!=null){
			Rect rect=new Rect();
			int[] vi=new int[2];
			bl.getLocationOnScreen(vi);
			int height=vi[1];
			v.getLocationOnScreen(vi);
			rect.left=vi[0];
			//rect.top=Build.VERSION.SDK_INT>18||activity.getWindow()?vi[1]:vi[1]-v.getResources().getDimensionPixelSize(v.getResources().getIdentifier("status_bar_height","dimen","android"));
			rect.top=vi[1]-height;
			rect.right=v.getMeasuredWidth();
			rect.bottom=v.getMeasuredHeight();
			onBitmapCreated(BitmapUtils.blur(v.getContext(),((BackgroundLayout)bl).getBackground(rect)));
		}
	}
	public abstract void onBitmapCreated(Bitmap bit);
}
