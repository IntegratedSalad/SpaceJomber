package io.github.SpaceJomber.systems;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {
    /* In the overriden method DO NOT start or end the sbatch */
    void render(SpriteBatch sbatch);
    void dispose();
}
