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

public class ChooseHeroAdapter extends RecyclerView.Adapter<ChooseHeroAdapter.ViewHolder>{

    private List<String> cardList;
	private List<String> id;
    private OnItemClickListener mOnItemClickListener ;
	private int selectedIndex;        //记录当前选中的条目索引
    static class ViewHolder extends RecyclerView.ViewHolder {
        View cardView;
        ImageView image;
		private LinearLayout bg;
        public ViewHolder(View view) {
            super(view);
            cardView = view;
            image = (ImageView) view.findViewById(R.id.image);
			bg=view.findViewById(R.id.item_choose_heroLinearLayout);
        }
    }

    public ChooseHeroAdapter(List<String> CardList,List<String> Id) {
        cardList = CardList;
		id = Id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_hero, parent, false);
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

		public void onLongClick(int adapterPosition, List<String> cardList);
//回调接口
        void onClick(View view,int position,List<String> id);//单击，设置为view是因为我想获得子控件的值
        void onLongClick(int position);//长按
    }

    //定义这个接口的set方法，便于调用

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ChooseHeroAdapter.ViewHolder holder, final int position) {
        String image_id = cardList.get(position);
		//  holder.image.setImageDrawable(image_id);
		Glide.with(App.sContext).load(image_id).into(holder.image);		
		if (selectedIndex == position) {
			holder.bg.setBackgroundResource(R.drawable.item_select);  //选中状态
			//way.seletedStatus = true;
		} else {                                                            //非选中状态
			holder.bg.setBackgroundResource(R.color.white);  
		}
        //设置点击和长按事件
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mOnItemClickListener.onClick(v,holder.getAdapterPosition(),id);
					}
				});
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						mOnItemClickListener.onLongClick(holder.getAdapterPosition(),cardList);
						return false;
					}
				});
        }
    }
	public void setSelectedIndex(int position) {
		this.selectedIndex = position;
		notifyDataSetChanged();
	}
    @Override
    public int getItemCount() {
		id.size();
        return cardList.size();
    }


}

