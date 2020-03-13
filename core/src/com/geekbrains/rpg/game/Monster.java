package com.geekbrains.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Monster {
    private GameScreen gameScreen;
    private TextureRegion texture;
    private TextureRegion textureHp;
    private Vector2 position;
    private Vector2 dst;
    private Vector2 tmp;
    private float lifetime;
    private float speed;
    private float attackTime;
    private int hp;
    private int hpMax;


    public Vector2 getPosition() {
        return position;
    }

    public Monster(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.texture = Assets.getInstance().getAtlas().findRegion("sm");
        this.textureHp = Assets.getInstance().getAtlas().findRegion("hp");
        this.position = new Vector2(800, 300);
        this.dst = new Vector2(position);
        this.tmp = new Vector2(0, 0);
        this.speed = 50.0f;
        this.hpMax = 30;
        this.hp = 30;
    }

    public boolean takeDamage(int amount) {
        hp -= amount;
        if (hp<=0) {
            return true;
        }
        return false;
    }

    public void recreate() {
        this.position.set(MathUtils.random(0,1280), MathUtils.random(0,720));
        this.hp = this.hpMax;
    }

    public void render(SpriteBatch batch) {
        batch.setColor(0.5f, 0.5f, 0.5f, 0.7f);
        batch.draw(texture, position.x - 30, position.y - 30, 30, 30, 60, 60, 1, 1, 0);
        batch.setColor(1, 1, 1, 1);
        batch.draw(textureHp, position.x - 30, position.y + 30, 60 * ((float) hp / hpMax), 12);
    }

    public void update(float dt) {
        lifetime += dt;
        if (this.position.dst(gameScreen.getHero().getPosition()) < 40) {
            attackTime += dt;
            if (attackTime > 0.3f) {
                attackTime = 0.0f;
                gameScreen.getHero().takeDamage(1);
            }
        }
        tmp.set(gameScreen.getHero().getPosition()).sub(position).nor().scl(speed);
        position.mulAdd(tmp, dt);
    }
}
