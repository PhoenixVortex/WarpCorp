package game;

import engine.AbstractGame;
import engine.Launcher;
import engine.gfx.Renderer;
import engine.gui.Gui;
import game.entity.Transform;
import game.gfx.Assets;
import game.planet.Planet;
import game.player.Player;

public class GameManager extends AbstractGame {

	private Planet prison;
	private Player player;
	private Gui gui;
	
	public void init(Launcher l) {
		Assets.initAssets();
		player = new Player(new Transform());
		prison = new Planet(player, "jungle");
		prison.calculateView(l.getWindow());
		
		gui = new Gui(l.getWindow());
	}

	public void update(Launcher l, float delta) {
		prison.update(l, delta);
		prison.correctCamera(l.getCamera(), l.getWindow());

		gui.resizeCamera(l.getWindow());
		prison.calculateView(l.getWindow());
	}

	public void render(Launcher l, Renderer r) {
		prison.render(r, l.getShader(), l.getCamera(), l.getWindow());
		gui.render();
	}

	public static void main(String[] args) {
		new Launcher(new GameManager(), 1600, 900, false);
		
	}
	
}
