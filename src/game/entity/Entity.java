package game.entity;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Camera;
import engine.collision.AABB;
import engine.collision.Collision;
import engine.gfx.Animation;
import engine.gfx.Shader;
import engine.gfx.Window;
import game.gfx.Assets;
import game.planet.Planet;

public abstract class Entity {

	protected AABB bounding_box;
	protected Animation texture;
	protected Transform transform;
	protected boolean solid;

	public Entity(Animation animation, Transform transform) {
		this.texture = animation;

		this.transform = transform;

		bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y),
				new Vector2f(transform.scale.x, transform.scale.y));
		solid = false;
	}

	public abstract void update(float delta, Window window, Camera camera, Planet planet);

	public void collideWithTiles(Planet planet) {
		AABB[] boxes = new AABB[25];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i + j * 5] = planet.getTileBoundingBox((int)(transform.pos.x - 5/2) + j, (int)(-transform.pos.y - 5/2) + i);
			}
		}

		AABB box = null;
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) {
				if (box == null)
					box = boxes[i];

				Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

				if (length1.lengthSquared() > length2.lengthSquared()) {
					box = boxes[i];
				}
			}
		}
		if (box != null) {
			Collision data = bounding_box.getCollision(box);
			if (data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}

			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) {
					if (box == null)
						box = boxes[i];

					Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

					if (length1.lengthSquared() > length2.lengthSquared()) {
						box = boxes[i];
					}
				}
			}

			data = bounding_box.getCollision(box);
			if (data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}
		}

	}

	public void collideWithEntity(Entity entity) {
		Collision collision = bounding_box.getCollision(entity.bounding_box);

		if (collision.isIntersecting) {
			collision.distance.x/=2;
			collision.distance.y/=2;
			
			bounding_box.correctPosition(entity.bounding_box, collision);
			transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y, 0);

			entity.bounding_box.correctPosition(bounding_box, collision);
			entity.transform.pos.set(entity.bounding_box.getCenter().x, entity.bounding_box.getCenter().y, 0);
		}
	}

	public Transform getTransform() {
		return transform;
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

	public void move(Vector2f direction) {
		transform.pos.add(new Vector3f(direction, 0));

		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
	}

	public AABB getBoundingBox() {
		return bounding_box;
	}

	public boolean isSolid() {
		return solid;
	}
	
	public Animation getTexture() {
		return texture;
	}
}
