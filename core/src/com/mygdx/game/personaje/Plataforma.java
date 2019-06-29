package com.mygdx.game.personaje;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.util.Assets;

public class Plataforma {

    public final float top;
    public final float bottom;
    public final float left;
    public final float right;

    public Plataforma(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    public void render(SpriteBatch batch) {
        final float width = right - left;
        final float height = top - bottom;
        Assets.instance.platformAssets.platformNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }

}
