package game.entity.staticentities;

import engine.Camera;
import engine.gfx.Animation;
import engine.gfx.Window;
import game.entity.Transform;
import game.planet.Planet;

public class Tree extends StaticEntity {

	public static Tree tree = new Tree(new Animation(1,1,"tree"));
	
	public Tree(Tree t) {
		super(t.transform, t.texture);
		solid = t.solid;
	}
	
	public Tree(Animation tex) {
		super(new Transform(), tex);
		
	}

	public void update(float delta, Window window, Camera camera, Planet planet) {
		
	}

	public Tree setSolid() {
		solid = true;
		return this;
	}
}
