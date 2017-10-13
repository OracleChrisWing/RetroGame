package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.Main;
import toolbox.GameState;

public class Info {
	
	private final String RES = "res\\Assets\\Gui";
	
	private Image backgroundImage;
	private Vector2f backgroundPosition = new Vector2f(0, 0);
	private Vector2f backgroundDimension = new Vector2f(Main.getWindowWidth(), Main.getWindowHeight());
	
	private Image infoText;
	private Vector2f infoTextPosition = new Vector2f((Main.getWindowWidth() / 2) - 250, 
													(Main.getWindowHeight() / 2) - 250);
	private Vector2f infoTextDimension = new Vector2f(508, 318);
	
	
	public Info() {
		
		try {
			
			backgroundImage = new Image(RES + "\\InfoBackGround.png");
			infoText = new Image(RES + "\\infoText.png");
			
		} catch (SlickException e) {
			
			e.printStackTrace();
		}	
	}
	
	public void displayInfo() {
		
		backgroundImage.draw(backgroundPosition.x, backgroundPosition.y, backgroundDimension.x, backgroundDimension.y);
		infoText.draw(infoTextPosition.x, infoTextPosition.y, infoTextDimension.x, infoTextDimension.y);
	}
	
	public void updateInfo(GameContainer c) {
		
		Input input_ = c.getInput();
		
		if(input_.isKeyPressed(Input.KEY_ESCAPE)) {
			
			Main.updateGameState(GameState.MENU);
		}
	}
}
