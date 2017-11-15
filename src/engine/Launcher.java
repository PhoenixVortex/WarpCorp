package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import engine.gfx.Renderer;
import engine.gfx.Shader;
import engine.gfx.Window;
import game.GameManager;

public class Launcher {

	private Window window;
	private Camera camera;
	private Shader shader;
	private Renderer r;
	private int width, height;

	private void init(boolean fullscreen) {
		//Window.setCallbacks();//uncoment if debugging
		if (!glfwInit()) {
			throw new IllegalStateException("Failed tp initialize GLFW!");
		}
		window = new Window();
		window.setSize(width, height);
		if (fullscreen)
			window.setFullscreen(true);
		window.createWindow("WarpCorp v0.0.0");
		GL.createCapabilities();// Sets up openGLs drawing
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);

		camera = new Camera(window.getWidth(), window.getHeight());
		shader = new Shader("shader");
	}

	public Launcher(GameManager gm, int width, int height, boolean fullscreen) {
		if (fullscreen) {
			this.width = 1920;
			this.height = 1080;
		} else {
			this.width = width;
			this.height = height;
		}
		init(fullscreen);
		r = new Renderer();
		double frame_cap = 1.0 / 60.0;// seconds / frames

		double frame_time = 0;
		int frames = 0;

		double time = Timer.getTime();
		double unprocessed = 0;

		gm.init(this);

		while (!window.shouldClose()) {
			boolean can_render = false;

			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;

			time = time_2;

			while (unprocessed >= frame_cap) {
				if(window.hasResized()) {
					camera.setProjection(window.getWidth(), window.getHeight());
					glViewport(0,0, window.getWidth(), window.getHeight());
				}
				unprocessed -= frame_cap;
				can_render = true;

				// UPDATE

				if (window.getInput().isKeyDown(GLFW_KEY_ESCAPE))// How to do input
					glfwSetWindowShouldClose(window.getWindow(), true);

				gm.update(this, (float) frame_cap);

				window.update();
				// END UPDATE
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS:" + frames);
					frames = 0;
				}

			}

			if (can_render) {// RENDER ************************************
				glClear(GL_COLOR_BUFFER_BIT);// Clear screen

				gm.render(this, r);

				window.swapBuffers();// Move back buffer to front and front buffer to back
				frames++;
			}
		}

		glfwTerminate();
	}

	public Window getWindow() {
		return window;
	}

	public Camera getCamera() {
		return camera;
	}

	public Shader getShader() {
		return shader;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
