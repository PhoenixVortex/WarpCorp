package engine.gfx;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.io.Input;

public class Window {

	private long window;
	private int width, height;
	private boolean fullscreen;
	private boolean hasResized;
	private GLFWWindowSizeCallback windowSizeCallback;
	
	
	private Input input;
	
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	private void setLocalCallbacks() {
		windowSizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int w, int h) {
				width = w;
				height = h;
				hasResized = true;
			}
		};
		
		glfwSetWindowSizeCallback(window, windowSizeCallback);
	}
	
	public Window() {
		setSize(640, 480);
		setFullscreen(false);
		hasResized = false;
	}
	
	public void update() {
		hasResized = false;
		input.update();
		glfwPollEvents();
	}
	
	public boolean hasResized() {
		return hasResized;
	}

	public void createWindow(String title) {
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		
		if(window == 0)
			throw new IllegalStateException("Failed to create window!");
		
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vid.width()-width)/2, (vid.height()-height)/2);
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
		setLocalCallbacks();
	}
	
	public void cleanUp() {
		glfwFreeCallbacks(window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	public long getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
	
}
