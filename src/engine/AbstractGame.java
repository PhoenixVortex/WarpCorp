package engine;

import engine.gfx.Renderer;

public abstract class AbstractGame {

	public abstract void init(Launcher l);
	public abstract void update(Launcher l, float delta);
	public abstract void render(Launcher l, Renderer r);
	
}
