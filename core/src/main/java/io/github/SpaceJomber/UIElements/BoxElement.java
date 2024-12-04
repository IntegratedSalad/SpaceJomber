package io.github.SpaceJomber.UIElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.SpaceJomber.systems.Renderable;

/*
* This class can function as a button
*
*/
public class BoxElement implements Renderable {

    private int x;
    private int y;
    private int w;
    private int h;
    private boolean isFilled;
    private Color color;
    private String text = null;
    private Label label = null;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public BoxElement(final int x,
                      final int y,
                      final int w,
                      final int h,
                      final boolean isFilled,
                      final Color color,
                      final String text) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.isFilled = isFilled;
        this.color = color;
        this.text = text;
        if (this.text != null) {

            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("roboticsfont.ttf"));
            FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();

            fontParameter.size = 14;
            fontParameter.color = Color.WHITE;
            fontParameter.borderWidth = 1;
            fontParameter.borderColor = Color.BLACK;
            fontParameter.shadowOffsetX = 3;
            fontParameter.shadowOffsetY = 3;

            BitmapFont font = fontGenerator.generateFont(fontParameter);
            fontGenerator.dispose();

            Label.LabelStyle labelStyle = new Label.LabelStyle(font, color);
            this.label = new Label(this.text, labelStyle);
            this.label.setPosition(this.x + (this.w - this.label.getWidth()) / 2, this.y + (this.h - this.label.getHeight()) / 2);

        }
    }

    @Override
    public void render(SpriteBatch sbatch) {
        if (isFilled) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        } else {
            shapeRenderer.begin();
        }
        shapeRenderer.setColor(this.color);
        shapeRenderer.rect(this.x, this.y, this.w, this.h);
        shapeRenderer.end();

        if (this.text != null) {
            sbatch.begin();
            this.label.draw(sbatch, 1);
            sbatch.end();
        }
    }

    @Override
    public void dispose() {
        this.shapeRenderer.dispose();
        if (this.label != null && this.label.getStyle().font != null) {
            this.label.getStyle().font.dispose();
        }
    }
}
