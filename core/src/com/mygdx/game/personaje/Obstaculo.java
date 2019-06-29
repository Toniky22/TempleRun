package com.mygdx.game.personaje;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;

public class Obstaculo {

    public final float top;
    public final float bottom;
    public final float left;
    public final float right;

    public Obstaculo(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    public void render(SpriteBatch batch) {
        final float width = right - left;
        final float height = top - bottom;
        Assets.instance.obstaculoAssets.obstaculoNinePatch.draw(batch, left - 1, bottom - 1, width +2, height +2);

    }

}
