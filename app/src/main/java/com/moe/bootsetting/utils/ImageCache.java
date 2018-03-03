package com.moe.bootsetting.utils;
import android.widget.ImageView;
import android.support.v4.util.LruCache;
import android.graphics.Bitmap;
import com.moe.bootsetting.graphics.BitmapCache;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.util.TypedValue;
import android.view.View;

public class ImageCache
{
	private static LruCache<String,Bitmap> cache=new LruCache<String,Bitmap>(250){

		@Override
		protected int sizeOf(String key, Bitmap value)
		{                  
			return 1;
		}
		
		
		
	};
	public static void setLogo(final ImageView logo,final String packageName){
		loadIcon(logo, packageName, new Callback(){

				@Override
				public void onReady(Bitmap bit)
				{
					logo.setImageBitmap(bit);
				}
			});
	}
	public static void loadIcon(final View logo,final String packageName,final Callback call){
		final Bitmap bitm=cache.get(packageName);
		if(bitm==null||bitm.isRecycled())
			new Thread(){
				public void run(){
					final BitmapCache bit=new BitmapCache(bitm);
					cache.remove(packageName);
					try
						{
							
							PackageInfo pi=logo.getContext().getPackageManager().getPackageInfo(packageName, 0);
							Drawable icon=pi.applicationInfo.loadIcon(logo.getContext().getPackageManager());
							if(icon!=null){
								Bitmap buffer=Bitmap.createBitmap(icon.getIntrinsicWidth(),icon.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
								Canvas canvas=new Canvas(buffer);
								icon.setBounds(0,0,buffer.getWidth(),buffer.getHeight());
								icon.draw(canvas);
								float size=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,46,logo.getResources().getDisplayMetrics());
								size/=Math.max(buffer.getWidth(),buffer.getHeight());
								bit.update(BitmapUtils.scale(buffer,size,size));
								if(bit.getBit()!=buffer)
									buffer.recycle();
								cache.put(packageName,bit.getBit());
							}
						}
						catch (PackageManager.NameNotFoundException e)
						{}
					logo.post(new Runnable(){

							@Override
							public void run()
							{
								call.onReady(bit.getBit());
								//logo.setImageBitmap(bit.getBit());
							}
						});
				}
			}.start();
		else
			//logo.setImageBitmap(bit);
			call.onReady(bitm);
	}
	public abstract interface Callback{
		void onReady(Bitmap bit);
	}
}
