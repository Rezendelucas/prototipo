package com.mygdx.game.Models;

/**
 * Created by LucasRezende on 12/12/2016.
 */

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GoldCoin extends AbstractGameObject {


    private TextureRegion regGoldCoin;
    public boolean collected;


    public GoldCoin() {
        init();
    }

    private void init () {
        dimension.set(1, 1);
        regGoldCoin = Assets.instance.goldCoin.goldCoin;
        // Setando caixa de colisão
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }
    public void render (SpriteBatch batch) {
        if (collected) return;
        TextureRegion reg = null;
        reg = regGoldCoin;
        batch.draw(reg.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
    }
    public int getScore() {
        return 100;
    }

    @Override
    public void update(float delta) {}
}