package com.moe.bootsetting.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import org.apache.http.client.utils.Punycode;

public abstract class AbstractAdapter<T extends AbstractAdapter.ViewHolder> extends RecyclerView.Adapter<T>
{
	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
		private AbstractAdapter aa;
		public ViewHolder(AbstractAdapter aa,View v){
			super(v);
			this.aa=aa;
			if(aa.oicl!=null)
				v.setOnClickListener(this);
			if(aa.oilcl!=null)
				v.setOnLongClickListener(this);
		}

		@Override
		public void onClick(View p1)
		{
			aa.oicl.onItemClick(aa,this);
		}

		@Override
		public boolean onLongClick(View p1)
		{
			// TODO: Implement this method
			return aa.oilcl.onItemLongClick(aa,this);
		}


		
	}
	private OnItemClickListener oicl;
	private OnItemLongClickListener oilcl;
	public void setOnItemClickListener(OnItemClickListener l){
		this.oicl=l;
	}
	public void setOnItemLongClickListener(OnItemLongClickListener l){
		this.oilcl=l;
	}
	public abstract interface OnItemClickListener{
		void onItemClick(AbstractAdapter aa,ViewHolder vh);
	}
	public abstract interface OnItemLongClickListener{
		boolean onItemLongClick(AbstractAdapter aa,ViewHolder vh);
	}
}
