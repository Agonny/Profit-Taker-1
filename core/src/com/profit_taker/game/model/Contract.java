package com.profit_taker.game.model;

public class Contract {
    double money;
    double countProduct;
    double countResource1;
    double countResource2;
    double countResource3;
    int time;
    int typeContract;

    public Contract(double money, double countProduct, double countResource1, double countResource2,double countResource3, int time, int typeContract){
        this.money = money;
        this.countProduct = countProduct;
        this.countResource1 = countResource1;
        this.countResource2 = countResource2;
        this.countResource3 = countResource3;
        this.time = time;
        this.typeContract = typeContract;
    }
}
