package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private boolean visible;
    private TextureRegion textures;

    public Projectile(TextureAtlas atlas) {
        this.textures = (atlas.findRegion("bullet"));
        this.visible = false;
        this.position = new Vector2();
        this.velocity = new Vector2();
    }

    public void setup(Vector2 startPosition, float angle) {
        if (!visible) {
            this.position.set(startPosition);
            this.velocity.set(400.0f * MathUtils.cosDeg(angle), 400.0f * MathUtils.sinDeg(angle));
            this.visible = true;
        }
    }

    public void update(float dt) {
        if (visible) {
            position.mulAdd(velocity, dt);
            if (position.x < 0) {
                visible = false;
            }
            if (position.y < 0) {
                visible = false;
            }
            if (position.x > 1280) {
                visible = false;
            }
            if (position.y > 720) {
                visible = false;
            }
        }
    }
    public void render(SpriteBatch batch) {
        if(visible){
            batch.draw(textures, position.x - 10, position.y - 10 , 0, 0, 20, 20, 1, 1,0);
        }
    }
}