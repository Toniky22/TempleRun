package com.mygdx.game.personaje;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Vida {

    //Level level;
    //ShapeRenderer shapeRenderer;

    final public Vector2 position;

    public Vida(Vector2 position/*, Level level*/) {
        this.position = position;
        //this.level=level;
        //shapeRenderer=new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        final TextureRegion region = Assets.instance.vidaAssets.vida;
        Utils.drawTextureRegion(batch, region, position, Constants.VIDA_POSITION);
        /*batch.end();

        shapeRenderer.setProjectionMatrix(level.viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(position.x,position.y, 44,44);
        shapeRenderer.end();

        batch.begin();*/
    }
}
