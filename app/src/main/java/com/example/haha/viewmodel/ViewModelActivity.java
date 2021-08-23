package com.example.haha.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.haha.customview.R;

public class ViewModelActivity extends AppCompatActivity {

    private TextView tv;
    private MyViewModel myViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmodel);
        tv = findViewById(R.id.tv);
        myViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MyViewModel.class);
        tv.setText(myViewModel.number+"");
    }

    public void plus(View view) {
        tv.setText(++myViewModel.number+"");
    }
}
