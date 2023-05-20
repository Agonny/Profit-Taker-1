package com.profit_taker.game.model;

import com.badlogic.gdx.graphics.Texture;

public class WindowBuilder {
    Texture[] resources_tx = new Texture[6];
    Texture[] products_tx = new Texture[5];
    Texture[] upgrades_city_tx = new Texture[4];
    Texture[] upgrades_farm_tx = new Texture[2];
    Texture[] upgrades_empty_tx = new Texture[2];
    Texture[] buildings_farm_tx = new Texture[6];
    Texture[] buildings_city_tx = new Texture[2];
    Texture[] buildings_empty_tx = new Texture[2];
    Texture[] roads = new Texture[6];
    Texture[] building_roads_tx = new Texture[6];
    Texture[][] information_tx = new Texture[3][2];
    Texture[] lines_tx = new Texture[2];
    Texture[][] buttons_tx = new Texture[2][5];
    Texture[][] table_buttons_tx = new Texture[6][2];

    WindowBuilder(){}

    public void loadTextures(int numberThread){
        switch(numberThread){
            case 1:

                lines_tx[0] = new Texture("vertical_line.png");
                lines_tx[1] = new Texture("horizontal_line.png");

                table_buttons_tx[0][0] = new Texture("info_button.png");
                table_buttons_tx[1][0] = new Texture("contracts_button.png");
                table_buttons_tx[2][0] = new Texture("handshake_im.png");
                table_buttons_tx[3][0]= new Texture("upgrading_button.png");
                table_buttons_tx[4][0]= new Texture("building_button.png");
                table_buttons_tx[5][0]= new Texture("reputation_button.png");

                table_buttons_tx[0][1] = new Texture("info_button_pressed.png");
                table_buttons_tx[1][1] = new Texture("contracts_button_pressed.png");
                table_buttons_tx[2][1] = new Texture("handshake_im_pressed.png");
                table_buttons_tx[3][1] = new Texture("upgrading_button_pressed.png");
                table_buttons_tx[4][1]= new Texture("building_button_pressed.png");
                table_buttons_tx[5][1]= new Texture("reputation_button_pressed.png");
                break;
            case 2:
                information_tx[0][0] = new Texture("landscape_image.png");
                information_tx[0][1] = new Texture("humidity_image.png");
                information_tx[1][0] = new Texture("population_image.png");
                information_tx[1][1] = new Texture("happiness_image.png");
                information_tx[2][0] = new Texture("movement_image.png");
                information_tx[2][1] = new Texture("reputation_image.png");

                resources_tx[0] = new Texture("milk_image.png");
                resources_tx[1] = new Texture("wheat_image.png");
                resources_tx[2] = new Texture("egg_image.png");
                resources_tx[3] = new Texture("sugar_image.png");
                resources_tx[4] = new Texture("fruits_image.png");
                resources_tx[5] = new Texture("empty_slot_image.png");

                products_tx[0] = new Texture("cake_image.png");
                products_tx[1] = new Texture("bread_image.png");
                products_tx[2] = new Texture("bisquit_image.png");
                products_tx[3] = new Texture("dessert_image.png");
                products_tx[4] = new Texture("bun_image.png");
                break;
            case 3:
                upgrades_farm_tx[0] = new Texture("windmill_image.png");
                upgrades_farm_tx[1] = new Texture("boxes_image.png");

                upgrades_city_tx[0] = new Texture("package_image.png");
                upgrades_city_tx[1] = new Texture("promotions_image.png");
                upgrades_city_tx[2] = new Texture("advertisement_image.png");
                upgrades_city_tx[3] = new Texture("recipe_image.png");

                upgrades_empty_tx[0] = new Texture("ergonomics_image.png");
                upgrades_empty_tx[1] = new Texture("tax_image.png");

                buildings_city_tx[0] = new Texture("shop_image.png");
                buildings_city_tx[1] = new Texture("furnace_image.png");

                buildings_farm_tx[0] = new Texture("apartments_image.png");
                buildings_farm_tx[1] = new Texture("field_image.png");
                buildings_farm_tx[2] = new Texture("hay_image.png");
                buildings_farm_tx[3] = new Texture("coop_image.png");
                buildings_farm_tx[4] = new Texture("evaporator_image.png");
                buildings_farm_tx[5] = new Texture("garden_image.png");

                buildings_empty_tx[0] = new Texture("storage_image.png");
                buildings_empty_tx[1] = new Texture("checkpoint_image.png");
                buildings_empty_tx[2] = new Texture("expanding_image.png");
                break;
            case 4:
                buttons_tx[0][0] = new Texture("button_upgrade.png");
                buttons_tx[0][1] = new Texture("button_upgrade_pressed.png");

                buttons_tx[1][0] = new Texture("building_button.png");
                buttons_tx[1][1] = new Texture("building_button_pressed.png");

                buttons_tx[2][0] = new Texture("done_button.png");
                buttons_tx[2][1] = new Texture("done_button_pressed.png");

                buttons_tx[3][0] = new Texture("yes_button.png");
                buttons_tx[3][1] = new Texture("yes_button_pressed.png");

                buttons_tx[4][0] = new Texture("no_button.png");
                buttons_tx[4][1] = new Texture("no_button_pressed.png");

                buttons_tx[5][0] = new Texture("arrow_left_button.png");
                buttons_tx[5][1] = new Texture("arrow_left_button_pressed.png");

                buttons_tx[6][0] = new Texture("arrow_right_button.png");
                buttons_tx[6][1] = new Texture("arrow_right_button_pressed.png");

                buttons_tx[7][0] = new Texture("delete_button.png");
                buttons_tx[7][1] = new Texture("delete_button_pressed.png");
                break;
        }
    }

//    public Table createInfoTable(int x, int y ){
//
//    }
}
