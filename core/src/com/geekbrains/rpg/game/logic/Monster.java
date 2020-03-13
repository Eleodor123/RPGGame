package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.geekbrains.rpg.game.logic.utils.Poolable;
import com.geekbrains.rpg.game.screens.utils.Assets;

public class Monster extends GameCharacter implements Poolable {
    private float attackTime;

    @Override
    public boolean isActive() {
        return hp>0;
    }

    public Monster(GameController gc) {
        super(gc, 20, 100.0f);
        this.texture = Assets.getInstance().getAtlas().findRegion("sm");
        this.changePosition(800.0f,300.0f);
        this.dst.set(this.position);
        this.visionRadius = 250.0f;
    }

    public void generateMe() {
        do {
            changePosition(MathUtils.random(0,1280), MathUtils.random(0,720));
        } while (!gc.getMap().isGroundPassable(position));
        hpMax = 20;
        hp = hpMax;

    }

    @Override
    public void onDeath() {
        this.position.set(MathUtils.random(0,1280), MathUtils.random(0,720));
        this.hp = this.hpMax;
    }
    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        batch.setColor(0.5f, 0.5f, 0.5f, 0.7f);
        batch.draw(texture, position.x - 30, position.y - 30, 30, 30, 60, 60, 1, 1, 0);
        batch.setColor(1, 1, 1, 1);
        batch.draw(textureHp, position.x - 30, position.y + 30, 60 * ((float) hp / hpMax), 12);
    }

    public void update(float dt) {
        super.update(dt);
        if (this.position.dst(gc.getHero().getPosition()) < visionRadius) {
            dst.set(gc.getHero().getPosition());
        }
        if (position.dst(dst) < 2.0f) {
            dst.set(MathUtils.random(0,1280), MathUtils.random(0,720));
        }
        if (this.position.dst(gc.getHero().getPosition()) < 40) {
            attackTime += dt;
            if (attackTime > 0.3f) {
                attackTime = 0.0f;
                gc.getHero().takeDamage(1);
            }
        }
    }
}
