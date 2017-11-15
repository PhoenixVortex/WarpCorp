package game.entity.staticentities;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Camera;
import engine.collision.AABB;
import engine.collision.Collision;
import engine.gfx.Animation;
import engine.gfx.Window;
import game.entity.Entity;
import game.entity.Transform;
import game.planet.Planet;

public abstract class StaticEntity extends Entity{

	public StaticEntity(Transform transform, Animation tex) {
		super(tex, transform);

		bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y),
				new Vector2f(transform.scale.x, transform.scale.y));
	}

	public abstract void update(float delta, Window window, Camera camera, Planet planet);

	public void setPos(float x, float y) {
		this.transform.pos.set(new Vector3f(x,y,0));
	}


	public void collideWithEntity(Entity entity) {
		Collision collision = bounding_box.getCollision(entity.getBoundingBox());
		
		if (collision.isIntersecting) {
			collision.distance.x/=2;
			collision.distance.y/=2;
			
			bounding_box.correctPosition(entity.getBoundingBox(), collision);
			transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y, 0);

			entity.getBoundingBox().correctPosition(bounding_box, collision);
			entity.getTransform().pos.set(entity.getBoundingBox().getCenter().x, entity.getBoundingBox().getCenter().y, 0);
		}
	}
	
}
