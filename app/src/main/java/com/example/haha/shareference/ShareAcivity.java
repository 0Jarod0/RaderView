package com.example.haha.shareference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haha.customview.R;

public class ShareAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        SharedPreferences sp = getSharedPreferences("test", Context.MODE_PRIVATE);
        sp.edit().putString("demo","这是测试").apply();

        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("test",Context.MODE_PRIVATE);
        Log.i("ShareAcivity", "onCreate: "+sp1.getString("demo",""));

        SharedPreferences sp2 = getSharedPreferences("test",Context.MODE_PRIVATE);
        Log.i("ShareAcivity", "onCreate1: "+sp2.getString("demo",""));
    }
}
