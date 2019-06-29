package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.personaje.Indiana;
import com.mygdx.game.personaje.Meta;
import com.mygdx.game.personaje.Obstaculo;
import com.mygdx.game.personaje.Plataforma;
import com.mygdx.game.personaje.Salida;
import com.mygdx.game.personaje.Vida;
import com.mygdx.game.util.Constants;


public class Level {

    public static final String TAG = Level.class.getName();
    public Viewport viewport;
    public Indiana indiana;
    public Array<Plataforma> plataformas;
    public DelayedRemovalArray<Obstaculo> obstaculos;
    public DelayedRemovalArray<Vida> vidas;
    public Salida salida;
    public Meta meta;
    public Constants.Difficulty difficulty;
    public boolean victoria;
    public boolean gameOver;

    public Level(Viewport viewport,Constants.Difficulty difficulty) {
        this.viewport = viewport;
        this.difficulty=difficulty;
        //viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        indiana = new Indiana(Constants.INDIANA_SPAWN, this);
        plataformas = new Array<Plataforma>();
        obstaculos=new DelayedRemovalArray<Obstaculo>();
        vidas=new DelayedRemovalArray<Vida>();

        victoria=false;
        gameOver=false;

    }


    public void update(float delta) {

        if(indiana.getVidas()<=0){
            gameOver=true;
        }else if (indiana.position.x>meta.position.x){

            victoria = true;
        }

        if(!gameOver && !victoria){
            indiana.update(delta, plataformas);
        }




    }

    public void render(SpriteBatch batch) {
        batch.begin();

        for (Plataforma plataform : plataformas) {
            plataform.render(batch);
        }

        salida.render(batch);

        meta.render(batch);

        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.render(batch);
        }

        for (Vida vida : vidas) {
            vida.render(batch);
        }


        indiana.render(batch);


        batch.end();
    }

    public Array<Plataforma> getPlataforma(){
        return plataformas;
    }

    public DelayedRemovalArray<Obstaculo> getObstaculos(){
        return obstaculos;
    }

    public Indiana getIndiana() {
        return indiana;
    }

    public Constants.Difficulty getDifficulty() {
        return difficulty;
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida){
        this.salida=salida;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta){
        this.meta=meta;
    }

    public DelayedRemovalArray<Vida> getVidas(){
        return vidas;
    }
}
