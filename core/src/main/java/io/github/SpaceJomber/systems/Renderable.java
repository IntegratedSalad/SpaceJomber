package io.github.SpaceJomber.systems;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {
    void render(SpriteBatch sbatch);
    void dispose();
}
