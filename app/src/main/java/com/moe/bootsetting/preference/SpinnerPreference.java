package com.moe.bootsetting.preference;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.widget.Spinner;
import com.moe.bootsetting.R;
import android.widget.FrameLayout;
import android.widget.ArrayAdapter;

public class SpinnerPreference extends Preference
{
	private Spinner spinner;
	private CharSequence[] values;
	public SpinnerPreference(Context context){
		this(context,null);
	}
	public SpinnerPreference(Context context,AttributeSet attrs){
		super(context,attrs);
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{android.R.attr.entries});
		values=ta.getTextArray(0);
		ta.recycle();
	}

	public int getSelectedItem()
	{
		// TODO: Implement this method
		return spinner!=null?spinner.getSelectedItem():-1;
	}

	

	@Override
	public void onViewCreated(View view, Bundle b)
	{
		super.onViewCreated(view, b);
		spinner=new Spinner(getContext());
		((FrameLayout)view.findViewById(R.id.content)).addView(spinner);
		if(values!=null)
		spinner.setAdapter(new ArrayAdapter<CharSequence>(getContext(),android.R.layout.simple_list_item_1,values));
	}
	public void setEntries(CharSequence... data){
		values=data;
		if(spinner!=null)
		spinner.setAdapter(new ArrayAdapter<CharSequence>(getContext(),android.R.layout.simple_list_item_1,values));
		
	}
	public CharSequence[] getEntries(){
		return values;
	}
}
