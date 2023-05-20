package com.profit_taker.game.saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.profit_taker.game.model.CityCell;
import com.profit_taker.game.model.Contract;
import com.profit_taker.game.model.EmptyCell;
import com.profit_taker.game.model.FarmCell;

import java.util.ArrayList;

public class PreferencesSaver {
    Preferences[][] prefs = new Preferences[6][3];
    Preferences leaderboard = Gdx.app.getPreferences("leaderboard");

    public void checkFiles(){
        for(int i = 0; i<6; i++){
            for(int m = 0; m<3; m++){
                prefs[i][m] = Gdx.app.getPreferences("prefs_"+i+"_"+m);
                prefs[i][m].flush();
            }
        }
    }

    public void setToLeaderboard(int[] stats){
        int[][] leaders = getFromLeaderboard();
        if(!leaderboard.contains("check")){
            for(int i = 0; i<5; i++ )
            {
                leaders[i][0] = 0;
                leaders[i][1] = 0;
                leaders[i][2] = 0;
            }
        }
        int[][] saved_leaderboard = new int[5][3];
        for(int i = 0; i<5; i++ )
        {
            saved_leaderboard[i][0] =leaders[i][0];
            saved_leaderboard[i][1] =leaders[i][1];
            saved_leaderboard[i][2] =leaders[i][2];
        }

        boolean isChangingLeaderboard = false;
        int position = 0;
            for(int i = 0; i<5; i++){
                if(leaders[i][0] <= stats[0]){
                    if(!isChangingLeaderboard){
                        isChangingLeaderboard = true;
                        position = i;
                    }
                }
            }

        if(isChangingLeaderboard){

            for(int i = 0; i<5; i++){
                if(i<position){
                    leaders[i][0] = saved_leaderboard[i][0];
                    leaders[i][1] = saved_leaderboard[i][1];
                    leaders[i][2] = saved_leaderboard[i][2];
                }
                if(i == position){
                    leaders[i][0] = stats[0];
                    leaders[i][1] = stats[1];
                    leaders[i][2] = stats[2];
                }
                if(i> position){
                   leaders[i][0] = saved_leaderboard[i-1][0];
                   leaders[i][1] = saved_leaderboard[i-1][1];
                   leaders[i][2] = saved_leaderboard[i-1][2];
                }
            }
        }

        leaderboard.clear();
        for(int i = 0; i<5; i++){
            leaderboard.putInteger("moves_"+i,leaders[i][0]);
            leaderboard.putInteger("type_landscape_"+i,leaders[i][1]);
            leaderboard.putInteger("contracts_"+i,leaders[i][2]);
        }
        leaderboard.putInteger("check",1);
        //leaderboard.clear();
        leaderboard.flush();
    }

    public int[][] getFromLeaderboard(){
        int[][] leaders = new int[5][3];
        if(!leaderboard.contains("check")){
            for(int i = 0; i<5; i++ )
            {
                leaders[i][0] = 0;
                leaders[i][1] = 0;
                leaders[i][2] = 0;
            }
        } else{
            for(int i = 0; i<5; i++){
                leaders[i][0] = leaderboard.getInteger("moves_"+i,0);
                leaders[i][1] = leaderboard.getInteger("type_landscape_"+i,0);
                leaders[i][2] = leaderboard.getInteger("contracts_"+i,0);
            }
        }

        return leaders;
    }

