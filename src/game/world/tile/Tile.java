package game.world.tile;

public class Tile {

	public static Tile tiles[] = new Tile[255];
	public static byte not = 0;
	
	public static final Tile prison_floor = new Tile("prison_floor");
	public static final Tile prison_wall_base_0 = new Tile("prison_wall_base_0").setSolid();
	public static final Tile prison_wall_base_1 = new Tile("prison_wall_base_1").setSolid();
	public static final Tile prison_wall_middle = new Tile("prison_wall_middle").setSolid();
	public static final Tile prison_wall_top_back = new Tile("prison_wall_top_back").setSolid();
	public static final Tile prison_wall_top_side = new Tile("prison_wall_top_side").setSolid();
	public static final Tile prison_wall_top_face_0 = new Tile("prison_wall_top_face").setSolid();
	public static final Tile prison_wall_void = new Tile("prison_wall_void").setSolid();
	public static final Tile prison_wall_top_bottom_left_corner = new Tile("prison_wall_top_bottom_left_corner");
	public static final Tile prison_wall_top_face_1 = new Tile("prison_wall_top_face_1");
	public static final Tile prison_wall_top_face_2 = new Tile("prison_wall_top_face_2");
	
	private boolean solid;
	private byte id;
	private String texture;
	
	public Tile(String texture) {
		this.id = not;
		not++;
		this.texture = "tiles/"+texture;
		if(tiles[id] != null) 
			throw new IllegalStateException("Tiles at "+ id + "is already being used!");
		tiles[id] = this;
		solid = false;
	}

	public Tile setSolid() {
		this.solid = true;
		return this;
	}
	
	public byte getId() {
		return id;
	}

	public String getTexture() {
		return texture;
	}

	public boolean isSolid() {
		return solid;
	}

}
