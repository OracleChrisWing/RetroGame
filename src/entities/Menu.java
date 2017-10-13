package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.Main;
import toolbox.GameState;

public class Menu {

	private enum pointerState{
		
		UP, DOWN
	};
	
	// Initial state of pointer.
	pointerState state = pointerState.UP;
	
	private final String RES = "res\\Assets\\Gui";
	
	private Image backgroundImage;
	private Vector2f backgroundPosition = new Vector2f(0, 0);
	private Vector2f backgroundDimension = new Vector2f(Main.getWindowWidth(), Main.getWindowHeight());
	
	private Image makersImage;
	private Vector2f makersPosition = new Vector2f((Main.getWindowWidth() / 2) - 100, 
													(Main.getWindowHeight() / 2) + 250);
	private Vector2f makersDimension = new Vector2f(313, 57);
	
	private Image playerOneButton;
	private Vector2f playerOneButtonPosition = new Vector2f((Main.getWindowWidth() / 2) - 100, 
													(Main.getWindowHeight() / 2) + 175);
	private Vector2f playerOneButtonDimension = new Vector2f(312, 32);
	
	private Image menuPointer;
	private Vector2f menuPointerPosition = new Vector2f((Main.getWindowWidth() / 2) - 140, 
														(Main.getWindowHeight() / 2) + 175);
	private Vector2f menuPointerDimension = new Vector2f(22, 24);
	
	public Menu() {
		
		try {
			
			backgroundImage = new Image(RES + "\\MenuBackGround.png");
			makersImage = new Image(RES + "\\makers_button.png");
			playerOneButton = new Image(RES + "\\1_player_button.png");
			menuPointer = new Image(RES + "\\selector_icon.png");
			
		} catch (SlickException e) {
			
			e.printStackTrace();
		}	
	}
	
	public void displayMenu(){
		
		backgroundImage.draw(backgroundPosition.x, backgroundPosition.y, backgroundDimension.x, backgroundDimension.y);
		makersImage.draw(makersPosition.x, makersPosition.y, makersDimension.x, makersDimension.y);
		playerOneButton.draw(playerOneButtonPosition.x, playerOneButtonPosition.y, playerOneButtonDimension.x, playerOneButtonDimension.y);
		menuPointer.draw(menuPointerPosition.x, menuPointerPosition.y, menuPointerDimension.x, menuPointerDimension.y);
	}
	
	public void updateMenu(GameContainer c) {

		Input input_ = c.getInput();
		
		if(input_.isKeyPressed(Input.KEY_DOWN)){
			
			if(state == pointerState.UP) {
				
				menuPointerPosition.y += 80;
				state = pointerState.DOWN;
			}
			else if(state == pointerState.DOWN){
				
				menuPointerPosition.y -= 80;
				state = pointerState.UP;
			}
		}
		else if(input_.isKeyPressed(Input.KEY_UP)){
			
			if(state == pointerState.UP) {
				
				menuPointerPosition.y += 80;
				state = pointerState.DOWN;
			}
			else if(state == pointerState.DOWN){
				
				menuPointerPosition.y -= 80;
				state = pointerState.UP;
			}
		}
		else if(input_.isKeyPressed(Input.KEY_ENTER)) {
			
			if(state == pointerState.UP) {
				
				// Starts the loading state.
				Main.updateGameState(GameState.LOAD);
			}
			else {
				
				System.out.println("Display information screen");
			}
		}
	}
}
