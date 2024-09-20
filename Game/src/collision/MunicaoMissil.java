package collision;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MunicaoMissil {
	private int x, y;
	private Image image;
	boolean visible;
	private int width, height;


	public MunicaoMissil(int x, int y) {
		ImageIcon ii = new ImageIcon(getClass().getResource("municaoMissil.jpg"));
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void move(){
		if (x < 0) {
			x = 650;
		}
		x -= 1;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
