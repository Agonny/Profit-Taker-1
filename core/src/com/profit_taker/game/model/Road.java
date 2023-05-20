package com.profit_taker.game.model;

public class Road {
    double base_speed;
    double speed;
    int level;
    Road(int typeLandscape){
        this.level = 0;
        switch(typeLandscape){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;

        }
    }

    Road(double base_speed, int level){
        this.base_speed = base_speed;
        this.level = level;
        building(level);
    }

    public void building(int level){
        switch(level){

        }
    }
}
