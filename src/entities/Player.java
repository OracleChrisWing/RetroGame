package entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import main.Main;
import toolbox.Direction;

public class Player {

	// The current movement direction.
	private Direction currentDirection;
	
	// Path to the player sprite asset.
	private final String RES = "res\\Assets\\";
	
	// Distance between camera yPos and game ground (sea-level).
	private final int GROUND_OFFSET = 451;
	
	// The extra speed the player moves at when running.
	private final float RUN_MODIFIER = 0.2f;
	
	// The distance the camera needs to be away from the player before it kicks in.
	private final int CAMERA_DISTANCE = Main.getWindowWidth() / 2;
	
	// The current distance between the player and camera.
	private int currentCameraDistance;
	
	// The scale factor of the character.
	private float scale = 0.3f;
	
	// The position vector of the player.
	private Vector2f position;
	
	// The players XY velocity component.
	private Vector2f velocity = new Vector2f(0.1f, 0.1f);
	
	// The vertical speed of the player.
	private float verticalSpeed = 0.0f;
	
	// The input handler.
	private Input input;
	
	// The relative spritesheets for different directions.
	private SpriteSheet spriteSheetRight;
	private SpriteSheet spriteSheetLeft;

	private Animation walkingAnimationRight;
	private Animation walkingAnimationLeft;
	
	private Animation runningAnimationRight;
	private Animation runningAnimationLeft;
	
	// The static frame to be displayed when the player is not moving.
	private Image staticFrame;
	
	// The width and height of the player.
	private int playerWidthRight = 90;
	private int playerHeightRight = 175;
	
	// Logical variables.
	private boolean isMoving;
	private boolean isRunning;
	private boolean isJumping = false;
	private boolean isGrounded = true;
	
	// The games camera object.
	Camera camera;
	
	public Player() {
		
		position = new Vector2f((Main.getWindowWidth() / 2) + 20, (Main.getWindowHeight() / 2));
		
		input = new Input(Main.getWindowHeight());
		
		currentCameraDistance = 0;
	}
	
	public void load() {
		
		try {
			
			// The location of the players sprite sheet.
			spriteSheetRight = new SpriteSheet(RES + "\\mario_character_R.png", playerWidthRight, playerHeightRight, 4);
			spriteSheetLeft = new SpriteSheet(RES + "\\mario_character_L.png", playerWidthRight, playerHeightRight, 12);
			
			// Creates the walking animation object for the RIGHT direction.
			walkingAnimationRight = new Animation();
			
			// Setting auto-time update to true, and adding in the correct frames into the animation buffer.
			walkingAnimationRight.setAutoUpdate(true);
			walkingAnimationRight.addFrame(spriteSheetRight.getSprite(0,  0).getScaledCopy(scale), 200);
			walkingAnimationRight.addFrame(spriteSheetRight.getSprite(1,  0).getScaledCopy(scale), 200);
			
			// Creates the running animation object for the RIGHT direction.
			runningAnimationRight = new Animation();
			
			// Setting auto-time update to true, and adding in the correct frames into the animation buffer.
			runningAnimationRight.setAutoUpdate(true);
			runningAnimationRight.addFrame(spriteSheetRight.getSprite(0,  0).getScaledCopy(scale), 100);
			runningAnimationRight.addFrame(spriteSheetRight.getSprite(1,  0).getScaledCopy(scale), 100);
			
			// Creates the walking animation object for the LEFT direction.
			walkingAnimationLeft = new Animation();
			
			walkingAnimationLeft.setAutoUpdate(true);
			walkingAnimationLeft.addFrame(spriteSheetLeft.getSprite(4, 0).getScaledCopy(scale), 200);
			walkingAnimationLeft.addFrame(spriteSheetLeft.getSprite(3, 0).getScaledCopy(scale), 200);
			
			// Creates the running animation object for the LEFT direction.
			runningAnimationLeft = new Animation();
			
			runningAnimationLeft.setAutoUpdate(true);
			runningAnimationLeft.addFrame(spriteSheetLeft.getSprite(4, 0).getScaledCopy(scale), 100);
			runningAnimationLeft.addFrame(spriteSheetLeft.getSprite(3, 0).getScaledCopy(scale), 100);
			
			// This is the static frame which will display when the player is not moving. (default value before movement actually starts)
			staticFrame = spriteSheetRight.getSprite(0, 0).getScaledCopy(scale);
		}
		catch(SlickException e) {
			
			e.printStackTrace();
		}
	}
	
