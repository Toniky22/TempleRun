package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.personaje.Indiana;

public class ChaseCamera {
    public Camera camera;
    public Indiana target;


    public ChaseCamera(Camera camera, Indiana target) {
        this.camera = camera;
        this.target = target;
    }

    public void update() {
        camera.position.x = target.position.x;
        camera.position.y =170;
    }
}