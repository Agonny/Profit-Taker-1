package com.profit_taker.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public  class ProfitTaker extends Game {

	public MainScreen menuScreen;
	public NewGameChoosing gameChoosingScreen;
	public LoadGameChoosing loadGameChoosingScreen;
	public LayoutGameScreen layoutGameScreen;
	public LeaderboardScreen leaderboardScreen;

	@Override
	public void create() {
		float[] size = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};

		menuScreen = new MainScreen(this, size[0], size[1]);

		gameChoosingScreen = new NewGameChoosing(this, size[0], size[1]);

		setScreen(menuScreen);

	}

	public void recreateLayoutScreen(boolean isNew, int map, int save){
		float[] size = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};
		layoutGameScreen = new LayoutGameScreen(this, size[0], size[1], isNew, map,save);
	}
	public void recreateSavesScreen(){
		float[] size = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};
		loadGameChoosingScreen = new LoadGameChoosing(this, size[0], size[1]);
	}
	public void recreateLeaderboardScreen(){
		float[] size = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};
		leaderboardScreen = new LeaderboardScreen(this, size[0], size[1]);
	}
	public void recreateGameChosingScreen(){
		float[] size = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};
		gameChoosingScreen = new NewGameChoosing(this, size[0], size[1]);
	}

}
