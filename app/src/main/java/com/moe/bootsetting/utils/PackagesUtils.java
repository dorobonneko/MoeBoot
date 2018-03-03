package com.moe.bootsetting.utils;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;

public class PackagesUtils implements Comparator<PackageInfo>
{
	PackageManager pm;
	public PackagesUtils(PackageManager pm){
		this.pm=pm;
	}
	@Override
	public int compare(PackageInfo p1, PackageInfo p2)
	{
		return p1.applicationInfo.loadLabel(pm).toString().compareTo(p2.applicationInfo.loadLabel(pm).toString());
	}
	

	public void sort(List<PackageInfo> list)
	{
		Collections.sort(list,this);
	}
	public static void checkList(PackageManager pm,List list,String permission){
		Iterator<PackageInfo> ip=list.iterator();
		while(ip.hasNext()){
			boolean  f=false;
			try{
				for(String pi:pm.getPackageInfo(ip.next().packageName, PackageManager.GET_PERMISSIONS).requestedPermissions){
					if(pi.equals(permission)){
						f=true;break;
					}
				}
			}catch(Exception e){}
			if(!f)ip.remove();
		}
	}
}
