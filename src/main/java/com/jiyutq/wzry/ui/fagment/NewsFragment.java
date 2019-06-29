package com.jiyutq.wzry.ui.fagment;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import com.jiyutq.wzry.*;

import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import com.jiyutq.wzry.R;
import com.jiyutq.wzry.ui.adapter.*;
public class NewsFragment extends Fragment 
{
	private List<String> title = new ArrayList<String>();//必须初始化
	private List<String> time = new ArrayList<String>();//必须初始化
	private List<String> lx = new ArrayList<String>();//必须初始化
	private List<String> url = new ArrayList<String>();//必须初始化
	private RecyclerView mRecycler;
	private PopularAdapter adapter;//
	private String mmurl,bt,sj;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_b, null);

		mRecycler=(RecyclerView) v.findViewById(R.id.recyclerview);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		mRecycler.setLayoutManager(linearLayoutManager);
		//设置分割线使用的divider
		mRecycler.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(getActivity(), android.support.v7.widget.DividerItemDecoration.VERTICAL));
		//设置增加或删除条目的动画  
		mRecycler.setItemAnimator( new DefaultItemAnimator());  
	    //这里需要放在子线程中完成，否则报这个错android.os.NetworkOnMainThreadException
		new Thread(new Runnable() {
				@Override
				public void run() {
					popularData();
					//adapter.notifyDataSetChanged();
					//swiperereshlayout.setRefreshing(false);
				}
			}).start();



		return v;
	}



	private void popularData() {
		//新闻列表网址
        String b = "https://pvp.qq.com/webplat/info/news_version3/15592/24091/24092/24094/m15240/list_1.shtml";
        try {//捕捉异常
            Document liebiao = Jsoup.connect(b).get();//这里可用get也可以post方式，具体区别请自行了解
			Elements wzry = liebiao.select("div.art_lists").select("li");
		    for (int i = 0; i < wzry.size(); i++) {
				String href = wzry.get(i).select("a").attr("href");
				String target = wzry.get(i).select("a.art_type.fl").text();
				String href2 = wzry.get(i).select("a").get(1).attr("href");
				bt = wzry.get(i).select("a.art_word").text();
				String span = wzry.get(i).select("span.art_day").text();
				Log.i("热门标题",bt);
				title.add(bt);
				time.add(span);
				lx.add(target);
				url.add("https://pvp.qq.com"+href2);
				mRecycler.post(new Runnable(){
						@Override
						public void run() {
							adapter = new PopularAdapter(title,time,lx,url);

							mRecycler.setAdapter(adapter);

							adapter.setOnClickListener(new PopularAdapter.OnItemClickListener(){

									@Override
									public void onClick(View view, int position, List<String> url)
									{
										String murl =url.get(position);
										//Log.i("链接",murl);
										//ToastUtils.show(murl);
										View vie = LayoutInflater.from(getActivity()).inflate(R.layout.web,null);
										AlertDialog.Builder q= new AlertDialog.Builder(getActivity());
										WebView web = (WebView) vie.findViewById(R.id.webview);
										web.loadUrl(murl);
										//web.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
										WebSettings webSettings=web.getSettings();
										webSettings.setJavaScriptEnabled(true);//允许使用js
										//支持屏幕缩放
										webSettings.setSupportZoom(true);
										webSettings.setBuiltInZoomControls(true);
										q.setView(vie);
										q.show();
									}

									@Override
									public void onLongClick(int position)
									{
										// TODO: Implement this method
									}
								});
						}
					});

			}

        } catch (Exception e) {
            Log.e("wwwwwwwww==", e.toString());
        }

    }

}