    public void saving(int number, double[] stats, int[][][] map,EmptyCell[][] cells, FarmCell[][] farms, CityCell[][] cities){

        for(int m = 0; m<3; m++){
            prefs[number][m] = Gdx.app.getPreferences("prefs_"+number+"_"+m);
            if(prefs[number][m].contains("check")){
                prefs[number][m].clear();
            }
            prefs[number][0].putInteger("check",1);
        }

        prefs[number][0].putFloat("moves",(float)stats[0]);
        prefs[number][0].putFloat("money",(float)stats[1]);
        prefs[number][0].putFloat("landscape",(float)stats[2]);
        prefs[number][0].putFloat("current_size_x",(float)stats[3]);
        prefs[number][0].putFloat("current_size_y",(float)stats[4]);
        prefs[number][0].putFloat("count_of_contracts",(float)stats[5]);
        for(int  i =  0; i<5; i++){
            prefs[number][0].putFloat("storage_res_"+i,(float)stats[6+i]);
        }
        for(int i = 0; i<(int)stats[3]; i++){
            for(int l = 0; l<(int)stats[4]; l++){
                prefs[number][1].putInteger(i+"_"+l,map[i][l][1]);
                if(map[i][l][0] == 1) {
                    switch (map[i][l][1]) {
                        case 1:
                            int size = farms[i][l].getSizeContracts();
                            prefs[number][2].putInteger("size_of_contracts_" + i + "_" + l, size);
                            prefs[number][2].putString("name_" + i + "_" + l, farms[i][l].getName());
                            prefs[number][2].putInteger("type_landscape_" + i + "_" + l, farms[i][l].getTypeLandscape());
                            prefs[number][2].putInteger("population_" + i + "_" + l, farms[i][l].getPopulation());
                            prefs[number][2].putFloat("happiness_" + i + "_" + l, (float) farms[i][l].getHappiness());
                            prefs[number][2].putFloat("fertility_" + i + "_" + l, (float) farms[i][l].getFertility());
                            prefs[number][2].putFloat("rainfall_" + i + "_" + l, (float) farms[i][l].getRainfall());
                            prefs[number][2].putString("str_rainfall_" + i + "_" + l, farms[i][l].getStrRainfall());
                            prefs[number][2].putString("str_type_landscape_" + i + "_" + l, farms[i][l].getStrTypeLandscape());
                            for (int m = 0; m < 5; m++) {
                                prefs[number][2].putFloat("resource" + m + "_" + i + "_" + l, (float) farms[i][l].getResourceCounts(m));
                            }
                            for (int m = 0; m < 7; m++) {
                                prefs[number][2].putInteger("upgrade" + m + "_" + i + "_" + l, farms[i][l].getLevelsOfUpgrades(m));
                            }

                            for (int p = 1; p < size+1; p++) {
                                for (int m = 0; m < 5; m++) {
                                    for (int c = 1; c < 8; c++) {
                                        prefs[number][2].putFloat("contract" + m + "position" + p + "resourse" + c + "_" + i + "_" + l, (float) farms[i][l].getContractInfo(m, c, p));
                                    }

                                }
                            }

                            break;
                        case 2:
                            int siz = cities[i][l].getSizeContracts();
                            prefs[number][2].putInteger("size_of_contracts_" + i + "_" + l, siz);
                            prefs[number][2].putString("name_" + i + "_" + l, cities[i][l].getName());
                            prefs[number][2].putInteger("type_landscape_" + i + "_" + l, cities[i][l].getTypeLandscape());
                            prefs[number][2].putInteger("population_" + i + "_" + l, cities[i][l].getPopulation());
                            prefs[number][2].putFloat("happiness_" + i + "_" + l, (float) cities[i][l].getHappiness());
                            prefs[number][2].putFloat("rainfall_" + i + "_" + l, (float) cities[i][l].getRainfall());
                            prefs[number][2].putString("str_rainfall_" + i + "_" + l, cities[i][l].getStrRainfall());
                            prefs[number][2].putString("str_type_landscape_" + i + "_" + l, cities[i][l].getStrTypeLandscape());
                            for (int m = 0; m < 5; m++) {
                                prefs[number][2].putFloat("product" + m + "_" + i + "_" + l, (float) cities[i][l].getProductCount(m));
                                prefs[number][2].putFloat("saturation" + m + "_" + i + "_" + l, (float) cities[i][l].getSaturation(m));
                            }
                            for (int m = 0; m < 3; m++) {
                                prefs[number][2].putInteger("upgrade" + m + "_" + i + "_" + l, cities[i][l].getLevelsOfUpgrades(m));
                            }

                            for (int p = 1; p < siz+1; p++) {
                                for (int m = 0; m < 5; m++) {
                                    for (int c = 1; c < 8; c++) {
                                        prefs[number][2].putFloat("contract" + m + "position" + p + "resourse" + c + "_" + i + "_" + l, (float) cities[i][l].getContractInfo(m, c, p));
                                    }
                                }
                            }

                            break;
                        case 0:
                            prefs[number][2].putFloat("rainfall_" + i + "_" + l, (float) cells[i][l].getRainfall());
                            prefs[number][2].putInteger("type_landscape_" + i + "_" + l, cells[i][l].getTypeLandscape());
                            prefs[number][2].putString("name_" + i + "_" + l, cells[i][l].getName());
                            break;
                    }
                }
            }
        }
        prefs[number][0].flush();
        prefs[number][1].flush();
        prefs[number][2].flush();

    }

