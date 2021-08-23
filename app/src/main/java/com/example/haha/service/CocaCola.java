package com.example.haha.service;

import com.haha.api.annotation.Factory;

@Factory(type = Drink.class,id = "CocaCola")
public class CocaCola implements Drink{
    @Override
    public float getPrice() {
        return 5f;
    }
}
