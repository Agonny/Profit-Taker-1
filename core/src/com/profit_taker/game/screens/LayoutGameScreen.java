package com.profit_taker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.profit_taker.game.styles.StyleBuilder;
import com.profit_taker.game.model.CityCell;
import com.profit_taker.game.model.EmptyCell;
import com.profit_taker.game.model.FarmCell;
import com.profit_taker.game.model.MyStorage;
import com.profit_taker.game.saves.PreferencesSaver;

import java.text.DecimalFormat;
import java.util.Random;

public class LayoutGameScreen extends GameScreen implements InputProcessor, GestureDetector.GestureListener {

    int move = 1;
    double money = 5000;
    public int previous_x = 0, previous_y = 0;
    public int current_x = 21, current_y = 8;
    int countOfContracts = 0;
    int sizeOfMap = 3;
    final int[][][] map_1 = new int[][][]    {{{0,0,0},{0,0,0},{1,0,0},{1,0,1},{1,0,1},{0,0,1}},
            {{0,0,1},{1,0,0},{1,0,1},{1,0,0},{1,0,1},{0,0,0}},
            {{0,0,1},{1,0,0},{1,0,1},{1,0,2},{1,0,1},{1,0,2}},
            {{1,0,1},{1,0,0},{1,0,0},{1,0,1},{1,1,2},{1,0,0}},
            {{0,0,0},{1,0,1},{1,0,1},{1,2,1},{1,0,0},{1,0,1}},
            {{1,0,1},{1,0,1},{1,0,0},{1,0,1},{1,0,1},{1,0,0}},
            {{0,0,1},{1,2,1},{1,0,1},{1,0,1},{1,0,0},{1,0,1}},
            {{1,0,1},{1,1,1},{1,0,0},{1,0,0},{1,0,0},{1,0,2}},
            {{0,0,0},{1,0,1},{1,0,1},{1,0,1},{1,0,0},{1,0,0}},
            {{1,0,0},{1,0,0},{1,0,2},{1,0,1},{1,2,0},{1,1,1}},
            {{0,0,0},{1,0,1},{1,0,0},{1,0,2},{1,0,1},{1,0,0}},
            {{0,0,0},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{1,0,1},{1,0,1},{1,0,1},{0,0,1}}};

    final int[][][] map_2 = new int[][][]    {{{0,0,0},{0,0,0},{1,0,0},{1,0,1},{1,0,1},{0,0,1},{0,0,0}},
            {{0,0,1},{1,0,0},{1,1,1},{1,0,0},{1,0,1},{0,0,0},{0,0,1}},
            {{0,0,1},{1,0,0},{1,0,1},{1,0,2},{1,0,1},{1,0,2},{0,0,0}},
            {{1,0,1},{1,0,0},{1,0,0},{1,0,1},{1,1,2},{1,0,0},{0,0,0}},
            {{1,0,0},{1,0,1},{1,0,1},{1,2,1},{1,0,0},{1,0,1},{1,0,0}},
            {{1,0,1},{1,0,1},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{0,0,0}},
            {{1,0,1},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{1,0,1},{1,0,1}},
            {{1,0,1},{1,0,1},{1,0,0},{1,0,0},{1,0,0},{1,2,2},{0,0,0}},
            {{1,0,0},{1,0,0},{1,0,1},{1,0,0},{1,0,1},{1,0,1},{1,0,0}},
            {{1,0,1},{1,0,0},{1,1,0},{1,0,1},{1,0,0},{1,0,0},{0,0,0}},
            {{1,0,0},{1,0,1},{1,0,1},{1,0,1},{1,0,0},{1,0,0},{1,0,1}},
            {{1,0,0},{1,0,0},{1,0,0},{1,0,1},{1,0,0},{1,1,1},{0,0,0}},
            {{1,0,1},{1,0,2},{1,0,0},{1,0,0},{1,0,0},{1,0,1},{1,2,0}},
            {{1,0,0},{1,0,0},{1,0,2},{1,0,1},{1,2,0},{1,0,1},{0,0,0}},
            {{0,0,0},{1,1,1},{1,0,0},{1,0,2},{1,0,1},{1,0,0},{0,0,0}},
            {{0,0,0},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{1,0,1},{1,0,1},{1,0,1},{0,0,1},{0,0,1}}};

    final int[][][] map_3 = new int[][][]    {{{0,0,0},{0,0,0},{0,0,0},{1,0,0},{1,0,1},{1,0,1},{0,0,2},{0,0,1}},
            {{0,0,1},{0,0,0},{1,0,0},{1,1,1},{1,0,0},{1,0,1},{0,0,2},{0,0,0}},
            {{0,0,1},{0,0,0},{1,0,0},{1,0,1},{1,0,2},{1,0,1},{1,0,0},{0,0,2}},
            {{0,0,1},{1,0,0},{1,0,0},{1,0,0},{1,0,1},{1,1,2},{1,0,0},{0,0,0}},
            {{0,0,0},{1,0,2},{1,0,1},{1,0,1},{1,2,1},{1,0,0},{1,0,0},{1,0,1}},
            {{1,0,1},{1,0,2},{1,0,1},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{1,0,0}},
            {{0,0,1},{1,0,0},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{1,1,0},{1,0,1}},
            {{1,0,1},{1,0,0},{1,0,1},{1,0,0},{1,0,0},{1,0,0},{1,0,2},{1,2,2}},
            {{0,0,0},{1,2,0},{1,0,1},{1,0,1},{1,0,1},{1,0,0},{1,0,2},{1,0,0}},
            {{1,0,0},{1,0,0},{1,0,0},{1,0,2},{1,0,2},{1,0,2},{1,0,2},{1,0,1}},
            {{0,0,0},{1,0,0},{1,1,1},{1,0,1},{1,0,1},{1,0,0},{1,0,0},{1,0,0}},
            {{1,0,0},{1,0,0},{1,0,0},{1,0,2},{1,1,1},{1,2,1},{1,0,0},{1,0,1}},
            {{0,0,0},{1,0,0},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,1},{1,0,0}},
            {{1,0,0},{1,0,0},{1,0,0},{1,0,2},{1,0,1},{1,0,0},{1,0,1},{1,0,1}},
            {{0,0,0},{1,0,0},{1,2,1},{1,0,1},{1,0,1},{1,0,0},{1,0,2},{1,0,2}},
            {{1,0,0},{1,0,2},{1,0,2},{1,0,2},{1,0,1},{1,0,1},{1,0,2},{1,0,1}},
            {{0,0,0},{1,0,0},{1,0,1},{1,0,1},{1,0,1},{1,0,0},{1,0,2},{1,0,0}},
            {{0,0,0},{1,0,0},{1,0,0},{1,0,2},{1,2,1},{1,1,2},{1,0,0},{0,0,1}},
            {{0,0,0},{0,0,0},{1,0,1},{1,0,0},{1,0,2},{1,0,1},{1,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{1,0,0},{1,0,1},{1,0,1},{1,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{1,0,1},{1,0,1},{1,0,1},{0,0,0},{0,0,1}}};

    int [][][] current_map = map_3;
    int money_multiplier = 350;
    double scale_multiplier = 1.07;
    private long diff, start = System.currentTimeMillis();
    int current_size_x = 21, current_size_y = 8;
    double absoluteLosses = money_multiplier;
    double absoluteProfit = 0;
    double absoluteIncome = absoluteProfit - absoluteLosses;

    double[] countIncomeResources = new double[5];
    double[] countLossesResources = new double[5];
    double[] currentShowIncomeResources = new double[5];
    double[] currentShowStorageResources = new double[5];
    double[] sliders_numbers = new double[6];
    double[] bottom_info = new double[2];
    double[][] result = new double[5][4];

    public boolean cell_is_using = true, autosave_is_enabled = false;
    public float staticHeight, staticWidth;

    int sizeOfHorizontalTable = 3;

    MyStorage storage = new MyStorage();

    PreferencesSaver saver = new PreferencesSaver();

    Label.LabelStyle labelStyle1 = new Label.LabelStyle();
    Label.LabelStyle labelStyle2 = new Label.LabelStyle();
    Label.LabelStyle labelStyle3 = new Label.LabelStyle();
    Label.LabelStyle labelStyle4 = new Label.LabelStyle();

    BitmapFont font1, font2, font3, font4;

    Slider.SliderStyle sliderStyle;

    CheckBox.CheckBoxStyle checkBoxStyle;

    private final Stage stage, stage2;

    OrthographicCamera camera, camera2;

    GestureDetector gestureDetector = new GestureDetector(this);

    InputMultiplexer multiplexer = new InputMultiplexer();
    InputMultiplexer multiplexer2 = new InputMultiplexer();

    final Texture base_table_design_tx, bottom_table_design_tx, top_table_design_tx, texture3, hitbox_tx, settings_menu_tx, next_move_button_tx, next_move_button_tx_pressed, yes_button_tx, yes_button_tx_pressed, no_button_tx, no_button_tx_pressed,done_button_tx, done_button_tx_pressed;
    final Texture horizontal_info_closed_tx,horizontal_info_small_tx,horizontal_info_full_tx, vertical_info_full_tx, vertical_info_closed_tx,settings_tx, window_tx, vertical_l_tx, horizontal_l_tx;
    final Texture milk_tx, wheat_tx, egg_tx, fruits_tx, sugar_tx, empty_slot_tx, cake_tx, bisquit_tx, dessert_tx, bread_tx, bun_tx, move_tx, money_tx,income_tx, storage_tx;
    final Texture windmill_tx, apartments_tx, scarecrow_tx, hay_tx, coop_tx, evaporator_tx, fertilizer_tx, package_tx, promotions_tx, advertisement_tx, upgrade_button_tx, upgrade_button_tx_pressed;
    final Texture landscape_tx, humidity_tx, population_tx, happiness_tx, city_tx, farm_tx, empty_tx, movement_tx, reputation_tx;
    final Texture arrow_up_button_tx, arrow_left_button_tx, arrow_right_button_tx,arrow_up_button_tx_pressed, arrow_left_button_tx_pressed, arrow_right_button_tx_pressed, game_over_tx;

    final Texture[][] cell_textures = new Texture[3][2];
    final Texture[] empty_textures = new Texture[6], city_textures = new Texture[6], farm_textures = new Texture[7];

    Drawable[][] cell_drawables = new Drawable[3][2];
    Drawable[] empty_drawables = new Drawable[6], city_drawables = new Drawable[6], farm_drawables = new Drawable[7];

    final Texture empty_hill_tx, city_hill_tx, farm_hill_tx_1, farm_hill_tx_2;

    Drawable empty_hill_dr, city_hill_dr, farm_hill_dr_1, farm_hill_dr_2;

    ImageButton done_button, settings_button, expand_button_1,next_move_button, yes_button, no_button;
    ImageButton arrow_left_button, arrow_right_button, left_button, right_button;

    Image [] horizontal_lines = new Image[6], vertical_lines = new Image[4], horizontal_info_images = new Image[4];

    Image [][] cells_arr = new Image[21][8];
    Image [][] info_images_full = new Image[2][3];

    Image base_table_design, top_table_design, bottom_table_design, leave_menu, vertical_l_small;
    Image horizontal_info_closed, horizontal_info_small, horizontal_info_full, vertical_info_closed, vertical_info_full;
    Image moves, window, settings_menu, game_over_screen, settings_background;

    ImageButton[][] top_arr = new ImageButton[21][8];

    EmptyCell[][] cells = new EmptyCell[21][8];
    FarmCell[][] farms = new FarmCell[21][8];
    CityCell[][] cities = new CityCell[21][8];

    CheckBox check_button;

    Label[] horizontal_numbers = new Label[4];
    Label[][] info_numbers_full = new Label[2][3];
    Label[][] contract_show = new Label[6][2];
    Label[][] info_contracts_resources = new Label[10][2];
    Image[][] images_contracts_resources = new Image[10][2];
    Label[][] show_needed_resources = new Label[3][2];

    Label show_move, top_info_text, bottom_info_text, show_price_of_contract;

    Table current_window_table = new Table();
    Table previous_window_table = new Table();
    Table selection_window_table = new Table();
    Table current_vertical_table = new Table();
    Table current_horizontal_table = new Table();
    Table current_center_table = new Table();

    StyleBuilder builder = new StyleBuilder();

    public float scaleCalculating(int a){
        return a *(staticHeight/3240f);
    }

    public String numberScaling(double a){
        String str;
        String pattern = "#0.00";

        int position = 0;

        str = scaleNumber(a, pattern);
        char [] mas = str.toCharArray();

        for(int i = 0; i<mas.length; i++){
            if(mas[i] =='.'){
                position = i;
            }
        }

        if(position == 3 && mas.length>6){
            str =  scaleNumber(a, "#0.0");
        }

        return str;
    }

    public String scaleNumber(double a, String pattern){
        String format = new DecimalFormat(pattern).format(a);
        String str = format;
        if(a>=0){
            if(a>=1000){
                format = new DecimalFormat(pattern).format(a/1000);
                str = format + "K";
            }
            if(a>=1000000){
                format = new DecimalFormat(pattern).format(a/1000000);
                str = format + "KK";
            }
            if(a>=1000000000){
                format = new DecimalFormat(pattern).format(a/1000000000);
                str = format + "KKK";
            }
        } else{
            if(a<=-1000){
                format = new DecimalFormat(pattern).format(a/1000);
                str = format + "K";
            }
            if(a<=-1000000){
                format = new DecimalFormat(pattern).format(a/1000000);
                str = format + "KK";
            }
            if(a<=-1000000000){
                format = new DecimalFormat(pattern).format(a/1000000000);
                str = format + "KKK";
            }
        }
        return str;
    }

    public void addingToHorizontalTable(int i){

        Texture expand_button_tx = new Texture("expand_button.png");
        Texture expand_button_tx_pressed = new Texture("expand_button_pressed.png");

        Drawable expand_dr = new TextureRegionDrawable(new TextureRegion(expand_button_tx));
        Drawable expand_dr_pressed = new TextureRegionDrawable(new TextureRegion(expand_button_tx_pressed));

        Drawable arrow_left_dr = new TextureRegionDrawable(new TextureRegion(arrow_left_button_tx));
        Drawable arrow_left_dr_pressed = new TextureRegionDrawable(new TextureRegion(arrow_left_button_tx_pressed));

        Drawable arrow_right_dr = new TextureRegionDrawable(new TextureRegion(arrow_right_button_tx));
        Drawable arrow_right_dr_pressed = new TextureRegionDrawable(new TextureRegion(arrow_right_button_tx_pressed));

        current_horizontal_table.remove();
        current_horizontal_table.reset();
        switch (i){

            case 1:
                horizontal_info_small.remove();
                stage2.addActor(horizontal_info_full);
                horizontal_numbers[0] = new Label(String.valueOf(move), labelStyle3);
                horizontal_numbers[1] = new Label(numberScaling(money), labelStyle3);
                horizontal_numbers[2] = new Label(numberScaling(absoluteIncome), labelStyle3);
                horizontal_numbers[3] = new Label(numberScaling(storage.returnAllResources()), labelStyle3);

                horizontal_info_images[0] = new Image(move_tx);
                horizontal_info_images[1] = new Image(money_tx);
                horizontal_info_images[2] = new Image(income_tx);
                horizontal_info_images[3] = new Image(storage_tx);

                for(int l = 0; l<4; l++){
                    horizontal_numbers[l].setSize(scaleCalculating(550),scaleCalculating(200));
                    horizontal_numbers[l].setPosition(scaleCalculating(270+(l*1050)),scaleCalculating(50));
                    current_horizontal_table.addActor(horizontal_numbers[l]);

                    horizontal_info_images[l].setSize(scaleCalculating(200),scaleCalculating(200));
                    horizontal_info_images[l].setPosition(scaleCalculating(50+(l*1050)), scaleCalculating(50));
                    current_horizontal_table.addActor(horizontal_info_images[l]);

                    vertical_lines[l] = new Image(vertical_l_tx);
                    vertical_lines[l].setSize(scaleCalculating(12), scaleCalculating(200));
                    vertical_lines[l].setPosition(scaleCalculating(10+(l+1)*1050), scaleCalculating(50));
                    current_horizontal_table.addActor(vertical_lines[l]);
                }

                arrow_left_button = new ImageButton(arrow_left_dr, arrow_left_dr_pressed);
                arrow_left_button.setPosition(scaleCalculating(4190), scaleCalculating(50));
                arrow_left_button.setSize(scaleCalculating(200), scaleCalculating(200));
                arrow_left_button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        settings_button.remove();
                        addingToHorizontalTable(2);
                        stage2.addActor(settings_button);

                    }
                });

