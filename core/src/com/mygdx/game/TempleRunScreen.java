package com.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.overlays.GameOver;
import com.mygdx.game.overlays.Hud;
import com.mygdx.game.overlays.OnscreenControl;
import com.mygdx.game.overlays.Victoria;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.ChaseCamera;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelLoader;
import com.mygdx.game.util.Utils;

public class TempleRunScreen extends ScreenAdapter {

    public static final String TAG = TempleRunScreen.class.getName();

    SpriteBatch batch;
    ExtendViewport viewport;
    TempleRunGame game;
    Constants.Difficulty difficulty;
    Level level;
    public ChaseCamera chaseCamera;
    long levelEndOverlayStartTime;
    Victoria victoria;
    GameOver gameOver;
    Hud hud;
    OnscreenControl onscreenControls;
    public int cont;


    public TempleRunScreen(TempleRunGame game, Constants.Difficulty difficulty) {
        this.game = game;
        this.difficulty = difficulty;
    }

    @Override
    public void show() {
        Assets.instance.init();
        batch=new SpriteBatch();
        viewport=new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        level=new Level(viewport,difficulty);
        chaseCamera = new ChaseCamera(viewport.getCamera(), level.getIndiana());
        level = LevelLoader.load("Level1", viewport,difficulty);
        victoria=new Victoria();
        gameOver=new GameOver();
        hud=new Hud();
        onscreenControls = new OnscreenControl(level);
        if (onMobile()) {
            Gdx.input.setInputProcessor(onscreenControls);
        }
        cont=1;
    }

    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        hud.viewport.update(width, height, true);
        victoria.viewport.update(width, height, true);
        gameOver.viewport.update(width, height, true);
        level.viewport.update(width, height, true);
        chaseCamera.camera=level.viewport.getCamera();
        chaseCamera.target=level.getIndiana();
        onscreenControls.viewport.update(width, height, true);
        onscreenControls.recalculateButtonPositions();

    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render(float delta) {
        level.update(delta);
        chaseCamera.update();
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        level.render(batch);
        if (onMobile()) {
            onscreenControls.render(batch);
        }
        hud.render(batch, level.getIndiana().getVidas(), difficulty, level.indiana.tiempo);
        renderLevelEndOverlays(batch);
    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (level.gameOver) {

            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
            }

            gameOver.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelFailed();
            }
        } else if (level.victoria) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
            }
            victoria.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                if(cont!=2){
                    cont++;
                    levelComplete();

                }else{
                    finish();
                }

            }
        }
    }

    private void starNewLevel() {

        LevelLoader levelLoader=new LevelLoader();
        level=levelLoader.load("Level2", viewport, difficulty);


        chaseCamera.camera = level.viewport.getCamera();
        chaseCamera.target = level.getIndiana();
        onscreenControls.indiana = level.getIndiana();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void levelComplete() {
        starNewLevel();
    }

    public void levelFailed(){
        game.showDifficultyScreen();
    }

    public void finish(){
        game.showDifficultyScreen();
    }


}
