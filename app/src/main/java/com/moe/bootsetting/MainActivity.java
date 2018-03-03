package com.moe.bootsetting;
import java.util.ArrayList;
import com.moe.bootsetting.fragment.PackageFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.moe.bootsetting.adapter.FragmentAdapter;
import com.moe.bootsetting.fragment.BootFragment;
import com.moe.bootsetting.fragment.ExtraFragment;
import com.moe.bootsetting.fragment.WakeFragment;
import android.view.WindowManager;
import com.moe.bootsetting.fragment.PackageInfoFragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import com.moe.bootsetting.app.FloatFragment;
import com.moe.bootsetting.fragment.ModeFragment;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener
{
	private MenuItem add;
	private Toolbar toolbar;
	private ArrayList<com.moe.bootsetting.fragment.Fragment> list;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		getWindow().setStatusBarColor(0x00000000);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		list=new ArrayList<>();
		list.add(new PackageFragment());
		list.add(new BootFragment());
		list.add(new WakeFragment());
		list.add(new ExtraFragment());
		TabLayout tabl=(TabLayout) findViewById(R.id.tablayout);
		ViewPager vp=(ViewPager) findViewById(R.id.viewpager);
		tabl.setupWithViewPager(vp,true);
		vp.setOffscreenPageLimit(4);
		vp.setAdapter(new FragmentAdapter(getSupportFragmentManager(),list));
		toolbar=(Toolbar)findViewById(R.id.toolbar);
		toolbar.inflateMenu(R.menu.menu_main);
		add=toolbar.getMenu().findItem(R.id.menu_mode);
		toolbar.setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClick(MenuItem p1)
	{
		switch(p1.getItemId()){
			case R.id.menu_mode:
				 FloatFragment mode=(FloatFragment) getSupportFragmentManager().findFragmentByTag("mode");
				 if(mode==null)mode=new ModeFragment();
				 if(mode.isAdded())
					 getSupportFragmentManager().beginTransaction().show(mode).commit();
					 else
					getSupportFragmentManager().beginTransaction().add(android.R.id.background,mode,"mode").commit();
				break;
		}
		return true;
	}


	@Override
	public void onBackPressed()
	{
		List<android.support.v4.app.Fragment> list=getSupportFragmentManager().getFragments();
			for(int i=list.size()-1;i>-1;i--)
			if(list.get(i) instanceof FloatFragment&&!list.get(i).isHidden()){
				((FloatFragment)list.get(i)).hide();
				return;
			}
		super.onBackPressed();
	}
	
}
