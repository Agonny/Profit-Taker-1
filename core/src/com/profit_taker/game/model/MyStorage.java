package com.profit_taker.game.model;

public class MyStorage {
    double wheatCount;
    double milkCount;
    double eggCount;
    double sugarCount;
    double fruitCount;
    double currentWheatCount;
    double currentMilkCount;
    double currentEggCount;
    double currentSugarCount;
    double currentFruitCount;

    public MyStorage(){
        this.wheatCount = 0;
        this.milkCount = 0;
        this.eggCount = 0;
        this.sugarCount = 0;
        this.fruitCount = 0;
    }

    public void updateStorage(int type, double count, boolean isRealUpdate){
        if(isRealUpdate){
            switch (type){
                case 0:
                    this.wheatCount = this.wheatCount + count;
                    this.currentWheatCount = this.wheatCount;
                    break;
                case 1:
                    this.milkCount = this.milkCount + count;
                    this.currentMilkCount = this.milkCount;
                    break;
                case 2:
                    this.eggCount = this.eggCount + count;
                    this.currentEggCount = this.eggCount;
                    break;
                case 3:
                    this.sugarCount = this.sugarCount + count;
                    this.currentSugarCount = this.sugarCount;
                    break;
                case 4:
                    this.fruitCount = this.fruitCount + count;
                    this.currentFruitCount = fruitCount;
                    break;

            }
        }
        else {
            switch (type){
                case 0:
                    this.currentWheatCount = this.currentWheatCount - count;
                    break;
                case 1:
                    this.currentMilkCount = this.currentMilkCount - count;
                    break;
                case 2:
                    this.currentEggCount = this.currentEggCount - count;
                    break;
                case 3:
                    this.currentSugarCount = this.currentSugarCount - count;
                    break;
                case 4:
                    this.currentFruitCount = this.currentFruitCount - count;
                    break;

            }
        }
    }

    public double returnCountResource(int type){
        double res = 0;
            switch (type){
                case 0:
                    res = res+this.currentWheatCount;
                    break;
                case 1:
                    res = res+this.currentMilkCount;
                    break;
                case 2:
                    res = res+this.currentEggCount;
                    break;
                case 3:
                    res = res+this.currentSugarCount;
                    break;
                case 4:
                    res = res+this.currentFruitCount;
                    break;

        }
        return res;
    }

    public double returnAllResources(){
        return this.wheatCount + this.milkCount + this.eggCount + this.sugarCount + this.fruitCount;
    }
}

