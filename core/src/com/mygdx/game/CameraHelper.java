package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Models.AbstractGameObject;

/**
 * Created by LucasRezende on 24/01/2017.
 */

public class CameraHelper {
    private  static final  String Tag = CameraHelper.class.getName();

    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 10.0f;

    private Body target;
    private Vector2 position;
    private float zoom;


    public CameraHelper() {
        position = new Vector2();
        zoom = 1.0f;
    }



    public void update(float delta){
        if(!hasTarget())return;
        position.x = target.getPosition().x ;
        position.y = target.getPosition().y ;
        position.y = Math.max(-1f, position.y);
    }

    public void setPosition(float x, float y) {
        this.position.set(x,y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public  void addZoom(float amount){
        setZoom(zoom + amount);
    }

    public  void  setZoom(float zoom){
        this.zoom = MathUtils.clamp(zoom,MAX_ZOOM_IN,MAX_ZOOM_OUT);
    }

    public float getZoom(){
        return zoom;
    }

    public void setTarget(Body target){
        this.target = target;
    }

    public Body getTarget() {
        return target;
    }

    public  boolean hasTarget(){
        return  target != null;
    }


    public  boolean hasTarget(Body target){
        return hasTarget() && this.target.equals(target) ;
    }

    public  void applyTo(OrthographicCamera camera){
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }
}
