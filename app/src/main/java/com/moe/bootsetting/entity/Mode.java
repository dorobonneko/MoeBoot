package com.moe.bootsetting.entity;
import android.os.Parcelable;
import android.os.Parcel;

public class Mode implements Parcelable
{
	private String name,cpu_governor,gpu_governor,adj,minfree;
	private int cpu_max_freq,cpu_size,growth;
	private Mode(Parcel p){
		name=p.readString();
		cpu_governor=p.readString();
		gpu_governor=p.readString();
		cpu_max_freq=p.readInt();
		cpu_size=p.readInt();
		growth=p.readInt();
		adj=p.readString();
		minfree=p.readString();
	}
	public Mode()
	{}
	public void setAdj(String adj){
		this.adj=adj;
	}
	public void setMinfree(String minfree){
		this.minfree=minfree;
	}
	public String getAdj(){
		return this.adj;
	}
	public String getMinfree(){
		return this.minfree;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Mode)
			return name.equals(((Mode)obj).getName());
		return super.equals(obj);
	}
	public void setGrowth(int growth){
		this.growth=growth;
	}
	public int getGrowth(){
		return growth;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setCpu_governor(String cpu_governor)
	{
		this.cpu_governor = cpu_governor;
	}

	public String getCpu_governor()
	{
		return cpu_governor;
	}

	public void setGpu_governor(String gpu_governor)
	{
		this.gpu_governor = gpu_governor;
	}

	public String getGpu_governor()
	{
		return gpu_governor;
	}

	public void setCpu_max_freq(int cpu_max_freq)
	{
		this.cpu_max_freq = cpu_max_freq;
	}

	public int getCpu_max_freq()
	{
		return cpu_max_freq;
	}

	public void setCpu_size(int cpu_size)
	{
		this.cpu_size = cpu_size;
	}

	public int getCpu_size()
	{
		return cpu_size;
	}
	@Override
	public int describeContents()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p1, int p2)
	{
		p1.writeString(name);
		p1.writeString(cpu_governor);
		p1.writeString(gpu_governor);
		p1.writeInt(cpu_max_freq);
		p1.writeInt(cpu_size);
		p1.writeInt(growth);
	}
	public static final Creator<Mode> CREATOR=new Creator<Mode>(){

		@Override
		public Mode createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new Mode(p1);
		}

		@Override
		public Mode[] newArray(int p1)
		{
			// TODO: Implement this method
			return new Mode[p1];
		}
	};
}
