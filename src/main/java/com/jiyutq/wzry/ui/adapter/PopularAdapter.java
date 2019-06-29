package com.jiyutq.wzry.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.jiyutq.wzry.R;


import java.util.List;
import com.bumptech.glide.*;
import com.jiyutq.wzry.*;
import com.hjq.toast.*;
import android.widget.*;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{

    private List<String> datatitle;
	private List<String> datatime;
	private List<String> datatlx;
	private List<String> url;
    private OnItemClickListener mOnItemClickListener ;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View cardView;
		private TextView  title,time,lx;
        public ViewHolder(View view) {
            super(view);
            cardView = view;
            title=(TextView) itemView.findViewById(R.id.item_content);
			time=(TextView) itemView.findViewById(R.id.item_time);
			lx=(TextView) itemView.findViewById(R.id.item_title);
        }
    }

    public PopularAdapter(List<String> Datatitle,List<String> Datatime,List<String> Datalx,List<String> Url) {
        datatitle = Datatitle;
		datatime = Datatime;
		datatlx = Datalx;
		url = Url;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//ToastUtils.show(holder);
				}
			});
        return holder;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener
	{


//回调接口
        void onClick(View view,int position,List<String> url);//单击，设置为view是因为我想获得子控件的值
        void onLongClick(int position);//长按
    }

    //定义这个接口的set方法，便于调用

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final PopularAdapter.ViewHolder holder, final int position) {
        String bt = datatitle.get(position);
		String sj = datatime.get(position);
		String lx = datatlx.get(position);
		//String tpes = tdataList.get(position);
		//  holder.image.setImageDrawable(image_id);
		//holder.mp4.setUp(pes.getMp4_uri(),JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,pes.getMp4_title());
        //Glide.with(App.sContext).load(pes.getMp4_image()).into(holder.mp4.thumbImageView);		
		//holder.mp4.thumbImageView.setBackgroundResource(R.drawable.mybg);
		//holder.mp4.setText(pes);
		holder.title.setText(bt);
		holder.time.setText(sj);
		holder.lx.setText(lx);
        //设置点击和长按事件
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mOnItemClickListener.onClick(v,holder.getAdapterPosition(),url);
					}
				});
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						mOnItemClickListener.onLongClick(holder.getAdapterPosition());
						return false;
					}
				});
        }
    }

	@Override
    public int getItemCount() {
		url.size();
		datatlx.size();
		datatime.size();
        return datatitle.size();
    }


}


