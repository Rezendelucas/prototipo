package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Models.Assets;
import com.mygdx.game.Screens.MenuScreen;

import java.awt.Color;


public class FinukaMain extends Game {
	private static final java.lang.String Tag = FinukaMain.class.getName();

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		setScreen(new MenuScreen(this));
	}

}
