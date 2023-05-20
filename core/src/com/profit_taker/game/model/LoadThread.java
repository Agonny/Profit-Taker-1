package com.profit_taker.game.model;

public class LoadThread {
    int num;
    WindowBuilder builder;

    LoadThread(int num, WindowBuilder builder) {
        this.num = num;
        this.builder = builder;
    }

    public void run(int num) {
//        try {
                builder.loadTextures(num);

//        }
// Обработка исключения прерывания потока:
//        catch (InterruptedException e) {
//            System.out.println("Thread "+num+" ended");
//        }
    }

}
