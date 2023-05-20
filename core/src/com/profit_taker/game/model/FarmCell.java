package com.profit_taker.game.model;

import java.util.ArrayList;

public class FarmCell extends EmptyCell{
    String farmName;
    int population;
    int currentPopulation;
    double happiness;
    double currentHappiness;
    double fertility = 0;
    double currentFertility;
    String strRainfall = " ";
    double[] resourceCounts = {0,0,0,0,0};
    double[] incomes= {0,0,0,0,0};
    double[] upgrades = {1,1,1,1,1,1,1};
    int[] levels_of_upgrades = {0,0,0,0,0,0,0};
    String[] upgradeNames = {"Apartments", "Windmill", "Scarecrow", "Hoe", "Coop", "Evaporator", "Fertilizer"};
    int[] sellMultipliers = {3,5,3,5,10};
    double[] currentSellMultipliers = {3,5,3,5,10};
    double[] multipliers = {9.2,5.0,4.4,7.1,2.1};
    double farmMultiplier = 1.4;
    double controlLosses = 0;
    ArrayList <Contract>[] contracts;

    public FarmCell(double rainfall, int typeLandscape, String name) {
        super( rainfall, typeLandscape, name);
        this.farmName = name;
        this.rainfall = rainfall;
        if(typeLandscape == 0){
            this.fertility = 1.6 * this.rainfall + 0.3;
            this.strTypeLandscape = "Lowland";
            this.happiness = 0.60;
            this.population = 377;
        }
        if(typeLandscape == 1){
            this.fertility = 2.2 * this.rainfall + 0.3 ;
            this.strTypeLandscape = "Plain";
            this.happiness = 0.70;
            this.population = 510;
        }
        if(typeLandscape == 2){
            this.fertility = 1.2 * this.rainfall + 0.3;
            this.strTypeLandscape = "Hill";
            this.happiness = 0.63;
            this.population = 410;
        }
        currentFertility = fertility;
        currentPopulation = population;
        currentHappiness = happiness;
        if(rainfall>0&&rainfall<0.7){
            this.strRainfall = "Low";
        }else if(rainfall>=0.7&&rainfall<1 ){
            this.strRainfall = "Normal";
        }else if(rainfall>=1){
            this.strRainfall = "High";
        }

        this.contracts = new ArrayList[5];
        for(int i = 0; i<5; i++){
            this.contracts[i] = new ArrayList<Contract>();
        }

        for(int i = 0; i< contracts.length; i++){
            resourceCounts[i] =10*this.multipliers[i] * this.fertility;
        }
    }

    public FarmCell(String name, int typeLandscape, int population, double happiness, double fertility, double rainfall, String strRainfall, String strTypeLandscape , double[] resourceCounts, int[] levels_of_upgrades, ArrayList<Contract>[] contracts){
        super(rainfall, typeLandscape, name);
        this.farmName = name;
        this.rainfall = rainfall;
        this.population = population;
        this.happiness = happiness;
        this.fertility = fertility;
        this.strRainfall = strRainfall;
        this.strTypeLandscape = strTypeLandscape;
        this.resourceCounts = resourceCounts;
        this.contracts = contracts;

        for (int i = 0; i<7; i++){
            setUpgrades(i, levels_of_upgrades[i]);
        }
        updating(false);
        updateIncomes();
    }

    public void setUpgrades(int type, int level){

        switch(type){
            case 0:
                if(level<10){
                    upgrades[type] = 1+0.043*level;
                }
                break;
            case 1:
                if(level<30){
                    upgrades[type] = 1+0.033*level;
                }
                break;
            default:
                int a = level%10;
                double multiplier = Math.pow(1.03,(level-a))*Math.pow(1.3,(a)) ;
                upgrades[type] = multiplier;
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

    public void signContract(double money, double countProduct, int time, int type, int typeContract){

        contracts[type].add(new Contract(money,countProduct,0,0,0, time, typeContract));

    }

    public void updateContracts(){
        Contract c;
        for(int i = 0; i<5; i++){
            int size;
            try{
                size = contracts[i].size();
            }catch(NullPointerException e){
                size = 0;
            }

            for (int m = 0; m<size; m++){
                try{
                    size = contracts[i].size();
                }catch(NullPointerException e){
                    size = 0;
                }
                if(size>0) {
                    c = contracts[i].get(m);
                    c.time = c.time - 1;
                    if (c.time == 0) {
                        contracts[i].remove(m);
                        m--;
                    } else {
                        contracts[i].set(m, c);
                    }
                }
                try{
                    size = contracts[i].size();
                }catch(NullPointerException e){
                    size = 0;
                }
            }
        }
    }

    public void updateIncomes(){ //после заключения контракта
        for(int i = 0; i<5; i++){
            incomes[i] = multipliers[i] * this.currentFertility * this.farmMultiplier * upgrades[i+2] - getAllContractInfo(i, 2,3);
        }
    }

    public void updating(boolean m){ //вместе с updateContracs на новох ходу
        this.controlLosses = 0;
        currentPopulation = (int)(population*upgrades[0]);
        currentFertility = fertility*upgrades[1]+upgrades[0]-1;
        currentHappiness = happiness*upgrades[0];//повышает шанс ивента
        if(currentHappiness>=1) {
            currentHappiness = 1.00;
        }
        for(int i = 0; i<5; i++){
            if(m){
                currentSellMultipliers[i] = sellMultipliers[i]/upgrades[0];
                this.resourceCounts[i] = this.resourceCounts[i] + this.incomes[i];
            }

            this.controlLosses = controlLosses + getAllContractInfo(i,1,3);
        }
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

    public String getName(){
        return this.farmName;
    }

    public String getStrTypeLandscape(){
        return this.strTypeLandscape;
    }

    public int getCurrentPopulation(){
        return this.currentPopulation;
    }

    public double getCurrentHappiness(){return this.currentHappiness;}

    public String getStrRainfall(){
        return this.strRainfall;
    }

    public double getControlLosses() {
        return this.controlLosses;
    }

    public double getResourceCounts(int i){
        return resourceCounts[i];
    }

    public double getSellMultiplier(int i){
        return currentSellMultipliers[i];
    }

    public double getMultipliers(int i){
        return multipliers[i];
    }

    public double getContractInfo(int number, int l, int position) {
        position--;
        double count = 0;
        int size;
        Contract c;
        try{
            size = contracts[number].size();
        }catch(NullPointerException e){
            size = 0;
        }
        c = contracts[number].get(position);
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

    public double getIncomes(int i){
    return incomes[i];
    }

    public int getPopulation() {
        return population;
    }

    public double getFertility() {
        return fertility;
    }

    public double getHappiness() {
        return this.happiness;
    }
}