                current_horizontal_table.addActor(arrow_left_button);
                break;
            case 2:
                horizontal_info_full.remove();
                stage2.addActor(horizontal_info_closed);
                expand_button_1 = new ImageButton(expand_dr, expand_dr_pressed);
                expand_button_1.setPosition(scaleCalculating(44), scaleCalculating(54));
                expand_button_1.setSize(scaleCalculating(200), scaleCalculating(200));
                expand_button_1.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        settings_button.remove();

                        addingToHorizontalTable(3);
                        stage2.addActor(settings_button);

                    }
                });
                current_horizontal_table.addActor(expand_button_1);
                break;
            case 3:
                horizontal_info_closed.remove();
                stage2.addActor(horizontal_info_small);
                show_move = new Label(String.valueOf(move), labelStyle3);
                show_move.setSize(scaleCalculating(550),scaleCalculating(200));
                show_move.setPosition(scaleCalculating(270),scaleCalculating(50));
                current_horizontal_table.addActor(show_move);

                moves = new Image(move_tx);
                moves.setSize(scaleCalculating(200),scaleCalculating(200));
                moves.setPosition(scaleCalculating(50), scaleCalculating(50));
                current_horizontal_table.addActor(moves);

                vertical_l_small = new Image(vertical_l_tx);
                vertical_l_small.setSize(scaleCalculating(12), scaleCalculating(200));
                vertical_l_small.setPosition(scaleCalculating(1060), scaleCalculating(50));
                current_horizontal_table.addActor(vertical_l_small);

                arrow_right_button = new ImageButton(arrow_right_dr, arrow_right_dr_pressed);
                arrow_right_button.setPosition(scaleCalculating(1050), scaleCalculating(50));
                arrow_right_button.setSize(scaleCalculating(200), scaleCalculating(200));
                arrow_right_button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        settings_button.remove();
                        addingToHorizontalTable(1);
                        stage2.addActor(settings_button);
                    }
                });

                current_horizontal_table.addActor(arrow_right_button);
                break;
        }
        stage2.addActor(current_horizontal_table);
        sizeOfHorizontalTable = i;
    }

    public void addingToVerticalTable(int i){
        current_vertical_table.remove();
        current_vertical_table.reset();
        switch(i){
            case 1:
                Texture[][] textures = new Texture[6][2];

                textures[0][0] = new Texture("info_button.png");
                textures[1][0] = new Texture("contracts_button.png");
                textures[2][0] = new Texture("handshake_im.png");
                textures[3][0]= new Texture("upgrading_button.png");
                textures[4][0]= new Texture("building_button.png");
                textures[5][0]= new Texture("reputation_button.png");

                textures[0][1] = new Texture("info_button_pressed.png");
                textures[1][1] = new Texture("contracts_button_pressed.png");
                textures[2][1] = new Texture("handshake_im_pressed.png");
                textures[3][1] = new Texture("upgrading_button_pressed.png");
                textures[4][1]= new Texture("building_button_pressed.png");
                textures[5][1]= new Texture("reputation_button_pressed.png");

                Drawable[][] drawables = new Drawable[6][2];

                for(int m =0; m<6; m++){
                    drawables[m][0] = new TextureRegionDrawable(new TextureRegion(textures[m][0]));
                    drawables[m][1] = new TextureRegionDrawable(new TextureRegion(textures[m][1]));
                }

                vertical_info_closed.remove();
                stage2.addActor(vertical_info_full);

                ImageButton[] vertical_buttons = new ImageButton[6];
                for(int m =0; m<6; m++){
                    vertical_buttons[m] = new ImageButton(drawables[m][0], drawables[m][1]);
                    vertical_buttons[m].setPosition(scaleCalculating(40), scaleCalculating(1990- m * 370));
                    vertical_buttons[m].setSize(scaleCalculating(220), scaleCalculating(220));
                    final int mm = m;
                    vertical_buttons[m].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            changingWindows(mm+1,current_x,current_y);
                        }
                    });
                    current_vertical_table.addActor(vertical_buttons[m]);
                    if(m!=5){
                        horizontal_lines[m] = new Image(horizontal_l_tx);
                        horizontal_lines[m].setSize(scaleCalculating(200), scaleCalculating(12));
                        horizontal_lines[m].setPosition(scaleCalculating(50), scaleCalculating(1905 - m * 370));
                        current_vertical_table.addActor(horizontal_lines[m]);
                    }
                }

                break;
            case 2:
                vertical_info_full.remove();
                stage2.addActor(vertical_info_closed);

                break;
        }
        if(!cell_is_using){
            if (current_map[current_x][current_y][0] ==1){
                Image[] this_cell = new Image[3];
                this_cell[0] = new Image(empty_tx);
                this_cell[1] = new Image(farm_tx);
                this_cell[2] = new Image(city_tx);
                for(int m = 0; m<3; m++){
                    this_cell[m].setSize(scaleCalculating(200), scaleCalculating(200));
                    this_cell[m].setPosition(scaleCalculating(46), scaleCalculating(2380));
                }
                final int type = current_map[current_x][current_y][1];
                current_vertical_table.addActor(this_cell[type]);
            }
        }

        stage2.addActor(current_vertical_table);
    }

    public double[] chooseContract(int x, int y, int type, boolean changing_number, int position, boolean isFirst){
        int max;
        if (type == 1){
            max = farms[x][y].getSizeContracts();
        }else{
            max = cities[x][y].getSizeContracts();
        }
        if(!isFirst) {
            if (changing_number) {
                position++;
            } else position--;
            if (position < 0) {
                position = max;
            }
            if (position > max) {
                position = 0;
            }
            if (max == 0) {
                position = 0;
            }

        }
        bottom_info[1] = position;
        if(position==0) {
            if (type == 1) {
                for (int i = 1; i < 6; i++) {
                    contract_show[i][0].setText(numberScaling(farms[x][y].getAllContractInfo(i - 1, 2,3)));
                    contract_show[i][1].setText(numberScaling(farms[x][y].getAllContractInfo(i - 1, 1,3)));
                }
                bottom_info[0] = 0;
            } else {
                for (int i = 1; i < 6; i++) {
                    contract_show[i][0].setText(numberScaling(cities[x][y].getAllContractInfo(i - 1, 2,3)));
                    info_contracts_resources[(i - 1) * 2][0].setText(numberScaling(cities[x][y].getAllContractInfo(i - 1, 1,3)));
                    info_contracts_resources[(i - 1) * 2][1].setText(numberScaling(cities[x][y].getAllContractInfo(i - 1, 3,3)));
                    info_contracts_resources[(i - 1) * 2 + 1][0].setText(numberScaling(cities[x][y].getAllContractInfo(i - 1, 4,3)));
                    info_contracts_resources[(i - 1) * 2 + 1][1].setText(numberScaling(cities[x][y].getAllContractInfo(i - 1, 5,3)));
                }
                bottom_info[0] = 0;
            }
        }else{
            if (type == 1) {
                for (int i = 1; i < 6; i++) {
                    contract_show[i][0].setText(numberScaling(farms[x][y].getContractInfo(i - 1, 2, position)));
                    contract_show[i][1].setText(numberScaling(farms[x][y].getContractInfo(i - 1, 1,position)));

                }
                bottom_info[0] = farms[x][y].getContractInfo(1, 6, position);
            } else {
                for (int i = 1; i < 6; i++) {
                    contract_show[i][0].setText(numberScaling(cities[x][y].getContractInfo(i - 1, 2, position)));
                    info_contracts_resources[(i - 1) * 2][0].setText(numberScaling(cities[x][y].getContractInfo(i - 1, 1, position)));
                    info_contracts_resources[(i - 1) * 2][1].setText(numberScaling(cities[x][y].getContractInfo(i - 1, 3, position)));
                    info_contracts_resources[(i - 1) * 2 + 1][0].setText(numberScaling(cities[x][y].getContractInfo(i - 1, 4, position)));
                    info_contracts_resources[(i - 1) * 2 + 1][1].setText(numberScaling(cities[x][y].getContractInfo(i - 1, 5, position)));
                }
                bottom_info[0] = cities[x][y].getContractInfo(1, 6, position);
            }

        }
        return bottom_info;
    }

    public void changingWindows(int i, int x, int y){
        Gdx.input.setInputProcessor(multiplexer);
        Image [] big_vertical_lines = new Image[7];
        Image [] big_horizontal_lines = new Image [4];
        final Image [] types_of_resources = new Image[6];
        final Image [] types_of_products = new Image[6];

        final Slider[] sliders = new Slider[6];

        final Label[] sliders_info = new Label[6];

        final int xx = x;
        final int yy = y;

        bottom_info[0] = 0;
        bottom_info[1] = 0;

        current_window_table.remove();
        current_window_table.reset();

        top_info_text = new Label("", labelStyle4);
        top_info_text.setAlignment(Align.center);
        top_info_text.setPosition(scaleCalculating(72),scaleCalculating(2365));
        top_info_text.setSize(scaleCalculating(2000),scaleCalculating(240));

        Drawable done_button_dr = new TextureRegionDrawable(new TextureRegion(done_button_tx));
        Drawable done_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(done_button_tx_pressed));

        Drawable arrow_left_dr = new TextureRegionDrawable(new TextureRegion(arrow_left_button_tx));
        Drawable arrow_left_dr_pressed = new TextureRegionDrawable(new TextureRegion(arrow_left_button_tx_pressed));

        Drawable arrow_right_dr = new TextureRegionDrawable(new TextureRegion(arrow_right_button_tx));
        Drawable arrow_right_dr_pressed = new TextureRegionDrawable(new TextureRegion(arrow_right_button_tx_pressed));

        Drawable upgrade_button_dr = new TextureRegionDrawable(new TextureRegion(upgrade_button_tx));
        Drawable upgrade_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(upgrade_button_tx_pressed));

        for(int m = 0; m<7; m++){
            big_vertical_lines[m] = new Image(vertical_l_tx);
        }
        big_vertical_lines[0].setSize(scaleCalculating(12), scaleCalculating(1802));
        big_vertical_lines[0].setPosition(scaleCalculating(300), scaleCalculating(300));
        big_vertical_lines[1].setSize(scaleCalculating(12), scaleCalculating(920));
        big_vertical_lines[1].setPosition(scaleCalculating(1050), scaleCalculating(1270));
        big_vertical_lines[2].setSize(scaleCalculating(12), scaleCalculating(1802));
        big_vertical_lines[2].setPosition(scaleCalculating(1140), scaleCalculating(300));
        big_vertical_lines[3].setSize(scaleCalculating(12), scaleCalculating(280));
        big_vertical_lines[3].setPosition(scaleCalculating(1250), scaleCalculating(30));
        big_vertical_lines[4].setSize(scaleCalculating(12), scaleCalculating(280));
        big_vertical_lines[4].setPosition(scaleCalculating(650), scaleCalculating(30));
        big_vertical_lines[5].setSize(scaleCalculating(12), scaleCalculating(1802));
        big_vertical_lines[5].setPosition(scaleCalculating(980), scaleCalculating(300));
        big_vertical_lines[6].setSize(scaleCalculating(12), scaleCalculating(470));
        big_vertical_lines[6].setPosition(scaleCalculating(1250), scaleCalculating(30));

        for(int m = 0; m<4; m++){
            big_horizontal_lines[m] = new Image(horizontal_l_tx);
            big_horizontal_lines[m].setSize(scaleCalculating(1760), scaleCalculating(12));
        }
        big_horizontal_lines[0].setPosition(scaleCalculating(100), scaleCalculating(1900));
        big_horizontal_lines[1].setPosition(scaleCalculating(100), scaleCalculating(300));
        big_horizontal_lines[2].setPosition(scaleCalculating(100), scaleCalculating(700));
        big_horizontal_lines[3].setPosition(scaleCalculating(100), scaleCalculating(490));

        types_of_products[0] = new Image(cake_tx);
        types_of_products[1] = new Image(bisquit_tx);
        types_of_products[2] = new Image(bread_tx);
        types_of_products[3] = new Image(dessert_tx);
        types_of_products[4] = new Image(bun_tx);
        types_of_products[5] = new Image(move_tx);

        types_of_resources[0] = new Image(wheat_tx);
        types_of_resources[1] = new Image(milk_tx);
        types_of_resources[2] = new Image(egg_tx);
        types_of_resources[3] = new Image(sugar_tx);
        types_of_resources[4] = new Image(fruits_tx);
        types_of_resources[5] = new Image(move_tx);

        for (int m = 0; m<6; m++){
            types_of_products[m].setSize(scaleCalculating(300), scaleCalculating(300));
            types_of_resources[m].setSize(scaleCalculating(300), scaleCalculating(300));
        }

        if(!cell_is_using){
            if(current_map[x][y][0] == 1){

                final int type = current_map[x][y][1];
                    switch(i){
                        case 1:
                            bottom_info_text = new Label("Text will be added soon",labelStyle2);
                            bottom_info_text.setAlignment(Align.topLeft);
                            bottom_info_text.setSize(scaleCalculating(1742),scaleCalculating(1181));
                            bottom_info_text.setPosition(scaleCalculating(120),scaleCalculating(60));

                            info_images_full[0][0] = new Image(landscape_tx);
                            info_images_full[1][0] = new Image(population_tx);
                            info_images_full[0][1] = new Image(humidity_tx);
                            info_images_full[1][1] = new Image(happiness_tx);
                            info_images_full[0][2] = new Image(movement_tx);
                            info_images_full[1][2] = new Image(reputation_tx);

                            for(int m = 0; m<3; m++){
                                info_images_full[0][m].setSize(scaleCalculating(220),scaleCalculating(220));
                                info_images_full[1][m].setSize(scaleCalculating(220),scaleCalculating(220));
                                info_images_full[0][m].setPosition(scaleCalculating(100),scaleCalculating(1960 - 330*m));
                                info_images_full[1][m].setPosition(scaleCalculating(1130),scaleCalculating(1960 - 330*m));

                                current_window_table.addActor(info_images_full[0][m]);
                                current_window_table.addActor(info_images_full[1][m]);

                                info_numbers_full[0][m] = new Label("",labelStyle3);
                                info_numbers_full[1][m] = new Label("",labelStyle3);
                                info_numbers_full[0][m].setSize(scaleCalculating(638), scaleCalculating(177));
                                info_numbers_full[1][m].setSize(scaleCalculating(638), scaleCalculating(177));
                                info_numbers_full[0][m].setPosition(scaleCalculating(370),scaleCalculating(1985 - 330*m));
                                info_numbers_full[1][m].setPosition(scaleCalculating(1420),scaleCalculating(1985 - 330*m));
                                current_window_table.addActor(info_numbers_full[0][m]);
                                current_window_table.addActor(info_numbers_full[1][m]);
                            }
                            if(type == 1){
                                top_info_text.setText(farms[x][y].getName());
                                info_numbers_full[0][0].setText(farms[x][y].getStrTypeLandscape());
                                info_numbers_full[1][0].setText(farms[x][y].getStrRainfall());
                                info_numbers_full[0][1].setText(farms[x][y].getCurrentPopulation());
                                info_numbers_full[1][1].setText(numberScaling(farms[x][y].getCurrentHappiness()*100)+"%");
                                info_numbers_full[0][2].setText(farms[x][y].getStrMovement());
                                info_numbers_full[1][2].setText(farms[x][y].getReputation());
                            }
                            if(type == 2){
                                top_info_text.setText(cities[x][y].getName());
                                info_numbers_full[0][0].setText(cities[x][y].getStrTypeLandscape());
                                info_numbers_full[1][0].setText(cities[x][y].getStrRainfall());
                                info_numbers_full[0][1].setText(numberScaling(cities[x][y].getCurrentPopulation()));
                                info_numbers_full[1][1].setText(numberScaling(cities[x][y].getCurrentHappiness()*100)+"%");
                                info_numbers_full[0][2].setText(cities[x][y].getStrMovement());
                                info_numbers_full[1][2].setText(cities[x][y].getReputation());
                            }
                            if(type == 0){
                                top_info_text.setText("Empty cell");
                                info_numbers_full[0][0].setText(cells[x][y].getStrTypeLandscape());
                                info_numbers_full[1][0].setText(cells[x][y].getStrRainfall());
                                info_numbers_full[0][1].setText("0");
                                info_numbers_full[1][1].setText("0%");
                                info_numbers_full[0][2].setText(cells[x][y].getStrMovement());
                                info_numbers_full[1][2].setText(cells[x][y].getReputation());
                            }

                            current_window_table.addActor(big_vertical_lines[1]);
                            current_window_table.addActor(bottom_info_text);
                            break;
                        case 2:
                            top_info_text.setText("Contracts");
                            if(type!=0) {
                                Image moves_image = new Image(move_tx);
                                moves_image.setSize(scaleCalculating(200), scaleCalculating(200));
                                moves_image.setPosition(scaleCalculating(60), scaleCalculating(50));
                                current_window_table.addActor(moves_image);

                                final Label position_info = new Label("ALL", labelStyle3);
                                position_info.setSize(scaleCalculating(280), scaleCalculating(180));
                                position_info.setPosition(scaleCalculating(1165), scaleCalculating(67));
                                position_info.setAlignment(Align.center);
                                current_window_table.addActor(position_info);

                                final Label moves_info = new Label("-", labelStyle3);
                                moves_info.setSize(scaleCalculating(250), scaleCalculating(180));
                                moves_info.setPosition(scaleCalculating(350), scaleCalculating(67));
                                current_window_table.addActor(moves_info);

                                contract_show[0][0] = new Label("Revenue", labelStyle3);
                                contract_show[0][1] = new Label("Cost", labelStyle3);

                                images_contracts_resources[1][0]= new Image(milk_tx);
                                images_contracts_resources[1][1]= new Image(sugar_tx);
                                images_contracts_resources[3][0]= new Image(egg_tx);
                                images_contracts_resources[3][1]= new Image(sugar_tx);
                                images_contracts_resources[5][0]= new Image(empty_slot_tx);
                                images_contracts_resources[5][1]= new Image(empty_slot_tx);
                                images_contracts_resources[7][0]= new Image(fruits_tx);
                                images_contracts_resources[7][1]= new Image(sugar_tx);
                                images_contracts_resources[9][0]= new Image(sugar_tx);
                                images_contracts_resources[9][1]= new Image(empty_slot_tx);

                                left_button = new ImageButton(arrow_left_dr, arrow_left_dr_pressed);
                                left_button.setPosition(scaleCalculating(850), scaleCalculating(50));
                                left_button.setSize(scaleCalculating(200), scaleCalculating(200));
                                left_button.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        int pos = (int)bottom_info[1];
                                        bottom_info = chooseContract(xx,yy, type,false,pos,false);
                                        if(bottom_info[0]==0){
                                            moves_info.setText("-");
                                        } else moves_info.setText((int)bottom_info[0]);
                                        if(bottom_info[1]==0){
                                            position_info.setText("ALL");
                                        } else position_info.setText((int)bottom_info[1]);
                                    }
                                });
                                current_window_table.addActor(left_button);
                                right_button = new ImageButton(arrow_right_dr, arrow_right_dr_pressed);
                                right_button.setPosition(scaleCalculating(1400), scaleCalculating(50));
                                right_button.setSize(scaleCalculating(500), scaleCalculating(200));
                                right_button.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        int pos = (int)bottom_info[1];
                                        bottom_info = chooseContract(xx,yy, type,true,pos,false);
                                        if(bottom_info[0]==0){
                                            moves_info.setText("-");
                                        } else moves_info.setText((int)bottom_info[0]);
                                        if(bottom_info[1]==0){
                                            position_info.setText("ALL");
                                        } else position_info.setText((int)bottom_info[1]);
                                    }
                                });
                                current_window_table.addActor(right_button);

                                    if (type == 1) {
                                        for(int m = 1; m<6; m++) {
                                            types_of_resources[m - 1].setPosition(scaleCalculating(60), scaleCalculating(1620 - 300 * (m - 1)));
                                            types_of_resources[m - 1].setSize(scaleCalculating(200), scaleCalculating(200));
                                            current_window_table.addActor(types_of_resources[m - 1]);

                                            contract_show[m][0] = new Label(numberScaling(farms[x][y].getAllContractInfo(m - 1, 2,3)), labelStyle3);
                                            contract_show[m][1] = new Label(numberScaling(farms[x][y].getAllContractInfo(m - 1, 1,3)), labelStyle3);

                                        }
                                        for (int m = 0; m < 6; m++) {
                                            contract_show[m][0].setSize(scaleCalculating(700), scaleCalculating(177));
                                            contract_show[m][1].setSize(scaleCalculating(700), scaleCalculating(177));
                                            contract_show[m][0].setPosition(scaleCalculating(380), scaleCalculating(1920 - 300 * m));///330
                                            contract_show[m][1].setPosition(scaleCalculating(1170), scaleCalculating(1920 - 300 * m));//1170
                                            contract_show[m][0].setAlignment(Align.center);
                                            contract_show[m][1].setAlignment(Align.center);
                                            current_window_table.addActor(contract_show[m][0]);
                                            current_window_table.addActor(contract_show[m][1]);
                                        }
                                        current_window_table.addActor(big_vertical_lines[2]);
                                    }
                                    if (type == 2) {
                                        for(int m = 1; m<6; m++) {
                                        types_of_products[m - 1].setPosition(scaleCalculating(60), scaleCalculating(1620 - 300 * (m - 1)));
                                        types_of_products[m - 1].setSize(scaleCalculating(200), scaleCalculating(200));
                                        current_window_table.addActor(types_of_products[m - 1]);

                                        contract_show[m][0] = new Label(numberScaling(cities[x][y].getAllContractInfo(m - 1, 2,3)), labelStyle3);
                                        contract_show[m][1] = new Label(" ", labelStyle3);

                                        contract_show[m][0].setAlignment(Align.center);
                                        contract_show[m][1].setAlignment(Align.center);
                                        current_window_table.addActor(contract_show[m][0]);
                                        current_window_table.addActor(contract_show[m][1]);
                                        }
                                        for(int m = 0; m<5; m++) {
                                            info_contracts_resources[2*m][0] = new Label(numberScaling(cities[x][y].getAllContractInfo(m, 2,3)), labelStyle1);
                                            info_contracts_resources[2*m][1] = new Label(numberScaling(cities[x][y].getAllContractInfo(m, 3,3)), labelStyle1);
                                            info_contracts_resources[2*m+1][0] = new Label(numberScaling(cities[x][y].getAllContractInfo(m, 4,3)), labelStyle1);
                                            info_contracts_resources[2*m+1][1] = new Label(numberScaling(cities[x][y].getAllContractInfo(m, 5,3)), labelStyle1);

                                            info_contracts_resources[2*m][0].setSize(scaleCalculating(265), scaleCalculating(120));
                                            info_contracts_resources[2*m][1].setSize(scaleCalculating(265), scaleCalculating(120));
                                            info_contracts_resources[2*m+1][0].setSize(scaleCalculating(265), scaleCalculating(120));
                                            info_contracts_resources[2*m+1][1].setSize(scaleCalculating(265), scaleCalculating(120));

                                            info_contracts_resources[2*m][0].setPosition(scaleCalculating(1150), scaleCalculating(1710 - 300 * m));
                                            info_contracts_resources[2*m][1].setPosition(scaleCalculating(1570), scaleCalculating(1710 - 300 * m));
                                            info_contracts_resources[2*m+1][0].setPosition(scaleCalculating(1150), scaleCalculating(1590 - 300 * m));
                                            info_contracts_resources[2*m+1][1].setPosition(scaleCalculating(1570), scaleCalculating(1590 - 300 * m));

                                            current_window_table.addActor(info_contracts_resources[2*m][0]);
                                            current_window_table.addActor(info_contracts_resources[2*m][1]);
                                            current_window_table.addActor(info_contracts_resources[2*m+1][0]);
                                            current_window_table.addActor(info_contracts_resources[2*m+1][1]);

                                            images_contracts_resources[2*m][0]= new Image(money_tx);
                                            images_contracts_resources[2*m][1]= new Image(wheat_tx);

                                            images_contracts_resources[2*m][0].setSize(scaleCalculating(100), scaleCalculating(100));
                                            images_contracts_resources[2*m][1].setSize(scaleCalculating(100), scaleCalculating(100));
                                            images_contracts_resources[2*m+1][0].setSize(scaleCalculating(100), scaleCalculating(100));
                                            images_contracts_resources[2*m+1][1].setSize(scaleCalculating(100), scaleCalculating(100));

                                            images_contracts_resources[2*m][0].setPosition(scaleCalculating(1030), scaleCalculating(1720 - 300 * m));
                                            images_contracts_resources[2*m][1].setPosition(scaleCalculating(1460), scaleCalculating(1720 - 300 * m));
                                            images_contracts_resources[2*m+1][0].setPosition(scaleCalculating(1030), scaleCalculating(1600 - 300 * m));
                                            images_contracts_resources[2*m+1][1].setPosition(scaleCalculating(1460), scaleCalculating(1600 - 300 * m));

                                            current_window_table.addActor(images_contracts_resources[2*m][0]);
                                            current_window_table.addActor(images_contracts_resources[2*m][1]);
                                            current_window_table.addActor(images_contracts_resources[2*m+1][0]);
                                            current_window_table.addActor(images_contracts_resources[2*m+1][1]);

                                        }
                                        for (int m = 0; m < 6; m++) {
                                            contract_show[m][0].setPosition(scaleCalculating(290), scaleCalculating(1920 - 300 * m));
                                            contract_show[m][1].setPosition(scaleCalculating(1070), scaleCalculating(1920 - 300 * m));

                                            contract_show[m][0].setSize(scaleCalculating(700), scaleCalculating(177));
                                            contract_show[m][1].setSize(scaleCalculating(700), scaleCalculating(177));

                                            contract_show[m][0].setAlignment(Align.center);
                                            contract_show[m][1].setAlignment(Align.center);
                                            current_window_table.addActor(contract_show[m][0]);
                                            current_window_table.addActor(contract_show[m][1]);
                                        }
                                        current_window_table.addActor(big_vertical_lines[5]);
                                    }

                                current_window_table.addActor(big_vertical_lines[0]);
                                current_window_table.addActor(big_vertical_lines[4]);
                                current_window_table.addActor(big_horizontal_lines[0]);
                                current_window_table.addActor(big_horizontal_lines[1]);

                                bottom_info = chooseContract(xx,yy, type,true,(int)bottom_info[1],true);
                                }
                            break;
                        case 3:
                            Image[][] show_needed_image = new Image[3][2];
                            Gdx.input.setInputProcessor(multiplexer2);
                            top_info_text.setText("Add new...");
                            if(type!=0) {
                                check_button = new CheckBox("",checkBoxStyle);
                                check_button.setChecked(true);
                                check_button.addListener(new ChangeListener() {
                                    public void changed (ChangeEvent event, Actor actor) {
                                        if(check_button.isChecked()){
                                            current_window_table.addActor(sliders[5]);
                                            current_window_table.addActor(sliders_info[5]);
                                            if (type == 1) {
                                                current_window_table.addActor(types_of_resources[5]);
                                            }
                                            if (type == 2) {
                                                for(int m = 0; m<5;m++){
                                                    double[][] clear = calculatingResources(productToResources(m,sliders_numbers[m], true, false));
                                                }
                                                current_window_table.addActor(types_of_products[5]);
                                            }
                                        } else{
                                            sliders[5].remove();
                                            sliders_info[5].remove();
                                            if (type == 1) {
                                                types_of_resources[5].remove();
                                            }
                                            if (type == 2) {
                                                for(int m = 0; m<5;m++){
                                                    double[][] clear = calculatingResources(productToResources(m,sliders_numbers[m], true, false));
                                                }
                                                types_of_products[5].remove();
                                            }
                                        }

                                        if (type == 1) {
                                            show_price_of_contract.setText(numberScaling(0));
                                        }
                                        if (type == 2) {
                                            for(int t = 0; t<3; t++){
                                                show_needed_resources[t][0].setText(numberScaling(0));
                                                show_needed_resources[t][1].setText(numberScaling(0));
                                            }
                                        }for(int j = 0; j<5;j++){
                                            sliders_numbers[j] = 0;
                                            sliders_info[j].setText(numberScaling(0));
                                            sliders[j].setVisualPercent(0);
                                        }


                                    }
                                });
                                for(int l = 0; l<5;l++){
                                    final int m = l;
                                    sliders_numbers[l] = 0;
                                    double[][] clear = calculatingResources(productToResources(m,sliders_numbers[m], true, false));
                                    sliders_info[l] = new Label(numberScaling(0), labelStyle3);
                                    sliders_info[l].setAlignment(Align.left);
                                    sliders_info[l].setSize(scaleCalculating(530), scaleCalculating(166));
                                    sliders_info[l].setPosition(scaleCalculating(1370), scaleCalculating(1867 - (280 * l)));
                                    current_window_table.addActor(sliders_info[l]);

                                    sliders[l] = new Slider(1, 61, 1, false, sliderStyle);
                                    sliders[l].setSize(scaleCalculating(900), scaleCalculating(250));
                                    sliders[l].addListener(new InputListener() {
                                        @Override
                                        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                        double[][] results;
                                        double[][] check;
                                        if(type == 1) {
                                            sliders_numbers[m] = resourceCountCalculating(m,xx,yy)/60*(sliders[m].getValue()-1);
                                            show_price_of_contract.setText(numberScaling(farmPriceCalculating(xx,yy)));
                                        }
                                        if(type == 2) {
                                            sliders_numbers[m] = productCountCalculating(m)/60*(sliders[m].getValue()-1);
                                            results = calculatingResources(productToResources(m,sliders_numbers[m], false, false));
                                            if(check_button.isChecked()){
                                                check = checkResourcesInIncomes();
                                            }
                                            else{
                                                check = checkResourcesInStorage();
                                            }

                                            for(int t = 0; t<3; t++){
                                                if(check[t][0]>=results[t][0]){
                                                    show_needed_resources[t][0].setText(numberScaling(results[t][0]));
                                                }else{
                                                    show_needed_resources[t][0].setText(numberScaling(results[t][0])+"!");
                                                }
                                                if(check[t][1]>=results[t][1]){
                                                    show_needed_resources[t][1].setText(numberScaling(results[t][1]));
                                                }else{
                                                    show_needed_resources[t][1].setText(numberScaling(results[t][1])+"!");
                                                }
                                            }
                                        }
                                        sliders_info[m].setText(numberScaling(sliders_numbers[m]));

                                        }
                                        @Override
                                        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                                            return true;
                                        }
                                    });
                                }
                                sliders_numbers[5] = 1;
                                sliders_info[5] = new Label("1", labelStyle3);
                                sliders_info[5].setAlignment(Align.left);
                                sliders_info[5].setSize(scaleCalculating(320), scaleCalculating(166));

                                current_window_table.addActor(sliders_info[5]);

                                sliders[5] = new Slider(1, 60, 1, false, sliderStyle);
                                sliders[5].setSize(scaleCalculating(900), scaleCalculating(250));
                                sliders[5].addListener(new InputListener() {
                                    @Override
                                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                        int num = (int)(sliders[5].getValue());
                                        sliders_numbers[5] = num;
                                        sliders_info[5].setText(String.valueOf(num));
                                    }
                                    @Override
                                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                                        return true;
                                    }
                                });

                                show_price_of_contract = new Label(numberScaling(0), labelStyle4);
                                show_price_of_contract.setSize(scaleCalculating(1000), scaleCalculating(228));
                                show_price_of_contract.setPosition(scaleCalculating(100), scaleCalculating(40));

                                done_button = new ImageButton(done_button_dr, done_button_dr_pressed);

                                done_button.setSize(scaleCalculating(588), scaleCalculating(222));
                                done_button.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {

                                        for(int num = 0; num<5; num++){
                                            acceptContract(type,xx,yy,(int)sliders_numbers[5],num);
                                        }
                                        if(type == 2){
                                            if(!check_button.isChecked()) {

                                                for(int num = 0; num<5; num++){
                                                    double[] res = calculatingNeededResources(num,sliders_numbers[num]);
                                                    for(int s = 0; s<5; s++){
                                                        storage.updateStorage(s, res[s], false);
                                                    }
                                                }
                                            }
                                            else{
                                                updateIncomesResources(false);
                                            }

                                        }

                                        switch(type){
                                            case 1:
                                                countOfContracts++;
                                                break;
                                            case 2:
                                                if(checkPossibilityOfContract()){
                                                    countOfContracts++;
                                                }
                                                break;
                                        }
                                        updateMoneyChanges(false);
                                        updateIncomesResources(false);
                                        changingWindows(1,xx,yy);
                                    }
                                });

                                    if (type == 1) {
                                        for (int m = 0; m < 6; m++) {
                                            types_of_resources[m].setPosition(scaleCalculating(100), scaleCalculating(1850 - (280 * m)));
                                            types_of_resources[m].setSize(scaleCalculating(200), scaleCalculating(200));
                                            sliders_info[m].setPosition(scaleCalculating(1370), scaleCalculating(1867 - (280 * m)));
                                            sliders[m].setPosition(scaleCalculating(400), scaleCalculating(1800 - (280 * m)));
                                            current_window_table.addActor(sliders[m]);
                                            current_window_table.addActor(sliders_info[m]);
                                            current_window_table.addActor(types_of_resources[m]);
                                            current_window_table.addActor(show_price_of_contract);
                                        }
                                        done_button.setPosition(scaleCalculating(1270), scaleCalculating(40));
                                        check_button.setPosition(scaleCalculating(1650), scaleCalculating(435));
                                        current_window_table.addActor(big_vertical_lines[3]);
                                        current_window_table.addActor(big_horizontal_lines[1]);
                                    }
                                    if (type == 2) {
                                        show_needed_image[0][0] = new Image(money_tx);
                                        show_needed_image[1][0] = new Image(wheat_tx);
                                        show_needed_image[2][0] = new Image(milk_tx);
                                        show_needed_image[0][1] = new Image(egg_tx);
                                        show_needed_image[1][1] = new Image(sugar_tx);
                                        show_needed_image[2][1] = new Image(fruits_tx);
                                        for(int m = 0; m<3; m++){
                                            show_needed_image[m][0].setSize(scaleCalculating(120), scaleCalculating(120));
                                            show_needed_image[m][0].setPosition(scaleCalculating(90), scaleCalculating(340 - 155*m));
                                            current_window_table.addActor(show_needed_image[m][0]);

                                            show_needed_image[m][1].setSize(scaleCalculating(120), scaleCalculating(120));
                                            show_needed_image[m][1].setPosition(scaleCalculating(670), scaleCalculating(340 - 155*m));
                                            current_window_table.addActor(show_needed_image[m][1]);

                                            show_needed_resources[m][0] = new Label(numberScaling(0), labelStyle1);
                                            show_needed_resources[m][0].setSize(scaleCalculating(400), scaleCalculating(120));
                                            show_needed_resources[m][0].setPosition(scaleCalculating(230), scaleCalculating(340 - 155*m));
                                            current_window_table.addActor(show_needed_resources[m][0]);

                                            show_needed_resources[m][1] = new Label(numberScaling(0), labelStyle1);
                                            show_needed_resources[m][1].setSize(scaleCalculating(400), scaleCalculating(120));
                                            show_needed_resources[m][1].setPosition(scaleCalculating(810), scaleCalculating(340 - 155*m));
                                            current_window_table.addActor(show_needed_resources[m][1]);

                                        }
                                        for (int m = 0; m < 6; m++) {
                                            types_of_products[m].setPosition(scaleCalculating(100), scaleCalculating(1850 - (240 * m)));
                                            types_of_products[m].setSize(scaleCalculating(200), scaleCalculating(200));
                                            current_window_table.addActor(types_of_products[m]);
                                            sliders[m].setPosition(scaleCalculating(400), scaleCalculating(1800 - (240 * m)));
                                            current_window_table.addActor(sliders[m]);
                                            sliders_info[m].setPosition(scaleCalculating(1370), scaleCalculating(1867 - (240 * m)));
                                            current_window_table.addActor(sliders_info[m]);

                                        }
                                        done_button.setPosition(scaleCalculating(1270), scaleCalculating(140));
                                        check_button.setPosition(scaleCalculating(1650), scaleCalculating(635));
                                        current_window_table.addActor(big_vertical_lines[6]);
                                        current_window_table.addActor(big_horizontal_lines[3]);
                                    }
                                current_window_table.addActor(done_button);
                                current_window_table.addActor(check_button);
                            }
                            break;
                        case 4:
                            top_info_text.setText("Upgrading");
                            current_window_table.addActor(top_info_text);
                            if(type!=0){
                                Label[] names_of_upgrades = new Label[7];
                                Label[] show_levels = new Label[7];
                                Label[] show_cost = new Label[7];
                                Image[] images_of_upgrades = new Image[7];
                                final double[] cost_of_upgrades = new double[]{0,0,0,0,0,0,0};
                                ImageButton[] upgrading_buttons = new ImageButton[7];
                                for(int m = 0; m<7; m++){
                                    names_of_upgrades[m] = new Label("", labelStyle3);
                                    names_of_upgrades[m].setSize(scaleCalculating(1140), scaleCalculating(160));
                                    names_of_upgrades[m].setPosition(scaleCalculating(360), scaleCalculating(2075-320*m));
                                    show_levels[m] = new Label("",labelStyle1);
                                    show_levels[m].setSize(scaleCalculating(580), scaleCalculating(110));
                                    show_levels[m].setPosition(scaleCalculating(360), scaleCalculating(1995 - 320*m));
                                    show_cost[m] = new Label("",labelStyle1);
                                    show_cost[m].setSize(scaleCalculating(720), scaleCalculating(110));
                                    show_cost[m].setPosition(scaleCalculating(940), scaleCalculating(1995 - 320*m));
                                    upgrading_buttons[m] = new ImageButton(upgrade_button_dr, upgrade_button_dr_pressed);
                                    upgrading_buttons[m].setSize(scaleCalculating(461), scaleCalculating(247));
                                    upgrading_buttons[m].setPosition(scaleCalculating(1610), scaleCalculating(1995 - 320*m));
                                }
                                if(type ==1){
                                    images_of_upgrades[0] = new Image(apartments_tx);
                                    images_of_upgrades[1] = new Image(windmill_tx);
                                    images_of_upgrades[2] = new Image(scarecrow_tx);
                                    images_of_upgrades[3] = new Image(hay_tx);
                                    images_of_upgrades[4] = new Image(coop_tx);
                                    images_of_upgrades[5] = new Image(evaporator_tx);
                                    images_of_upgrades[6] = new Image(fertilizer_tx);

                                    for(int m = 0; m<7; m++){
                                        names_of_upgrades[m].setText(farms[xx][yy].getUpgradeName(m));
                                        images_of_upgrades[m].setSize(scaleCalculating(240),scaleCalculating(240));
                                        images_of_upgrades[m].setPosition(scaleCalculating(100),scaleCalculating(1995 - 320*m));
                                        switch(m){
                                            case 0:
                                                if(farms[xx][yy].getLevelsOfUpgrades(m) == 10){
                                                    show_levels[m].setText("lvl. MAX");
                                                } else{
                                                    show_levels[m].setText("lvl. "+ farms[xx][yy].getLevelsOfUpgrades(m));
                                                }
                                                break;
                                            case 1:
                                                if(farms[xx][yy].getLevelsOfUpgrades(m) == 30){
                                                    show_levels[m].setText("lvl. MAX");
                                                } else{
                                                    show_levels[m].setText("lvl. "+ farms[xx][yy].getLevelsOfUpgrades(m));
                                                }
                                                break;
                                            default:
                                                show_levels[m].setText("lvl. "+ farms[xx][yy].getLevelsOfUpgrades(m));
                                                break;
                                        }

                                        if(show_levels[m].equals("lvl. MAX")){
                                            cost_of_upgrades[m] = 0;
                                        } else{
                                            cost_of_upgrades[m] = calculateMoneyForUpgrade(type, m, farms[xx][yy].getLevelsOfUpgrades(m));
                                        }

                                        show_cost[m].setText("cost: "+numberScaling(cost_of_upgrades[m]));
                                        final int mm = m;
                                        upgrading_buttons[m].addListener(new ClickListener() {
                                            @Override
                                            public void clicked(InputEvent event, float x, float y) {;
                                                money = money - cost_of_upgrades[mm];
                                                switch(mm){
                                                    case 0:
                                                        if(farms[xx][yy].getLevelsOfUpgrades(mm) == 10){
                                                            farms[xx][yy].setUpgrades(mm,farms[xx][yy].getLevelsOfUpgrades(mm));
                                                        } else{
                                                            farms[xx][yy].setUpgrades(mm,farms[xx][yy].getLevelsOfUpgrades(mm)+1);
                                                        }
                                                        break;
                                                    case 1:
                                                        if(farms[xx][yy].getLevelsOfUpgrades(mm) == 30){
                                                            farms[xx][yy].setUpgrades(mm,farms[xx][yy].getLevelsOfUpgrades(mm));
                                                        } else{
                                                            farms[xx][yy].setUpgrades(mm,farms[xx][yy].getLevelsOfUpgrades(mm)+1);
                                                        }
                                                        break;
                                                    default:
                                                        farms[xx][yy].setUpgrades(mm,farms[xx][yy].getLevelsOfUpgrades(mm)+1);
                                                        break;
                                                }
                                                updateMoneyChanges(false);
                                                changingWindows(4,xx,yy);
                                            }
                                        });
                                        current_window_table.addActor(names_of_upgrades[m]);
                                        current_window_table.addActor(images_of_upgrades[m]);
                                        current_window_table.addActor(show_levels[m]);
                                        current_window_table.addActor(show_cost[m]);
                                        current_window_table.addActor(upgrading_buttons[m]);
                                    }

                                }
                                if(type ==2){
                                    images_of_upgrades[0] = new Image(package_tx);
                                    images_of_upgrades[1] = new Image(promotions_tx);
                                    images_of_upgrades[2] = new Image(advertisement_tx);
                                    for(int m = 0; m<3; m++){
                                        names_of_upgrades[m].setText(cities[xx][yy].getUpgradeName(m));
                                        images_of_upgrades[m].setSize(scaleCalculating(240),scaleCalculating(240));
                                        images_of_upgrades[m].setPosition(scaleCalculating(40),scaleCalculating(1870 - 310*m));
                                        switch(m){
                                            case 0:
                                                if(cities[xx][yy].getLevelsOfUpgrades(m) == 30){
                                                    show_levels[m].setText("lvl. MAX");
                                                } else{
                                                    show_levels[m].setText("lvl. "+ cities[xx][yy].getLevelsOfUpgrades(m));
                                                }
                                                break;
                                            case 1:
                                                if(cities[xx][yy].getLevelsOfUpgrades(m) == 10){
                                                    show_levels[m].setText("lvl. MAX");
                                                } else{
                                                    show_levels[m].setText("lvl. "+ cities[xx][yy].getLevelsOfUpgrades(m));
                                                }
                                                break;
                                            default:
                                                show_levels[m].setText("lvl. "+ cities[xx][yy].getLevelsOfUpgrades(m));
                                                break;
                                        }
                                        if(show_levels[m].equals("lvl. MAX")){
                                            cost_of_upgrades[m] = 0;
                                        } else{
                                            cost_of_upgrades[m] = calculateMoneyForUpgrade(type, m, cities[xx][yy].getLevelsOfUpgrades(m));
                                        }

                                        show_cost[m].setText("cost: "+numberScaling(cost_of_upgrades[m]));
                                        final int mm = m;
                                        upgrading_buttons[m].addListener(new ClickListener() {
                                            @Override
                                            public void clicked(InputEvent event, float x, float y) {
                                                money = money - cost_of_upgrades[mm];
                                                switch(mm){
                                                    case 0:
                                                        if(cities[xx][yy].getLevelsOfUpgrades(mm) == 10){
                                                            cities[xx][yy].setUpgrades(mm,cities[xx][yy].getLevelsOfUpgrades(mm));
                                                        } else{
                                                            cities[xx][yy].setUpgrades(mm,cities[xx][yy].getLevelsOfUpgrades(mm)+1);
                                                        }
                                                        break;
                                                    case 1:
                                                        if(cities[xx][yy].getLevelsOfUpgrades(mm) == 30){
                                                            cities[xx][yy].setUpgrades(mm,cities[xx][yy].getLevelsOfUpgrades(mm));
                                                        } else{
                                                            cities[xx][yy].setUpgrades(mm,cities[xx][yy].getLevelsOfUpgrades(mm)+1);
                                                        }
                                                        break;
                                                    default:
                                                        cities[xx][yy].setUpgrades(mm,cities[xx][yy].getLevelsOfUpgrades(mm)+1);
                                                        break;
                                                }
                                                updateMoneyChanges(false);
                                                changingWindows(4,xx,yy);
                                            }
                                        });
                                        current_window_table.addActor(names_of_upgrades[m]);
                                        current_window_table.addActor(images_of_upgrades[m]);
                                        current_window_table.addActor(show_levels[m]);
                                        current_window_table.addActor(show_cost[m]);
                                        current_window_table.addActor(upgrading_buttons[m]);
                                    }
                                }

                            }
                            break;
                        case 5:
                            top_info_text.setText("Build");
                            current_window_table.addActor(top_info_text);
                            break;
                        case 6:
                            top_info_text.setText("Reputation");
                            current_window_table.addActor(top_info_text);
                            break;

                }
            }
            current_window_table.addActor(top_info_text);
            stage2.addActor(current_window_table);
        }
    }

    public double calculateMoneyForUpgrade(int type, int position, int level){
        double result = 0;

        if (type == 1) {
            switch(position){
                case 0:
                    result = 5000 * Math.pow(2.5, level);
                    break;
                case 1:
                    result = 5000 * Math.pow(1.4, level);
                    break;
                default:
                    int a = (level+1)%10;
                    result = Math.pow(1.05,(level-a))*Math.pow(1.5,(a))*1000 ;
                    break;
            }
        }
        if (type == 2) {
            switch(position){
                case 0:
                    result = 10000 * Math.pow(1.4, level);
                    break;
                case 1:
                    result = 6000 * Math.pow(2.5, level);
                    break;
                case 2:
                    result = 2000 + 150*level;
                    break;
            }
        }
        return result;
    }

    public void updateIncomesResources(boolean isNewMove){
        countIncomeResources = new double[]{0,0,0,0,0};
        countLossesResources = new double[]{0,0,0,0,0};
            for(int i = 0; i<current_size_x; i++){
                for(int l = 0; l<current_size_y; l++){
                    if(current_map[i][l][0] == 1 && current_map[i][l][1] == 1){
                        for(int m = 0; m<5; m++) {
                            countIncomeResources[m] = countIncomeResources[m] + farms[i][l].getAllContractInfo(m,2,3);
                        }
                    }
                    if(current_map[i][l][0] == 1 && current_map[i][l][1] == 2){

                        for(int m = 0; m<5; m++) {
                            double[] count;
                            if(isNewMove){
                                count = calculatingNeededResources(m,cities[i][l].getAllContractInfo(m,2,3));
                            }
                            else {
                                count = calculatingNeededResources(m,cities[i][l].getAllContractInfo(m,2,1));
                            }

                            for(int n = 0; n<5; n++) {
                                countLossesResources[n] = countLossesResources[n]+ count[n];
                            }

                        }
                    }
                }
            }
        for(int n = 0; n<5; n++) {
            countIncomeResources[n] = countIncomeResources[n] -  countLossesResources[n];
        }
    }

    public void updateStorageInventory(){
            for(int n = 0; n<5; n++) {
                storage.updateStorage(n,countIncomeResources[n], true);
            }
    }

    public double productCountCalculating(int type){
        double max = 0;
        if(check_button.isChecked()){
            switch(type) {
                case 0:
                    max = countIncomeResources[1]/2;
                    break;
                case 1:
                    max = countIncomeResources[2]/2;
                    break;
                case 2:
                    max = countIncomeResources[0]/3;
                    break;
                case 3:
                    max = countIncomeResources[4]*2;
                    break;
                case 4:
                    max = countIncomeResources[3];
                    break;
            }

        } else{
            switch(type) {
                case 0:
                    max = storage.returnCountResource(1)/2;
                    break;
                case 1:
                    max = storage.returnCountResource(2)/2;
                    break;
                case 2:
                    max = storage.returnCountResource(0)/3;
                    break;
                case 3:
                    max = storage.returnCountResource(4)*2;
                    break;
                case 4:
                    max = storage.returnCountResource(3);
                    break;
            }
        }

        return max;
    }

    public double resourceCountCalculating(int type, int x, int y){
        double max;
        if(check_button.isChecked()){
            max = farms[x][y].getIncomes(type);
        }else{
            max = farms[x][y].getResourceCounts(type);
        }
        return max;
    }

    public boolean checkPossibilityOfContract(){
        int count = 0;
        for(int i = 0; i<3; i++){
            if(show_needed_resources[i][0].getText().contains("!")){
                count++;
            }
            if(show_needed_resources[i][1].getText().contains("!")){
                count++;
            }
        }
        if(count>0){
            return false;
        } else
        return true;
    }

    public double[][] checkResourcesInStorage(){
        double[][] result = new double[3][2];
        result[0][0] = money;
        result[1][0] = storage.returnCountResource(0);
        result[2][0] = storage.returnCountResource(1);
        result[0][1] = storage.returnCountResource(2);
        result[1][1] = storage.returnCountResource(3);
        result[2][1] = storage.returnCountResource(4);
        return result;
    }

    public double[][] checkResourcesInIncomes(){
        double[][] result = new double[3][2];
        result[0][0] = money;
        result[1][0] = countIncomeResources[0];
        result[2][0] = countIncomeResources[1];
        result[0][1] = countIncomeResources[2];
        result[1][1] = countIncomeResources[3];
        result[2][1] = countIncomeResources[4];
        return result;
    }

    public void acceptContract(int type, int x, int y, int time, int num){
            if(type == 1){
                if(check_button.isChecked()) {
                    farms[x][y].signContract(sliders_numbers[num] * farms[x][y].getSellMultiplier(num), sliders_numbers[num], time, num,1);
                } else{
                    farms[x][y].signContract(sliders_numbers[num] * farms[x][y].getSellMultiplier(num), sliders_numbers[num], 1, num,2);
                }
                farms[x][y].updating(false);
            }

            if(type == 2){
                if(checkPossibilityOfContract()){
                    double[][] res;
                    res = productToResources(num,sliders_numbers[num],false,true);
                    if(check_button.isChecked()) {
                        cities[x][y].signContract(res[num][0],sliders_numbers[num],res[num][1], res[num][2], res[num][3], time, num,1);

                    } else{
                        //cities[x][y].addProduct(num, sliders_numbers[num]);
                        cities[x][y].signContract(res[num][0],sliders_numbers[num],res[num][1], res[num][2], res[num][3], 1, num,2);

                }
                    cities[x][y].updating(false);
            }
        }
    }

    public double[] calculatingNeededResources(int type, double product){

        double[] res = new double[5];
        switch (type) {
            case 0:
                res = new double[]{0 * product, 2 * product, 2 * product, 2 * product, 0 * product};
                break;
            case 1:
                res = new double[]{1 * product, 0 * product, 2 * product, 2 * product, 0 * product};
                break;
            case 2:
                res = new double[]{3 * product, 0 * product, 0 * product, 0 * product, 0 * product};
                break;
            case 3:
                res = new double[]{2 * product, 0 * product, 0 * product, 2 * product, 0.5 * product};
                break;
            case 4:
                res = new double[]{3 * product, 0 * product, 0 * product, 1 * product, 0 * product};
                break;
        }
        return res;
    }

    public double farmPriceCalculating(int x, int y){
        double res = 0;
        for(int i = 0; i<5; i++){
                res = res + sliders_numbers[i]*farms[x][y].getSellMultiplier(i);
        }

        return res;
    }

    public double[][] productToResources(int type, double multiplier, boolean isFirst, boolean isAcceptContract){
        double[] res = new double[4];
        double[][] r = new double[5][4];
        switch (type) {
            case 0:
                res = new double[]{5 * multiplier, 2 * multiplier, 2 * multiplier, 2 * multiplier};
                break;
            case 1:
                res = new double[]{3 * multiplier, 1 * multiplier, 2 * multiplier, 2 * multiplier};
                break;
            case 2:
                res = new double[]{1 * multiplier, 3 * multiplier, 0 * multiplier, 0 * multiplier};
                break;
            case 3:
                res = new double[]{5 * multiplier, 2 * multiplier, 0.5 * multiplier, 2 * multiplier};
                break;
            case 4:
                res = new double[]{3 * multiplier, 3 * multiplier, 1 * multiplier, 0 * multiplier};
                break;
        }
        if(isAcceptContract){
            r[type][0] = res[0];
            r[type][1] = res[1];
            r[type][2] = res[2];
            r[type][3] = res[3];
            return r;
        }
        else
        {
            result[type][0] = res[0];
            result[type][1] = res[1];
            result[type][2] = res[2];
            result[type][3] = res[3];
            if(isFirst) {
                res = new double[]{0, 0, 0, 0};
                for (int i = 0; i < 5; i++) {
                    result[i][0] = res[0];
                    result[i][1] = res[1];
                    result[i][2] = res[2];
                    result[i][3] = res[3];
                }

            }
            return result;
        }
    }

    public double[][] calculatingResources(double[][] resources){
        double[][] res = new double[][] {{0,0},{0,0},{0,0}};
        res[0][0] = 0 + resources[0][0] + resources[1][0] + resources[2][0] + resources[3][0] + resources[4][0];
        res[1][0] = 0 + resources[1][1] + resources[2][1] + resources[3][1] + resources[4][1];
        res[2][0] = 0 + resources[0][2];
        res[0][1] = 0 + resources[0][1] + resources[1][2];
        res[1][1] = 0 + resources[0][3] + resources[1][3] + resources[3][3] + resources[4][2];
        res[2][1] = 0 + resources[3][2];
        return res;
    }

    public LayoutGameScreen(final ProfitTaker game, float width, float height, boolean isNew, int size, int save) {
        super(game);

        camera = new OrthographicCamera(width, height);
        camera.update();

        FillViewport viewport = new FillViewport(width, height, camera);
        stage = new Stage(viewport);

        camera2 = new OrthographicCamera(width, height);
        camera2.update();
        FillViewport viewport2 = new FillViewport(width, height, camera2);
        stage2 = new Stage(viewport2);

        multiplexer.addProcessor(gestureDetector);
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage2);
        multiplexer.addProcessor(stage);

        multiplexer2.addProcessor(stage2);
        multiplexer.addProcessor(stage);

        staticHeight = height;
        staticWidth = width;

        sliderStyle = builder.createSlider(scaleCalculating(1));

        checkBoxStyle = builder.createCheckBox(scaleCalculating(1));

        font1 = builder.createFont((int)scaleCalculating(100));
        font2 = builder.createFont((int)scaleCalculating(120));
        font3 = builder.createFont((int)scaleCalculating(150));
        font4 = builder.createFont((int)scaleCalculating(200));

        current_window_table.setSize(scaleCalculating(2164), scaleCalculating(2680));
        current_window_table.top();
        current_window_table.setPosition(scaleCalculating(520), scaleCalculating(70));

        current_vertical_table.setSize(scaleCalculating(300), scaleCalculating(1990));
        current_vertical_table.top();
        current_vertical_table.setPosition(scaleCalculating(123), scaleCalculating(70));
        // 350 /// 2180
        current_horizontal_table.setPosition(scaleCalculating(480), scaleCalculating(2842));
        current_horizontal_table.setSize(scaleCalculating(3730), scaleCalculating(300));
        // 350 /// 1120 /// 3730
        current_center_table.setPosition(stage.getWidth()/2 -  scaleCalculating(1640),stage.getHeight()/2 -  scaleCalculating(1150));
        current_center_table.setSize(scaleCalculating(3280), scaleCalculating(2500));

        labelStyle1.font = font1;
        labelStyle2.font = font2;
        labelStyle3.font = font3;
        labelStyle4.font = font4;

        for(int i = 0; i<3; i++){
            for(int l = 0; l<2; l++){
                if(l == 0){
                    cell_textures[i][l] = new Texture(Gdx.files.internal("cells/cell_"+i+"_down.png"));

                } else{
                    cell_textures[i][l] = new Texture(Gdx.files.internal("cells/cell_"+i+"_up.png"));
                }
                cell_drawables[i][l] = new TextureRegionDrawable(new TextureRegion(cell_textures[i][l]));
            }
        }
        empty_hill_tx = new Texture(Gdx.files.internal("empties/hill_empty_1.png"));
        city_hill_tx = new Texture(Gdx.files.internal("cities/hill_city_1.png"));
        farm_hill_tx_1 = new Texture(Gdx.files.internal("farms/hill_farm_1.png"));
        farm_hill_tx_2 = new Texture(Gdx.files.internal("farms/hill_farm_2.png"));

        empty_hill_dr = new TextureRegionDrawable(new TextureRegion(empty_hill_tx));
        city_hill_dr = new TextureRegionDrawable(new TextureRegion(city_hill_tx));
        farm_hill_dr_1 = new TextureRegionDrawable(new TextureRegion(farm_hill_tx_1));
        farm_hill_dr_2 = new TextureRegionDrawable(new TextureRegion(farm_hill_tx_2));

        for(int i = 1; i<8; i++){
            farm_textures[i-1] = new Texture(Gdx.files.internal("farms/farm_"+i+".png"));
            farm_drawables[i-1] = new TextureRegionDrawable(new TextureRegion(farm_textures[i-1]));
            if(i<7){
                city_textures[i-1] = new Texture(Gdx.files.internal("cities/city_"+i+".png"));
                empty_textures[i-1] = new Texture(Gdx.files.internal("empties/empty_"+i+".png"));

                city_drawables[i-1] = new TextureRegionDrawable(new TextureRegion(city_textures[i-1]));
                empty_drawables[i-1] = new TextureRegionDrawable(new TextureRegion(empty_textures[i-1]));
            }
        }

        base_table_design_tx = new Texture(Gdx.files.internal("backgrounds/"+size+"_background.png"));
        top_table_design_tx = new Texture(Gdx.files.internal("backgrounds/"+size+"_background_top.png"));
        bottom_table_design_tx = new Texture(Gdx.files.internal("backgrounds/"+size+"_background_bottom.png"));

        texture3 = new Texture(Gdx.files.internal("hexagon_hitbox.png"));

        hitbox_tx = new Texture(Gdx.files.internal("hexagon_hitbox_normal.png"));
        settings_menu_tx = new Texture(Gdx.files.internal("settings_menu.png"));

        next_move_button_tx  = new Texture("next_move_button.png");
        next_move_button_tx_pressed= new Texture("next_move_button_pressed.png");

        yes_button_tx  = new Texture("yes_button.png");
        yes_button_tx_pressed= new Texture("yes_button_pressed.png");

        no_button_tx  = new Texture("no_button.png");
        no_button_tx_pressed= new Texture("no_button_pressed.png");

        horizontal_info_closed_tx = new Texture("top_panel_closed.png");
        horizontal_info_small_tx = new Texture("top_panel_piece.png");
        horizontal_info_full_tx = new Texture("top_panel_full.png");
        vertical_info_closed_tx = new Texture("vertical_info_small.png");
        vertical_info_full_tx = new Texture("vertical_info_full.png");
        vertical_l_tx = new Texture("vertical_line.png");
        horizontal_l_tx = new Texture("horizontal_line.png");

        move_tx = new Texture("move_label.png");
        money_tx = new Texture("money_label.png");
        income_tx = new Texture("income_label.png");
        storage_tx = new Texture("storage_label.png");

        milk_tx = new Texture("milk_image.png");
        wheat_tx = new Texture("wheat_image.png");
        egg_tx = new Texture("egg_image.png");
        sugar_tx = new Texture("sugar_image.png");
        fruits_tx = new Texture("fruits_image.png");
        empty_slot_tx = new Texture("empty_slot_image.png");

        cake_tx = new Texture("cake_image.png");
        bread_tx = new Texture("bread_image.png");
        bisquit_tx = new Texture("bisquit_image.png");
        dessert_tx = new Texture("dessert_image.png");
        bun_tx = new Texture("bun_image.png");

        landscape_tx = new Texture("landscape_image.png");
        humidity_tx = new Texture("humidity_image.png");
        population_tx = new Texture("population_image.png");
        happiness_tx = new Texture("happiness_image.png");
        movement_tx = new Texture("movement_image.png");
        reputation_tx = new Texture("reputation_image.png");

        city_tx = new Texture("city_image.png");
        farm_tx = new Texture("farm_image.png");
        empty_tx = new Texture("empty_image.png");

        arrow_left_button_tx = new Texture("arrow_left_button.png");
        arrow_right_button_tx = new Texture("arrow_right_button.png");
        arrow_up_button_tx = new Texture("arrow_up_button.png");
        done_button_tx = new Texture("done_button.png");

        arrow_left_button_tx_pressed = new Texture("arrow_left_button_pressed.png");
        arrow_right_button_tx_pressed = new Texture("arrow_right_button_pressed.png");
        arrow_up_button_tx_pressed = new Texture("arrow_up_button_pressed.png");
        done_button_tx_pressed = new Texture("done_button_pressed.png");

        settings_tx = new Texture("settings_button.png");
        window_tx = new Texture("window.png");

        windmill_tx = new Texture("windmill_image.png");
        apartments_tx = new Texture("apartments_image.png");
        scarecrow_tx = new Texture("scarecrow_image.png");
        hay_tx = new Texture("hay_image.png");
        coop_tx = new Texture("coop_image.png");
        evaporator_tx = new Texture("evaporator_image.png");
        fertilizer_tx = new Texture("fertilizer_image.png");

        package_tx = new Texture("package_image.png");
        promotions_tx = new Texture("promotions_image.png");
        advertisement_tx = new Texture("advertisement_image.png");

        upgrade_button_tx = new Texture("button_upgrade.png");
        upgrade_button_tx_pressed = new Texture("button_upgrade_pressed.png");

        game_over_tx = new Texture("game_over_image.png");

        Drawable next_move_dr = new TextureRegionDrawable(new TextureRegion(next_move_button_tx));
        Drawable next_move_dr_pressed = new TextureRegionDrawable(new TextureRegion(next_move_button_tx_pressed));

        Drawable settings_dr = new TextureRegionDrawable(new TextureRegion(settings_tx));
        Drawable settings_dr_pressed = new TextureRegionDrawable(new TextureRegion(settings_tx));

        horizontal_info_closed = new Image(horizontal_info_closed_tx);
        horizontal_info_small = new Image(horizontal_info_small_tx);
        horizontal_info_full = new Image(horizontal_info_full_tx);
        vertical_info_closed = new Image(vertical_info_closed_tx);
        vertical_info_full = new Image(vertical_info_full_tx);

        horizontal_info_closed.setPosition(scaleCalculating(70), scaleCalculating(2813));
        horizontal_info_small.setPosition(scaleCalculating(70), scaleCalculating(2813));
        horizontal_info_full.setPosition(scaleCalculating(70), scaleCalculating(2813));
        vertical_info_closed.setPosition(scaleCalculating(90), scaleCalculating(1998));
        vertical_info_full.setPosition(scaleCalculating(90), scaleCalculating(70));

        horizontal_info_closed.setSize(scaleCalculating(763), scaleCalculating(363));
        horizontal_info_small.setSize(scaleCalculating(1721), scaleCalculating(363));
        horizontal_info_full.setSize(scaleCalculating(4877), scaleCalculating(363));
        vertical_info_closed.setSize(scaleCalculating(363), scaleCalculating(758));
        vertical_info_full.setSize(scaleCalculating(363), scaleCalculating(2686));

        createMap(size);
        createCells(isNew,save);

        window = new Image(window_tx);
        window.setSize(scaleCalculating(2164), scaleCalculating(2686));
        window.setPosition(scaleCalculating(520), scaleCalculating(70));

        settings_menu = new Image(settings_menu_tx);
        settings_menu.setSize(scaleCalculating(1931), scaleCalculating(1161));
        settings_menu.setPosition(current_center_table.getWidth()/2-scaleCalculating(1931)/2,current_center_table.getHeight()/2-scaleCalculating(1161)/2);

        game_over_screen = new Image(game_over_tx);
        game_over_screen.setSize(scaleCalculating(7200), scaleCalculating(3240));
        game_over_screen.setPosition(stage2.getWidth()/2 - game_over_screen.getWidth()/2, scaleCalculating(0));

        settings_background = new Image(hitbox_tx);
        settings_background.setSize(scaleCalculating(7200), scaleCalculating(3240));
        settings_background.setPosition(stage2.getWidth()/2 - game_over_screen.getWidth()/2, scaleCalculating(0));

        addingToHorizontalTable(3);
        addingToVerticalTable(2);
        updateMoneyChanges(false);

        settings_button = new ImageButton(settings_dr, settings_dr_pressed);
        settings_button.setPosition(scaleCalculating(137), scaleCalculating(2854));
        settings_button.setSize(scaleCalculating(280), scaleCalculating(280));
        settings_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addSettingsMenu();
                stage2.addActor(current_center_table);
            }
        });
        stage2.addActor(settings_button);

        next_move_button = new ImageButton(next_move_dr, next_move_dr_pressed);
        next_move_button.setPosition(stage2.getWidth()-scaleCalculating(510), scaleCalculating(81));
        next_move_button.setSize(scaleCalculating(419), scaleCalculating(363));
        next_move_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                move = move + 1;
                updateIncomesResources(true);
                updateStorageInventory();
                boolean isBreakingContract = false;
                for(int i = 0; i<5; i++){
                    if(storage.returnCountResource(i)<0){
                        isBreakingContract = true;
                        storage.updateStorage(i, -storage.returnCountResource(i), true);
                    }
                }
                for(int i = 0; i<current_size_x; i++){
                    for(int l = 0; l<current_size_y; l++){
                        if(current_map[i][l][0] == 1){
                            int type = current_map[i][l][1];
                            switch(type){
                                case 1:
                                    farms[i][l].updateIncomes();
                                    farms[i][l].updating(true);
                                    farms[i][l].updateContracts();
                                    farms[i][l].updating(false);
                                    break;
                                case 2:
                                    if(isBreakingContract){
                                        cities[i][l].resetContracts();
                                    }
                                    cities[i][l].updateIncomes();
                                    cities[i][l].updating(true);
                                    cities[i][l].updateContracts();
                                    cities[i][l].updating(false);
                            }
                        }
                    }
                }
                updateIncomesResources(true);
                money = money + absoluteIncome;
                updateMoneyChanges(true);
                horizontal_numbers[0] = new Label(String.valueOf(move), labelStyle3);
                horizontal_numbers[1] = new Label(numberScaling(money), labelStyle3);
                horizontal_numbers[2] = new Label(numberScaling(absoluteIncome), labelStyle3);
                horizontal_numbers[3] = new Label(numberScaling(storage.returnAllResources()), labelStyle3);

                show_move.setText(String.valueOf(move));
                changingWindows(1,current_x,current_y);

            }
        });
        stage2.addActor(next_move_button);

    }

    public void createMap(int size){
        Drawable hitbox_dr = new TextureRegionDrawable(new TextureRegion(hitbox_tx));

        int spacing = 0;
        sizeOfMap = size;
        switch(size){
            case 1:
                current_size_x = 13;
                current_size_y = 6;
                money_multiplier = 200;
                scale_multiplier = 1.06;
                for(int i = 0; i<current_size_x; i++){
                    for(int l = 0; l<current_size_y; l++){
                        current_map[i][l] = map_1[i][l];
                    }
                }

                break;
            case 2:
                current_size_x = 17;
                current_size_y = 7;
                money_multiplier = 280;
                scale_multiplier = 1.065;
                for(int i = 0; i<current_size_x; i++){
                    for(int l = 0; l<current_size_y; l++){
                        current_map[i][l] = map_2[i][l];
                    }
                }
                spacing = 402;
                break;


        }
        base_table_design = new Image(base_table_design_tx);
        base_table_design.setSize(scaleCalculating(base_table_design_tx.getWidth()), scaleCalculating(base_table_design_tx.getHeight()));
        base_table_design.setPosition(stage.getWidth()/2-base_table_design.getWidth()/2, scaleCalculating(-1000));
        stage.addActor(base_table_design);

        top_table_design = new Image(top_table_design_tx);
        top_table_design.setSize(scaleCalculating(top_table_design_tx.getWidth()), scaleCalculating(top_table_design_tx.getHeight()));
        top_table_design.setPosition(stage.getWidth()/2-base_table_design.getWidth()/2, base_table_design.getY()+base_table_design.getHeight()-top_table_design.getHeight()+scaleCalculating(115));
        stage.addActor(top_table_design);

        for(int i = 0; i<current_size_x; i++){
            for(int l = 0; l<current_size_y; l++){
                if(current_map[i][l][0]==1){
                    switch(current_map[i][l][1]){
                        case 1:
                            cells_arr[i][l]= new Image(cell_textures[1][0]);
                            break;
                        case 2:
                            cells_arr[i][l]= new Image(cell_textures[2][0]);
                            break;
                        default:
                            cells_arr[i][l]= new Image(cell_textures[0][0]);
                            break;
                    }
                    cells_arr[i][l].setSize(scaleCalculating(540),scaleCalculating(623));

                    if(i%2 == 1){
                        cells_arr[i][l].setPosition(base_table_design.getX()+scaleCalculating((int)base_table_design_tx.getWidth()-347 -l*786 - 393-spacing), scaleCalculating((int)base_table_design_tx.getHeight()- 1620-i*227));
                    }else{
                        cells_arr[i][l].setPosition(base_table_design.getX()+scaleCalculating((int)base_table_design_tx.getWidth()-347 -l*786-spacing), scaleCalculating((int)base_table_design_tx.getHeight()-1620-i*227));
                    }
                    stage.addActor(cells_arr[i][l]);
                }else{
                    cells_arr[i][l] = new Image();
                }
            }
        }

        bottom_table_design = new Image(bottom_table_design_tx);
        bottom_table_design.setSize(scaleCalculating(bottom_table_design_tx.getWidth()), scaleCalculating(bottom_table_design_tx.getHeight()));
        bottom_table_design.setPosition(stage.getWidth()/2- base_table_design.getWidth()/2, base_table_design.getY()+base_table_design.getHeight()-top_table_design.getHeight()+scaleCalculating(115));
        stage.addActor(bottom_table_design);

        int num = 0;
        final Random random = new Random();
        int farmCount = 0, cityCount = 0;
        for(int i = 0; i<current_size_x; i++){
            for(int l = 0; l<current_size_y; l++){
                if(current_map[i][l][0]==1){
                    switch(current_map[i][l][1]){
                        case 0:
                            switch (current_map[i][l][2]) {
                                case 0:
                                    top_arr[i][l] = new ImageButton(hitbox_dr);
                                    break;
                                case 1:
                                    int number = random.nextInt(2);
                                    int pos = random.nextInt(6);
                                    if(number == 1){
                                        top_arr[i][l] = new ImageButton(empty_drawables[pos]);
                                    } else{
                                        top_arr[i][l] = new ImageButton(hitbox_dr);
                                    }
                                    break;
                                case 2:
                                    top_arr[i][l] = new ImageButton(empty_hill_dr);
                                    break;
                            }

                            break;
                        case 1:

                            if(current_map[i][l][2] == 2){
                                if(num == 0){
                                    top_arr[i][l]= new ImageButton(farm_hill_dr_1);
                                }else{
                                    top_arr[i][l]= new ImageButton(farm_hill_dr_2);
                                }
                                num++;
                            }else{
                                top_arr[i][l]= new ImageButton(farm_drawables[farmCount],farm_drawables[farmCount]);
                            }

                            farmCount= farmCount+1;
                            break;
                        case 2:
                            if(current_map[i][l][2] ==2){
                                top_arr[i][l]= new ImageButton(city_hill_dr, city_hill_dr);
                            }else {
                                top_arr[i][l]= new ImageButton(city_drawables[cityCount], city_drawables[cityCount]);
                            }

                            cityCount = cityCount+1;
                            break;

                    }
                    if(i%2 == 1){
                        top_arr[i][l].setPosition(base_table_design.getX()+scaleCalculating((int)base_table_design_tx.getWidth()-250 -l*786 - 397-spacing), scaleCalculating((int)base_table_design_tx.getHeight()- 1460-i*227));
                    }else{
                        top_arr[i][l].setPosition(base_table_design.getX()+scaleCalculating((int)base_table_design_tx.getWidth()-250 -l*786-spacing), scaleCalculating((int)base_table_design_tx.getHeight()- 1460-i*227));
                    }
                    final int ii = i;
                    final int ll = l;
                    top_arr[i][l].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            current_x = ii;
                            current_y = ll;
                            if(previous_x == ii && previous_y == ll){
                                cell_is_using = true;
                                moveCells(previous_x,previous_y,ii,ll);
                                addingToVerticalTable(2);
                                changingWindows(1,ii,ll);
                                previous_x = 0;
                                previous_y = 0;
                            } else {
                                cell_is_using = false;
                                moveCells(previous_x,previous_y,ii,ll);
                                addingToVerticalTable(1);
                                changingWindows(1,ii,ll);
                                previous_x = ii;
                                previous_y = ll;
                            }

                        }
                    });
                }else{
                    top_arr[i][l] = new ImageButton(hitbox_dr,hitbox_dr);
                }
                top_arr[i][l].setSize(scaleCalculating(350),scaleCalculating(350));
                stage.addActor(top_arr[i][l]);

            }
        }
    }

    public void createCells(boolean isNew, int save){
        int farmCount = 0, cityCount = 0;
        if(isNew){
            for(int i = 0; i<current_size_x; i++){
                for(int l = 0; l<current_size_y; l++){
                    if(current_map[i][l][0]==1) {
                        switch (current_map[i][l][1]) {
                            case 0:
                                cells[i][l] = new EmptyCell(Math.random(), current_map[i][l][2], "Empty cell");
                                break;
                            case 1:

                                switch (farmCount) {
                                    case 0:
                                        farms[i][l] = new FarmCell(0.78, current_map[i][l][2], "New Daves");
                                        break;
                                    case 1:
                                        farms[i][l] = new FarmCell(0.97, current_map[i][l][2], "Orlando");
                                        break;
                                    case 2:
                                        farms[i][l] = new FarmCell(1.12, current_map[i][l][2], "Heraldes");
                                        break;
                                    case 3:
                                        farms[i][l] = new FarmCell(1.03, current_map[i][l][2], "Charrue");
                                        break;
                                    case 4:
                                        farms[i][l] = new FarmCell(0.81, current_map[i][l][2], "Fuego");
                                        break;
                                    case 5:
                                        farms[i][l] = new FarmCell(0.72, current_map[i][l][2], "Regardo");
                                        break;
                                }
                                farms[i][l].updateIncomes();
                                farmCount = farmCount + 1;
                                break;
                            case 2:
                                switch (cityCount) {
                                    case 0:
                                        cities[i][l] = new CityCell(0.48, current_map[i][l][2], "Polito");
                                        break;
                                    case 1:
                                        cities[i][l] = new CityCell(0.60, current_map[i][l][2], "Wales");
                                        break;
                                    case 2:
                                        cities[i][l] = new CityCell(1.02, current_map[i][l][2], "Tham");
                                        break;
                                    case 3:
                                        cities[i][l] = new CityCell(0.80, current_map[i][l][2], "Rill");
                                        break;
                                    case 4:
                                        cities[i][l] = new CityCell(0.91, current_map[i][l][2], "Podarwa");
                                        break;
                                    case 5:
                                        cities[i][l] = new CityCell(0.44, current_map[i][l][2], "Iglecia");
                                        break;
                                }
                                cities[i][l].updateIncomes();
                                cityCount = cityCount + 1;
                                break;

                        }
                    }
                }
            }
        } else{
            cells = saver.getEmpties(save);
            farms = saver.getFarms(save);
            cities = saver.getCities(save);

            double[] stats = saver.getStats(save);
            move = (int)stats[0];
            money = stats[1];
            countOfContracts = (int)stats[5];
            for(int i = 0; i<5; i++){
                storage.updateStorage(i,stats[6+i], true);
            }
        }

    }

    public void addSettingsMenu(){
        current_center_table.reset();
        final Texture leave_button_tx = new Texture(Gdx.files.internal("leave_button.png"));
        final Texture leave_button_tx_pressed = new Texture(Gdx.files.internal("leave_button_pressed.png"));

        final Texture saves_button_tx = new Texture(Gdx.files.internal("saves_button.png"));
        final Texture saves_button_tx_pressed = new Texture(Gdx.files.internal("saves_button_pressed.png"));

        final Texture resume_button_tx = new Texture(Gdx.files.internal("resume_button.png"));
        final Texture resume_button_tx_pressed = new Texture(Gdx.files.internal("resume_button_pressed.png"));

        Drawable leave_button_dr = new TextureRegionDrawable(new TextureRegion(leave_button_tx));
        Drawable leave_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(leave_button_tx_pressed));

        Drawable saves_button_dr = new TextureRegionDrawable(new TextureRegion(saves_button_tx));
        Drawable saves_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(saves_button_tx_pressed));

        Drawable resume_button_dr = new TextureRegionDrawable(new TextureRegion(resume_button_tx));
        Drawable resume_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(resume_button_tx_pressed));

        final CheckBox autosave_check = new CheckBox("",checkBoxStyle);
        autosave_check.setPosition(current_center_table.getWidth()/2 - scaleCalculating(470), current_center_table.getHeight()/2 - scaleCalculating(490));
        autosave_check.setSize(scaleCalculating(200), scaleCalculating(200));
        autosave_check.setChecked(autosave_is_enabled);
        autosave_check.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                if(autosave_check.isChecked()){
                    autosave_is_enabled = true;
                } else{
                    autosave_is_enabled = false;
                }
            }
        });

        final Label autosave = new Label("autosave", labelStyle3);
        autosave.setPosition(current_center_table.getWidth()/2 - scaleCalculating(250), current_center_table.getHeight()/2 - scaleCalculating(450));
        autosave.setSize(scaleCalculating(720), scaleCalculating(120));

        final ImageButton leave_button = new ImageButton(leave_button_dr, leave_button_dr_pressed);
        leave_button.setPosition(current_center_table.getWidth()/2 - scaleCalculating(780), current_center_table.getHeight()/2 - scaleCalculating(250));
        leave_button.setSize(scaleCalculating(400), scaleCalculating(400));
        leave_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addLeaveMenu();
            }
        });

        final ImageButton saves_button = new ImageButton(saves_button_dr, saves_button_dr_pressed);
        saves_button.setPosition(current_center_table.getWidth()/2 - scaleCalculating(200), current_center_table.getHeight()/2 - scaleCalculating(250));
        saves_button.setSize(scaleCalculating(400), scaleCalculating(400));
        saves_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addSavesMenu();
            }
        });

        final ImageButton resume_button = new ImageButton(resume_button_dr,resume_button_dr_pressed);
        resume_button.setPosition(current_center_table.getWidth()/2 + scaleCalculating(380), current_center_table.getHeight()/2 - scaleCalculating(250));
        resume_button.setSize(scaleCalculating(400), scaleCalculating(400));
        resume_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings_background.remove();
                current_center_table.remove();
            }
        });
        stage2.addActor(settings_background);
        current_center_table.addActor(settings_menu);
        current_center_table.addActor(autosave_check);
        current_center_table.addActor(leave_button);
        current_center_table.addActor(saves_button);
        current_center_table.addActor(resume_button);
        current_center_table.addActor(autosave);
    }

    public void addLeaveMenu(){
        final Texture leave_menu_tx = new Texture(Gdx.files.internal("leave_menu.png"));

        Drawable yes_dr = new TextureRegionDrawable(new TextureRegion(yes_button_tx));
        Drawable yes_dr_pressed = new TextureRegionDrawable(new TextureRegion(yes_button_tx_pressed));

        Drawable no_dr = new TextureRegionDrawable(new TextureRegion(no_button_tx));
        Drawable no_dr_pressed = new TextureRegionDrawable(new TextureRegion(no_button_tx_pressed));

        leave_menu = new Image(leave_menu_tx);
        leave_menu.setSize(scaleCalculating(2450), scaleCalculating(918));
        leave_menu.setPosition(current_center_table.getWidth()/2-scaleCalculating(1225),current_center_table.getHeight()/2-scaleCalculating(459));

        yes_button = new ImageButton(yes_dr, yes_dr_pressed);
        yes_button.setPosition(current_center_table.getWidth()/2 - scaleCalculating(755), current_center_table.getHeight()/2 - scaleCalculating(350));
        yes_button.setSize(scaleCalculating(300), scaleCalculating(300));
        yes_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leave_menu.remove();
                yes_button.remove();
                no_button.remove();
                current_center_table.remove();
                current_center_table.reset();
                if(autosave_is_enabled){
                    final double[] stats = {move, money, sizeOfMap, current_size_x, current_size_y, countOfContracts, storage.returnCountResource(0), storage.returnCountResource(1),storage.returnCountResource(2),storage.returnCountResource(3),storage.returnCountResource(4)};
                    saver.saving(0,stats, current_map,cells, farms, cities);
                }
                game.setScreen(game.menuScreen);


            }
        });

        no_button = new ImageButton(no_dr, no_dr_pressed);
        no_button.setPosition(current_center_table.getWidth()/2 + scaleCalculating(455), current_center_table.getHeight()/2 - scaleCalculating(350));
        no_button.setSize(scaleCalculating(300), scaleCalculating(300));
        no_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leave_menu.remove();
                yes_button.remove();
                no_button.remove();
            }
        });
        current_center_table.addActor(leave_menu);
        current_center_table.addActor(yes_button);
        current_center_table.addActor(no_button);
    }

    public void addSavesMenu(){
        final Texture back_button_tx = new Texture(Gdx.files.internal("back_button.png"));
        final Texture back_button_tx_pressed = new Texture(Gdx.files.internal("back_button_pressed.png"));
        final Texture save_button_tx_pressed = new Texture(Gdx.files.internal("save_button_pressed.png"));

        Drawable back_button_dr = new TextureRegionDrawable(new TextureRegion(back_button_tx));
        Drawable back_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(back_button_tx_pressed));

        Drawable save_button_dr = new TextureRegionDrawable(new TextureRegion(hitbox_tx));
        Drawable save_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(save_button_tx_pressed));

        final Image[] big_horizontal_lines = new Image[4];
        final Image[][] current_images = new Image[5][3];
        final Label[][] current_labels = new Label[5][4];
        final ImageButton[] current_buttons = new ImageButton[5];

        for(int m = 0; m<4; m++){
            big_horizontal_lines[m] = new Image(horizontal_l_tx);
            big_horizontal_lines[m].setSize(scaleCalculating(3000), scaleCalculating(12));
        }
        big_horizontal_lines[0].setPosition(scaleCalculating(140), scaleCalculating(1518));
        big_horizontal_lines[1].setPosition(scaleCalculating(140), scaleCalculating(1198));
        big_horizontal_lines[2].setPosition(scaleCalculating(140), scaleCalculating(878));
        big_horizontal_lines[3].setPosition(scaleCalculating(140), scaleCalculating(558));

        final Texture saves_menu_tx = new Texture(Gdx.files.internal("saves_menu.png"));

        final Image saves_menu = new Image(saves_menu_tx);
        saves_menu.setSize(scaleCalculating(3280), scaleCalculating(2300));
        saves_menu.setPosition(0,0);

        current_center_table.addActor(saves_menu);

        final ImageButton back_button = new ImageButton(back_button_dr, back_button_dr_pressed);
        back_button.setSize(scaleCalculating(400), scaleCalculating(160));
        back_button.setPosition(current_center_table.getWidth()/2-scaleCalculating(200),scaleCalculating(90));
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for(int i = 0; i<5; i++){
                    current_labels[i][0].remove();
                    current_labels[i][1].remove();
                    current_labels[i][2].remove();
                    current_labels[i][3].remove();

                    current_images[i][0].remove();
                    current_images[i][1].remove();
                    current_images[i][2].remove();

                    if(i<4){
                        big_horizontal_lines[i].remove();
                    }
                    current_buttons[i].remove();
                }
                back_button.remove();
                saves_menu.remove();
            }
        });

        for(int m = 0; m<5; m++) {
            current_images[m][0] = new Image(move_tx);
            current_images[m][1] = new Image(money_tx);
            current_images[m][2] = new Image(landscape_tx);

            current_images[m][0].setSize(scaleCalculating(200), scaleCalculating(200));
            current_images[m][1].setSize(scaleCalculating(200), scaleCalculating(200));
            current_images[m][2].setSize(scaleCalculating(200), scaleCalculating(200));

            current_images[m][0].setPosition(scaleCalculating(700), scaleCalculating(1574 - (320 * m)));
            current_images[m][1].setPosition(scaleCalculating(1380), scaleCalculating(1574 - (320 * m)));
            current_images[m][2].setPosition(scaleCalculating(2345), scaleCalculating(1574 - (320 * m)));

            for (int l = 0; l < 4; l++) {
                current_labels[m][l] = new Label("", labelStyle3);
            }
            if (saver.checkIsEmpty(m + 1)) {
                for (int l = 1; l < 4; l++) {
                    current_labels[m][l].setText("----");
                }
            } else {
                current_labels[m][0].setText("Save " + (m + 1));

                double[] stats = saver.getStats(m+1);
                current_labels[m][1].setText(String.valueOf((int)stats[0]));
                current_labels[m][2].setText(numberScaling(stats[1]));
                switch((int)stats[2]){
                    case 1:
                        current_labels[m][3].setText("Small");
                        break;
                    case 2:
                        current_labels[m][3].setText("Medium");
                        break;
                    case 3:
                        current_labels[m][3].setText("Large");
                        break;
                }
            }
            current_labels[m][0].setSize(scaleCalculating(530), scaleCalculating(160));
            current_labels[m][1].setSize(scaleCalculating(480), scaleCalculating(160));
            current_labels[m][2].setSize(scaleCalculating(760), scaleCalculating(160));
            current_labels[m][3].setSize(scaleCalculating(760), scaleCalculating(160));

            current_labels[m][0].setPosition(scaleCalculating(120), scaleCalculating(1594 - (320 * m)));
            current_labels[m][1].setPosition(scaleCalculating(940), scaleCalculating(1594 - (320 * m)));
            current_labels[m][2].setPosition(scaleCalculating(1620), scaleCalculating(1594 - (320 * m)));
            current_labels[m][3].setPosition(scaleCalculating(2580), scaleCalculating(1594 - (320 * m)));

            current_buttons[m] = new ImageButton(save_button_dr, save_button_dr_pressed);
            current_buttons[m].setSize(scaleCalculating(3100), scaleCalculating(220));
            current_buttons[m].setPosition(scaleCalculating(70),scaleCalculating(1564- (320 * m)));

            final int position = m+1;
            final double[] stats = {move, money, sizeOfMap, current_size_x, current_size_y, countOfContracts, storage.returnCountResource(0), storage.returnCountResource(1),storage.returnCountResource(2),storage.returnCountResource(3),storage.returnCountResource(4)};

            current_buttons[m].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    saver.saving(position,stats, current_map,cells, farms, cities);
                    for(int i = 0; i<5; i++){
                        current_labels[i][0].remove();
                        current_labels[i][1].remove();
                        current_labels[i][2].remove();
                        current_labels[i][3].remove();

                        current_images[i][0].remove();
                        current_images[i][1].remove();
                        current_images[i][2].remove();

                        if(i<4){
                            big_horizontal_lines[i].remove();
                        }
                        current_buttons[i].remove();
                    }
                    back_button.remove();
                    saves_menu.remove();
                }
            });

            current_center_table.addActor(current_labels[m][0]);
            current_center_table.addActor(current_labels[m][1]);
            current_center_table.addActor(current_labels[m][2]);
            current_center_table.addActor(current_labels[m][3]);

            current_center_table.addActor(current_images[m][0]);
            current_center_table.addActor(current_images[m][1]);
            current_center_table.addActor(current_images[m][2]);
            if(m<4){
                current_center_table.addActor(big_horizontal_lines[m]);
            }

            current_center_table.addActor(current_buttons[m]);
        }

        current_center_table.addActor(back_button);

    }

    public void sleep(int fps) {
        if(fps>0){
            diff = System.currentTimeMillis() - start;
            long targetDelay = 1000/fps;
            if (diff < targetDelay) {
                try{
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException e) {}
            }
            start = System.currentTimeMillis();
        }
    }

    public void moveCells(int prev_x, int prev_y, int x, int y){
        switch(current_map[prev_x][prev_y][1]){
            case 1:
                cells_arr[prev_x][prev_y].setDrawable(cell_drawables[1][0]);
                break;
            case 2:
                cells_arr[prev_x][prev_y].setDrawable(cell_drawables[2][0]);
                break;
            default:
                cells_arr[prev_x][prev_y].setDrawable(cell_drawables[0][0]);
                break;
        }
        top_arr[prev_x][prev_y].setPosition(top_arr[prev_x][prev_y].getX(), top_arr[prev_x][prev_y].getY() - scaleCalculating(62));
        window.remove();

        if(!cell_is_using) {
            switch(current_map[x][y][1]){
                case 1:
                    cells_arr[x][y].setDrawable(cell_drawables[1][1]);
                    break;
                case 2:
                    cells_arr[x][y].setDrawable(cell_drawables[2][1]);
                    break;
                default:
                    cells_arr[x][y].setDrawable(cell_drawables[0][1]);
                    break;
            }
            top_arr[x][y].setPosition(top_arr[x][y].getX(), top_arr[x][y].getY() + scaleCalculating(62));
            stage2.addActor(window);
        }
        //current_window_table.setColor(0,0,0,0.4f);
    }

    public void updateMoneyChanges(boolean isNewMove){
        absoluteLosses = money_multiplier*Math.pow(scale_multiplier, move-1);
        absoluteProfit = 0;
        absoluteIncome = 0;

        for(int i = 0; i<current_size_x; i++) {
            for (int l = 0; l < current_size_y; l++) {
                if (current_map[i][l][0] == 1) {
                    if (current_map[i][l][1] == 1) {
                        farms[i][l].updateIncomes();
                        if(!isNewMove){
                            farms[i][l].updating(false);
                        }
                        absoluteLosses = absoluteLosses + farms[i][l].getControlLosses();
                    }
                    if (current_map[i][l][1] == 2) {
                        cities[i][l].updateIncomes();
                        if(!isNewMove){
                            cities[i][l].updating(false);
                        }
                        absoluteLosses = absoluteLosses + cities[i][l].getControlLosses();
                        absoluteProfit = absoluteProfit + cities[i][l].getControlProfit();
                    }
                }
            }
        }

        absoluteIncome = absoluteProfit - absoluteLosses;
        addingToHorizontalTable(sizeOfHorizontalTable);
        if(money <0){
            cell_is_using = true;
            moveCells(previous_x,previous_y,previous_x,previous_y);

            gameOver();

        }
    }

    public void gameOver(){
        stage2.addActor(game_over_screen);
        current_center_table.reset();

        final Texture leave_button_tx = new Texture(Gdx.files.internal("big_leave_button.png"));
        final Texture leave_button_tx_pressed = new Texture(Gdx.files.internal("big_leave_button_pressed.png"));

        final Texture handshake_tx = new Texture("handshake_im.png");

        Drawable leave_button_dr = new TextureRegionDrawable(new TextureRegion(leave_button_tx));
        Drawable leave_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(leave_button_tx_pressed));

        final Image[] current_images = new Image[3];
        final Label[] current_labels = new Label[3];

        final Label name_table = new Label("Stats:", labelStyle4);
        name_table.setAlignment(Align.center);
        current_images[0] = new Image(move_tx);
        current_images[1] = new Image(landscape_tx);
        current_images[2] = new Image(handshake_tx);

        current_images[0].setSize(scaleCalculating(200), scaleCalculating(200));
        current_images[1].setSize(scaleCalculating(200), scaleCalculating(200));
        current_images[2].setSize(scaleCalculating(200), scaleCalculating(200));

        current_images[0].setPosition(current_center_table.getWidth() / 2 - scaleCalculating(1100), scaleCalculating(1180));
        current_images[1].setPosition(current_center_table.getWidth() / 2 - scaleCalculating(410), scaleCalculating(1180));
        current_images[2].setPosition(current_center_table.getWidth() / 2 + scaleCalculating(670), scaleCalculating(1180));

        String s = "";
        switch (sizeOfMap) {
            case 1:
                s = "Small";
                break;
            case 2:
                s = "Medium";
                break;
            case 3:
                s = "Large";
                break;
        }

        final ImageButton back_button = new ImageButton(leave_button_dr, leave_button_dr_pressed);
        back_button.setSize(scaleCalculating(900), scaleCalculating(900));
        back_button.setPosition(current_center_table.getWidth()/2-scaleCalculating(450),scaleCalculating(100));
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.menuScreen);
                current_labels[0].remove();
                current_labels[1].remove();

                current_images[0].remove();
                current_images[1].remove();
                current_images[2].remove();

                back_button.remove();
                name_table.remove();
            }
        });

        int[] stats = {move, sizeOfMap, countOfContracts};
        saver.setToLeaderboard(stats);

        current_labels[0] = new Label(String.valueOf(move),labelStyle3);
        current_labels[1] = new Label(s,labelStyle3);
        current_labels[2] = new Label(String.valueOf(countOfContracts),labelStyle3);

        current_labels[0].setSize(scaleCalculating(600), scaleCalculating(180));
        current_labels[1].setSize(scaleCalculating(600), scaleCalculating(180));
        current_labels[2].setSize(scaleCalculating(800), scaleCalculating(180));
        name_table.setSize(scaleCalculating(800), scaleCalculating(220));

        current_labels[0].setPosition(current_center_table.getWidth() / 2 - scaleCalculating(880), scaleCalculating(1190));
        current_labels[1].setPosition(current_center_table.getWidth() / 2 - scaleCalculating(160), scaleCalculating(1190));
        current_labels[2].setPosition(current_center_table.getWidth() / 2 + scaleCalculating(900), scaleCalculating(1190));
        name_table.setPosition(current_center_table.getWidth() / 2 - name_table.getWidth()/2, scaleCalculating(1552));

        current_center_table.addActor(current_labels[0]);
        current_center_table.addActor(current_labels[1]);
        current_center_table.addActor(current_labels[2]);

        current_center_table.addActor(current_images[0]);
        current_center_table.addActor(current_images[1]);
        current_center_table.addActor(current_images[2]);

        current_center_table.addActor(name_table);
        current_center_table.addActor(back_button);
        stage2.addActor(current_center_table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sleep(60);
        stage.draw();
        stage.getCamera().update();
        stage2.draw();
        stage2.getCamera().update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        base_table_design_tx.dispose();
        texture3.dispose();
        no_button_tx.dispose();
        no_button_tx_pressed.dispose();
        yes_button_tx.dispose();
        yes_button_tx_pressed.dispose();
        next_move_button_tx.dispose();
        next_move_button_tx_pressed.dispose();

        stage.dispose();
        stage2.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX();
        float y = Gdx.input.getDeltaY();
        camera.translate(-x*3f*camera.zoom*scaleCalculating(1),y*3f*camera.zoom*scaleCalculating(1));
        camera2.translate(-x*2f,y*2f);
        current_vertical_table.setPosition( current_vertical_table.getX()-x*2f,  current_vertical_table.getY() + y*2f);
        current_horizontal_table.setPosition(current_horizontal_table.getX()-x*2f, current_horizontal_table.getY() + y*2f);
        current_window_table.setPosition(current_window_table.getX()-x*2f, current_window_table.getY() + y*2f);
        current_center_table.setPosition(current_center_table.getX()-x*2f, current_center_table.getY() + y*2f);
        settings_button.setPosition(settings_button.getX()-x*2f, settings_button.getY() + y*2f);
        next_move_button.setPosition(next_move_button.getX()-x*2f, next_move_button.getY() + y*2f);

        horizontal_info_full.setPosition(horizontal_info_full.getX()-x*2f, horizontal_info_full.getY() + y*2f);
        horizontal_info_small.setPosition(horizontal_info_small.getX()-x*2f, horizontal_info_small.getY() + y*2f);
        horizontal_info_closed.setPosition(horizontal_info_closed.getX()-x*2f, horizontal_info_closed.getY() + y*2f);

        vertical_info_full.setPosition(vertical_info_full.getX()-x*2f, vertical_info_full.getY() + y*2f);
        vertical_info_closed.setPosition(vertical_info_closed.getX()-x*2f, vertical_info_closed.getY() + y*2f);

        game_over_screen.setPosition(game_over_screen.getX()-x*2f, game_over_screen.getY() + y*2f);
        settings_background.setPosition(settings_background.getX()-x*2f, settings_background.getY() + y*2f);

        window.setPosition(window.getX()-x*2f, window.getY() + y*2f);

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchDown(float v, float v1, int i, int i1) {
        return false;
    }

    @Override
    public boolean tap(float v, float v1, int i, int i1) {
        return false;
    }

    @Override
    public boolean longPress(float v, float v1) {
        return false;
    }

    @Override
    public boolean fling(float v, float v1, int i) {
        return false;
    }

    @Override
    public boolean pan(float v, float v1, float v2, float v3) {
        return false;
    }

    @Override
    public boolean panStop(float v, float v1, int i, int i1) {
        return false;
    }

    @Override
    public boolean zoom(float v, float v1) {

        camera.zoom = camera.zoom+ (v-v1)*0.00004f;

        if(camera.zoom >=1.5f){
            camera.zoom =1.5f;
        }
        if(camera.zoom <=0.5f){
            camera.zoom =0.5f;
        }
        return true;
    }

    @Override
    public boolean pinch(Vector2 vector2, Vector2 vector21, Vector2 vector22, Vector2 vector23) {

        return false;
    }

    @Override
    public void pinchStop() {

    }
}

