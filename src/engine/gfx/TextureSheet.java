package engine.gfx;

import org.joml.Matrix4f;

public class TextureSheet {

	private Texture texture;
	private Matrix4f scale;
	private Matrix4f translation;
	private int amountOfTiles;
	
	public TextureSheet(String path, int amountOfTiles) {
		texture = new Texture(path+".png");
		
		scale = new Matrix4f().scale(1.0f / (float)amountOfTiles);
		translation = new Matrix4f();
		
		this.amountOfTiles = amountOfTiles;
	}
	
	public void bindTile(Shader shader, int x, int y) {
		scale.translate(x, y, 0, translation);
		shader.setUniform("sampler", 0);
		shader.setUniform("texModifier", translation);
		texture.bind(0);
	}
	
	public void bindTile(Shader shader, int i) {
		int x = i % amountOfTiles;
		int y = i / amountOfTiles;
		
		bindTile(shader, x, y);
	}
	
}
