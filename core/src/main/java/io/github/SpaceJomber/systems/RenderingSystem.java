package io.github.SpaceJomber.systems;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.*;

public class RenderingSystem {
    /*
     * Rendering system should provide an interface for
     * rendering different menu screens.
     * It has a list of Renderables.
     * It is Screen agnostic.
     */

    private List<Renderable> renderableList;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer layer = null;
    private TiledMap map;
    private TiledMapTileSet tileset;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont mainFont = null;
    private BitmapFont mainTitleFont = null;
    private Texture backgroundImage = null;
    private HashMap<String, Texture> textureMap = null;
    private HashMap<String, Sprite> spriteMap = null;

    public RenderingSystem(OrthographicCamera camera) {
        this.renderableList = new ArrayList<Renderable>();
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.textureMap = new HashMap<>();
        this.spriteMap = new HashMap<String, Sprite>();
        this.camera = camera;

//        this.map = new TmxMapLoader().load("gamemap.tmx"); // should be passed, in case they're different maps
//        this.layer = (TiledMapTileLayer) this.map.getLayers().get(0);
//        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/16f);
        // TODO: maybe pass fontPath into constructor
    }

    public void AddRenderable(Renderable renderable) {
        this.renderableList.add(renderable);
    }

    public void SetBackgroundImage(Texture backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Texture SetBackgroundImage() {
        return this.backgroundImage;
    }

    private void SetLayer() {
        this.layer = (TiledMapTileLayer) this.map.getLayers().get(0);
    }

    public void LoadMap(final String tmxFilePath) {
        this.map = new TmxMapLoader().load("gamemap.tmx");
        this.SetLayer();
    }

    public void SetTileSet() {
        if (this.map != null) {
            this.tileset = this.map.getTileSets().getTileSet(0);
        }
    }

    public void SetTiledMapRenderer() {
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(this.map, 1/16f);
    }

    public void SetupCamera() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 15, 13);
    }

    public void renderAll() {
//        Gdx.app.log("Camera", "Viewport X: " + camera.position.x + ", Y: " + camera.position.y);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        if (this.tiledMapRenderer != null) {
            this.tiledMapRenderer.setView(this.camera); // this is only a map renderer!!!
            this.tiledMapRenderer.render();
        }
        spriteBatch.setProjectionMatrix(camera.combined);
        this.spriteBatch.begin();
        for (Renderable renderable : renderableList) {
            renderable.render(this.spriteBatch);
        }
        this.spriteBatch.end();
//        Gdx.app.log("Camera Debug",
//            "Viewport MinX: " + (camera.position.x - camera.viewportWidth / 2) +
//                ", MaxX: " + (camera.position.x + camera.viewportWidth / 2) +
//                ", MinY: " + (camera.position.y - camera.viewportHeight / 2) +
//                ", MaxY: " + (camera.position.y + camera.viewportHeight / 2));
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

    public BitmapFont GetMainFont() {
        return this.mainFont;
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

    public BitmapFont GetMainTitleFont() {
        return this.mainTitleFont;
    }

    private void AddSpriteToDict(String spriteString, Sprite sprite) {
        this.spriteMap.put(spriteString, sprite);
    }

    private Sprite CreateSpriteFromTileset(final String tilesetPath,
                                          final int x,
                                          final int y,
                                          final int w,
                                          final int h) {

        // load only once
        Texture tilesetTexture = this.textureMap.computeIfAbsent(tilesetPath, path -> new Texture(Gdx.files.internal(path)));
        if (this.textureMap.get(tilesetPath) == null) {
            Gdx.app.log("Debug", "Texture not found or not loaded!");
        } else {
            Gdx.app.log("Debug", "Texture loaded: Width=" + tilesetTexture.getWidth() +
                ", Height=" + tilesetTexture.getHeight());
        }
        Gdx.app.log("Sprite Region Debug",
            "RegionX: " + x + ", RegionY: " + y +
                ", Width: " + w + ", Height: " + h +
                ", Texture Width: " + tilesetTexture.getWidth() +
                ", Texture Height: " + tilesetTexture.getHeight());
        TextureRegion region = new TextureRegion(tilesetTexture, x, y, w, h);
        return new Sprite(region);
    }

    public void RegisterSprite(final String spriteString,
                               final String tilesetPath,
                               final int x,
                               final int y,
                               final int w,
                               final int h) {
        final int regionX = x * 16;
        final int regionY = y * 16;

        Sprite sprite = this.CreateSpriteFromTileset(tilesetPath, regionX, regionY, w, h);
        sprite.setSize(1, 1);
        this.AddSpriteToDict(spriteString, sprite);
    }

    public Sprite GetSprite(final String spriteString) {
        return this.spriteMap.get(spriteString);
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
