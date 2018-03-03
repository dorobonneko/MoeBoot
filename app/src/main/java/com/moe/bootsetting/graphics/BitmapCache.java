package com.moe.bootsetting.graphics;
import android.graphics.Bitmap;

public class BitmapCache
{
	private Bitmap bit;
	public BitmapCache(Bitmap bit){
		this.bit=bit;
	}
	public Bitmap getBit(){
		return bit;
	}
	public void update(Bitmap bit){
		if(this.bit!=null)
			this.bit.recycle();
			this.bit=bit;
	}
}
