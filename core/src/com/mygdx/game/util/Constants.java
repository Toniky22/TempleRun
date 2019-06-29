package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {


    //tamaño del mundo

    public static final float WORLD_SIZE = 480.0f;
    public static final String TEXTURE_ATLAS = "images/templerun.pack.atlas";
    public static final float GRAVITY = 10;
    public static final Color BACKGROUND_COLOR = Color.SKY;

    //pantalla de carga

    public static final Color EASY_COLOR = new Color(0.5f, 1, 0.5f, 1);
    public static final Color MEDIUM_COLOR = new Color(0.2f, 1, 0.2f, 1);
    public static final Color HARD_COLOR = new Color(0, 1, 0, 1);

    public static final Vector2 EASY_CENTER = new Vector2(WORLD_SIZE / 3, WORLD_SIZE / 2);
    public static final Vector2 MEDIUM_CENTER = new Vector2(WORLD_SIZE / 1.5f, WORLD_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(WORLD_SIZE * 4 / 4, WORLD_SIZE / 2);

    public static final float EASY_SPEED = 0.75f;
    public static final float MEDIUM_SPEED = 1;
    public static final float HARD_SPEED = 3;

    public static final String EASY_LABEL = "Fácil";
    public static final String MEDIUM_LABEL = "Medio";
    public static final String HARD_LABEL = "Difícil";

    public static final float DIFFICULTY_BUBBLE_RADIUS = WORLD_SIZE / 9;
    public static final float DIFFICULTY_LABEL_SCALE = 1.5f;

    //plataforma
    public static final String PLATFORM_SPRITE = "plataforma";
    public static final int PLATFORM_EDGE = 8;

    //obstaculo
    public static final String OBSTACULO_SPRITE = "obstaculo";
    public static final int OBSTACULO_EDGE = 8;

    //salida y meta
    public  static final String SALIDA_SPRITE="salida";
    public static final Vector2 SALIDA_POSITION = new Vector2(0, 0);
    public  static final String META_SPRITE="meta";
    public static final Vector2 META_POSITION = new Vector2(0, 0);

    //vida
    public static final String VIDA_SPRITE="heart0";
    public static final Vector2 VIDA_POSITION = new Vector2(0, 0);
    public static final int VIDAS_INICIAL_FACIL=3;
    public static final int VIDAS_INICIAL_MEDIO=2;
    public static final int VIDAS_INICIAL_DIFICIL=1;

    //personaje
    public static final Vector2 INDIANA_SPAWN = new Vector2(16, 24);
    public static final Vector2 INDIANA_EYE_POSITION = new Vector2(16, 24);
    public static final float INDIANA_EYE_HEIGHT = 16.0f;
    public static final float INDIANA_STANCE_WIDTH = 50.0f;
    public static final float INDIANA_HEIGHT = 23.0f;
    public static final String IDLE = "Idle_0";
    //correr
    public static final String RUN_0 = "Run_0";
    public static final String RUN_1 = "Run_1";
    public static final String RUN_2 = "Run_2";
    public static final String RUN_3 = "Run_3";
    public static final String RUN_4 = "Run_4";
    public static final String RUN_5 = "Run_5";
    public static final String RUN_6 = "Run_6";
    public static final String RUN_7 = "Run_7";
    public static final String RUN_8 = "Run_8";
    public static final String RUN_9 = "Run_9";
    public static final float RUN_LOOP_DURATION = 0.25f;

    //salto
    public static final String JUMP_2 = "Jump_2";
    public static final float JUMP_SPEED = 350;
    public static final float MAX_JUMP_DURATION = 0.1f;

    //muerte
    public static final String DEAD_0 = "Dead_0";

    // Level Loading
    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_FILE_EXTENSION = "dt";
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "Problema al cargar el nivel";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";

    //mensajes
    public static final float LEVEL_END_DURATION = 3;
    public static final String VICTORY_MESSAGE = "GANASTE";
    public static final String GAME_OVER_MESSAGE = "PERDISTE";
    public static final String FONT_FILE = "font/header.fnt";

    // HUD
    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 20;
    public static final String HUD_DIFFICULTY_LABEL = "Dificultad: ";
    public static final String HUD_TIME_LABEL = "Tiempo: ";
    public static final String HUD_VIDAS_LABEL = "Vidas: ";

    // Onscreen Controls
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;
    public static final String JUMP_BUTTON = "button-jump";
    public static final Vector2 BUTTON_CENTER = new Vector2(15, 15);
    public static final float BUTTON_RADIUS = 32;


    //enum
    public enum Difficulty {
        EASY(EASY_SPEED, EASY_LABEL),
        MEDIUM(MEDIUM_SPEED, MEDIUM_LABEL),
        HARD(HARD_SPEED, HARD_LABEL);

        float  velocity;
        String label;

        Difficulty(float velocity, String label) {
            this.velocity = velocity;
            this.label = label;
        }

        public float getVelocity(){
            return velocity;
        }
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        COLISION
    }

}
