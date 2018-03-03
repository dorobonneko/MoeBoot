package com.moe.bootsetting.database;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.util.List;
import com.moe.bootsetting.entity.Mode;
import android.database.sqlite.SQLiteStatement;
import android.database.Cursor;
import java.util.ArrayList;

public class ModeDatabase extends SQLiteOpenHelper
{
	private Callback call;
	private SQLiteDatabase sql;
	private static ModeDatabase mdb;
	private ModeDatabase(Context context){
		super(context.getApplicationContext(),ModeDatabase.class.getName(),null,3);
		sql=getReadableDatabase();
	}
	public static ModeDatabase getInstance(Context context){
		if(mdb==null)mdb=new ModeDatabase(context);
		return mdb;
	}
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		p1.execSQL("create table mode(name TEXT primary key,cpu_governor TEXT,gpu_governor TEXT,cpu_max_freq INTEGER,cpu_size INTEGER,growth INTEGER,adj TEXT,minfree TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
	}
	public void insert(Mode mode){
		sql.acquireReference();
		SQLiteStatement state=sql.compileStatement("insert into mode values(?,?,?,?,?,?,?,?)");
		state.bindString(1,mode.getName().trim());
		state.bindString(2,mode.getCpu_governor());
		state.bindString(3,mode.getGpu_governor());
		state.bindLong(4,mode.getCpu_max_freq());
		state.bindLong(5,mode.getCpu_size());
		state.bindLong(6,mode.getGrowth());
		state.bindString(7,mode.getAdj());
		state.bindString(8,mode.getMinfree());
		long size=state.executeInsert();
		state.close();
		sql.releaseReference();
		if(size>0&&call!=null)
			call.onAdded(mode);
	}
	public boolean isExists(String name){
		boolean flag;
		Cursor c=sql.query("mode",null,"name=?",new String[]{name.trim()},null,null,null);
		flag=c.getCount()>0;
		c.close();
		return flag;
	}
	public Mode query(String name){
		if(name==null)return null;
		Cursor c=sql.query("mode",null,"name=?",new String[]{name},null,null,null);
		try{
		while(c.moveToNext()){
			Mode mode=new Mode();
			mode.setName(c.getString(0));
			mode.setCpu_governor(c.getString(1));
			mode.setGpu_governor(c.getString(2));
			mode.setCpu_max_freq(c.getInt(3));
			mode.setCpu_size(c.getInt(4));
			mode.setGrowth(c.getInt(5));
			mode.setAdj(c.getString(6));
			mode.setMinfree(c.getString(7));
			return mode;
		}
		}catch(Exception e){
			
		}finally{
		c.close();
		}
		return null;
	}
	public List<Mode> query(){
		ArrayList<Mode> list=new ArrayList<>();
		Cursor c=sql.query("mode",null,null,null,null,null,null);
		while(c.moveToNext()){
			Mode mode=new Mode();
			mode.setName(c.getString(0));
			mode.setCpu_governor(c.getString(1));
			mode.setGpu_governor(c.getString(2));
			mode.setCpu_max_freq(c.getInt(3));
			mode.setCpu_size(c.getInt(4));
			mode.setGrowth(c.getInt(5));
			mode.setAdj(c.getString(6));
			mode.setMinfree(c.getString(7));
			list.add(mode);
		}
		c.close();
		return list;
	}
	public void delete(Mode mode){
		if(sql.delete("table","name=?",new String[]{mode.getName()})>0&&call!=null)
			call.onDelete(mode);
	}
	public void setCallback(Callback call){
		this.call=call;
	}
	public abstract interface Callback{
		void onAdded(Mode mode);
		void onDelete(Mode mode);
	}
}
