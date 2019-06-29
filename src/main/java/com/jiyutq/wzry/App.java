package com.jiyutq.wzry;

import android.app.*;
import android.content.*;


import org.json.*;

import java.io.*;

public class App extends Application
{
	public static Context sContext;
	@Override
	public void onCreate()
	{
		super.onCreate();
		sContext = this;
		// 初始化吐司工具类


	}

}

