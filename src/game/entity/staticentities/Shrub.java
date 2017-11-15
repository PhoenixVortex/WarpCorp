package game.entity.staticentities;

import org.joml.Matrix4f;

import engine.Camera;
import engine.gfx.Animation;
import engine.gfx.Shader;
import engine.gfx.Window;
import game.entity.Transform;
import game.gfx.Assets;
import game.planet.Planet;

public class Shrub extends StaticEntity {

	public static Shrub shrub = new Shrub(new Animation(1,1,"shrub"));

	public Shrub(Shrub s) {
		super(s.transform, s.texture);
		solid = s.solid;
	}
	
	public Shrub(Animation tex) {
		super(new Transform(), tex);
		solid = false;
	}

	public void update(float delta, Window window, Camera camera, Planet planet) {
		
	}

	public Shrub setSolid() {
		solid = true;
		return this;
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