	public void move(int delta, GameContainer c) {
		
		Input input_ = c.getInput();
		
		// Player is not moving.
		isMoving = false;
		
		// Player is moving and running.
		isRunning = false;
		
		// Right arrow key pressed.
		if(input.isKeyDown(Input.KEY_RIGHT)) {
			
			// The player is moving RIGHT.
			this.currentDirection = Direction.RIGHT;
			
			// The static frame for the current direction.
			this.staticFrame = spriteSheetRight.getSprite(0, 0).getScaledCopy(scale);
			
			// Player is running
			if(input.isKeyDown(Input.KEY_LSHIFT)) {
				
				isMoving = true;
				isRunning = true;
				
				this.position.x += (this.velocity.x + RUN_MODIFIER) * delta;
				
				if(this.currentCameraDistance < this.CAMERA_DISTANCE) {
				
					this.currentCameraDistance += (this.velocity.x + RUN_MODIFIER) * delta;
				}
			}
			
			// The player is walking.
			else {
				
				isMoving = true;
				isRunning = false;
				this.position.x += this.velocity.x * delta;
				
				if(this.currentCameraDistance < this.CAMERA_DISTANCE) {
					
					this.currentCameraDistance += this.velocity.x * delta;
				}	
			}
		}
		// Left arrow key pressed.
		else if(input.isKeyDown(Input.KEY_LEFT)) {
			
			// The player is moving RIGHT.
			this.currentDirection = Direction.LEFT;
			
			// The static frame for the current direction.
			this.staticFrame = spriteSheetLeft.getSprite(4, 0).getScaledCopy(scale);
			
			// Player is running
			if(input.isKeyDown(Input.KEY_LSHIFT)) {
				
				isMoving = true;
				isRunning = true;
				
				this.position.x -= (this.velocity.x + RUN_MODIFIER) * delta;
				
				if(this.currentCameraDistance > 50) {
					
					this.currentCameraDistance -= (this.velocity.x + RUN_MODIFIER) * delta;
				}
			}
			
			// The player is walking.
			else {
				
				isMoving = true;
				isRunning = false;
				
				this.position.x -= this.velocity.x * delta;
				
				if(this.currentCameraDistance > 50) {
					
					this.currentCameraDistance -= this.velocity.x * delta;
				}	
			}
		}
		
		/* Jumping logic (very basic but will do for now, also need to add a jumping animation)*/
		if(input_.isKeyPressed(Input.KEY_SPACE) && !isJumping) {
			
			isJumping = true;
			isGrounded = false;
			
			verticalSpeed = -0.3f * delta;
		}
		if(isJumping) {
			
			verticalSpeed += 0.01f * delta;
		}
		
		if(this.position.y > 430 && isJumping) {
			
			this.position.y += verticalSpeed;
		}
		else if(!isGrounded) {
			
			isJumping = false;
			this.position.y -= verticalSpeed;	
			
			if(this.position.y >= 490) {
				
				isGrounded = true;
			}
		}
	}
	
	public void draw() {
	
		// If the player is moving, render the animation.
		if(isMoving) {
			
			if(isRunning) {
				
				if(this.currentDirection == Direction.RIGHT) {
					
					runningAnimationRight.draw(position.x, position.y + GROUND_OFFSET);
				}
				else {
					
					runningAnimationLeft.draw(position.x, position.y + GROUND_OFFSET);
				}
			}
			else {
				
				if(this.currentDirection == Direction.RIGHT) {
					
					walkingAnimationRight.draw(position.x, position.y + GROUND_OFFSET);
				}
				else {
					
					walkingAnimationLeft.draw(position.x, position.y + GROUND_OFFSET);
				}
			}
		}
		// If the player is static, render a single frame.
		else {
			
			staticFrame.draw(position.x, position.y + GROUND_OFFSET);
		}
		
	}
	
	public void update(long delta) {
		
		// Updates the two animations.
		runningAnimationRight.update(delta);
		walkingAnimationRight.update(delta);
		
		runningAnimationLeft.update(delta);
		walkingAnimationLeft.update(delta);
	}
	
	public Vector2f getPosition() { return this.position; }
	
	public int getCurrentCameraDistance() { return this.currentCameraDistance; }
	
	public int getCameraDistance() { return this.CAMERA_DISTANCE; }
}
