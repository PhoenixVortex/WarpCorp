package game.gfx;

import engine.gfx.Model;
import engine.gfx.TextureSheet;

public class Assets {

	private static Model model;
	
	//TextureSheets
	public static TextureSheet tiles;
	//Textures
	
	private static void initTextures() {
		tiles = new TextureSheet("tiles/tiles", 7);
	}
	
	public static Model getModel() {
		return model;
	}
	
	public static void initAssets() {
		float[] vertices = new float[] { -.5f, .5f, 0, // TOP LEFT
				.5f, .5f, 0, // TOP RIGHT
				0.5f, -.5f, 0, // BOTTOM RIGHT
				-.5f, -.5f, 0, // Button LEFT

		};// Triangle

		float[] texture = new float[] { 0, 0, 1, 0, 1, 1, 0, 1, };// Texture

		int[] indices = new int[] { 0, 1, 2, // Draws first triangle
				2, 3, 0// Draws second triangle
		};

		model = new Model(vertices, texture, indices);
		initTextures();
	}

	public static void deleteAsset() {
		model = null;
	}
}
