package com.jiyutq.wzry.ui.fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.cpoopc.scrollablelayoutlib.ScrollableHelper;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.*;

import com.jiyutq.wzry.R;
import com.jiyutq.wzry.model.*;
import com.jiyutq.wzry.ui.adapter.*;
import com.jiyutq.wzry.*;


/**
 * Created by apple on 2017/6/20.
 */

public class ScrollableFragment extends Fragment implements ScrollableHelper.ScrollableContainer {
    RecyclerViewHeader header;

    RecyclerView recyclerView;

    ScrollView scrollView;
    private View view;
    List<MainBean> data = new ArrayList<>();
    MainBean bean;
    FragmentAdapter adapter;
    RelativeLayout relativeLayout;
    MainActivity activity;
    @Override
    public View getScrollableView() {
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        view = View.inflate(getActivity(), R.layout.fragment_listview, null);

		header = (RecyclerViewHeader) view.findViewById(R.id.header);
		recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		scrollView=(ScrollView) view.findViewById(R.id.scrollView);

        initView();
        activity= (MainActivity) getActivity();
        return view;
    }
	private void initView() {
        initAdapter();
        initData();
        initonScroll();
    }


    private void initonScroll() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
				@Override
				public void onScrollChange(View view, int i, int i1, int i2, int i3) {
					//Log.e("i======", "i:" + i + ",i1:" + i1 + ",i2:" + i2 + ",i3:" + i3);
					if (i1>300) {
						activity.getonScroll(i1);
					}
				}
			});
        //这里获取不到recycler的监听事件，因为ScrollView冲突问题，在布局将滚动焦点交给了ScrollView处理
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
					super.onScrollStateChanged(recyclerView, newState);
				}

				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView, dx, dy);
					//Log.e("d======", dx+","+dy);

				}
			});
    }


    private void initData() {

        for (int i = 0; i < 10; i++) {
            data.add(i, bean);
        }
        adapter.notifyDataSetChanged();
    }


    private void initAdapter() {
        View headerView = View.inflate(getContext(), R.layout.view_header, null);
        relativeLayout = (RelativeLayout) headerView.findViewById(R.id.header_title);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        header.addView(headerView);
//        header.attachTo(recyclerView);
        adapter = new FragmentAdapter(data, getActivity());
        //分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}


