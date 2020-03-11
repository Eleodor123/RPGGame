package com.geekbrains.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GeekRpgGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font32;
    private TextureAtlas atlas;
    private TextureRegion textureGrass;
    private ProjectilesController projectilesController;
    private Hero hero;

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.atlas = new TextureAtlas("game.pack");
        this.projectilesController = new ProjectilesController(atlas);
        this.hero = new Hero(this, atlas);
        this.textureGrass = atlas.findRegion("grass");
        this.font32 = new BitmapFont(Gdx.files.internal("font32.fnt"));
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(textureGrass, i * 80, j * 80);
            }
        }
        hero.render(batch);
        projectilesController.render(batch);
        hero.renderGUI(batch, font32);
        batch.end();
    }

    public void update(float dt) {
        hero.update(dt);
        projectilesController.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}