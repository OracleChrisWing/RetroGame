package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Camera;
import entities.Menu;
import entities.Player;
import mapManager.MapData;
import mapManager.MapLoader;
import sprites.SpriteSheet;
import toolbox.GameState;

public class Main extends BasicGame {
	
	// The initial state of the game.
	private static GameState gameState = GameState.MENU;
	
	// Set the dimensions of the game window.
	private static final int WIDTH = 1260;
	private static final int HEIGHT = 980;
	
	// Set the game window title.
	private static final String TITLE = "Retro Mario Game - Chris Wing";
	
	// Used for working out DELTA.
	private long lastUpdateTime = System.nanoTime();
	
	// DELTA time.
	private long delta;
	
	// Initialise any classes that the game needs.
	private Menu menu;
	private SpriteSheet spriteSheet;
	private MapLoader mapLoader;
	private MapData mapData;
	private Camera camera;
	private Player player;
	
	public Main() {
		
		super(TITLE);
		
		// Assign the class declarations.
		mapData = new MapData();
		mapLoader = new MapLoader(mapData, "map_v4");
		// 
		
		player = new Player();
		
		camera = new Camera(mapLoader, player);
		
		spriteSheet = new SpriteSheet(mapLoader);
	}
	
	// The games main method, auto-executes when game starts.
	public static void main(String[] args) {
		
		try {
				
			AppGameContainer agc = new AppGameContainer(new Main());
			
			agc.setVSync(false);
			agc.setDisplayMode(WIDTH, HEIGHT, false);
			agc.start();
		}
		catch(SlickException e) {
			
			e.printStackTrace();
		}
	}

	// Render method, ONLY drawing code here.
	@Override
	public void render(GameContainer c, Graphics g) throws SlickException {
		
		if(gameState == GameState.MENU) {
			
			menu.displayMenu();
			
		}
		else if(gameState == GameState.GAME) {
			
			// Translates the camera matrix.
			g.translate(-camera.getX(), -camera.getY());
			
			// The temp Image class object that is reused for each tile in the game.
			Image tile;
			
			// The width and height of the game tiles.
			int tileWidth = mapLoader.getTileWidth();
			int tileHeight = mapLoader.getTileHeight();
			
			// Here, Z = Layer number, Y = y-comp of array, X = x-comp of array.
			for(int z = mapData.getMapData().size() - 1; z >= 0; z--) {
				
				for(int y = 0; y < mapData.getMapData().get(z)[0].length; y++) {
					
					for(int x = 0; x < mapData.getMapData().get(z).length; x++) {
						
						// Get the correct tile data.
						tile = spriteSheet.getSprites()[mapData.getMapData().get(z)[x][y]];
					
						// Draw the tile.
						tile.draw(x * tileWidth, y * tileHeight);
					}
				}
			}
			
			// Draws the player to the screen.
			player.draw();	
		}		
	}

	// ONLY init code here, this will only be run ONCE (on game load).
	@Override
	public void init(GameContainer c) throws SlickException {
		
		// We load the menu first.
		if(gameState == GameState.MENU) {
			
			menu = new Menu();
		}
	}

	// UPDATE code only, used for game logic, player control, camera movement etc.
	@Override
	public void update(GameContainer c, int delta) throws SlickException {
		
		if(gameState == GameState.MENU) {
			
			menu.updateMenu(c);
		}
		else if(gameState == GameState.LOAD) {
			
			// Load up the spritesheet and game data first.#
			mapLoader.loadMap();
			spriteSheet.load();
			
			player.load();	
			
			// Assets loaded, now we proceed to game.
			gameState = GameState.GAME;
		}
		else if(gameState == GameState.GAME) {
			
			this.delta = delta;
		
			camera.move(delta);
			player.move(delta, c);
			player.update(delta);
		}
	}
	
	public static int getWindowWidth() { return WIDTH; }
	
	public static int getWindowHeight() { return HEIGHT; }
	
	public static void updateGameState(GameState state) { gameState = state; }
}
