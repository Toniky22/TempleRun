package com.mygdx.game.personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.gwt.junit.Platform;
import com.mygdx.game.Level;
import com.mygdx.game.TempleRunGame;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Indiana {


    public Vector2 spawnLocation;
    public Level level;
    public Vector2 position;
    public Vector2 lastFramePosition;
    public Vector2 velocity;
    public Animation animationR;
    public TextureRegion region;
    public float run_time;
    public float tiempo;
    public long jumpStartTime;
    public Constants.JumpState jumpState;
    public int vidas;
    TempleRunGame templeRunGame;
    public boolean jumpButtonPressed;

    public Indiana(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        animationR = Assets.instance.indianaAssets.runningRightAnimation;
        templeRunGame = new TempleRunGame();
        init();

    }

    public void init() {
        if (level.getDifficulty().getVelocity() == 0.75) {
            vidas = Constants.VIDAS_INICIAL_FACIL;
        } else if (level.getDifficulty().getVelocity() == 1) {
            vidas = Constants.VIDAS_INICIAL_MEDIO;
        } else if (level.getDifficulty().getVelocity() == 3) {
            vidas = Constants.VIDAS_INICIAL_DIFICIL;
        }

        respawn();
    }

    public void respawn() {
        position.set(spawnLocation);
        lastFramePosition.set(spawnLocation);
        velocity.setZero();
        jumpState = Constants.JumpState.FALLING;
    }

    public int getVidas() {
        return vidas;
    }


    public void update(float delta, Array<Plataforma> plataformas) {

        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);
        run_time += delta * level.getDifficulty().getVelocity();
        tiempo += delta;

        if (position.y - Constants.INDIANA_EYE_HEIGHT < 0) {
            jumpState = Constants.JumpState.GROUNDED;
            position.y = Constants.INDIANA_EYE_HEIGHT;
            velocity.y = 0;
        }

        if (jumpState != Constants.JumpState.JUMPING) {

            for (Plataforma plataforma : plataformas) {
                if (landedOnPlatform(plataforma)) {
                    jumpState = Constants.JumpState.GROUNDED;
                    velocity.y = 0;
                    velocity.x = 0;
                    position.y = plataforma.top + Constants.INDIANA_EYE_HEIGHT;
                }
            }
        }

        Rectangle indianaBounds = new Rectangle(
                position.x - Constants.INDIANA_STANCE_WIDTH / 2,
                position.y - Constants.INDIANA_EYE_HEIGHT,
                Constants.INDIANA_STANCE_WIDTH,
                Constants.INDIANA_HEIGHT);


        /*for (Obstaculo obstaculo : level.getObstaculos()) {
            if (position.x >= obstaculo.left && position.x < obstaculo.right && position.y <= obstaculo.top) {
                jumpState = Constants.JumpState.COLISION;
                vidas = vidas - 1;
                if (vidas > 0) {
                    respawn();
                }
            }

        }*/

        for (Obstaculo obstaculo : level.getObstaculos()) {
            Rectangle obstaculoBounds = new Rectangle(
                    obstaculo.left - 1,
                    obstaculo.bottom-1,
                    obstaculo.right-obstaculo.left-10,
                    obstaculo.top-obstaculo.bottom-10
            );
            if (indianaBounds.overlaps(obstaculoBounds)) {
                jumpState = Constants.JumpState.COLISION;
                vidas = vidas - 1;
                if (vidas > 0) {
                    respawn();
                }else{
                    level.gameOver=true;
                }
                break;
            }
        }

        DelayedRemovalArray<Vida> vidaArray = level.getVidas();
        vidaArray.begin();
        for (int i = 0; i < vidaArray.size; i++) {
            Vida vida = vidaArray.get(i);
            Rectangle vidaBounds = new Rectangle(
                    vida.position.x ,
                    vida.position.y ,
                    Assets.instance.vidaAssets.vida.getRegionWidth(),
                    Assets.instance.vidaAssets.vida.getRegionHeight()
            );
            if (indianaBounds.overlaps(vidaBounds)) {
                vidas = vidas + 1;
                vidaArray.removeIndex(i);
            }
        }
        vidaArray.end();

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) || jumpButtonPressed) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                    break;
            }
        }


    }


    boolean landedOnPlatform(Plataforma plataforma) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.INDIANA_EYE_HEIGHT >= plataforma.top &&
                position.y - Constants.INDIANA_EYE_HEIGHT < plataforma.top) {

            float leftFoot = position.x - Constants.INDIANA_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.INDIANA_STANCE_WIDTH / 2;

            leftFootIn = (plataforma.left < leftFoot && plataforma.right > leftFoot);
            rightFootIn = (plataforma.left < rightFoot && plataforma.right > rightFoot);
            straddle = (plataforma.left > leftFoot && plataforma.right < rightFoot);
        }
        return leftFootIn || rightFootIn || straddle;
    }

    public void run(){
        position.x+=level.getDifficulty().getVelocity();
    }

    public void stop(){
        position.x=level.meta.position.x;
    }

    public void jump(){
        if(level.getDifficulty().getVelocity()==0.75){
            position.x+=(3*level.getDifficulty().getVelocity());
        }else if(level.getDifficulty().getVelocity()==1){
            position.x+=(2.25*level.getDifficulty().getVelocity());
        }else if(level.getDifficulty().getVelocity()==3){
            position.x+=level.getDifficulty().getVelocity();
        }

    }

    private void startJump() {
        jumpState = Constants.JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == Constants.JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == Constants.JumpState.JUMPING) {
            jumpState = Constants.JumpState.FALLING;
        }
    }

    public void render(SpriteBatch batch){

        if(jumpState == Constants.JumpState.COLISION){
            region=  Assets.instance.indianaAssets.deadRight;
        }else if ( jumpState != Constants.JumpState.GROUNDED && jumpState != Constants.JumpState.COLISION) {
            region = Assets.instance.indianaAssets.jumpingRight;
            jump();
        }else if(jumpState == Constants.JumpState.GROUNDED){
            region= (TextureRegion) animationR.getKeyFrame(run_time,true);
            run();
        }
        if(level.victoria){
            region=Assets.instance.indianaAssets.standingRight;
            stop();
        }
        batch.draw(
                region.getTexture(),
                position.x - Constants.INDIANA_EYE_POSITION.x,
                position.y - Constants.INDIANA_EYE_POSITION.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);

    }
}
