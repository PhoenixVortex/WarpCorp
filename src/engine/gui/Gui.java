package engine.gui;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Camera;
import engine.gfx.Shader;
import engine.gfx.TextureSheet;
import engine.gfx.Window;

public class Gui {

	private Shader shader;
	private Camera camera;
	private TextureSheet sheet;
	
	private Button temp;
	
	public Gui(Window window) {
		shader = new Shader("shaderGui");
		camera = new Camera(window.getWidth(), window.getHeight());
		sheet = new TextureSheet("button", 2);
		temp = new Button(new Vector2f(0,0), new Vector2f(128, 128));
	}
	
	public void resizeCamera(Window window) {
		camera.setProjection(window.getWidth(), window.getHeight());
	}
	
	public void render() {
		shader.bind();
		shader.setUniform("color", new Vector4f(1,1,1,1));
		temp.render(camera, sheet, shader);
	}
	
}
