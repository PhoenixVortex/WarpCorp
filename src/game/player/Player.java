package game.player;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import engine.Camera;
import engine.gfx.Animation;
import engine.gfx.Shader;
import engine.gfx.Window;
import game.entity.Entity;
import game.entity.Transform;
import game.gfx.Assets;
import game.planet.Planet;

public class Player extends Entity {

	public Player(Transform transform) {
		super(new Animation(1, 1, "player"), transform);
	}

	public void update(float delta, Window window, Camera camera, Planet planet) {
		float speed = 4.5f * delta;
		Vector2f movement = new Vector2f();
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A))
			movement.add(new Vector2f(-speed, 0));
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D))
			movement.add(new Vector2f(speed, 0));
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
			movement.add(new Vector2f(0, -speed));
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
			movement.add(new Vector2f(0, speed));

		move(movement);
		camera.getPosition().lerp(transform.pos.mul(-planet.getScale(), new Vector3f()), 0.05f);
	}

	public void render(Shader shader, Camera camera, Planet planet) {
		Matrix4f target = camera.getProjection();
		target.mul(planet.getPlanetMatrix());

		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		texture.bind(0);
		Assets.getModel().render();

	}
	
}
