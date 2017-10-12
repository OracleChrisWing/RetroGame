package toolbox;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public class ImageConverter {

	
	public static Image convertToImage(BufferedImage buffImage) {
		
		Texture tex;
		Image image = null;
		
		try {
			
			tex = BufferedImageUtil.getTexture("", buffImage);
			
			image = new Image(tex.getImageWidth(), tex.getImageHeight());
			
			image.setTexture(tex);
			
		}
		catch (IOException e) {

			e.printStackTrace();
			
		}
		catch (SlickException e) {
			
			e.printStackTrace();
		}
		
		return image;
	}
}
