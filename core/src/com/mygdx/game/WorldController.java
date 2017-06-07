package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.mygdx.game.Screens.AbstractGameScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MenuScreen;


/**
 * Created by LucasRezende on 24/01/2017.
 */

public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getName();

    public World b2world;
    public CameraHelper camera;
    public Level level;
    public int score;
    public Game game;

    //retangulos para detectar colisões com corpos nao fisicos
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    //////somente para testes//////
    private SpriteBatch currentBatch;
    private Texture currenteTexture;
    ////////////////////////////////


    public WorldController(Game game){
        this.game = game;
        init();
    }

    private void init(){
        Gdx.input.setInputProcessor(this);
        camera = new CameraHelper();
        initLevel();
    }

    private void initLevel(){
        score = 0;
        level = new Level(com.mygdx.game.Utils.Constants.LEVEL_01);
        initPhysics();
        camera.setTarget(level.whiteBall.body);
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

        BodyDef bodyDefWhiteBall = new BodyDef();
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


    ////////////////////////////////////// Update //////////////////////////////////////////////

    public  void update(float delta){
        handleDebugInput(delta);//Funçao de controle do cena para debuger
        handleInputGame(delta);
        level.update(delta);
        testCollisions();
        b2world.step(delta,8,3);
        camera.update(delta);
    }



    ///////////////////////////colisões com objetos sem corpo fisico (Moedas) ////////////////////////////////////////

    private void onCollisionWithGoldCoin(GoldCoin goldCoin){
        goldCoin.collected = true;
        score += goldCoin.getScore();
        Gdx.app.log(TAG, "Gold coin collected");
    }

    private void testCollisions () {
        r1.set(level.whiteBall.body.getPosition().x - 0.5f, level.whiteBall.body.getPosition().y - 0.5f, level.whiteBall.bounds.width, level.whiteBall.bounds.height);

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
                r1.set(ball.body.getPosition().x - 0.5f, ball.body.getPosition().y - 0.5f, ball.bounds.width, ball.bounds.height);
                if (goldcoin.collected) continue;
                r2.set(goldcoin.position.x, goldcoin.position.y,
                        goldcoin.bounds.width, goldcoin.bounds.height);
                if (!r1.overlaps(r2)) continue;
                onCollisionWithGoldCoin(goldcoin);
                break;
            }
        }

    }
    //////////////////////////////////////  Classes de debug  /////////////////////////////////////

    private void handleInputGame (float deltaTime) {
        if (camera.hasTarget(level.whiteBall.body)) {
            // movimento
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                level.whiteBall.body.applyLinearImpulse(-50f,0,level.whiteBall.body.getPosition().x,level.whiteBall.body.getPosition().y,true);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                level.whiteBall.body.applyLinearImpulse(50f,0,level.whiteBall.body.getPosition().x,level.whiteBall.body.getPosition().y,true);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                level.whiteBall.body.applyLinearImpulse(0,50f,level.whiteBall.body.getPosition().x,level.whiteBall.body.getPosition().y,true);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                level.whiteBall.body.applyLinearImpulse(0,-50f,level.whiteBall.body.getPosition().x,level.whiteBall.body.getPosition().y,true);
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

    //////////////////////////////////////  Funções de controle  /////////////////////////////////////

    private void backToMenu () {
        // retornar para o menu screen
        game.setScreen(new MenuScreen(game));
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
        } else if (keycode == Input.Keys.ENTER) {// Toggle camera follow
            camera.setTarget(camera.hasTarget() ? null: level.whiteBall.body);
            Gdx.app.debug(TAG,"Camera follow enabled: " + camera.hasTarget());
        }else if (keycode == Input.Keys.ESCAPE) {// back to menu
            backToMenu();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 ponto = new Vector3(screenX, screenY, 0);
        //.camera.unproject(ponto);

        Gdx.app.debug(TAG,"clique em " + ponto.x + "/" + ponto.y );
        Gdx.app.debug(TAG,"clique em " + level.whiteBall.body.getPosition().x + " / " + level.whiteBall.body.getPosition().y);
        //level.whiteBall.body.setTransform(0,0,0);
        if(Math.sqrt(Math.pow((level.whiteBall.body.getPosition().x - screenX),2) +  Math.pow((level.whiteBall.body.getPosition().y - screenY),2)) <= (0.5f) ){
            Gdx.app.debug(TAG,"clique na branca");
           return super.touchDown(screenX, screenY, pointer, button);
        }
        Gdx.app.debug(TAG,"clique na mesa");
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

}
