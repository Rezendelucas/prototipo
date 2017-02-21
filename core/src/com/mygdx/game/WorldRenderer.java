package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Models.Assets;


/**
 * Created by LucasRezende on 24/01/2017.
 */

public class WorldRenderer implements Disposable{

    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;
    private Box2DDebugRenderer b2debugRenderer;


    public WorldRenderer(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    public void  init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);
        cameraGUI.update();
        b2debugRenderer = new Box2DDebugRenderer();
    }

    public void render(){
        renderWorld(batch);
        renderGui(batch);
    }

    private void renderWorld(SpriteBatch batch){
        worldController.camera.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
        b2debugRenderer.render(worldController.b2world, camera.combined);

    }

    public void resize(int width , int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height)*width;
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT/height)*width;
        cameraGUI.position.set(cameraGUI.viewportWidth/2, cameraGUI.viewportHeight/2,0);
        cameraGUI.update();
    }

    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        renderGuiScore(batch);
        renderGuiFpsCounter(batch);
        batch.end();
    }

    private void renderGuiScore(SpriteBatch batch){
        float x = -15;
        float y = -15;
        batch.draw(Assets.instance.goldCoin.goldCoin,x,y,50,50,100,100,0.35f,-0.35f,0);
        Assets.instance.fontes.defaultBig.draw(batch,"" + worldController.score,x+75,y+37);
    }

    private void renderGuiFpsCounter (SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fontes.defaultNormal;
        if (fps >= 45) {
            fpsFont.setColor(0, 1, 0, 1);//verde
        } else if (fps >= 30) {
            fpsFont.setColor(1, 1, 0, 1);//amarelo
        } else {
            fpsFont.setColor(1, 0, 0, 1);//vermelho
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); //branco
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

