package com.moe.bootsetting.preference;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.graphics.drawable.Drawable;
import com.moe.bootsetting.R;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.graphics.drawable.VectorDrawableCompat;
import com.moe.bootsetting.app.FloatPreferenceFragment;
import android.widget.TextView;
import android.support.v4.content.res.TypedArrayUtils;
import android.text.TextUtils;

public class Preference
{
	private FloatPreferenceFragment fpf;
	private CharSequence title,summary;
	private Drawable icon;
	private View view;
	private String key;
	private int id;
	private Context context;
	private TextView mTitle,mSummary;
	public Preference(Context context){
		this(context,null);
	}
	public Preference(Context context,AttributeSet attrs){
		this.context=context;
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{android.R.attr.title,android.R.attr.key,android.R.attr.summary,android.R.attr.icon,android.R.attr.id});
		id=ta.getResourceId(4,-1);
		try{
			icon=VectorDrawableCompat.create(context.getResources(),ta.getResourceId(3,-1),context.getTheme());
		}catch(Exception e){}
		summary=ta.getString(2);
		key=ta.getString(1);
		title=ta.getString(0);
		ta.recycle();
	}
public CharSequence getSummary(){
	return summary;
}
	public final void setFragment(FloatPreferenceFragment p0)
	{
		fpf=p0;
	}
	public Context getContext(){
		return context;
	}
	public Resources getResources(){
		return context.getResources();
	}
	public void onCreate(Bundle save){
		
	}
	public void onSaveInstance(Bundle save){
		save.putCharSequence("title",title);
		save.putCharSequence("summary",summary);
	}
	public View onCreateView(LayoutInflater inflater,ViewGroup parent){
		return inflater.inflate(R.layout.preference,parent,false);
	}
	public void onViewCreated(View view,Bundle b){
		if(b!=null){
			title=b.getCharSequence("title",title);
			summary=b.getCharSequence("summary",summary);
		}
		mTitle=(TextView) view.findViewById(android.R.id.title);
		mSummary=(TextView) view.findViewById(android.R.id.summary);
		mTitle.setText(title);
		if(!TextUtils.isEmpty(summary))
			mSummary.setVisibility(View.VISIBLE);
		mSummary.setText(summary);
		
	}
	public View getView(){
		return view;
	}
	public void onClick(){
		
	}
	public boolean isVisiable()
	{
		return view!=null&&view.getVisibility()==View.VISIBLE;
	}
	public String getKey(){
		return key;
	}
	public void setTitle(CharSequence title){
		this.title=title;
		if(mTitle!=null)
			mTitle.setText(title);
	}
	public void setSummary(CharSequence summary){
		this.summary=summary;
		if(mSummary!=null){
			if(TextUtils.isEmpty(summary))
				mSummary.setVisibility(View.GONE);
				else
				mSummary.setVisibility(View.VISIBLE);
			mSummary.setText(summary);
			}
	}
}
