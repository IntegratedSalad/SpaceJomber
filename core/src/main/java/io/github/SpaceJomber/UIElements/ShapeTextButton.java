package io.github.SpaceJomber.UIElements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/* Shape text button has two shapes:
 * One, outer shape and one inner.
 * They can have different colors.
 * It also has a Label
*/
public class ShapeTextButton extends TextButton {

    private ShapeRenderer shapeRenderer;
    private Color outerColor;
    private Color innerColor;
    private Color hoverInnerColor;
    private Color pressedInnerColor;

    public ShapeTextButton(ShapeRenderer shapeRenderer,
                           String text,
                           TextButtonStyle style,
                           Color outerColor,
                           Color innerColor,
                           Color hoverInnerColor,
                           Color pressedInnerColor) {
        super(text, style);
        this.shapeRenderer = shapeRenderer;
        this.outerColor = outerColor;
        this.innerColor = innerColor; // this color changes upon hovering
        this.hoverInnerColor = hoverInnerColor;
        this.pressedInnerColor = pressedInnerColor;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(outerColor);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight()); // this draws to the screen

        if (this.isPressed()) {
            shapeRenderer.setColor(this.pressedInnerColor);
        } else if (this.isOver()) {
            shapeRenderer.setColor(this.hoverInnerColor);
        } else {
            shapeRenderer.setColor(this.innerColor);
        }

        float padding = 5;
        shapeRenderer.rect(getX() + padding, getY() + padding, getWidth() - 2*padding, getHeight() - 2*padding);
        shapeRenderer.end();

        batch.begin();
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        // Dispose font
    }
}
