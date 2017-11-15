package engine.gui;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import engine.Camera;
import engine.collision.AABB;
import engine.gfx.Shader;
import engine.gfx.TextureSheet;
import game.gfx.Assets;

public class Button {

	private static final int STATE_IDLE = 0;
	private static final int STATE_SELECTED = 1;
	private static final int STATE_CLICKED = 2;
	private AABB bounding_box;
	private static Matrix4f transform = new Matrix4f();
	
	private int selected_state;
	
	public Button(Vector2f position, Vector2f scale) {
		this.bounding_box = new AABB(position, scale);
	}
	
	public void render(Camera camera, TextureSheet sheet, Shader shader) {
		Vector2f position = bounding_box.getCenter(), scale = bounding_box.getHalf_extent();
		
		transform.identity().translate(position.x, position.y, 0).scale(scale.x, scale.y, 1);
		shader.setUniform("projection", camera.getProjection().mul(transform));
		sheet.bindTile(shader, 0, 0);
		Assets.getModel().render();
		
		
	}
	
}
