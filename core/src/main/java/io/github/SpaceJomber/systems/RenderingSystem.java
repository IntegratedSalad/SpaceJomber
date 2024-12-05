package io.github.SpaceJomber.systems;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

public class RenderingSystem {
    /*
     * Rendering system should provide an interface for
     * rendering different menu screens.
     * It has a list of Renderables.
     * It is Screen agnostic.
     * Maintain rendering order - first background (tile map), then anything else.
     */

    private List<Renderable> renderableList;
    private OrthographicCamera camera;
    private TiledMapTileLayer tiledMap = null;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont mainFont = null;
    private BitmapFont mainTitleFont = null;
    private Texture backgroundImage = null;

    public RenderingSystem(OrthographicCamera camera) {
        this.renderableList = new ArrayList<Renderable>();
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.camera = camera;
        // TODO: maybe pass fontPath into constructor
    }

    public RenderingSystem(List<Renderable> renderableList, OrthographicCamera camera, TiledMapTileLayer tiledMap) {
        this.renderableList = renderableList;
        this.camera = camera;
        this.tiledMap = tiledMap;
    }

    public void AddRenderable(Renderable renderable) {
        this.renderableList.add(renderable);
    }

    public void SetMap(TiledMapTileLayer map) {
        this.tiledMap = map;
    }

    public void SetBackgroundImage(Texture backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void renderAll() {
        // this.spriteBatch.begin();

        if (this.backgroundImage != null) {
            // TODO:
            // maybe make RenderableImage class that implements Renderable interface and does .draw
            // on provided spriteBatch
            // because what we're doing here, is sending multiple batches to the gpu
            // if each renderable would begin and end
            // and if any renderable doesn't begin and end the batch,
            // nothing will be rendered to the screen
            this.spriteBatch.begin();
            this.spriteBatch.draw(this.backgroundImage, 0, 0);
            this.spriteBatch.end();
        }

        this.spriteBatch.begin();
        for (Renderable renderable : renderableList) {
            renderable.render(this.spriteBatch);
        }
        this.spriteBatch.end();
        // this.spriteBatch.end();
    }

    public ShapeRenderer getShapeRenderer() {
        // This is very wrong, why would I expose the renderer?
        return this.shapeRenderer;
    }

    private BitmapFont CreateFont(String fontPath,
                                  Color fontColor,
                                  float borderWidth,
                                  Color borderColor,
                                  int shadowOffsetX,
                                  int shadowOffsetY) {
        /* TODO: use this method */
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 32;
        fontParameter.color = fontColor;
        fontParameter.borderWidth = borderWidth;
        fontParameter.borderColor = borderColor;
        fontParameter.shadowOffsetX = shadowOffsetX;
        fontParameter.shadowOffsetY = shadowOffsetY;
        final BitmapFont font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
        return font;
    }

    public void RegisterMainFont(String fontPath) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 14;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 1;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.shadowOffsetX = 3;
        fontParameter.shadowOffsetY = 3;
        this.mainFont = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    public void RegisterMainTitleFont(String fontPath) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 80;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 3;
        fontParameter.borderColor = Color.BLUE;
        fontParameter.shadowOffsetX = 3;
        fontParameter.shadowOffsetY = 3;
        this.mainTitleFont = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    public BitmapFont GetMainFont() {
        return this.mainFont;
    }

    public BitmapFont GetMainTitleFont() {
        return this.mainTitleFont;
    }

    public void dispose() {
        for (Renderable renderable : renderableList) {
            renderable.dispose();
        }
        this.shapeRenderer.dispose();
        this.spriteBatch.dispose();
        this.mainFont.dispose();
        this.mainTitleFont.dispose();
    }
}
