package com.mygdx.game.Models;

/**
 * Created by LucasRezende on 12/12/2016.
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BallWhite extends AbstractGameObject {

    public static final String TAG = BallWhite.class.getName();

    private TextureRegion regBallWhite;

    public BallWhite() {
        init();
    }

    public void init () {
        dimension.set(1, 1);
        regBallWhite = Assets.instance.whiteball.whiteball;
        origin.set(bounds.width / 2, bounds.height / 2); //coloca o centro da imagem no centro do objeto
        bounds.set(0, 0, dimension.x, dimension.y); //seta caixa de colisao
   }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        // Draw image
        reg = regBallWhite;
        batch.draw(reg.getTexture(),
                position.x - 0.5f, position.y - 0.5f,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                true, false);
    }

    @Override
    public void update (float deltaTime) {
        position.set(body.getPosition());
        Vector2 atrito;
        float torque;
        atrito = body.getLinearVelocity();
        atrito.x = atrito.x * -0.5f ;
        atrito.y = atrito.y * -0.5f ;
        body.applyForce(atrito,origin,true);
        torque = body.getAngularVelocity();
       torque = torque * -0.8f;
       body.applyTorque(torque,true);
    }
}