package com.moe.bootsetting;
import android.app.*;
import android.os.*;
import android.widget.*;

public class CrashActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		TextView tv=new TextView(this);
		tv.setText(getIntent().getCharSequenceExtra("error"));
		setContentView(tv);
		tv.setTextIsSelectable(true);
		tv.setMovementMethod(new android.text.method.ArrowKeyMovementMethod());
	}
	
}
