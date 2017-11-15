package game.planet;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Launcher;
import engine.collision.AABB;
import engine.gfx.Renderer;
import engine.gfx.Shader;
import engine.gfx.Window;
import game.entity.Entity;
import game.entity.staticentities.Shrub;
import game.entity.staticentities.Tree;
import game.player.Player;
import game.world.tile.Tile;

public class Planet {

	private Matrix4f planet;
	private int viewX, viewY;
	private byte[] tiles;
	private AABB[] bounding_boxes;
	private List<Entity> entities;
	private int width, height;
	private int scale;
	private Vector2f player_spawn;

	public Planet(Player player, String path) {
		this.width = 64;
		this.height = 64;
		this.scale = 64;
		tiles = new byte[width * height];
		bounding_boxes = new AABB[width * height];
		entities = new ArrayList<Entity>();
		planet = new Matrix4f();
		planet.scale(scale);
		player_spawn = new Vector2f(6, -6);
		int j = 0;
		for (int i = 0; i < width; i++) {
			setTile(Tile.prison_wall_top_face_0, i, j);
			setTile(Tile.prison_wall_base_0, i, j+2);
			setTile(Tile.prison_wall_middle, i, j+1);
		}
		j = height - 1;
		for (int i = 0; i < width; i++)
			setTile(Tile.prison_wall_top_back, i, j);
		j = 0;
		for (int i = 0; i < height; i++)
			setTile(Tile.prison_wall_top_side, j, i);
		j = width - 1;
		for (int i = 0; i < height; i++)
			setTile(Tile.prison_wall_top_side, j, i);

		player.getTransform().pos.set(new Vector3f(player_spawn, 0));
		entities.add(player);

	}

	public void update(Launcher l, float delta) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(delta, l.getWindow(), l.getCamera(), this);
			entities.get(i).collideWithTiles(this);
			if (entities.get(i).isSolid()) {
				for (int j = i + 1; j < entities.size(); j++) {
					entities.get(i).collideWithEntity(entities.get(j));
				}
			}
			entities.get(i).collideWithTiles(this);
		}

	}

	public void render(Renderer r, Shader shader, Camera camera, Window window) {
		int posX = ((int) camera.getPosition().x + (window.getWidth() / 2)) / scale;
		int posY = ((int) camera.getPosition().y - (window.getHeight() / 2)) / scale;

		for (int i = 0; i < viewX; i++) {
			for (int j = 0; j < viewY; j++) {
				Tile t = getTile(i - posX, j + posY);
				if (t != null) {
					r.renderTile(t, i - posX, -j - posY, shader, planet, camera);
				}
			}
		}
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).render(shader, camera, this);
	}

	public void calculateView(Window window) {
		viewX = window.getWidth()/scale;
		viewY = window.getHeight()/scale;
		viewX+=4;
		viewY+=4;
	}
	
	public void correctCamera(Camera camera, Window window) {
		Vector3f pos = camera.getPosition();
		int w = -width * scale;
		int h = height * scale;

		if (pos.x > -(window.getWidth() / 2) + scale / 2)
			pos.x = -(window.getWidth() / 2) + scale / 2;
		if (pos.x < w + (window.getWidth() / 2) + scale)
			pos.x = w + (window.getWidth() / 2) + scale;
		if (pos.y < (window.getHeight() / 2) - scale / 2)
			pos.y = (window.getHeight() / 2) - scale / 2;
		if (pos.y > h - (window.getHeight() / 2) - scale)
			pos.y = h - (window.getHeight() / 2) - scale;
	}

	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getId();
		if (tile.isSolid()) {
			bounding_boxes[x + y * width] = new AABB(new Vector2f(x, -y), new Vector2f(1 / 2 , 1 / 2));
		} else {
			bounding_boxes[x + y * width] = null;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public AABB getTileBoundingBox(int x, int y) {
		try {
			return bounding_boxes[x + y * width];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public Matrix4f getPlanetMatrix() {
		return planet;
	}

	public int getScale() {
		return scale;
	}

	public void addTree(Tree t, float x, float y, int scaleX, int scaleY) {
		Tree a = new Tree(t.getTexture());
		a.getTransform().scale.set(new Vector3f(scaleX, scaleY, 0));
		a.setPos(x, -y);
		entities.add(a);
	}
	
	public void addShrub(Shrub s, float x, float y, int scaleX, int scaleY) {
		Shrub a = new Shrub(s.getTexture());
		a.getTransform().scale.set(new Vector3f(scaleX, scaleY, 0));
		a.setPos(x, -y);
		entities.add(a);
	}

}
