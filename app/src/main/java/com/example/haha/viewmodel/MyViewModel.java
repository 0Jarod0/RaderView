package com.example.haha.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    public int number;

    private MutableLiveData<Integer> currentSecond;

    private MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getCurrentSecond() {
        if (currentSecond == null){
            currentSecond = new MutableLiveData<>();
            currentSecond.setValue(0);
        }
        return currentSecond;
    }

    public MutableLiveData<Integer> getProgress() {
        if (progress == null){
            progress = new MutableLiveData<>();
            progress.setValue(0);
        }
        return progress;
    }
}