    public boolean checkIsEmpty(int number){
        prefs[number][0] = Gdx.app.getPreferences("prefs_"+number+"_0");
        if(prefs[number][0].contains("check")){
            return false;
        } else{
            return true;
        }
    }

    public double[] getStats(int number){
        prefs[number][0] = Gdx.app.getPreferences("prefs_"+number+"_0");

        double[] stats = new double[11];

        stats[0] = prefs[number][0].getFloat("moves");
        stats[1] = prefs[number][0].getFloat("money");
        stats[2] = prefs[number][0].getFloat("landscape");
        stats[3] = prefs[number][0].getFloat("current_size_x");
        stats[4] = prefs[number][0].getFloat("current_size_y");
        stats[5] = prefs[number][0].getFloat("count_of_contracts");
        for(int  i =  0; i<5; i++){
            stats[6+i] = prefs[number][0].getFloat("storage_res_"+i,0);
        }
        return stats;
    }

    public EmptyCell[][] getEmpties(int number){
        prefs[number][0] = Gdx.app.getPreferences("prefs_"+number+"_0");
        prefs[number][1] = Gdx.app.getPreferences("prefs_"+number+"_1");
        prefs[number][2] = Gdx.app.getPreferences("prefs_"+number+"_2");

        int current_size_x = (int)prefs[number][0].getFloat("current_size_x");
        int current_size_y = (int)prefs[number][0].getFloat("current_size_y");

        EmptyCell[][] empties = new EmptyCell[current_size_x][current_size_y];
        for(int i = 0; i<current_size_x; i++){
            for(int l = 0; l< current_size_y; l++){
                int type = prefs[number][1].getInteger(i+"_"+l);
                if(type ==0){
                    double rainfalll = prefs[number][2].getFloat("rainfall_"+i+"_"+l);
                    int typeLandscape = prefs[number][2].getInteger("type_landscape_"+i+"_"+l);
                    String name = prefs[number][2].getString("name_"+i+"_"+l);
                    empties[i][l] = new EmptyCell(rainfalll, typeLandscape, name);
                }

            }
        }

        return empties;
    }

    public FarmCell[][] getFarms(int number){
        prefs[number][0] = Gdx.app.getPreferences("prefs_"+number+"_0");
        prefs[number][1] = Gdx.app.getPreferences("prefs_"+number+"_1");
        prefs[number][2] = Gdx.app.getPreferences("prefs_"+number+"_2");

        int current_size_x = (int)prefs[number][0].getFloat("current_size_x");
        int current_size_y = (int)prefs[number][0].getFloat("current_size_y");

        FarmCell[][] farms = new FarmCell[current_size_x][current_size_y];

        for(int i = 0; i<current_size_x; i++){
            for(int l = 0; l< current_size_y; l++){
                int type = prefs[number][1].getInteger(i+"_"+l);
                if(type ==1){
                    int size = prefs[number][2].getInteger("size_of_contracts_"+i+"_"+l);
                    String name = prefs[number][2].getString("name_"+i+"_"+l);
                    int typeLandscape = prefs[number][2].getInteger("type_landscape_"+i+"_"+l);
                    int population = prefs[number][2].getInteger("population_"+i+"_"+l);
                    double happiness = prefs[number][2].getFloat("happiness_"+i+"_"+l);
                    double fertility = prefs[number][2].getFloat("fertility_"+i+"_"+l);
                    double rainfall = prefs[number][2].getFloat("rainfall_"+i+"_"+l);
                    String strRainfall = prefs[number][2].getString("str_rainfall_"+i+"_"+l);
                    String strTypeLandscape = prefs[number][2].getString("str_type_landscape_"+i+"_"+l);
                    double[] resourceCounts = new double[5];

                    for(int m = 0; m<5; m++){
                        resourceCounts[m] = prefs[number][2].getFloat("resource"+m+"_"+i+"_"+l);
                    }

                    int[] levels_of_upgrades = new int[7];

                    for(int m = 0; m<7; m++){
                        levels_of_upgrades[m] = prefs[number][2].getInteger("upgrade"+m+"_"+i+"_"+l);
                    }

                    ArrayList<Contract>[] contracts = new ArrayList[5];
                    for(int k = 0; k<5; k++){
                        contracts[k] = new ArrayList<Contract>();
                    }


                    for(int p = 1; p<size+1; p++){
                        for(int m = 0; m<5; m++){
                            double[] mas = new double[7];
                            for(int c = 1; c<8; c++){
                                mas[c-1] = prefs[number][2].getFloat("contract"+m+"position"+p+"resourse"+c+"_"+i+"_"+l, 1);

                            }
                            contracts[m].add(new Contract(mas[0],mas[1],0,0,0,(int)mas[5],(int)mas[6]));

                        }
                    }
                    farms[i][l] = new FarmCell(name, typeLandscape, population, happiness, fertility,rainfall, strRainfall, strTypeLandscape, resourceCounts, levels_of_upgrades, contracts);

                }

            }
        }

        return farms;
    }

