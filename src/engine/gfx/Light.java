package engine.gfx;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import game.entity.Transform;
import game.gfx.Assets;
import game.planet.Planet;

public class Light {

	private Transform transform;
	private Texture texture;
	private float str;
	private float radius;
	private LightShader ls;
	private Vector3f color;
	
	public Light(Vector3f color, float str, float radius) {
		transform = new Transform();
		this.color = new Vector3f(color);
		this.str = str;
		this.radius = radius;
		texture = new Texture("light");
		ls = new LightShader("shaderLight");
	}
	
	public void render(Camera camera, Planet planet) {
		Matrix4f target = camera.getProjection();
		target.mul(planet.getPlanetMatrix());

		ls.bind();
		ls.setUniform("sampler", 0);
		ls.setUniform("projection", transform.getProjection(target));
		ls.setUniform("red", color.x);
		ls.setUniform("green", color.y);
		ls.setUniform("blue", color.z);
		ls.setUniform("radius", radius);
		ls.setUniform("str", str);
		texture.bind(0);
		Assets.getModel().render();

	}
	
	public Transform getTransform() {
		return transform;
	}

	public float getStr() {
		return str;
	}

	public float getRadius() {
		return radius;
	}
	
	
	
}
