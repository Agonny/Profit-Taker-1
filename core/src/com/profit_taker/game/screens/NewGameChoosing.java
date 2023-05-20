package com.profit_taker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class NewGameChoosing extends GameScreen {

    private final Stage stage;
    private final Texture small_button_tx, small_button_tx_pressed, medium_button_tx, medium_button_tx_pressed, large_button_tx, large_button_tx_pressed, chosing_logo_tx;

    ImageButton small_button, medium_button, large_button;

    public float staticHeight;

    public float scaleCalculating(int a){
        return a *(staticHeight/1080f);
    }

    public NewGameChoosing(final ProfitTaker game, float width, float height) {
        super(game);
        stage = new Stage(new FillViewport(width,height));

        staticHeight = height;

        small_button_tx = new Texture(Gdx.files.internal("small_button.png"));
        small_button_tx_pressed = new Texture(Gdx.files.internal("small_button_pressed.png"));

        medium_button_tx = new Texture(Gdx.files.internal("medium_button.png"));
        medium_button_tx_pressed = new Texture(Gdx.files.internal("medium_button_pressed.png"));

        large_button_tx = new Texture(Gdx.files.internal("large_button.png"));
        large_button_tx_pressed = new Texture(Gdx.files.internal("large_button_pressed.png"));

        chosing_logo_tx = new Texture(Gdx.files.internal("choosing_logo.png"));

        Drawable small_button_dr = new TextureRegionDrawable(new TextureRegion(small_button_tx));
        Drawable small_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(small_button_tx_pressed));

        Drawable medium_button_dr = new TextureRegionDrawable(new TextureRegion(medium_button_tx));
        Drawable medium_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(medium_button_tx_pressed));

        Drawable large_button_dr = new TextureRegionDrawable(new TextureRegion(large_button_tx));
        Drawable large_button_dr_pressed = new TextureRegionDrawable(new TextureRegion(large_button_tx_pressed));

        Image logo = new Image(chosing_logo_tx);
        logo.setSize(scaleCalculating(690), scaleCalculating(320));
        logo.setPosition(stage.getWidth()/2-scaleCalculating(345), scaleCalculating(720));
        stage.addActor(logo);

        small_button = new ImageButton(small_button_dr, small_button_dr_pressed);
        small_button.setSize(scaleCalculating(670),scaleCalculating(670));
        small_button.setPosition(stage.getWidth()/2-scaleCalculating(1000 - (2400-(int)stage.getWidth())/10), scaleCalculating(350));
        small_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.recreateLayoutScreen(true, 1, 0);
                game.setScreen(game.layoutGameScreen);
            }
        });
        stage.addActor(small_button);

        medium_button = new ImageButton(medium_button_dr, medium_button_dr_pressed);
        medium_button.setSize(scaleCalculating(670),scaleCalculating(670));
        medium_button.setPosition(stage.getWidth()/2-scaleCalculating(335), scaleCalculating(20));
        medium_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.recreateLayoutScreen(true, 2,0);
                game.setScreen(game.layoutGameScreen);
            }
        });
        stage.addActor(medium_button);

        large_button = new ImageButton(large_button_dr, large_button_dr_pressed);
        large_button.setSize(scaleCalculating(670),scaleCalculating(670));
        large_button.setPosition(stage.getWidth()/2 + scaleCalculating(330- (2400-(int)stage.getWidth())/10), scaleCalculating(350));
        large_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.recreateLayoutScreen(true, 3,0);
                game.setScreen(game.layoutGameScreen);
            }
        });
        stage.addActor(large_button);

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
        stage.dispose();
        super.dispose();
    }

}
