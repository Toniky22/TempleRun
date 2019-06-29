package com.mygdx.game.personaje;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Salida {
    public final static String TAG = Salida.class.getName();

    public final Vector2 position;

    public Salida(Vector2 position) {

        this.position = position;

    }

    public void render(SpriteBatch batch) {

        final TextureRegion region = (TextureRegion) Assets.instance.salidaAssets.salida;
        Utils.drawTextureRegion(batch, region, position, Constants.SALIDA_POSITION);
    }
}
