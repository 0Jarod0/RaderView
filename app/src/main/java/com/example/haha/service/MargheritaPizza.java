package com.example.haha.service;

import com.haha.api.annotation.Factory;

@Factory(
        id = "MargheritaPizza",
        type = Meal.class
)
public class MargheritaPizza implements Meal{
    @Override
    public float getPrice() {
        return 6.0f;
    }
}
