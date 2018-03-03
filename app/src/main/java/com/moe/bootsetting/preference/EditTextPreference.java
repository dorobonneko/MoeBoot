package com.moe.bootsetting.preference;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moe.bootsetting.R;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.InputType;
import android.support.v7.widget.AppCompatImageView;
import android.widget.FrameLayout;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.text.method.DigitsKeyListener;

public class EditTextPreference extends Preference
{
	private CharSequence value;
	private int inputType;
	private ImageView check;
	private EditText mSummary;
	private String digits;
	public EditTextPreference(Context context){
		this(context,null);
	}
	public EditTextPreference(Context context,AttributeSet attrs){
		super(context,attrs);
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{android.R.attr.digits,android.R.attr.defaultValue,android.R.attr.inputType});
		value=ta.getString(1);
		inputType=ta.getInt(2,InputType.TYPE_CLASS_TEXT);
		digits=ta.getString(0);
		ta.recycle();
	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.edittext_preference,parent,false);
	}*/

	@Override
	public void onViewCreated(View view, Bundle b)
	{
		if(b!=null){
			value=b.getCharSequence("value");
		}
		super.onViewCreated(view, b);
		check=new AppCompatImageView(getContext());
		check.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		check.setImageResource(R.drawable.check);	
		TypedArray ta=getContext().obtainStyledAttributes(new int[]{android.support.v7.appcompat.R.attr.colorControlNormal,android.R.attr.textColorSecondary});
		check.getDrawable().setTintList(new ColorStateList(new int[][]{{android.R.attr.state_enabled},{}},new int[]{ta.getColor(0,0xffffffff),ta.getColor(1,0xffdddddd)}));
		ta.recycle();
		check.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					EditTextPreference.this.onClick();
				}
			});
		check.setEnabled(false);
		int width=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,46,getResources().getDisplayMetrics());
		((FrameLayout)view.findViewById(R.id.content)).addView(check,new FrameLayout.LayoutParams(width,width));
		mSummary=new EditText(getContext());
		mSummary.setInputType(inputType);
		if(digits!=null)
			mSummary.setKeyListener(DigitsKeyListener.getInstance(digits));
		mSummary.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					if(!check.isEnabled()&&p1.length()>0)
						check.setEnabled(true);
				}
			});
			if(value!=null)
		mSummary.setText(value);
		((LinearLayout)view.findViewById(R.id.list)).addView(mSummary,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onSaveInstance(Bundle save)
	{
		// TODO: Implement this method
		super.onSaveInstance(save);
		save.putCharSequence("value",mSummary.getText());
	}

	@Override
	public void onClick()
	{
		check.setEnabled(false);
	}
	public CharSequence getText(){
		if(mSummary==null)
			return value;
			else
		return  mSummary.getText();
	}

	public void setValue(CharSequence summary)
	{
		this.value=summary;
		if(mSummary!=null)mSummary.setText(summary);
	}
	
}
