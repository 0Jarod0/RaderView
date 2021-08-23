package com.example.haha.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author haha
 */
public class WidgetService extends Service {

    private final String ACTION_UPDATE_ALL = "com.example.widget.UPDATE_ALL";

    private static final int UPDATE_TIME = 1000;

    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Intent updateIntent = new Intent(ACTION_UPDATE_ALL);
                sendBroadcast(updateIntent);
            }
        };
        mTimer.schedule(mTimerTask,1000,UPDATE_TIME);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimerTask.cancel();
        mTimer.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
