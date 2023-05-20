package com.profit_taker.game.screens;

import com.badlogic.gdx.Screen;

public abstract class GameScreen implements Screen {
    public ProfitTaker game;

    public GameScreen(ProfitTaker game) {
        this.game=game;

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }

    @Override
    public void dispose() {

    }
}