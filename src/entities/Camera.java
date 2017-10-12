package entities;

import org.newdawn.slick.Input;

import main.Main;
import mapManager.MapLoader;

public class Camera{

	private int offsetMaxX;
	private int offsetMaxY;
	
	private int offsetMinX = 0;
	private int offsetMinY = 0;
	
	private int camX;
	private int camY;
	
	private int screenHeight;
	
	private float cameraSpeed = 0.1f;
	
	private MapLoader mapLoader;
	
	private Input input;
	
	private Player player;
	
	public Camera(MapLoader mapLoader, Player player) {
		
		this.mapLoader = mapLoader;
		this.player = player;
		
		input = new Input(Main.getWindowHeight());
		
		// Determine the actual dimensions of the world.
		float worldWidth = mapLoader.getMapWidth() * mapLoader.getTileWidth();
		float worldHeight = mapLoader.getMapHeight() * mapLoader.getTileHeight();
		
		// Work out the max XY offset the camera is allowed.
		this.offsetMaxX = (int) (worldWidth - Main.getWindowWidth());
		this.offsetMaxY = (int) (worldHeight - Main.getWindowHeight());
		
		// Give the camera an initial starting position.
		camX = Main.getWindowWidth() / 2;
		camY = Main.getWindowHeight() / 2;
	}
	
	public void move(int delta) {
			
		System.out.println(player.getCurrentCameraDistance());
		
		if(input.isKeyDown(Input.KEY_RIGHT)) {
			
			// Player is in the middle of the screen.
			if(player.getCurrentCameraDistance() >= player.getCameraDistance()) {
				
				this.camX += cameraSpeed * delta;	
			}
		}
		
		else if(input.isKeyDown(Input.KEY_LEFT)) {
			
			if(player.getCurrentCameraDistance() <= 50) {
				
				this.camX -= cameraSpeed * delta;
			}
			
		}
	}
	
	public int getX() { return this.camX; }
	
	public int getY() { return this.camY; }
}
