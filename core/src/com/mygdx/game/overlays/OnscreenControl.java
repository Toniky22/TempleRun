package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Level;
import com.mygdx.game.personaje.Indiana;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class OnscreenControl extends InputAdapter {

    public static final String TAG = OnscreenControl.class.getName();

    public final Viewport viewport;
    public Indiana indiana;
    private Vector2 jumpCenter;
    private int jumpPointer;

    public OnscreenControl(Level level) {
        this.viewport = new ExtendViewport(Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE);

        indiana=level.getIndiana();
        jumpCenter = new Vector2();

        recalculateButtonPositions();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(jumpCenter) < Constants.BUTTON_RADIUS) {

            jumpPointer = pointer;
            indiana.jumpButtonPressed = true;

        }

        return true;
    }


    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        if (!Gdx.input.isTouched(jumpPointer)) {
            indiana.jumpButtonPressed = false;
            jumpPointer = 0;
        }

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.jump,
                jumpCenter,
                Constants.BUTTON_CENTER
        );
        batch.end();
    }

    public void recalculateButtonPositions() {
        jumpCenter.set(viewport.getWorldWidth() - Constants.BUTTON_RADIUS * 3 / 4,Constants.BUTTON_RADIUS);
    }
}
