package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.util.Constants;


public class TempleRunGame extends Game {

    @Override
    public void create() {
        showDifficultyScreen();
    }


    public void showDifficultyScreen() {
        setScreen(new DifficultyScreen(this));
    }

    public void showTempleRunScreen(Constants.Difficulty difficulty) {
        setScreen(new TempleRunScreen(this, difficulty));
    }

}
