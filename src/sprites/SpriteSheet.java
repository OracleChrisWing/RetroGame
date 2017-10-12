package sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.Image;

import mapManager.MapLoader;
import toolbox.ImageConverter;

public class SpriteSheet {

	// The final value 
	public final int TILE_SIZE = 16;
	
	private final String RES = "res\\Assets\\";
	
	private BufferedImage spriteSheet;
	
	private BufferedImage[] sprites;
	
	private Image[] sprites_;
	
	private MapLoader mapLoader;
	
	public SpriteSheet(MapLoader mapLoader) {
		
		this.mapLoader = mapLoader;		
	}
	
	public void load() {
		
		// The width and height of the spritesheet in pixels.
		int sheetWidth = 0, sheetHeight = 0;
		
		// Gets tile dimensions from the map loader class.
		int tileWidth = mapLoader.getTileWidth();
		int tileHeight = mapLoader.getTileHeight();
			
		// Tries to open a stream to load in the sprite sheet.
		try {
			
			spriteSheet = ImageIO.read(new File(RES + "spritesheet.png"));
			
			sheetWidth = spriteSheet.getWidth();
			sheetHeight = spriteSheet.getHeight();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		// calculates the number of cols/ rows within the spritesheet.
		int cols = sheetWidth / tileWidth;
		int rows = sheetHeight / tileHeight;
		
		sprites = new BufferedImage[cols * rows];
		
		// Adds new sprites with correct ID values to the list of sprites ready for rendering.
		for(int i = 0; i < rows; i++) {
			
			for(int j = 0; j < cols; j++) {
				
				sprites[(i * cols) + j] = spriteSheet.getSubimage(
						j * tileWidth,
						i * tileHeight,
						tileWidth,
						tileHeight
						);
			}
		}
		
		convertAllSprites();
	}
	
	// Used to convert the tile type from BufferedImage to Image.
	private void convertAllSprites() {
		
		int spriteCount = sprites.length;
		
		sprites_ = new Image[spriteCount];
		
		for(int i = 0; i < spriteCount; i++) {
			
			sprites_[i] = ImageConverter.convertToImage(sprites[i]);
		}
		
	}
	
	public Image[] getSprites() { return sprites_;}
	
	
	public Image getSprite(int gridX, int gridY) {
		
		BufferedImage buffImage = spriteSheet.getSubimage(gridX * TILE_SIZE, gridY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	
		return ImageConverter.convertToImage(buffImage);
	}
}
