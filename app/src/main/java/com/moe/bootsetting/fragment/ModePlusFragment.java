package com.moe.bootsetting.fragment;
import com.moe.bootsetting.app.FloatFragment;
import android.view.View;
import com.moe.bootsetting.widget.SlideLayout;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.moe.bootsetting.R;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import java.lang.reflect.Field;
import android.util.TypedValue;
import android.view.MenuItem;
import com.moe.bootsetting.app.FloatPreferenceFragment;
import com.moe.bootsetting.preference.SpinnerPreference;
import com.moe.bootsetting.preference.EditTextPreference;
import com.moe.bootsetting.preference.SeekBarPreference;
import com.moe.bootsetting.entity.Mode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ModePlusFragment extends FloatPreferenceFragment implements Toolbar.OnMenuItemClickListener
{
	private EditTextPreference name,adj,minfree;
	private SpinnerPreference cpu_max_freq,cpu_size,cpu_governor,gpu_governor;
	private SeekBarPreference growth;
	private Mode mode;
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		Toolbar toolbar=(Toolbar) view.findViewById(R.id.toolbar);
		toolbar.inflateMenu(R.menu.menu_mode_plus);
		toolbar.setOnMenuItemClickListener(this);
		
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem p1)
	{
		switch(p1.getItemId()){
			case R.id.menu_check:
				if(mode==null)mode=new Mode();
				mode.setName(name.getText().toString());
				mode.setAdj(adj.getText().toString());
				mode.setMinfree(minfree.getText().toString());
				if(cpu_governor.isVisiable())
				mode.setCpu_governor(cpu_governor.getEntries()[cpu_governor.getSelectedItem()].toString());
				mode.setCpu_max_freq(Integer.parseInt(cpu_max_freq.getEntries()[cpu_size.getSelectedItem()].toString()));
				mode.setGpu_governor(gpu_governor.getEntries()[gpu_governor.getSelectedItem()].toString());
				mode.setGrowth(growth.getProgress());
				break;
		}
		return true;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		if(savedInstanceState!=null)
			mode=savedInstanceState.getParcelable("mode");
		else if(getArguments()!=null)
			mode=getArguments().getParcelable("mode");
		super.onActivityCreated(savedInstanceState);
		setPreferenceXml(R.xml.mode,savedInstanceState);
		name=(EditTextPreference) findPreference("name");
		growth=(SeekBarPreference) findPreference("growth");
		adj=(EditTextPreference) findPreference("adj");
		minfree=(EditTextPreference) findPreference("minfree");
		cpu_max_freq=((SpinnerPreference)findPreference("max_freq"));
		cpu_size=(SpinnerPreference) findPreference("cpu_size");
		cpu_governor=(SpinnerPreference) findPreference("cpu_governor");
		gpu_governor=(SpinnerPreference) findPreference("gpu_governor");
		cpu_size.setEntries("4","6","8");
		if(savedInstanceState==null){
			if(mode!=null){
		name.setValue(mode.getName());
		growth.setProgress(mode.getGrowth());
		adj.setValue(mode.getAdj());
		minfree.setValue(mode.getMinfree());
		
		//cpu_max_freq.set
		}else{
			new Thread(){
				public void run(){
					CharSequence[] cpu=readData("/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors");
					
					try{//cpu频率范围
						FileInputStream fis=new FileInputStream("/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies");
					}catch(FileNotFoundException e){}
					try{
						FileInputStream fis=new FileInputStream("/sys/class/kgsl/kgsl-3d0/devfreq/available_governors");
					}catch(FileNotFoundException fe){}
				}
			}.start();
		}
		}
		
	}
	private CharSequence[] readData(String path){
		FileInputStream fis=null;
		ByteArrayOutputStream baos=null;
		try
		{//cpu调度器
			fis=new FileInputStream(path);
			baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[512];
			int len=-1;
			while((len=fis.read(buffer))!=-1){
				baos.write(buffer,0,len);
			}
			baos.flush();
			return baos.toString().split(" ");
		}
		catch (FileNotFoundException e)
		{}
		catch(IOException e){}
		finally{
			try
			{
				if (baos != null)baos.close();
			}
			catch (IOException e)
			{}
			try
			{
				if (fis != null)fis.close();
			}
			catch (IOException e)
			{}
		}
		return null;
	}
}
