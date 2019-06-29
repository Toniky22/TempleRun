package com.mygdx.game.personaje;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Meta {
    public final static String TAG = Meta.class.getName();

    public final Vector2 position;

    public Meta(Vector2 position) {

        this.position = position;

    }

    public void render(SpriteBatch batch) {

        final TextureRegion region = (TextureRegion) Assets.instance.metaAssets.meta;
        Utils.drawTextureRegion(batch, region, position, Constants.META_POSITION);
    }
}
