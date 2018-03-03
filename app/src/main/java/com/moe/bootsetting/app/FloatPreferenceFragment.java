package com.moe.bootsetting.app;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import com.moe.bootsetting.widget.SlideLayout;
import android.widget.ImageView;
import java.lang.reflect.Field;
import com.moe.bootsetting.R;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.content.res.XmlResourceParser;
import java.util.ArrayList;
import com.moe.bootsetting.preference.Preference;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import android.util.AttributeSet;
import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import android.content.SharedPreferences;

public class FloatPreferenceFragment extends FloatFragment
{
	private ArrayList<Preference> list=new ArrayList<>();
	private int xml;
	private Toolbar toolbar;
	private ViewGroup content;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.preference_layout,container,false);
	}
	public SharedPreferences getSharedPreferences(){
		return null;
	}
	@Override
	public SlideLayout getSlideLayout(View parent)
	{
		toolbar=(Toolbar) parent.findViewById(R.id.toolbar);
		toolbar.setTitle("添加模式");
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
		//parent.setOnClickListener(null);
		return (SlideLayout)parent;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		content=(ViewGroup) view.findViewById(R.id.content);
		super.onViewCreated(view, savedInstanceState);
	}
	
	public void setPreferenceXml(int xmlId,Bundle b){
		this.xml=xmlId;
		try{
			onXmlRender(getResources().getXml(xmlId),b);
		}catch(Exception e){}
	}
	private void onXmlRender(XmlResourceParser xml,Bundle b) throws Exception{
		int flag=0;
		while((flag=xml.next())!=xml.END_DOCUMENT){
			switch(flag){
				case xml.START_TAG:
					if(!xml.getName().equals("moe")){
						Preference p=createPreferenceFromTag(xml);
					if(p!=null)
						addPreference(p,b==null?null:b.getBundle(p.getKey()));
					}
					break;
			}
		}
	}
	private Preference createPreferenceFromTag(XmlResourceParser xml) throws Exception {
			return (Preference)(Class.forName(xml.getName()).getConstructor(Context.class, AttributeSet.class).newInstance(getContext(), xml));
		
		
	}
	public final void addPreference(Preference preference,Bundle b){
		preference.setFragment(this);
		preference.onCreate(b);
		list.add(preference);
		View v=(preference.onCreateView(LayoutInflater.from(getContext()),content));
		content.addView(v);
		preference.onViewCreated(v,b);
	}
	public Preference findPreference(String key){
		if(key==null)return null;
		Iterator<Preference> list=this.list.iterator();
		while(list.hasNext()){
			Preference preference=list.next();
			if(key.equals(preference.getKey()))
				return preference;
		}
		return null;
	}
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// TODO: Implement this method
		super.onSaveInstanceState(outState);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getKey()!=null){
				Bundle b=new Bundle();
			list.get(i).onSaveInstance(b);
			outState.putBundle(list.get(i).getKey(),b);
			}
			}
		
	}

}
