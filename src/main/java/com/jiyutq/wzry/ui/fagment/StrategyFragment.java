package com.jiyutq.wzry.ui.fagment;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import com.jiyutq.wzry.*;
import com.jiyutq.wzry.ui.adapter.*;
import java.util.*;
import java.io.*;
import android.util.*;
import org.json.*;
import android.widget.*;
import com.bumptech.glide.*;
import android.view.View.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.*;

import android.graphics.*;
import com.bumptech.glide.request.target.*;
import android.support.annotation.*;
import android.transition.*;
import android.support.v7.graphics.*;
import com.bumptech.glide.request.animation.*;
import android.graphics.drawable.*;
import android.content.*;

public class StrategyFragment extends Fragment
{
	// 选择英雄
    private List<String> data = new ArrayList<>();//必须初始化
	private List<String> id = new ArrayList<>();//必须初始化
    private RecyclerView recyclerView;//英雄导航栏
    private ChooseHeroAdapter adapter;//选择英雄配适器
	private int xuhao;//英雄序号，用来记录点击
	private String resultString;
	private String name;
	private ImageView bd,jn1,jn2,jn3;
	private TextView jntext,jqiao;
	private ImageView header1,header2,cs;
	private TextView mname,pfname,dinwei;
	private ImageView pf1,pf2,pf3,pf4,pf5,pf6,pf7;
	private String jibd;
	private String jin1;
	private String jin2;
	private String jin3;
	private ImageView mybg;

	private static final int iterations = 5;

	private static final float hRadius = 25;

