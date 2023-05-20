package com.profit_taker.game.model;


public class EmptyCell extends GameObject{
    int typeLandscape;
    String name;
    String strRainfall = "No info";
    String strTypeLandscape = "No info";
    int loyalty = 50;
    String strMovement = "Normal";

    public EmptyCell(double rainfall,int typeLandscape, String name) {
        this.typeLandscape = typeLandscape;
        this.rainfall = rainfall;
        this.name = name;
        if(typeLandscape == 0){
            this.strTypeLandscape = "Lowland";
        }
        if(typeLandscape == 1){
            this.strTypeLandscape = "Plain";
        }
        if(typeLandscape == 2){
            this.strTypeLandscape = "Hill";
        }
        if(rainfall>0&&rainfall<0.7){
            this.strRainfall = "Low";
        }else if(rainfall>=0.7&&rainfall<1 ){
            this.strRainfall = "Normal";
        }else if(rainfall>=1){
            this.strRainfall = "High";
        }
    }

    public String getName(){
        return this.name;
    }

    public String getStrTypeLandscape(){
        return this.strTypeLandscape;
    }

    public int getTypeLandscape() {
        return typeLandscape;
    }

    public String getStrRainfall(){
        return this.strRainfall;
    }

    public int getReputation(){
        return this.loyalty;
    }

    public String getStrMovement(){
        return this.strMovement;
    }
}
