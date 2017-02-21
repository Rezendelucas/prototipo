package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Models.AbstractGameObject;
import com.mygdx.game.Models.Ball;
import com.mygdx.game.Models.BallWhite;
import com.mygdx.game.Models.Borda;
import com.mygdx.game.Models.GoldCoin;
import com.mygdx.game.Models.Mesa;

/**
 * Created by LucasRezende on 24/01/2017.
 */

public class Level {
    public static final String TAG = Level.class.getName();

    public enum BLOCK_TYPE {

        EMPTY(0, 0, 0), // black
        BORDA_H(0, 255, 0), // green
        BORDA_V(0, 0, 255), // blue
        CANTO(255, 0, 0), // red
        WHITE_BALL(255, 255, 255), // white
        BALL(255, 0, 255), // purple
        ITEM_GOLD_COIN(255, 255, 0),// yellow
        MESAINI(0, 255, 255), // azul claro
        MESAFIN(255, 120,120);// pink



        private int color;

        private BLOCK_TYPE (int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor (int color) {
            return this.color == color;
        }

        public int getColor () {
            return color;
        }
    }


    public Array<Borda> bordas_h;
    public Array<Borda> bordas_v;
    public Array<Borda> cantos;
    public BallWhite whiteBall;
    public Array<Ball> balls;
    public Array<GoldCoin> goldcoins;
    public Mesa mesa;
    private float mesaX;
    private float mesaY;
    private float mesaWidth;
    private float mesaHeight;

    public Level (String filename) {
        init(filename);
    }

    private void init (String filename){

        whiteBall = null;
        balls = new Array<Ball>();
        bordas_h = new Array<Borda>();
        bordas_v = new Array<Borda>();
        cantos = new Array<Borda>();
        goldcoins = new Array<GoldCoin>();

        // load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(Constants.LEVEL_01));

        // scan pixels from top-left to bottom-right
        int lastPixel = -1;

        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {

                AbstractGameObject obj = null;
                float offsetHeight = 0;
                float baseHeight = pixmap.getHeight() - pixelY;
                int currentPixel = pixmap.getPixel(pixelX, pixelY);


                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {}   // Nada
                else if (BLOCK_TYPE.BORDA_H.sameColor(currentPixel)) {
                    Gdx.app.debug(TAG, "borda h criada");
                    if (lastPixel != currentPixel) {
                        obj = new Borda();
                        float heightIncreaseFactor = 1f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX, + baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        bordas_h.add((Borda) obj);
                    } else {
                        bordas_h.get(bordas_h.size - 1).increaseLength(1);
                    }
                }else if (BLOCK_TYPE.BORDA_V.sameColor(currentPixel)) {
                    Gdx.app.debug(TAG, "borda v criada");
                    if (lastPixel != currentPixel) {
                        obj = new Borda();
                        float heightIncreaseFactor = 1f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        bordas_v.add((Borda) obj);
                    }
                }else if (BLOCK_TYPE.CANTO.sameColor(currentPixel)) {
                    Gdx.app.debug(TAG, "canto criada");
                    if (lastPixel != currentPixel) {
                        obj = new Borda();
                         float heightIncreaseFactor = 1f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        cantos.add((Borda) obj);
                    }
                } else if (BLOCK_TYPE.WHITE_BALL.sameColor(currentPixel)) {//player spawn
                        obj = new BallWhite();
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                        whiteBall = (BallWhite) obj;
                }
                else if (BLOCK_TYPE.BALL.sameColor(currentPixel)) {
                    obj = new Ball();
                    offsetHeight = -2.5f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    balls.add((Ball)obj);
                }
                else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {// Moeda spawn
                    obj = new GoldCoin();
                    offsetHeight = -2.5f;
                    obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
                    goldcoins.add((GoldCoin)obj);
                }else if (BLOCK_TYPE.MESAINI.sameColor(currentPixel)) {// inicio da mesa
                    offsetHeight = -0.0f;
                    mesaX = pixelX + 1.0f;
                    mesaY = pixelY + offsetHeight;
                }else if (BLOCK_TYPE.MESAFIN.sameColor(currentPixel)) {// fim da mesa
                    mesaWidth = pixelX - 1.0f;
                    mesaHeight = pixelY - 2.0f;
                } else {                                                          // objeto desconhecido
                    int r = 0xff & (currentPixel >>> 24); //red color channel
                    int g = 0xff & (currentPixel >>> 16); //green color channel
                    int b = 0xff & (currentPixel >>> 8); //blue color channel
                    int a = 0xff & currentPixel; //alpha channel
                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y <" + pixelY + ">: r <" + r +"> g <" + g +"> b <" + b +"> a <" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }
        // criar a area da mesa
        AbstractGameObject obj = new Mesa(mesaX,mesaY,mesaWidth,mesaHeight);
        mesa = (Mesa) obj;
        // liberar a memoria desalocando o mapa do lvl
        pixmap.dispose();
        Gdx.app.debug(TAG, "level '" + filename + "' carregada");
    }

    public void render (SpriteBatch batch){
        mesa.render(batch);
        for(Borda borda: bordas_h) {
            borda.setTipo_de_borda(Borda.TIPO_DE_BORDA.HORIZONTAL);
            borda.render(batch);
        }
        for(Borda borda: bordas_v) {
            borda.setTipo_de_borda(Borda.TIPO_DE_BORDA.VERTICAL);
            borda.render(batch);
        }
        for(Borda borda: cantos) {
            borda.setTipo_de_borda(Borda.TIPO_DE_BORDA.CANTO);
            borda.render(batch);
        }
        for (GoldCoin goldCoin : goldcoins)
            goldCoin.render(batch);
        for (Ball ball : balls)
            ball.render(batch);
        whiteBall.render(batch);

    }

    public void update (float deltaTime) {
        whiteBall.update(deltaTime);
        mesa.update(deltaTime);
        for(Borda borda : bordas_h)
            borda.update(deltaTime);
        for(Borda borda : bordas_v)
            borda.update(deltaTime);
        for(Borda borda : cantos)
            borda.update(deltaTime);
        for(GoldCoin goldCoin : goldcoins)
            goldCoin.update(deltaTime);
        for(Ball ball : balls)
            ball.update(deltaTime);
    }
}
