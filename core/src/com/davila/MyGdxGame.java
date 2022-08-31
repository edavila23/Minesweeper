package com.davila;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
