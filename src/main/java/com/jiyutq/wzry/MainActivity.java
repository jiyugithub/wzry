package com.jiyutq.wzry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import android.view.View.*;
import java.lang.reflect.*;
import com.jiyutq.wzry.utils.*;
import com.jiyutq.wzry.R;
import com.jiyutq.wzry.*;
import com.jiyutq.wzry.ui.fagment.*;
import com.jiyutq.wzry.ui.fagment.PopularFragment;
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Banner header;
    private TabLayout tab;
    private ViewPager vp;
    private ScrollableLayout scrollablelayout;
    private RelativeLayout title;
    private ImageView titleBarBack;
    private TextView titleBarTitle;
    private TextView titleBarContent;
    private RelativeLayout headerTitle;
//    @InjectView(R.id.srl)
//    SwipeRefreshLayout srl;
    private  PopularFragment fragment;
    private String[] titles = new String[]{"热门", "公告", "新闻", "活动", "攻略", "赛事", "收藏"};

    private  List<Object> img = new ArrayList<>();
    private String url = "https://ossweb-img.qq.com/upload/webplat/info/yxzj/20190116/82470431476138.jpg";
    private ViewPagerAdapter adapterVP;
    private List<Fragment> fragmentList = new ArrayList<>();
    private RelativeLayout relativeLayout;
    //动画
    private TranslateAnimation mShowAction, mHiddenAction;
    private View headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initmView();
    }

	private void initmView()
	{
		header = (Banner) findViewById(R.id.header);
		tab = (TabLayout) findViewById(R.id.tab);
		vp = (ViewPager) findViewById(R.id.vp);
		scrollablelayout = (ScrollableLayout) findViewById(R.id.scrollablelayout);
		title = (RelativeLayout) findViewById(R.id.title);
		titleBarBack = (ImageView) findViewById(R.id.title_bar_back);
		titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
		titleBarContent = (TextView) findViewById(R.id.title_bar_content);
		headerTitle = (RelativeLayout) findViewById(R.id.header_title);
		// TODO: Implement this method
		initView();
	}
	private void initView() {
        initBanner();
        initTabLayout();
        initFragment();
        title.setBackgroundColor(Color.argb(150, 0, 0, 0));
        titleBarTitle.setTextColor(Color.argb((int) 255, 198, 166, 102));
        titleBarContent.setTextColor(Color.argb((int) 255, 198, 166, 102));
        initOnClickScroll();
        initSwipeRefresh();
    }
    /*刷新监听*/
    private void initSwipeRefresh() {
//        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                srl.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        srl.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });
    }

    /*滚动监听*/
    private void initOnClickScroll() {
        scrollablelayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
				@Override
				public void onScroll(int i, int i1) {
					float scale = (float) i1 - i;
					float alpha = (255 * scale);
					float alpha2 = scale / i1 * 150;
					float alphaTv = scale / i1 * 250;
					float alpha3 = (float) i / i1 * 130;

					float alphaTop = (float) i / i1 * 150;
					LinearLayout.LayoutParams lp = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				    LinearLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(0, (int) alphaTop, 0, 0);
					tab.setLayoutParams(lp);
					if (i == i1) {
//                    title.setVisibility(View.GONE);
						//titleBarTitle.setText("");
						//titleBarTitle.setTextColor(Color.GREEN);
						titleBarTitle.setBackgroundResource(R.drawable.logo);
					} else if (i < i1) {
						//titleBarTitle.setText("");
						//titleBarTitle.setTextColor(Color.);
						titleBarTitle.setBackgroundResource(R.drawable.logo);
					}
					if (i < i1) {
						title.setVisibility(View.VISIBLE);
					}
					//img设置渐变
					float f = (float) i / i1; //0-1递增
					float f1 = scale / i1;   //1-0递减
					titleBarBack.setAlpha(f1);
//                0-100递增偏移量
					titleBarBack.scrollTo((int) alpha3, 0);
					Log.i("aaa======", i + "    ,alpha:" + alpha + "     ,alpha2:" + alpha2 + "  ,alpha3:" + alpha3 + "    ,alphaTop:" + alphaTop);
//                titleBarBack.setPadding((int) scale / i1 * 100, 12, 0, 8);
					//通过距离设置渐变效果
					//title.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
					title.setBackgroundColor(Color.argb((int) alpha2, 0, 0, 0));
					titleBarTitle.setTextColor(Color.argb((int) alphaTv - 1, 198, 166, 102));
					titleBarContent.setTextColor(Color.argb((int) alphaTv, 198, 166, 102));
				}
			});
    }

    /*初始化Fragment*/
    private void initFragment() {
        fragment = new PopularFragment();
        NoticeFragment fragment1 = new NoticeFragment();
        NewsFragment fragment2 = new NewsFragment();
        CampaignFragment fragment3 = new CampaignFragment();
        StrategyFragment fragment4 = new StrategyFragment();
        MatchFragment fragment5 = new MatchFragment();
        ScrollableFragment fragment6 = new ScrollableFragment();
        fragmentList.add(fragment);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
        fragmentList.add(fragment6);
        adapterVP = new ViewPagerAdapter(getSupportFragmentManager());
		vp.setOffscreenPageLimit(7);
		vp.setCurrentItem(1);
        vp.setAdapter(adapterVP);
		setTabLayoutIndicator(tab);
        tab.setupWithViewPager(vp);
    }

    /*通过Fragmenbt的滚动距离回调
	 * 注意：这里只能处理当ScrollLayout完全隐藏后，Fragment的ScrollView才能开始滚动事件，
	 * 上滑的时候却优先执行ScrollLayout的方法
     */

    public void getonScroll(int i) {
        Log.i("activity======I:", "i:" + i);
//        srl.setEnabled(i<450);

        if (i > 700) {
            headerTitle.setAnimation(mHiddenAction);
            headerTitle.setVisibility(View.GONE);
        }
        if (i < 450) {
            headerTitle.setAnimation(mShowAction);
            headerTitle.setVisibility(View.VISIBLE);
        }
    }

    /*初始化tab标签*/
    private void initTabLayout() {

        for (int i = 0; i < titles.length; i++) {
            tab.addTab(tab.newTab().setText(titles[i]));
        }

    }

    /*轮播*/
    private void initBanner() {
        //圆形指示器
        header.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //指示器居中
        header.setIndicatorGravity(BannerConfig.CENTER);
        img.add("http://m.beequick.cn/static/bee/img/m/boot_logo-275a61e3.png");
		img.add("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/177/177-bigskin-2.jpg");
        img.add("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/176/176-bigskin-2.jpg");
        img.add("https://ossweb-img.qq.com/upload/webplat/info/yxzj/20190116/82470431476138.jpg");
        img.add("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/174/174-bigskin-2.jpg");
        img.add("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/173/173-bigskin-2.jpg");
        img.add("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/180/180-bigskin-3.jpg");
        header.setImageLoader(new ImageLoader() {
				@Override
				public void displayImage(Context context, Object o, ImageView imageView) {
					Picasso.with(context)
                        .load(url)
                        .into(imageView);
				}
			});
        header.setImages(img);
        header.start();
    }
	@Override
	public void onClick(View view)
	{
		switch (view.getId()) {
            case R.id.title_bar_back:
                break;
            case R.id.title_bar_content:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
		// TODO: Implement this method
	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

	/**
     * 通过反射{@link TabLayout}设置下划线(Indicator)宽度，字多宽线就多宽，参阅 https://blog.csdn.net/waplyj/article/details/81068127
     */
    public static void setTabLayoutIndicator(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
				@Override
				public void run() {
					try {
						Field field = tabLayout.getClass().getDeclaredField("mTabStrip");
						field.setAccessible(true);
						//拿到tabLayout的mTabStrip属性
						LinearLayout tabStrip = (LinearLayout) field.get(tabLayout);
						for (int i = 0, count = tabStrip.getChildCount(); i < count; i++) {
							View tabView = tabStrip.getChildAt(i);
							//拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
							Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
							mTextViewField.setAccessible(true);
							TextView textView = (TextView) mTextViewField.get(tabView);

							tabView.setPadding(0, 0, 0, 0);
							//因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
							int textWidth = 0;
							textWidth = textView.getWidth();
							if (textWidth == 0) {
								textView.measure(0, 0);
								textWidth = textView.getMeasuredWidth();
							}
							int tabWidth = 0;
							tabWidth = tabView.getWidth();
							if (tabWidth == 0) {
								tabView.measure(0, 0);
								tabWidth = tabView.getMeasuredWidth();
							}
							LinearLayout.LayoutParams tabViewParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
							int margin = (tabWidth - textWidth) / 2;
							//LogUtils.d("textWidth=" + textWidth + ", tabWidth=" + tabWidth + ", margin=" + margin);
							tabViewParams.leftMargin = margin;
							tabViewParams.rightMargin = margin;
							tabView.setLayoutParams(tabViewParams);
						}
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			});
    }

    /**
     * 设置{@link TabLayout}的每项之间的分隔线
     */
    public static void setTabLayoutDivider(TabLayout tabLayout) {
        setTabLayoutDivider(tabLayout, 24);
    }

    /**
     * 设置{@link TabLayout}的每项之间的分隔线
     */
    public static void setTabLayoutDivider(TabLayout tabLayout, int paddingDip) {
        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
        mTabStrip.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mTabStrip.setDividerPadding(UIUtils.dip2px(paddingDip));
        mTabStrip.setDividerDrawable(UIUtils.getDrawalbe(R.drawable.ic_launcher));
    }
}

