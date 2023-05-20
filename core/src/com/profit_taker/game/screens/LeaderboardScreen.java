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
import com.profit_taker.game.saves.PreferencesSaver;
import com.profit_taker.game.styles.StyleBuilder;

public class LeaderboardScreen extends GameScreen{

    private final Stage stage;

    private final Texture back_button_tx, back_button_tx_pressed, leaderboard_tx, move_tx, landscape_tx, handshake_tx, horizontal_l_tx;

    private final Image leaderboard_table;

    public float staticHeight;

    StyleBuilder builder = new StyleBuilder();

    public float scaleCalculating(int a){
        return a *(staticHeight/1080f);
    }

    public LeaderboardScreen(final ProfitTaker game, float width, float height) {
        super(game);
        stage = new Stage(new FillViewport(width,height));

        staticHeight = height;

        back_button_tx = new Texture("back_button_main.png");
        back_button_tx_pressed = new Texture("back_button_main_pressed.png");

        horizontal_l_tx = new Texture("horizontal_line.png");

        move_tx = new Texture("move_label.png");
        handshake_tx = new Texture("handshake_im.png");
        landscape_tx = new Texture("landscape_image.png");

        leaderboard_tx = new Texture("leaderboard_"+game.menuScreen.type+".png");

        Drawable back_button_dr = new TextureRegionDrawable(new TextureRegion(back_button_tx));
        Drawable back_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(back_button_tx_pressed));

        PreferencesSaver saver = new PreferencesSaver();

        BitmapFont font0 = builder.createFont((int)scaleCalculating(70));
        BitmapFont font1 = builder.createFont((int)scaleCalculating(100));

        Label.LabelStyle labelStyle0 = new Label.LabelStyle();
        Label.LabelStyle labelStyle1 = new Label.LabelStyle();

        labelStyle0.font = font0;
        labelStyle1.font = font1;

        final Image[] big_horizontal_lines = new Image[5];
        final Image[][] current_images = new Image[5][3];
        final Label[][] current_labels = new Label[5][4];
        final Label name_table = new Label("Leaderboard", labelStyle1);

        leaderboard_table = new Image(leaderboard_tx);
        leaderboard_table.setSize(scaleCalculating(1530), scaleCalculating(977));
        leaderboard_table.setPosition(stage.getWidth()/2-scaleCalculating(765),scaleCalculating(52));

        stage.addActor(leaderboard_table);

        name_table.setAlignment(Align.center);
        name_table.setSize(scaleCalculating(700), scaleCalculating(120));
        name_table.setPosition(stage.getWidth()/2-scaleCalculating(350), scaleCalculating(880));

        final ImageButton back_button = new ImageButton(back_button_dr, back_button_dr_pressed);
        back_button.setSize(scaleCalculating(200), scaleCalculating(55));
        back_button.setPosition(stage.getWidth()/2-scaleCalculating(100),scaleCalculating(100));
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.menuScreen);
                for(int i = 0; i<5; i++){
                    current_labels[i][0].remove();
                    current_labels[i][1].remove();
                    current_labels[i][2].remove();
                    current_labels[i][3].remove();

                    current_images[i][0].remove();
                    current_images[i][1].remove();
                    current_images[i][2].remove();

                    big_horizontal_lines[i].remove();

                }
                back_button.remove();
                leaderboard_table.remove();
                name_table.remove();
            }
        });
        int position = 1;
        for(int m = 0; m<5; m++) {
            current_images[m][0] = new Image(move_tx);
            current_images[m][1] = new Image(landscape_tx);
            current_images[m][2] = new Image(handshake_tx);

            current_images[m][0].setSize(scaleCalculating(85), scaleCalculating(85));
            current_images[m][1].setSize(scaleCalculating(85), scaleCalculating(85));
            current_images[m][2].setSize(scaleCalculating(85), scaleCalculating(85));

            current_images[m][0].setPosition(stage.getWidth() / 2 - scaleCalculating(470), scaleCalculating(747 - (132 * m)));
            current_images[m][1].setPosition(stage.getWidth() / 2 - scaleCalculating(60), scaleCalculating(747 - (132 * m)));
            current_images[m][2].setPosition(stage.getWidth() / 2 + scaleCalculating(360), scaleCalculating(747 - (132 * m)));

            big_horizontal_lines[m] = new Image(horizontal_l_tx);
            big_horizontal_lines[m].setSize(scaleCalculating(1430), scaleCalculating(7));
            big_horizontal_lines[m].setPosition(stage.getWidth()/2-scaleCalculating(715), scaleCalculating(720 - 132*m));

            for (int l = 0; l < 4; l++) {
                current_labels[m][l] = new Label("-", labelStyle0);
            }
            int[][] stats = saver.getFromLeaderboard();

            if (stats[m][0] == 0) {
                current_labels[m][0].setText("-");
                for (int l = 1; l < 4; l++) {
                    current_labels[m][l].setText("----");
                }

            } else {
                current_labels[m][0].setText(position);
                position++;

                current_labels[m][1].setText(""+stats[m][0]);
                current_labels[m][3].setText(""+stats[m][2]);
                switch (stats[m][1]) {
                    case 1:
                        current_labels[m][2].setText("Small");
                        break;
                    case 2:
                        current_labels[m][2].setText("Medium");
                        break;
                    case 3:
                        current_labels[m][2].setText("Large");
                        break;
                }
            }

            current_labels[m][0].setSize(scaleCalculating(250), scaleCalculating(75));
            current_labels[m][1].setSize(scaleCalculating(270), scaleCalculating(75));
            current_labels[m][2].setSize(scaleCalculating(410), scaleCalculating(75));
            current_labels[m][3].setSize(scaleCalculating(300), scaleCalculating(75));

            current_labels[m][0].setPosition(stage.getWidth() / 2 - scaleCalculating(650), scaleCalculating(752 - (132 * m)));
            current_labels[m][1].setPosition(stage.getWidth() / 2 - scaleCalculating(370), scaleCalculating(752 - (132 * m)));
            current_labels[m][2].setPosition(stage.getWidth() / 2 + scaleCalculating(40), scaleCalculating(752 - (132 * m)));
            current_labels[m][3].setPosition(stage.getWidth() / 2 + scaleCalculating(460), scaleCalculating(752 - (132 * m)));

            stage.addActor(current_labels[m][0]);
            stage.addActor(current_labels[m][1]);
            stage.addActor(current_labels[m][2]);
            stage.addActor(current_labels[m][3]);

            stage.addActor(current_images[m][0]);
            stage.addActor(current_images[m][1]);
            stage.addActor(current_images[m][2]);

            stage.addActor(big_horizontal_lines[m]);

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

        back_button_tx.dispose();
        back_button_tx_pressed.dispose();

        stage.dispose();
        super.dispose();
    }

}
