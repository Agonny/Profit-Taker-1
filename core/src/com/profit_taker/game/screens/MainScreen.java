package com.profit_taker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.profit_taker.game.saves.PreferencesSaver;

import java.util.Random;


public class MainScreen extends GameScreen{
    final float staticHeight;

    int type;

    private final Stage stage;

    private final Texture logo_tx, main_design_tx, play_button_tx, play_button_tx_pressed, loadgame_button_tx,loadgame_button_tx_pressed,leaderboard_button_tx, leaderboard_button_tx_pressed;

    private final Image text_logo, green_logo, yellow_logo, gray_logo, design;

    ImageButton newGameButton, loadGameButton, leaderboardButton;

    public float scaleCalculating(int a){
        return a *(staticHeight/1080f);
    }

    public MainScreen(final ProfitTaker game, float width, float height) {
        super(game);
        staticHeight = height;
        stage = new Stage(new FillViewport(width, height));

        play_button_tx = new Texture("play_button.png");
        play_button_tx_pressed = new Texture("play_button_pressed.png");

        leaderboard_button_tx = new Texture("leaderboard_button.png");
        leaderboard_button_tx_pressed = new Texture("leaderboard_button_pressed.png");

        loadgame_button_tx = new Texture("loadgame_button.png");
        loadgame_button_tx_pressed = new Texture("loadgame_button_pressed.png");

        Drawable new_game_button_dr = new TextureRegionDrawable(new TextureRegion(play_button_tx));
        Drawable new_game_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(play_button_tx_pressed));

        Drawable loadgame_button_dr = new TextureRegionDrawable(new TextureRegion(loadgame_button_tx));
        Drawable loadgame_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(loadgame_button_tx_pressed));

        Drawable leaderboard_button_dr = new TextureRegionDrawable(new TextureRegion(leaderboard_button_tx));
        Drawable leaderboard_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(leaderboard_button_tx_pressed));

        logo_tx = new Texture(Gdx.files.internal("logo.png"));
        main_design_tx = new Texture(Gdx.files.internal("main_design.png"));

        final Texture green_logo_tx = new Texture(Gdx.files.internal("green_logo.png"));
        final Texture yellow_logo_tx = new Texture(Gdx.files.internal("yellow_logo.png"));
        final Texture gray_logo_tx = new Texture(Gdx.files.internal("gray_logo.png"));

        design = new Image(main_design_tx);
        design.setSize(scaleCalculating(2400),scaleCalculating(1080));
        design.setPosition(stage.getWidth() / 2 - design.getWidth()/2,stage.getHeight() / 2 - design.getHeight()/2);

        text_logo = new Image(logo_tx);
        text_logo.setSize(scaleCalculating(1408),scaleCalculating(160));
        text_logo.setPosition(stage.getWidth() / 2 - text_logo.getWidth()/2,scaleCalculating(797));

        green_logo = new Image(green_logo_tx);
        green_logo.setSize(scaleCalculating(326),scaleCalculating(338));
        green_logo.setPosition(stage.getWidth() / 2 - green_logo.getWidth()/2,scaleCalculating(700));

        yellow_logo = new Image(yellow_logo_tx);
        yellow_logo.setSize(scaleCalculating(326),scaleCalculating(338));
        yellow_logo.setPosition(stage.getWidth() / 2 - yellow_logo.getWidth()/2,scaleCalculating(700) );

        gray_logo = new Image(gray_logo_tx);
        gray_logo.setSize(scaleCalculating(326),scaleCalculating(338));
        gray_logo.setPosition(stage.getWidth() / 2 - gray_logo.getWidth()/2,scaleCalculating(700));

        newGameButton = new ImageButton(new_game_button_dr, new_game_button_dr_pressed);
        newGameButton.setSize(scaleCalculating(500), scaleCalculating(450));
        newGameButton.setPosition(stage.getWidth() / 2 - newGameButton.getWidth()/2, scaleCalculating(175));

        loadGameButton = new ImageButton(loadgame_button_dr, loadgame_button_dr_pressed);
        loadGameButton.setPosition(stage.getWidth() / 2 - scaleCalculating(640), scaleCalculating(90));
        loadGameButton.setSize(scaleCalculating(340), scaleCalculating(300));

        leaderboardButton = new ImageButton(leaderboard_button_dr, leaderboard_button_dr_pressed);
        leaderboardButton.setPosition(stage.getWidth() / 2 + scaleCalculating(295), scaleCalculating(90));
        leaderboardButton.setSize(scaleCalculating(340), scaleCalculating(300));

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.recreateGameChosingScreen();
                game.setScreen(game.gameChoosingScreen);
            }
        });
        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.recreateSavesScreen();
                game.setScreen(game.loadGameChoosingScreen);
            }
        });
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.recreateLeaderboardScreen();
                game.setScreen(game.leaderboardScreen);
            }
        });
        stage.addActor(design);
        stage.addActor(text_logo);
        stage.addActor(newGameButton);
        stage.addActor(loadGameButton);
        stage.addActor(leaderboardButton);

        final Random random = new Random();
        type = random.nextInt(3);
        switch(type){
            case 0:
                stage.addActor(green_logo);
                break;
            case 1:
                stage.addActor(yellow_logo);
                break;
            case 2:
                stage.addActor(gray_logo);
                break;
        }

        PreferencesSaver saver = new PreferencesSaver();
        saver.checkFiles();
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
        logo_tx.dispose();
        play_button_tx.dispose();
        play_button_tx_pressed.dispose();

        stage.dispose();
        super.dispose();
    }
}
