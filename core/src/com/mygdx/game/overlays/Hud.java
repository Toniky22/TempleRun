package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TempleRunGame;
import com.mygdx.game.util.Constants;


public class Hud{

    public final Viewport viewport;
    final BitmapFont font;
    //public ShapeRenderer renderer;
    String dificultad;
    TempleRunGame game;
    public Rectangle rect;

    public Hud() {
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        //renderer=new ShapeRenderer();
        font.getData().setScale(1.5f);
        game=new TempleRunGame();
        //Gdx.input.setInputProcessor(this);
    }

    public void render(SpriteBatch batch, int lives, Constants.Difficulty difficulty, float tiempo) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        if (difficulty.getVelocity()==0.75){
            dificultad="Fácil";
        }else if(difficulty.getVelocity()==1){
            dificultad="Medio";
        }else if(difficulty.getVelocity()==3){
            dificultad="Difícil";
        }
        batch.begin();
        final String hudString =Constants.HUD_TIME_LABEL + Math.round(tiempo)+ "\n" + Constants.HUD_DIFFICULTY_LABEL + dificultad;

        font.draw(batch, hudString, Constants.HUD_MARGIN, viewport.getWorldHeight() - Constants.HUD_MARGIN);

        font.draw(batch,Constants.HUD_VIDAS_LABEL+lives, Constants.WORLD_SIZE, Constants.WORLD_SIZE-Constants.HUD_MARGIN);

        batch.end();

        /*renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Constants.BACKGROUND_COLOR);
        rect=new Rectangle(480,420,70,25);
        renderer.rect(rect.x,rect.y,rect.width,rect.height);
        renderer.end();

        batch.begin();

        font.draw(batch, "Volver", 500, 438, 0, Align.center, false);

        batch.end();*/
    }

    /*@Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 pos=viewport.unproject(new Vector2(screenX,screenY));
        if(rect.contains(pos)){
            Gdx.app.log("pincha","pincha");
            game.showDifficultyScreen();
        }

        return true;
    }*/


}
