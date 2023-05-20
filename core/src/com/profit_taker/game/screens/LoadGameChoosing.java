package com.profit_taker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.profit_taker.game.styles.StyleBuilder;
import com.profit_taker.game.saves.PreferencesSaver;

import java.text.DecimalFormat;

public class LoadGameChoosing extends GameScreen{

    private final Stage stage;

    private final Texture back_button_tx, back_button_tx_pressed, save_table_tx, save_button_tx, save_button_tx_pressed, horizontal_l_tx;
    private final Texture move_tx, money_tx, landscape_tx;

    private final Image save_table;

    public float staticHeight;

    PreferencesSaver saver = new PreferencesSaver();

    StyleBuilder builder = new StyleBuilder();

    BitmapFont font0, font1;

    Label.LabelStyle labelStyle0 = new Label.LabelStyle();
    Label.LabelStyle labelStyle1 = new Label.LabelStyle();

    public float scaleCalculating(int a){
        return a *(staticHeight/1080f);
    }

    public String numberScaling(double a){
        String format = new DecimalFormat("#0.00").format(a);
        String str = format;
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

    public LoadGameChoosing(final ProfitTaker game, float width, float height) {
        super(game);
        stage = new Stage(new FillViewport(width,height));

        staticHeight = height;

        back_button_tx = new Texture(Gdx.files.internal("back_button_main.png"));
        back_button_tx_pressed = new Texture(Gdx.files.internal("back_button_main_pressed.png"));

        save_button_tx= new Texture(Gdx.files.internal("hexagon_hitbox_normal.png"));
        save_button_tx_pressed = new Texture(Gdx.files.internal("loadsave_button_pressed.png"));

        horizontal_l_tx = new Texture("horizontal_line.png");

        move_tx = new Texture("move_label.png");
        money_tx = new Texture("money_label.png");
        landscape_tx = new Texture("landscape_image.png");

        save_table_tx = new Texture("saves_"+game.menuScreen.type+".png");

        font0 = builder.createFont((int)scaleCalculating(70));
        font1 = builder.createFont((int)scaleCalculating(100));

        labelStyle0.font = font0;
        labelStyle1.font = font1;

        Drawable back_button_dr = new TextureRegionDrawable(new TextureRegion(back_button_tx));
        Drawable back_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(back_button_tx_pressed));

        Drawable save_button_dr = new TextureRegionDrawable(new TextureRegion(save_button_tx));
        Drawable save_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(save_button_tx_pressed));

        final Image[] big_horizontal_lines = new Image[6];
        final Image[][] current_images = new Image[6][3];
        final Label[][] current_labels = new Label[6][4];
        final ImageButton[] current_buttons = new ImageButton[6];
        final Label name_table = new Label("Saves", labelStyle1);
        name_table.setAlignment(Align.center);
        name_table.setSize(scaleCalculating(400), scaleCalculating(120));
        name_table.setPosition(stage.getWidth()/2-scaleCalculating(200), scaleCalculating(880));


        for(int m = 0; m<6; m++){
            big_horizontal_lines[m] = new Image(horizontal_l_tx);
            big_horizontal_lines[m].setSize(scaleCalculating(1600), scaleCalculating(7));
            big_horizontal_lines[m].setPosition(stage.getWidth()/2-scaleCalculating(800), scaleCalculating(731 - 110*m));
        }

        save_table = new Image(save_table_tx);
        save_table.setSize(scaleCalculating(1742), scaleCalculating(977));
        save_table.setPosition(stage.getWidth()/2-scaleCalculating(871),scaleCalculating(52));

        stage.addActor(save_table);

