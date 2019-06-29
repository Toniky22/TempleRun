package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    public IndianaAssets indianaAssets;
    public PlatformAssets platformAssets;
    public ObstaculoAssets obstaculoAssets;
    public SalidaAssets salidaAssets;
    public MetaAssets metaAssets;
    public VidaAssets vidaAssets;
    public OnscreenControlsAssets onscreenControlsAssets;

    public AssetManager assetManager;

    private Assets() {
    }

    public void init() {
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        indianaAssets = new IndianaAssets(atlas);
        platformAssets=new PlatformAssets(atlas);
        obstaculoAssets=new ObstaculoAssets(atlas);
        salidaAssets=new SalidaAssets(atlas);
        metaAssets=new MetaAssets(atlas);
        vidaAssets=new VidaAssets(atlas);
        onscreenControlsAssets = new OnscreenControlsAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class IndianaAssets {

        public final TextureAtlas.AtlasRegion standingRight;
        public final TextureAtlas.AtlasRegion jumpingRight;
        public final TextureAtlas.AtlasRegion runningRight;
        public final TextureAtlas.AtlasRegion deadRight;


        public final Animation runningRightAnimation;

        public IndianaAssets(TextureAtlas atlas) {

            standingRight=atlas.findRegion(Constants.IDLE);

            jumpingRight = atlas.findRegion(Constants.JUMP_2);

            runningRight=atlas.findRegion(Constants.RUN_0);

            deadRight=atlas.findRegion(Constants.DEAD_0);

            Array<TextureAtlas.AtlasRegion> runningRightFrames = new Array<TextureAtlas.AtlasRegion>();
            runningRightFrames.add(atlas.findRegion(Constants.RUN_0));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_1));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_2));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_3));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_4));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_5));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_6));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_7));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_8));
            runningRightFrames.add(atlas.findRegion(Constants.RUN_9));
            runningRightAnimation = new Animation(Constants.RUN_LOOP_DURATION, runningRightFrames, Animation.PlayMode.LOOP);

        }
    }

    public class PlatformAssets {

        public final NinePatch platformNinePatch;

        public PlatformAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_SPRITE);
            int edge = Constants.PLATFORM_EDGE;
            platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class ObstaculoAssets{
        public final NinePatch obstaculoNinePatch;

        public ObstaculoAssets(TextureAtlas atlas) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.OBSTACULO_SPRITE);
            int edge = Constants.OBSTACULO_EDGE;
            obstaculoNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class SalidaAssets{
        public final TextureAtlas.AtlasRegion salida;

        public SalidaAssets(TextureAtlas atlas){
            salida = atlas.findRegion(Constants.SALIDA_SPRITE);
        }
    }

    public class MetaAssets{
        public final TextureAtlas.AtlasRegion meta;

        public MetaAssets(TextureAtlas atlas){
            meta=atlas.findRegion(Constants.META_SPRITE);
        }
    }

    public class VidaAssets{
        public final TextureAtlas.AtlasRegion vida;

        public VidaAssets(TextureAtlas atlas){
            vida=atlas.findRegion(Constants.VIDA_SPRITE);
        }
    }

    public class OnscreenControlsAssets {

        public final TextureAtlas.AtlasRegion jump;

        public OnscreenControlsAssets(TextureAtlas atlas) {
            jump = atlas.findRegion(Constants.JUMP_BUTTON);
        }


    }
}
