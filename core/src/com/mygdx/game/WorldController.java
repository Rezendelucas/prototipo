package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Models.Ball;
import com.mygdx.game.Models.Borda;
import com.mygdx.game.Models.GoldCoin;
import com.badlogic.gdx.physics.box2d.FixtureDef;


/**
 * Created by LucasRezende on 24/01/2017.
 */

public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getName();

    public World b2world;
    public CameraHelper camera;
    public Level level;
    public int score;
    //retangulos para detectar colisões com corpos nao fisicos
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    public WorldController(){
        init();
    }

    private void init(){
        Gdx.input.setInputProcessor(this);
        camera = new CameraHelper();
        initLevel();
    }

    private void initLevel(){
        score = 0;
        level = new Level(Constants.LEVEL_01);
        camera.setTarget(level.whiteBall.body);
        initPhysics();
    }

    ////////////////////////////////////////inicia a fisica////////////////////////////////////////////////

    private void initPhysics () {
        if (b2world != null) b2world.dispose();
        b2world = new World(new Vector2(0, 0), true);//-9.81f gravidade padrao
        Vector2 origin = new Vector2();

        for (Borda bordas : level.bordas_h) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.KinematicBody;
            bodyDef.position.set(bordas.position);
            Body body = b2world.createBody(bodyDef);
            bordas.body = body;
            PolygonShape polygonShape = new PolygonShape();
            origin.x = bordas.bounds.width / 2.0f;
            origin.y = bordas.bounds.height / 2.0f;
            polygonShape.setAsBox(bordas.bounds.width / 2.0f, bordas.bounds.height / 2.0f, origin, 0);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            bordas.body.createFixture(fixtureDef);
            polygonShape.dispose();

        }
        for (Borda bordas : level.bordas_v) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.KinematicBody;
            bodyDef.position.set(bordas.position);
            Body body = b2world.createBody(bodyDef);
            bordas.body = body;
            PolygonShape polygonShape = new PolygonShape();
            origin.x = bordas.bounds.width / 2.0f;
            origin.y = bordas.bounds.height / 2.0f;
            polygonShape.setAsBox(bordas.bounds.width / 2.0f, bordas.bounds.height / 2.0f, origin, 0);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            bordas.body.createFixture(fixtureDef);
            polygonShape.dispose();
        }
        for (Borda bordas : level.cantos) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.KinematicBody;
            bodyDef.position.set(bordas.position);
            Body body = b2world.createBody(bodyDef);
            bordas.body = body;
            PolygonShape polygonShape = new PolygonShape();
            origin.x = bordas.bounds.width / 2.0f;
            origin.y = bordas.bounds.height / 2.0f;
            polygonShape.setAsBox(bordas.bounds.width / 2.0f, bordas.bounds.height / 2.0f, origin, 0);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            bordas.body.createFixture(fixtureDef);
            polygonShape.dispose();
        }
        for (Ball ball : level.balls) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(ball.position);
            Body body = b2world.createBody(bodyDef);
            body.setLinearDamping(0.5f);
            body.setAngularDamping(0.5f);
            body.setFixedRotation(false);
            ball.body = body;
            CircleShape ballShape = new CircleShape();
            float raio = ball.dimension.x / 2;
            ballShape.setRadius(raio);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = ballShape;
            fixtureDef.shape.setRadius(raio);
            fixtureDef.density = 1.0f;
            fixtureDef.restitution = 0.5f;
            fixtureDef.friction = 0.5f;
            ball.body.createFixture(fixtureDef);
            ballShape.dispose();
        }
        ////////////////////////fisica da bola branca/////////////////////

        BodyDef bodyDefWhiteBall = new BodyDef();         //   linha de debug
        bodyDefWhiteBall.type = BodyType.DynamicBody;

        bodyDefWhiteBall.position.set(level.whiteBall.position);
        Body bodyBall = b2world.createBody(bodyDefWhiteBall);
        bodyBall.setLinearDamping(0.5f);
        bodyBall.setAngularDamping(0.5f);
        bodyBall.setFixedRotation(false);
        level.whiteBall.body = bodyBall;
        CircleShape ballShape = new CircleShape();
        float raio = level.whiteBall.dimension.x /2;
        ballShape.setRadius(raio);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.density = 5.0f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.friction = 10.0f;
        level.whiteBall.origin.set(ballShape.getPosition());
        level.whiteBall.body.createFixture(fixtureDef);
        ballShape.dispose();
    }


    //////////////////////////////////////////////////////////

    public  void update(float delta){
        handleDebugInput(delta);//Funçao de controle do cena para debuger
        handleInputGame(delta);
        level.update(delta);
        testCollisions();
        b2world.step(delta,8,3);
        camera.update(delta);
    }



    ///////////////////////////colisões com objetos sem corpo fisico////////////////////////////////////////

    private void onCollisionWithGoldCoin(GoldCoin goldCoin){
        goldCoin.collected = true;
        score += goldCoin.getScore();
        Gdx.app.log(TAG, "Gold coin collected");
    }

    private void testCollisions () {
        r1.set(level.whiteBall.position.x, level.whiteBall.position.y, level.whiteBall.bounds.width, level.whiteBall.bounds.height);

        for (GoldCoin goldcoin : level.goldcoins) {
            if (goldcoin.collected) continue;
            r2.set(goldcoin.position.x, goldcoin.position.y,
                    goldcoin.bounds.width, goldcoin.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionWithGoldCoin(goldcoin);
            break;
        }

        for (GoldCoin goldcoin : level.goldcoins) {
            for (Ball ball : level.balls) {
                r1.set(ball.position.x, ball.position.y, ball.bounds.width, ball.bounds.height);
                if (goldcoin.collected) continue;
                r2.set(goldcoin.position.x, goldcoin.position.y,
                        goldcoin.bounds.width, goldcoin.bounds.height);
                if (!r1.overlaps(r2)) continue;
                onCollisionWithGoldCoin(goldcoin);
                break;
            }
        }

    }
    ///////////////////////////////////Classes de debug/////////////////////////////////////

    private void handleInputGame (float deltaTime) {
        if (camera.hasTarget(level.whiteBall.body)) {
            // movimento
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                level.whiteBall.body.applyLinearImpulse(-1f,0,level.whiteBall.body.getPosition().x,level.whiteBall.body.getPosition().y,true);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                level.whiteBall.body.applyForceToCenter(300f,0,true);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                level.whiteBall.body.applyLinearImpulse(0,1f,level.whiteBall.origin.x,level.whiteBall.origin.y,true);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                level.whiteBall.body.applyLinearImpulse(0,-1f,level.whiteBall.origin.x,level.whiteBall.origin.y,true);
            }
        }
    }

    private void handleDebugInput(float delta) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;

        // controle da camera
        float camMovSpeed = 5 * delta;
        float camMovSpeedAcelerate = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            camMovSpeed *= camMovSpeedAcelerate;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            camera.setPosition(0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveCamera(-camMovSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveCamera(camMovSpeed,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            moveCamera(0,camMovSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            moveCamera(0,-camMovSpeed);
        }
        //zoom
        float camZoomSpeed = 1* delta;
        float camZoomSpeedAcelerate = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            camZoomSpeed *= camZoomSpeedAcelerate;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            camera.setZoom(1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            camera.addZoom(camZoomSpeed);
            Gdx.app.debug(TAG,"ZoomIn");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.X)){
            camera.addZoom(-camZoomSpeed);
            Gdx.app.debug(TAG,"ZoomOut");
        }

    }

    private void  moveCamera(float x , float y){
        x += camera.getPosition().x;
        y += camera.getPosition().y;
        camera.setPosition(x, y);
    }

    public boolean keyUp(int keycode){//reseta o world
        if(keycode == Input.Keys.R){
            init();
            Gdx.app.debug(TAG,"Game world resetado");
        }
        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
            camera.setTarget(camera.hasTarget() ? null: level.whiteBall.body);
            Gdx.app.debug(TAG, "Camera follow enabled: " + camera.hasTarget());
        }
        return false;
    }

}
