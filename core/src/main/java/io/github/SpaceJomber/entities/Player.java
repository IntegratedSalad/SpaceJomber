package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.SpaceJomber.systems.Renderable;

public class Player implements Renderable {

    // TODO: Instantiate the multiplayerComponent as needed
    private Sprite sprite;
    private int x; // is this needed?
    private int y;

    public Player(Sprite sprite, int x, int y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
    }

    public void Move(final int x, final int y) {
        this.sprite.setX(this.sprite.getX() + x);
        this.sprite.setY(this.sprite.getY() + y);
    }

    @Override
    public void render(SpriteBatch sbatch) {
        Gdx.app.log("Player Position", "Sprite X: " + this.sprite.getX() + ", Y: " + this.sprite.getY());
        this.sprite.setPosition(this.x, this.y);
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {
    }
}