    public CityCell[][] getCities(int number){
        prefs[number][0] = Gdx.app.getPreferences("prefs_"+number+"_0");
        prefs[number][1] = Gdx.app.getPreferences("prefs_"+number+"_1");
        prefs[number][2] = Gdx.app.getPreferences("prefs_"+number+"_2");

        int current_size_x = (int)prefs[number][0].getFloat("current_size_x");
        int current_size_y = (int)prefs[number][0].getFloat("current_size_y");

        CityCell[][] cities = new CityCell[current_size_x][current_size_y];
        for(int i = 0; i<current_size_x; i++){
            for(int l = 0; l< current_size_y; l++){
                int type = prefs[number][1].getInteger(i+"_"+l);
                if(type ==2){
                    int size = prefs[number][2].getInteger("size_of_contracts_"+i+"_"+l);
                    String name = prefs[number][2].getString("name_"+i+"_"+l);
                    int typeLandscape = prefs[number][2].getInteger("type_landscape_"+i+"_"+l);
                    int population = prefs[number][2].getInteger("population_"+i+"_"+l);
                    double happiness = prefs[number][2].getFloat("happiness_"+i+"_"+l);
                    double rainfall = prefs[number][2].getFloat("rainfall_"+i+"_"+l);
                    String strRainfall = prefs[number][2].getString("str_rainfall_"+i+"_"+l);
                    String strTypeLandscape = prefs[number][2].getString("str_type_landscape_"+i+"_"+l);
                    double[] productCounts = new double[5];
                    double[] saturation = new double[5];

                    for(int m = 0; m<5; m++){
                        productCounts[m] = prefs[number][2].getFloat("product"+m+"_"+i+"_"+l);
                        saturation[m] = prefs[number][2].getFloat("saturation"+m+"_"+i+"_"+l);
                    }

                    int[] levels_of_upgrades = new int[3];

                    for(int m = 0; m<3; m++){
                        levels_of_upgrades[m] = prefs[number][2].getInteger("upgrade"+m+"_"+i+"_"+l);
                    }

                    ArrayList<Contract>[] contracts = new ArrayList[5];
                    for(int k = 0; k<5; k++){
                        contracts[k] = new ArrayList<Contract>();
                    }
                    for(int p = 1; p<size+1; p++){
                        for(int m = 0; m<5; m++){
                            double[] mas = new double[7];
                            for(int c = 1; c<8; c++){
                                mas[c-1] = prefs[number][2].getFloat("contract"+m+"position"+p+"resourse"+c+"_"+i+"_"+l,1);
                            }
                            contracts[m].add(new Contract(mas[0],mas[1],mas[2],mas[3],mas[4],(int)mas[5],(int)mas[6]));
                        }
                    }
                    cities[i][l] = new CityCell(name, typeLandscape, population, happiness, rainfall, strRainfall, strTypeLandscape, productCounts, levels_of_upgrades, contracts, saturation);
                }

            }
        }

        return cities;
    }

}
