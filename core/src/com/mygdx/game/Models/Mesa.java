package com.mygdx.game.Models;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by LucasRezende on 14/02/2017.
 */

public class Mesa extends AbstractGameObject {

    public Mesa(float x, float y , float width, float height) {
        init(x,y,width,height);
    }

    private void init(float x, float y , float width, float height) {
        position.set(x, y);
        dimension.set(width, height);
        bounds.set(x,y,width,height);
    }

    @Override
    public void render(SpriteBatch batch) {
        Pixmap pixmap = createProceduralPixmap(dimension.x,dimension.y);
        Texture texture = new Texture(pixmap);
        Sprite spr = new Sprite(texture);
        spr.setPosition(position.x,position.y);
        spr.draw(batch);
    }

    private Pixmap createProceduralPixmap (float width, float height) {
        Pixmap pixmap = new Pixmap((int)width,(int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 1, 0, 0.7f);
        pixmap.fill();
        return pixmap;
    }

    @Override
    public void update (float deltaTime) {

    }

}
