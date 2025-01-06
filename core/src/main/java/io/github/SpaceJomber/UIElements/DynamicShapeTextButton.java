package io.github.SpaceJomber.UIElements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

public class DynamicShapeTextButton extends ShapeTextButton {

    private int currentIndex;
    private List<String> stringList;
    private ShapeRenderer shapeRenderer;
    private Color borderColor = Color.RED; // Default border color
    private float borderWidth = 2f; // Default border width
    private boolean isBorder = false;

    public DynamicShapeTextButton(ShapeRenderer shapeRenderer,
                                  List<String> stringList,
                                  int initialIndex,
                                  TextButtonStyle style,
                                  Color outerColor,
                                  Color innerColor,
                                  Color hoverInnerColor,
                                  Color pressedInnerColor) {
        super(shapeRenderer, stringList.get(initialIndex), style, outerColor, innerColor, hoverInnerColor, pressedInnerColor);
        this.currentIndex = initialIndex;
        this.stringList = stringList;
        this.shapeRenderer = shapeRenderer;

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentIndex = (currentIndex + 1) % stringList.size();
                setText(stringList.get(currentIndex));
            }
        });
    }

    public String GetDisplayedText() {
        return this.getText().toString();
    }

    public void SetBorderColor(Color color) {
        this.borderColor = color;
    }

    public void SetBorder(final boolean isBorder) {
        this.isBorder = isBorder;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        if (isBorder) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(borderColor);
            shapeRenderer.rect(getX() - borderWidth, getY() - borderWidth,
                getWidth() + 2 * borderWidth, getHeight() + 2 * borderWidth);
            shapeRenderer.end();
        }

        batch.begin();

        super.draw(batch, parentAlpha);
    }
}
