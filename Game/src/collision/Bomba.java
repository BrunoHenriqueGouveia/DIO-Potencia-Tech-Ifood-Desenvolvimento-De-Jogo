package collision;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bomba {
	private int x, y;
	private Image image;
	boolean visible;
	private int width, height;

	private final int BOARD_WIDTH = 640;
	private final int MISSILE_SPEED = 4;

	public Bomba(int x, int y) {
		ImageIcon ii = new ImageIcon(getClass().getResource("bomba.jpg"));
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
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public void move() {
		x += MISSILE_SPEED;
		if (x > BOARD_WIDTH)
			visible = false;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
