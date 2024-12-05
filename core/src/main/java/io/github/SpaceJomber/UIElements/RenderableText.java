package io.github.SpaceJomber.UIElements;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.SpaceJomber.systems.Renderable;

public class RenderableText implements Renderable {

    RenderableText(String text, float x, float y, BitmapFont font) {

    }

    @Override
    public void render(SpriteBatch sbatch) {
        // render:
        // this.font.draw(sbatch, text, x, y)
    }

    @Override
    public void dispose() {

    }

}