	private static final float vRadius = 25;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_strategy, null);
		mybg=v.findViewById(R.id.data_heroImageView);
		recyclerView = (RecyclerView) v.findViewById(R.id.mrecyclerview);
		// 选择英雄
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置为横向
        recyclerView.setLayoutManager(layoutManager);
		//解析
		jsoupData();
		//技能图标
		bd=(ImageView) v.findViewById(R.id.bdimg);
		jn1=(ImageView) v.findViewById(R.id.aimg);
		jn2=(ImageView) v.findViewById(R.id.bimg);
		jn3=(ImageView) v.findViewById(R.id.cimg);

		jntext=(TextView) v.findViewById(R.id.jntext);
		//资料
		cs = (ImageView) v.findViewById(R.id.yxjsImageView1);
		mname = (TextView) v.findViewById(R.id.name);
		pfname = (TextView) v.findViewById(R.id.pfname);
		dinwei=(TextView) v.findViewById(R.id.dinwei);
		jqiao=(TextView) v.findViewById(R.id.data_herojiqiao);
		//皮肤按钮
		pf1=(ImageView) v.findViewById(R.id.pfbn1);
		pf2=(ImageView) v.findViewById(R.id.pfbn2);
		pf3=(ImageView) v.findViewById(R.id.pfbn3);
		pf4=(ImageView) v.findViewById(R.id.pfbn4);
		pf5=(ImageView) v.findViewById(R.id.pfbn5);
		pf6=(ImageView) v.findViewById(R.id.pfbn6);
		pf7=(ImageView) v.findViewById(R.id.pfbn7);
		initdata1();
		onclic();

		return v;
	}




	private void onclic()
	{
		adapter.setOnClickListener(new ChooseHeroAdapter.OnItemClickListener(){

				@Override
				public void onLongClick(int adapterPosition, List<String> cardList)
				{
					// TODO: Implement this method
				}

				@Override
				public void onClick(View view, int position, List<String> id)
				{
					adapter.setSelectedIndex(position);
					int cs =  Integer.parseInt(id.get(position));
					//ToastUtils.show(cs);
					xuhao=// 获取itemView的位置
						cs;
					initdate(xuhao);
					setjin();
					//技能图标
					Glide.with(getActivity())
						.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"00.png")
						.into(bd);
					Glide.with(getActivity())
						.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"10.png")
						.into(jn1);
					Glide.with(getActivity())
						.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"20.png")
						.into(jn2);
					Glide.with(getActivity())
						.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"30.png")
						.into(jn3);
				}

				@Override
				public void onLongClick(int position)
				{
					// TODO: Implement this method
				}
			});
		Log.i("编号",name);
	}

	private void initdata1()
	{
		xuhao=// 获取itemView的位置
			105;
		initdate(xuhao);
		setjin();
	}
	private void initmdate(int xuhao){

		jntext.post(new Runnable(){

				@Override
				public void run()
				{
					jntext.setText("点击技能查看");
				}
			});
		//抓取的目标网址
		String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
		//英雄定位网址
		String yxdw="https://pvp.qq.com/web201605/herodetail/m/"+xuhao+".html";

		try {//捕捉异常
			Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
			Elements nv1_elements_list = yingxiong.select("h2.cover-name");
			final String name = nv1_elements_list.text();

			mname.post(new Runnable(){

					@Override
					public void run()
					{
						mname.setText(name);
					}
				});
			Log.d("英雄名称",name);
			Elements nv2_elements_list = yingxiong.select("p.skill-name");
			Log.d("技能",nv2_elements_list.text());
			Elements nv3_elements_list = yingxiong.select("p.skill-desc");
			Log.d("技能描述",nv3_elements_list.text());

			jibd=nv3_elements_list.text().split(" ")[0];
		    jin1=nv3_elements_list.text().split(" ")[1];
			jin2=nv3_elements_list.text().split(" ")[2];
			jin3=nv3_elements_list.text().split(" ")[3];
			Log.d("被动",jibd);
			Log.d("一技能",jin1);
			Log.d("二技能",jin2);
			Log.d("三技能",jin3);
			Document jiqiao = Jsoup.connect(yxdw).get();
			Elements jq = jiqiao.select("p.use-skills");
			final String log = jq.text();
			//Log.i("技巧",log);

			jqiao.post(new Runnable(){

					@Override
					public void run()
					{
						jqiao.setText(log);
					}
				});
			/*Elements nv4_elements_list = yingxiong.select("p.skill-tips");
			 Log.d("技能玩法",nv4_elements_list.text());*/
			Elements nv5_elements_list = yingxiong.select("p.sugg-tips");
			Log.d("出装推荐",nv5_elements_list.text());
			final Elements nv6_elements_list = yingxiong.select("h3.cover-title");
			Log.d("皮肤名称",nv6_elements_list.text());

			pfname.post(new Runnable(){

					@Override
					public void run()
					{
						pfname.setText(nv6_elements_list.text());
					}
				});
			Elements elements2 = yingxiong.select("i.ibar");
			if (elements2.isEmpty()) {
				Log.d("null", "null");
			} else {
				for (int s = 0; s < elements2.size(); s++) {
					String str = elements2.get(s).attr("style");
					Log.i("属性", str.replaceAll("[^\\d]",""));
					String[] arr={str};
					List list = Arrays.asList(arr);

				}
			}
			Document dw = Jsoup.connect(yxdw).get();

			Elements wzdw = dw.select("p.hero-location");
			for(int i=0;i<wzdw.size();i++) {
				String as =wzdw.get(i).attr("data-herotype");
				String bs=as.split("")[1];
				int cs =  Integer.parseInt(bs);
				Log.i("定位",bs);
				if(cs==1){

					dinwei.post(new Runnable(){

							@Override
							public void run()
							{
								dinwei.setText("战士");
							}
						});
				}
				if(cs==2){

					dinwei.post(new Runnable(){

							@Override
							public void run()
							{
								dinwei.setText("法师");
							}
						});
				}
				if(cs==3){

					dinwei.post(new Runnable(){

							@Override
							public void run()
							{
								dinwei.setText("坦克");
							}
						});
				}
				if(cs==4){

					dinwei.post(new Runnable(){

							@Override
							public void run()
							{
								dinwei.setText("刺客");
							}
						});
				}
				if(cs==5){

					dinwei.post(new Runnable(){

							@Override
							public void run()
							{
								dinwei.setText("射手");
							}
						});
				}
				if(cs==6){

					dinwei.post(new Runnable(){

							@Override
							public void run()
							{
								dinwei.setText("辅助");
							}
						});
				}
				Elements zhs = yingxiong.select("div.sugg-info2.info");
				Elements jianyi = yingxiong.select("p.sugg-name");
				Elements icon = yingxiong.select("p.icon.sugg-skill");
				Elements cz = yingxiong.select("ul.equip-list.fl");
				Elements cznr1 = yingxiong.select("p.equip-tips");
				Elements mwjy = yingxiong.select("ul.sugg-u1");
				Elements mwjs = yingxiong.select("p.sugg-tips");
				for (int a = 0; a < jianyi.size(); a++) {
					//Log.i("技能加点建议",jianyi.get(i).text());
				}
				for (int b = 0; b < icon.size(); b++) {
					Log.i("技能加点建议图标",icon.get(b).select("img").attr("src"));
				}

			}

		} catch (Exception e) {
			Log.e("wwwwwwwww==", e.toString());
		}

	}

	private void setjin()
	{
		bd.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					jntext.setText(jibd);
				}
			});
		jn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					jntext.setText(jin1);
				}
			});
		jn2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					jntext.setText(jin2);
				}
			});
		jn3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					jntext.setText(jin3);
				}
			});

		// TODO: Implement this method
	}
	public static Drawable BoxBlurFilter(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width  * height];
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < iterations; i++) {
            blur(inPixels,  outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }
        blurFractional(inPixels, outPixels, width, height, hRadius);
        blurFractional(outPixels, inPixels, height, width, vRadius);
        bitmap.setPixels(inPixels, 0,width, 0, 0,width, height);
        Drawable  drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public static void blur(int[] in, int[] out, int width, int height, float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];
        for (int i = 0; i < 256 * tableSize; i++) divide[i] = i / tableSize;
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];
                int i1 = x + r + 1;
                if (i1 > widthMinus1) i1 = widthMinus1;
                int i2 = x - r;
                if (i2 < 0) i2 = 0;
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];

                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }

    public static void blurFractional(int[] in, int[] out, int width, int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];
                int a1 = (rgb1 >> 24) & 0xff;
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;
                int a2 = (rgb2 >> 24) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;
                int a3 = (rgb3 >> 24) & 0xff;
                int r3 = (rgb3 >> 16) & 0xff;
                int g3 = (rgb3 >> 8) & 0xff;
                int b3 = rgb3 & 0xff;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;
        }
    }

    public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }
	
	
	private void kipp(int xuhao){
		String url = "https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-1.jpg";
		Glide.with(this)
			.load(url)
			.asBitmap()
			.into(new SimpleTarget<Bitmap>(750, 750) {

				@Override
				public void onResourceReady(@NonNull Bitmap srcBitmap,@Nullable GlideAnimation<? super Bitmap> p2)
				{
				//	mybg.setImageBitmap(getReverseBitmap(App.sContext,srcBitmap,0.5f));
					mybg.setImageDrawable(BoxBlurFilter(srcBitmap));
					}
				});}
	/**
     *
     * @param context  上下文
     * @param resId    图片id
     * @param percent  倒影的深度 0~1f
     * @return Bitmap
     */
	/*public Bitmap getReverseBitmap(Context sContext, Bitmap srcBitmap, float percent)
	{
		// 获取原始位图
       // Bitmap srcBitmap= BitmapFactory.decodeResource(context.getResources(), resId);

        // 运用Matrix类反转像素
        Matrix matrix=new Matrix();
        matrix.setScale(1, -1);

        //创建倒影位图
        Bitmap rvsBitmap=Bitmap.createBitmap(srcBitmap, 0, (int) (srcBitmap.getHeight()*(1-percent)),
											 srcBitmap.getWidth(), (int) (srcBitmap.getHeight()*percent), matrix, false);

        // 根据上面原始位图和倒影位图高度+相隔20的高度创建新位图
        Bitmap comBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),
											 srcBitmap.getHeight()+rvsBitmap.getHeight()+20, srcBitmap.getConfig());

        //绘制出原始位图和倒影位图
        Canvas gCanvas=new Canvas(comBitmap);
        gCanvas.drawBitmap(srcBitmap, 0, 0, null);
        gCanvas.drawBitmap(rvsBitmap, 0, srcBitmap.getHeight(), null);


        Paint paint=new Paint();

        //LinearGradient,我们可以将之译为线型渐变、线型渲染等
        //Shader.TileMode.CLAMP,这种模式表示重复最后一种颜色直到该View结束的地方
        LinearGradient shader=new LinearGradient(0, srcBitmap.getHeight(), 0, comBitmap.getHeight(),
												 Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        //setXfermode图像混合
        //DST_IN为显示上方覆盖内容
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        gCanvas.drawRect(0, srcBitmap.getHeight(), srcBitmap.getWidth(), comBitmap.getHeight(), paint);
        return comBitmap;
	}
    *
	//创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor,int color) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;

        Bitmap  bgBitmap= Bitmap.createBitmap(mybg.getWidth(),mybg.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas  canvas=new Canvas();
        Paint   paint=new Paint();
        canvas.setBitmap(bgBitmap);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient=new LinearGradient(0, 0, 0, bgBitmap.getHeight(),bgColors[0],bgColors[1], Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        paint.setAntiAlias(true);
        RectF rectF=new RectF(0,0,bgBitmap.getWidth(),bgBitmap.getHeight());
        canvas.drawRoundRect(rectF,20,20,paint);
        canvas.drawRect(rectF,paint);
       // mybg.setImageBitmap(bgBitmap);
		
    }
	//修改透明度
   /* private static Bitmap getImageToChange(Bitmap mBitmap) {
        Log.d("渐变","with="+mBitmap.getWidth()+"--height="+mBitmap.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        int mWidth = mBitmap.getWidth();
        int mHeight = mBitmap.getHeight();
        for (int i = 0; i < mHeight; i++) {
            for (int j = 0; j < mWidth; j++) {
                int color = mBitmap.getPixel(j, i);
                int g = Color.green(color);
                int r = Color.red(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);

                float index=i*1.0f/mHeight;
                if(index>0.5f ){
                    float temp=i-mHeight/2.0f;
                    a= 255-(int) (temp/375*255);
                }
                color = Color.argb(a, r, g, b);
                createBitmap.setPixel(j, i, color);
            }
        }
        return createBitmap;
    }*/
    private void initdate(final int xuhao){
		new Thread(new Runnable() {
				@Override
				public void run() {
					initmdate(xuhao);
					//	pickColor(xuhao);
				}
			}).start();
		mybg.post(new Runnable(){

				@Override
				public void run()
				{
					kipp(xuhao);
				}
			});
			
		//data.add("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+i+"/"+i+".jpg");
		Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-1.jpg").into(cs);		
		//Glide.with(getActivity()).load(url).bitmapTransform(new BlurTransformation(getActivity(), 10)).into(mybg);
		final int p1 =1;
		final int p2 =2;
		final int p3 =3;
		final int p4 =4;
		final int p5 =5;
		final int p6 =6;
		final int p7 =7;
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p1+".jpg")
			.into(pf1);
		pf1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p1+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p1);
							}

							private void initpfdate(int xuhao, int p1)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[0];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});

										}
									}

								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
					// TODO: Implement this method
				}
			});
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p2+".jpg")
			.into(pf2);
		pf2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p2+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p2);
							}

							private void initpfdate(int xuhao, int p2)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[1];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});
										}
									}
								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
				}
			});
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p3+".jpg")
			.into(pf3);
		pf3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p3+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p3);
							}

							private void initpfdate(int xuhao, int p3)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[2];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});
										}
									}
								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
				}
			});
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p4+".jpg")
			.into(pf4);
		pf4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p4+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p4);
							}

							private void initpfdate(int xuhao, int p4)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[3];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});
										}
									}
								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
				}
			});
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p5+".jpg")
			.into(pf5);
		pf5.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p5+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p5);
							}

							private void initpfdate(int xuhao, int p5)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[4];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});
										}
									}
								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
				}
			});
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p6+".jpg")
			.into(pf6);
		pf6.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick( View p1)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p6+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p6);
							}

							private void initpfdate(int xuhao, int p6)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[5];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});
										}
									}
								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
				}
			});
		Glide.with(getActivity())
			.load("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+xuhao+"/"+xuhao+"-smallskin-"+p7+".jpg")
			.into(pf7);
		pf7.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick( View p1)
				{
					Glide.with(getActivity()).load("https://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/"+xuhao+"/"+xuhao+"-bigskin-"+p7+".jpg").into(cs);		
					new Thread(new Runnable() {
							@Override
							public void run() {
								initpfdate(xuhao,p7);
							}

							private void initpfdate(int xuhao, int p7)
							{
								//抓取的目标网址
								String url = "https://pvp.qq.com/web201605/herodetail/"+xuhao+".shtml";
								try {//捕捉异常
									Document yingxiong = Jsoup.connect(url).get();//这里可用get也可以post方式，具体区别请自行了解
									Elements elements = yingxiong.select("ul.pic-pf-list.pic-pf-list3");
									if (elements.isEmpty()) {
										Log.d("null", "null");
									} else {
										for (int i = 0; i < elements.size(); i++) {
											Log.d("新英雄名称", elements.get(i).attr("data-imgname"));
											String str3=elements.get(i).attr("data-imgname");
											final String strarray3=str3.split("[|]")[6];
											Log.i("截取",strarray3);
											pfname.post(new Runnable(){

													@Override
													public void run()
													{
														pfname.setText(strarray3);
													}
												});
										}
									}
								} catch (Exception e) {
									Log.e("wwwwwwwww==", e.toString());
								}
								// TODO: Implement this method
								// TODO: Implement this method
							}
						}).start();
				}
			});

	}

	private void jsoupData()
	{
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(getResources().getAssets().open("herolist.json"), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);
			String line;
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			inputStreamReader.close();
			bufferedReader.close();
			resultString = stringBuilder.toString();
			Log.i("TAG", stringBuilder.toString());
			//将读出的字符串转换成JSONobject
			doParseJson();

		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doParseJson()
	{
		// 判断用户是否读取了Json文件
		if (resultString == null) {
			Toast.makeText(getActivity(), "请先读取Json文件！", Toast.LENGTH_SHORT).show();
		} else {
			try {

				// 基于content字符串创建Json数组
				JSONArray jsonArray = new JSONArray(resultString);
				// 遍历Json数组
				for (int i = 0; i < jsonArray.length(); i++) {
					// 通过下标获取json数组元素——Json对象
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					// 对Json对象按键取值

					name = jsonObject.getString("ename");
					id.add(name);
					data.add("https://game.gtimg.cn/images/yxzj/img201606/heroimg/"+name+"/"+name+".jpg");
					adapter = new ChooseHeroAdapter(data,id);
					recyclerView.setAdapter(adapter);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}

