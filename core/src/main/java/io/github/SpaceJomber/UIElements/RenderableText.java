package io.github.SpaceJomber.UIElements;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.SpaceJomber.systems.Renderable;

public class RenderableText implements Renderable {

    private String text;
    private BitmapFont font;
    private float x;
    private float y;

    public RenderableText(String text, float x, float y, BitmapFont font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
    }

    @Override
    public void render(SpriteBatch sbatch) {
         this.font.draw(sbatch, text, x, y);
    }

    @Override
    public void dispose() {
        // Don't dispose this.font, this.font is a reference to the RenderingSystem font.
    }

}
