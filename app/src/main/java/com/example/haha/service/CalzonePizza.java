package com.example.haha.service;

import com.haha.api.annotation.Factory;

/**
 * @author haha
 */
@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal{
    @Override
    public float getPrice() {
        return 8.5f;
    }
}