        final ImageButton back_button = new ImageButton(back_button_dr, back_button_dr_pressed);
        back_button.setSize(scaleCalculating(200), scaleCalculating(55));
        back_button.setPosition(stage.getWidth()/2-scaleCalculating(100),scaleCalculating(100));
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.menuScreen);
                for(int i = 0; i<6; i++){
                    current_labels[i][0].remove();
                    current_labels[i][1].remove();
                    current_labels[i][2].remove();
                    current_labels[i][3].remove();

                    current_images[i][0].remove();
                    current_images[i][1].remove();
                    current_images[i][2].remove();

                    big_horizontal_lines[i].remove();

                    current_buttons[i].remove();
                }
                back_button.remove();
                save_table.remove();
            }
        });

        for(int m = 0; m<6; m++) {
            current_images[m][0] = new Image(move_tx);
            current_images[m][1] = new Image(money_tx);
            current_images[m][2] = new Image(landscape_tx);

            current_images[m][0].setSize(scaleCalculating(85), scaleCalculating(85));
            current_images[m][1].setSize(scaleCalculating(85), scaleCalculating(85));
            current_images[m][2].setSize(scaleCalculating(85), scaleCalculating(85));

            current_images[m][0].setPosition(stage.getWidth()/2-scaleCalculating(470), scaleCalculating(747 - (110 * m)));
            current_images[m][1].setPosition(stage.getWidth()/2-scaleCalculating(60), scaleCalculating(747 - (110 * m)));
            current_images[m][2].setPosition(stage.getWidth()/2+scaleCalculating(440), scaleCalculating(747 - (110 * m)));

            for (int l = 0; l < 4; l++) {
                current_labels[m][l] = new Label("", labelStyle0);
            }
            if (saver.checkIsEmpty(m)) {
                for (int l = 1; l < 4; l++) {
                    current_labels[m][l].setText("----");
                }

            } else {

                double[] stats = saver.getStats(m);
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
            if(m!=0){
                current_labels[m][0].setText("Save " + (m));
            } else{
                current_labels[m][0].setText("Autosave");
            }


            current_labels[m][0].setSize(scaleCalculating(350), scaleCalculating(75));
            current_labels[m][1].setSize(scaleCalculating(270), scaleCalculating(75));
            current_labels[m][2].setSize(scaleCalculating(410), scaleCalculating(75));
            current_labels[m][3].setSize(scaleCalculating(300), scaleCalculating(75));

            current_labels[m][0].setPosition(stage.getWidth()/2-scaleCalculating(800), scaleCalculating(752 - (110 * m)));
            current_labels[m][1].setPosition(stage.getWidth()/2-scaleCalculating(370), scaleCalculating(752 - (110 * m)));
            current_labels[m][2].setPosition(stage.getWidth()/2+scaleCalculating(40), scaleCalculating(752 - (110 * m)));
            current_labels[m][3].setPosition(stage.getWidth()/2+scaleCalculating(540), scaleCalculating(752 - (110 * m)));

            current_buttons[m] = new ImageButton(save_button_dr, save_button_dr_pressed);
            current_buttons[m].setSize(scaleCalculating(1620), scaleCalculating(90));
            current_buttons[m].setPosition(stage.getWidth()/2 -scaleCalculating(810),scaleCalculating(745- (110 * m)));

            final int mm = m;
            current_buttons[m].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(!saver.checkIsEmpty(mm)){
                        double[] stats = saver.getStats(mm);
                        game.recreateLayoutScreen(false, (int)stats[2], mm);
                        game.setScreen(game.layoutGameScreen);
                    }

                }
            });

            stage.addActor(current_labels[m][0]);
            stage.addActor(current_labels[m][1]);
            stage.addActor(current_labels[m][2]);
            stage.addActor(current_labels[m][3]);

            stage.addActor(current_images[m][0]);
            stage.addActor(current_images[m][1]);
            stage.addActor(current_images[m][2]);
            if(m<6){
                stage.addActor(big_horizontal_lines[m]);
            }

            stage.addActor(current_buttons[m]);
        }

        stage.addActor(back_button);
        stage.addActor(name_table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
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

        super.dispose();
    }

}
