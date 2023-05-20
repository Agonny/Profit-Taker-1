package com.profit_taker.game.model;

import java.util.ArrayList;

public class CityCell extends EmptyCell{
    String cityName;
    int population;
    int currentPopulation;
    double happiness;
    double currentHappiness;
    double controlProfit;
    double controlLosses;
    double[] saturation = {1,1,1,1,1};
    double[] productCounts = {0,0,0,0,0};
    double[] incomes = {0,0,0,0,0};
    double[] counts  = {0,0,0,0,0};
    double[] upgrades  = {1,1,1};
    int[] levels_of_upgrades  = {0,0,0};
    String[] upgradeNames = {"Package","Promotions","Advertisement"};
    int[] sellMultipliers = {46,30,16,36,26};
    double[] currentSellMultipliers = {46,30,16,36,26};
    ArrayList<Contract>[] contracts;

    public CityCell(double rainfall, int typeLandscape, String name) {
        super(rainfall, typeLandscape, name);
        this.cityName = name;

        if(typeLandscape == 0){
            this.population = 41000;
            this.strTypeLandscape = "Lowland";
            this.happiness = 0.67;
            this.strRainfall = "Low";
        }
        if(typeLandscape == 1){
            this.population = 87000;
            this.strTypeLandscape = "Plain";
            this.happiness = 0.76;
            this.strRainfall = "Normal";
        }
        if(typeLandscape == 2){
            this.population = 55000;
            this.strTypeLandscape = "Hill";
            this.happiness =0.70;
            this.strRainfall = "High";
        }
        this.contracts = new ArrayList[5];
        for(int i = 0; i<5; i++){
            this.contracts[i] = new ArrayList<>();
        }
        currentPopulation = population;
        currentHappiness = happiness;
    }

    public CityCell(String name, int typeLandscape, int population, double happiness, double rainfall, String strRainfall, String strTypeLandscape , double[] productCounts, int[] levels_of_upgrades, ArrayList<Contract>[] contracts, double[] saturation){
        super(rainfall, typeLandscape, name);
        this.cityName = name;
        this.rainfall = rainfall;
        this.population = population;
        this.happiness = happiness;
        this.strRainfall = strRainfall;
        this.strTypeLandscape = strTypeLandscape;
        this.productCounts = productCounts;
        this.contracts = contracts;

        for (int i = 0; i<3; i++){
            setUpgrades(i, levels_of_upgrades[i]);
        }
        this.saturation = saturation;
        updating(false);
        updateIncomes();
    }

    public void updateSaturation(int i){
            if(getAllContractInfo(i,2,3)/2>currentPopulation){
                saturation[i] = saturation[i] - 0.1;
                if(saturation[i]<0.7){
                    saturation[i] = 0.7;
                }
            }
            if(getAllContractInfo(i,2,3)*6<currentPopulation){
                saturation[i] = saturation[i] + 0.1;
                if(saturation[i]>1.4){
                    saturation[i] = 1.4;
                }
            }
    }

    public void resetContracts(){
        this.contracts = new ArrayList[5];
        for(int i = 0; i<5; i++){
            this.contracts[i] = new ArrayList<>();
        }
    }

    public void setUpgrades(int type, int level){
        switch(type){
            case 0:
                if(level<30){
                    upgrades[type] = 1+0.033*level;
                }
                upgrades[type] = 2;

                break;
            case 1:
                if(level<10){
                    upgrades[type] = 1+0.030*level;
                }
                break;
            case 2:
                upgrades[type] = 1+0.05*level;
                break;
        }
        levels_of_upgrades[type] = level;
    }

    public String getUpgradeName(int i){
        return upgradeNames[i];
    }

    public int getLevelsOfUpgrades(int i){
        return levels_of_upgrades[i];
    }

    public void updateIncomes(){
        for(int i = 0; i<5; i++){
            incomes[i] = getAllContractInfo(i,2,3);
        }
    }

    public void updateContracts(){
        Contract c;
        for(int i = 0; i<5; i++) {
            int size;
            try {
                size = contracts[i].size();
            } catch (NullPointerException e) {
                size = 0;
            }

            for (int m = 0; m < size; m++) {
                try {
                    size = contracts[i].size();
                } catch (NullPointerException e) {
                    size = 0;
                }
                if (size > 0) {
                    c = contracts[i].get(m);
                    c.time = c.time - 1;
                    if (c.time == 0) {
                        contracts[i].remove(m);
                        m--;
                    } else {
                        contracts[i].set(m, c);
                    }
                }
                try {
                    size = contracts[i].size();
                } catch (NullPointerException e) {
                    size = 0;
                }
            }
        }
    }

