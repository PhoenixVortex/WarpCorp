package engine.gfx;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import game.world.tile.Tile;

public class Renderer {

	private HashMap<String, Texture> tile_textures;
	private Model tile_model;

	public Renderer() {
		tile_textures = new HashMap<String, Texture>();

		float[] vertices = new float[] { -.5f, .5f, 0, // TOP LEFT
				.5f, .5f, 0, // TOP RIGHT
				0.5f, -.5f, 0, // BOTTOM RIGHT
				-.5f, -.5f, 0, // Button LEFT

		};// Triangle

		float[] texture = new float[] { 0, 0, 1, 0, 1, 1, 0, 1, };// Texture

		int[] indices = new int[] { 0, 1, 2, // Draws first triangle
				2, 3, 0// Draws second triangle
		};

		tile_model = new Model(vertices, texture, indices);

		for (int i = 0; i < Tile.tiles.length; i++) {
			if (Tile.tiles[i] != null) {
				if (!tile_textures.containsKey(Tile.tiles[i].getTexture())) {
					String tex = Tile.tiles[i].getTexture();
					tile_textures.put(tex, new Texture(tex + ".png"));
				}
			}
		}

	}

	public void renderTile(Tile tile, int x, int y, Shader shader, Matrix4f world, Camera camera) {
		shader.bind();
		if (tile_textures.containsKey(tile.getTexture()))
			tile_textures.get(tile.getTexture()).bind(0);

		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x, y, 0));
		Matrix4f target = new Matrix4f();

		camera.getProjection().mul(world, target);
		target.mul(tile_pos);

		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		shader.setUniform("red", 0.9f);
		shader.setUniform("green", 0.9f);
		shader.setUniform("blue", .9f);
		shader.setUniform("alpha", .9f);

		tile_model.render();
	}
	
}
