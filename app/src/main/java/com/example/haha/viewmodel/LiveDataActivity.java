package com.example.haha.viewmodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.haha.customview.R;

import java.util.Timer;
import java.util.TimerTask;

public class LiveDataActivity extends AppCompatActivity {

    private TextView tv;
    private MyViewModel myViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        tv = findViewById(R.id.textview);
        myViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MyViewModel.class);
        tv.setText(myViewModel.getCurrentSecond().getValue()+"");

        myViewModel.getCurrentSecond().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tv.setText(integer+"");
            }
        });

        startTimer();
    }

    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //非UI线程 使用postValue
                //UI线程 使用setvalue
                myViewModel.getCurrentSecond().postValue(myViewModel.getCurrentSecond().getValue()+1);
            }
        },1000,1000);
    }


}
