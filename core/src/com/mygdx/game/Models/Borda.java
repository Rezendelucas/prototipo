package com.mygdx.game.Models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by LucasRezende on 30/11/2016.
 */
public class Borda extends AbstractGameObject {

    private TextureRegion regCorner;
    private TextureRegion regMiddle;
    private int length;

    public enum TIPO_DE_BORDA {HORIZONTAL, VERTICAL, CANTO}

    private TIPO_DE_BORDA tipo_de_borda;

    public Borda() {
        init();
    }

    private void init() {
        dimension.set(1, 1);
        regCorner = Assets.instance.borda.corner;
        regMiddle = Assets.instance.borda.middle;
        setLength(1);
    }

    public void setLength(int length) {
        this.length = length;
        bounds.set(0, 0, dimension.x * length, dimension.y);
    }

    public void increaseLength(int amount) {
        setLength(length + amount);
    }

    public void setTipo_de_borda(TIPO_DE_BORDA tipo_de_borda) {
        this.tipo_de_borda = tipo_de_borda;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        float relX = 0;
        float relY = 0;
         // draw corner region
        if (tipo_de_borda == TIPO_DE_BORDA.CANTO) {
            reg = regCorner;
            batch.draw(reg.getTexture(),
                    position.x, position.y,
                    origin.x, origin.y,
                    dimension.x, dimension.y,
                    scale.x, scale.y,
                    rotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);

        } else if (tipo_de_borda == TIPO_DE_BORDA.HORIZONTAL) {
            //draw middle region horizontal
            relX = 0;
            reg = regMiddle;
            for (int i = 0; i < length; i++) {
                batch.draw(reg.getTexture(),
                        position.x + relX, position.y + relY,
                        origin.x, origin.y,
                        dimension.x, dimension.y,
                        scale.x, scale.y,
                        rotation,
                        reg.getRegionX(), reg.getRegionY(),
                        reg.getRegionWidth(), reg.getRegionHeight(),
                        false, false);
                relX += dimension.x;
            }
        } else if (tipo_de_borda == TIPO_DE_BORDA.VERTICAL) {
            //draw middle region vertical
            relX = 1;
            relY = 0;
            reg = regMiddle;
            rotation = 90.0f;
            for (int i = 0; i < length; i++) {
                batch.draw(reg.getTexture(),
                        position.x + relX, position.y + relY,
                        origin.x, origin.y,
                        dimension.x, dimension.y,
                        scale.x, scale.y,
                        rotation,
                        reg.getRegionX(), reg.getRegionY(),
                        reg.getRegionWidth(), reg.getRegionHeight(),
                        false, false);
                relX += dimension.x;
                relY += dimension.y;
            }
        }
    }
    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);
    }
}





















