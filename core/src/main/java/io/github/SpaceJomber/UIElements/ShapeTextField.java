package io.github.SpaceJomber.UIElements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ShapeTextField extends TextField {

    private ShapeRenderer shapeRenderer;

    public ShapeTextField(String text, TextFieldStyle style, ShapeRenderer shapeRenderer) {
        super(text, style);
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        // Black background
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(Color.BLACK);
        this.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        this.shapeRenderer.end();

        // White border
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.WHITE);
        this.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        this.shapeRenderer.end();

        // TODO: It doesn't get drawn...

        batch.begin();
        super.draw(batch, parentAlpha);
    }
}
