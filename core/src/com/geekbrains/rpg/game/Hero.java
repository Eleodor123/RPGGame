package com.geekbrains.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Texture texture;
    private Vector2 position;
    private float speed;
    private boolean isWalking;
    private Vector2 startPos;

    public Hero() {
        this.texture = new Texture("hero.png");
        this.position = new Vector2(100, 100);
        this.speed = 100.0f;
        this.isWalking = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32,32,32,64,64,1,1,0,0,0,64,64,false,false);
    }

    public void update(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * dt;
        }
    }

    public void update(Vector2 pointerPos, float dt) {
        if (isWalking) {
//            if (!(position.x >= pointerPos.x - 100 && position.x <= pointerPos.x + 100 && position.y >= pointerPos.y - 100 && position.y <= pointerPos.y + 100) )  {
//                position.x += (pointerPos.x-32)*dt;
//                position.y += (pointerPos.y-32)*dt;
              if (position != pointerPos) {
                  position = startPos.interpolate(pointerPos,dt,Interpolation.elasticOut);
            } else {
                isWalking = false;
            }
        }
    }

    public void startWalk (){
        startPos = position;
        this.isWalking = true;
    }
}