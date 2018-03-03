package com.moe.bootsetting.preference;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.os.Bundle;
import android.util.TypedValue;
import android.content.res.TypedArray;
import android.widget.SeekBar;
import android.widget.LinearLayout;
import com.moe.bootsetting.R;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.view.Gravity;

public class SeekBarPreference extends Preference
{
	private SeekBar seekbar;
	private int progress,max;
	private TextView tip;
	public SeekBarPreference(Context context){
		this(context,null);
	}
	public SeekBarPreference(Context context,AttributeSet attrs){
		super(context,attrs);
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{android.R.attr.progress,android.R.attr.max});
		progress=ta.getInt(0,0);
		max=ta.getInt(1,100);
		ta.recycle();
	}

	@Override
	public void onViewCreated(View view, Bundle b)
	{
		if(b!=null){
			progress=b.getInt("progresd",progress);
			max=b.getInt("max",max);
		}
		super.onViewCreated(view, b);
		tip=new TextView(getContext());
		tip.setGravity(Gravity.CENTER);
		tip.setTextColor(((TextView)view.findViewById(android.R.id.summary)).getTextColors());
		int width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,46,getResources().getDisplayMetrics());
		((FrameLayout)view.findViewById(R.id.content)).addView(tip,new FrameLayout.LayoutParams(width,width));
		seekbar=new SeekBar(getContext());
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					tip.setText(p2+"");
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}
			});
		seekbar.setMax(max);
		seekbar.setProgress(progress);
		((LinearLayout)view.findViewById(R.id.list)).addView(seekbar,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	public int getProgress(){
		return seekbar!=null?seekbar.getProgress():progress;
	}
	public int getMax(){
		return seekbar==null?max:seekbar.getMax();
	}
	public void setProgress(int progress){
	this.progress=progress;
	if(seekbar!=null)
		seekbar.setProgress(progress);
	}
	public void setMax(int max){
		this.max=max;
		if(seekbar!=null)
			seekbar.setMax(max);
	}

	@Override
	public void onSaveInstance(Bundle save)
	{
		super.onSaveInstance(save);
		save.putInt("progress",seekbar.getProgress());
		save.putInt("max",seekbar.getMax());
	}
	
}
