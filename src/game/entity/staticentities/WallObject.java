package game.entity.staticentities;

import engine.Camera;
import engine.gfx.Animation;
import engine.gfx.Window;
import game.entity.Transform;
import game.planet.Planet;

public class WallObject extends StaticEntity {

	public WallObject(Animation tex) {
		super(new Transform(), tex);
		
	}

	public void update(float delta, Window window, Camera camera, Planet planet) {
		
	}
	
}
