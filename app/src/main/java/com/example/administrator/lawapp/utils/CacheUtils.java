package com.example.administrator.lawapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.administrator.lawapp.SplashActivity;
import com.example.administrator.lawapp.activity.GuideActivity;

/**
 * @author 吴青晓
 * @data 2019/1/17 21:27
 * 作用：缓存软件一些参数和数据
 */
public class CacheUtils {
    /**
     * 得到缓存值
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("law",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    /**
     * 保存软件的参数
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("law",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
