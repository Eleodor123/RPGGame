package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameController {
    private ProjectilesController projectilesController;
    private Map map;
    private Hero hero;
    private MonstersController monstersController;
    private Vector2 tmp, tmp2;

    public Hero getHero() {
        return hero;
    }

    public MonstersController getMonstersController() {
        return monstersController;
    }

    public Map getMap() {
        return map;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public GameController() {
        this.projectilesController = new ProjectilesController();

        this.hero = new Hero(this);
        this.map = new Map();
        this.monstersController = new MonstersController(this,5);
        this.tmp = new Vector2(0.0f, 0.0f);
        this.tmp2 = new Vector2(0.0f, 0.0f);
    }

    public void update(float dt) {
        hero.update(dt);
        monstersController.update(dt);
        checkCollisions();
        projectilesController.update(dt);
    }

    public void collideUnits(GameCharacter u1, GameCharacter u2) {
        if (u1.getArea().overlaps(u2.getArea())) {
            tmp.set(u1.getArea().x, u1.getArea().y);
            tmp.sub(u2.getArea().x, u2.getArea().y);
            float halfInterLen = ((u1.getArea().radius + u2.getArea().radius) - tmp.len()) / 2.0f;
            tmp.nor();

            tmp2.set(u1.getPosition()).mulAdd(tmp, halfInterLen);
            if (map.isGroundPassable(tmp2)) {
                u1.changePosition(tmp2);
            }
            u1.changePosition(tmp2);

            tmp2.set(u2.getPosition()).mulAdd(tmp, -halfInterLen);
            if (map.isGroundPassable(tmp2)) {
                u2.changePosition(tmp2);
            }
            u2.changePosition(tmp2);
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < monstersController.getActiveList().size() - 1; i++) {
            Monster m = monstersController.getActiveList().get(i);
            collideUnits(hero,m);
        }
        for (int i = 0; i < monstersController.getActiveList().size() - 1; i++) {
            Monster m = monstersController.getActiveList().get(i);
            for (int j = i+1; j < monstersController.getActiveList().size(); j++) {
                Monster m2 = monstersController.getActiveList().get(i);
                collideUnits(m,m2);
            }
        }
        for (int i = 0; i < projectilesController.getActiveList().size(); i++) {
            Projectile p = projectilesController.getActiveList().get(i);
            if (!map.isAirPassable(p.getCellX(), p.getCellY())) {
                p.deactivate();
                continue;
            }
            for (int j = 0; j < monstersController.getActiveList().size(); j++) {
                Monster m = monstersController.getActiveList().get(j);
                if (p.getPosition().dst(m.getPosition()) < 24) {
                    p.deactivate();
                    if (m.takeDamage(1)) {
                        hero.addCoins(MathUtils.random(3, 10));
                    }
                }
            }

        }

    }
}
