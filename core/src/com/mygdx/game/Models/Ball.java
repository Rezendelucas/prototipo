package com.mygdx.game.Models;

/**
 * Created by LucasRezende on 12/12/2016.
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ball extends AbstractGameObject {

    public static final String TAG = Ball.class.getName();

    private TextureRegion regBall;

    public Ball() {
        init();
    }

    public void init () {
        dimension.set(1, 1);
        regBall = Assets.instance.ball.ball;
        origin.set(dimension.x / 2, dimension.y / 2); //coloca o centro da imagem no centro do objeto
        bounds.set(0, 0, dimension.x, dimension.y); //seta caixa de colisao
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        // Draw image
        reg = regBall;
        batch.draw(reg.getTexture(),
                body.getPosition().x - 0.5f, body.getPosition().y - 0.5f,
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
     //   position.set(body.getPosition());
     //   Vector2 atrito;
     //   float torque;
     //   atrito = body.getLinearVelocity();
     //   atrito.x = atrito.x * -0.1f ;
     //   atrito.y = atrito.y * -0.1f ;
     //   body.applyForce(atrito,origin,true);
     //   torque = body.getAngularVelocity();
     //   torque = torque * -0.8f;
     //   body.applyTorque(torque,true);
    }
}