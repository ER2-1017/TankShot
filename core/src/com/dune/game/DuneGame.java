package com.dune.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DuneGame extends ApplicationAdapter {
    private static class Tank {
        private Vector2 position;
        private Texture texture;
        private float angle;
        private float speed;

        public Tank(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("tank.png");
            this.speed = 300.0f;
        }

        public void update(float dt) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                angle += 400.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle -= 400.0f * dt;
            }
            if ((position.x > 0 && position.x <= 1280) && (position.y > 0 && position.y <= 720)){
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    position.x += speed * MathUtils.cosDeg(angle) * dt;
                    position.y += speed * MathUtils.sinDeg(angle) * dt;
                }
            } else {
                speed = 0;
                // Тут я мог бы прописать условие, в котором для каждой стороны поля задать определенные углы поворота,
                // при которых нос танка смотрит внутрь поля и скорость его вновь не равна нулю, но наверняка можно сделать
                // это гораздо проще. Посмотрю реализацию на занятии, а пока мой танк застревает, доехав до границы.
            }
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, angle, 0, 0, 80, 80, false, false);
        }

        public void dispose() {
            texture.dispose();
        }
    }


    private static class Circle {
        private Vector2 position;
        private Texture texture;
        private float speed;
        private float angle;

        public Circle(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("circle.png");
        }

       public void update(float dt) {
            position.x += speed * MathUtils.cosDeg(angle) * dt;
            position.y += speed * MathUtils.sinDeg(angle) * dt;
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 0, 0, 50, 50, 1, 1, 0, 0, 0, 50, 50, false, false);
        }

        public void dispose() {
            texture.dispose();
        }
    }

    private SpriteBatch batch;
    private Tank tank;
    private Circle circle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        tank = new Tank(200, 200);
        circle = new Circle (300,300);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.4f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tank.render(batch);
        circle.render(batch);
        batch.end();
    }

    public void update(float dt) {
        tank.update(dt);
        if ((Math.abs(tank.position.x - circle.position.x) < 50) && (Math.abs(tank.position.y - circle.position.y) < 50)){
            float newCirclePosX = (float) (50+Math.random()*1000);
            float newCirclePosY = (float) (50+Math.random()*650);
            circle.position.x = newCirclePosX;
            circle.position.y = newCirclePosY;
            // эта реализация позволяет танку толкать мяч перед собой
            //circle.angle = tank.angle;
            //circle.speed = tank.speed;
        } else {
            circle.speed = 0;
        }
        circle.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        tank.dispose();
        circle.dispose();
    }

    // Домашнее задание:
    // - Задать координаты точки, и нарисовать в ней круг (любой круг, радиусом пикселей 50)
    // - Если "танк" подъедет вплотную к кругу, то круг должен переместиться в случайную точку
    // - * Нельзя давать танку заезжать за экран
}