    public void updating(boolean m){
        this.controlProfit = 0;
        this.controlLosses = 0;
        currentPopulation = (int)(population*upgrades[2]);
        currentHappiness = happiness*upgrades[1];
        if(currentHappiness>=1) {
            currentHappiness = 1.00;
        }
        //this.controlLosses =
        for(int i = 0; i<5; i++){
            this.controlLosses = this.controlLosses + getAllContractInfo(i,1,3);
            currentSellMultipliers[i] = sellMultipliers[i]*upgrades[0];
            if(m){
                this.productCounts[i] = getAllContractInfo(i,2,3)+ this.productCounts[i];
                this.controlProfit = this.controlProfit + (this.productCounts[i]*currentSellMultipliers[i]*0.65*saturation[i]);
                this.productCounts[i] = this.productCounts[i] * 0.35;
                updateSaturation(i);
            } else{
                counts[i] = productCounts[i];
                this.counts[i] = getAllContractInfo(i,2,3)+this.counts[i];
                this.controlProfit = this.controlProfit + (this.counts[i]*currentSellMultipliers[i]*saturation[i]*0.65);
            }
        }
    }

    public void signContract(double money, double countProduct, double countResource1, double countResource2, double countResource3, int time, int type, int typeContract){

        contracts[type].add(new Contract(money,countProduct,countResource1,countResource2,countResource3, time, typeContract));

    }

    public String getName(){
        return this.cityName;
    }

    public String getStrTypeLandscape(){
        return this.strTypeLandscape;
    }

    public String getStrRainfall(){
        return this.strRainfall;
    }

    public int getSizeContracts(){
        int size;
        try{
            size = contracts[0].size();
        }catch(NullPointerException e){
            size = 0;
        }
        return size;
    }

    public int getCurrentPopulation(){
        return this.currentPopulation;
    }

    public double getControlLosses() {
        return this.controlLosses;
    }

    public double getCurrentHappiness() {
        return this.currentHappiness;
    }

    public double getHappiness() {
        return this.happiness;
    }

    public double getControlProfit() {
        return this.controlProfit;
    }

    public double getMultipliers(int i){
        return this.sellMultipliers[i];
    }

    public double getContractInfo(int i, int l, int position) {
        position--;
        double count = 0;
        int size;
        Contract c;
        try{
            size = contracts[i].size();
        }catch(NullPointerException e){
            size = 0;
        }
        c = contracts[i].get(position);
        switch(l){
            case 1:
                count = count + c.money;
                break;
            case 2:
                count = count + c.countProduct;
                break;
            case 3:
                count = count + c.countResource1;
                break;
            case 4:
                count = count + c.countResource2;
                break;
            case 5:
                count = count + c.countResource3;
                break;
            case 6:
                count = count + c.time;
                break;
            case 7:
                count = count + c.typeContract;
                break;
            }

        return count;
    }

    public double getAllContractInfo(int i, int l, int typeContract) {
        double count = 0;
        int size;
        Contract c;
        try{
            size = contracts[i].size();
        }catch(NullPointerException e){
            size = 0;
        }
        for(int m = 0; m<size; m++){
            c = contracts[i].get(m);
            if(typeContract!=3){
                if(typeContract == c.typeContract){
                    switch(l){
                        case 1:
                            count = count + c.money;
                            break;
                        case 2:
                            count = count + c.countProduct;
                            break;
                        case 3:
                            count = count + c.countResource1;
                            break;
                        case 4:
                            count = count + c.countResource2;
                            break;
                        case 5:
                            count = count + c.countResource3;
                            break;
                        case 6:
                            count = count + c.time;
                            break;
                    }
                }
            }else{
                switch(l){
                    case 1:
                        count = count + c.money;
                        break;
                    case 2:
                        count = count + c.countProduct;
                        break;
                    case 3:
                        count = count + c.countResource1;
                        break;
                    case 4:
                        count = count + c.countResource2;
                        break;
                    case 5:
                        count = count + c.countResource3;
                        break;
                    case 6:
                        count = count + c.time;
                        break;
                }
            }
        }
        return count;
    }

    public double getProductCount(int i){
        return this.productCounts[i];
    }

    public int getPopulation() {
        return population;
    }

    public double getSaturation(int i){
        return saturation[i];
    }
}
