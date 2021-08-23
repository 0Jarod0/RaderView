package com.example.haha.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.haha.customview.MainActivity;
import com.example.haha.customview.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author haha
 */
public class WidgetProvider extends AppWidgetProvider {

    //更新widget的广播对应的action
    private final String ACTION_UPDATE_ALL = "com.example.widget.UPDATE_ALL";
    //保存widget的id的HashSet,每新建一个widget都会为该widget分配一个id
    private static Set idsSet = new HashSet();

    public static int mIndex;

    /**
     * 接受小部件点击时发送的广播
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();

        if (ACTION_UPDATE_ALL.equals(action)){
            updateAllAppWidgets(context,AppWidgetManager.getInstance(context),idsSet);
        }else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)){
            mIndex = 0;
            updateAllAppWidgets(context,AppWidgetManager.getInstance(context),idsSet);
        }
    }

    //更新所有的widget
    private void updateAllAppWidgets(Context context
            , AppWidgetManager appWidgetManager,Set set){
        int appId;
        //迭代器，用于遍历所有保存的widget的id
        Iterator it = set.iterator();

        mIndex++;

        while (it.hasNext()){
            appId = ((Integer)it.next()).intValue();
            //获取example_appwidget.xml对应的RemoteViews
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            //设置显示数字
            remoteViews.setTextViewText(R.id.widget_txt,String.valueOf(mIndex));
            //设置点击按钮对应的PendingIntent,即点击按钮时，发送广播
            remoteViews.setOnClickPendingIntent(R.id.widget_btn_reset,getResetPendingIntent(context));
            remoteViews.setOnClickPendingIntent(R.id.widget_btn_open,getOpenPendingIntent(context));

            appWidgetManager.updateAppWidget(appId,remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Intent intent = new Intent(context,WidgetService.class);
        context.startService(intent);
        Toast.makeText(context,"开始计数",Toast.LENGTH_SHORT).show();
        super.onEnabled(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId:appWidgetIds){
            idsSet.add(Integer.valueOf(appWidgetId));
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId:appWidgetIds){
            idsSet.remove(Integer.valueOf(appWidgetId));
        }
        super.onDeleted(context,appWidgetIds);
    }


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent intent = new Intent(context,WidgetService.class);
        context.stopService(intent);
        super.onDisabled(context);
    }

    /**
     * 获取重置数字的广播
     * @param context
     * @return
     */
    private PendingIntent getResetPendingIntent(Context context){
        Intent intent = new Intent();
        intent.setClass(context,WidgetProvider.class);
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        PendingIntent pi = PendingIntent.getBroadcast(context,0 , intent, 0);
        return pi;
    }

    /**
     * 获取打开MainActivity的PendingIntent
     * @param context
     * @return
     */
    private PendingIntent getOpenPendingIntent(Context context){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra("main","这句话是我从桌面点开传过去的");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        return pi;
    }
}
