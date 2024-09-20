package collision;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Alien {
	private String alien = "alien.jpg";
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean visible;
	private Image image;

	public Alien(int x, int y) {
		ImageIcon ii = 
				new ImageIcon(getClass().getResource(alien));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		this.x = x;
		this.y = y;
	}

	public void move() {
		if (x < 0) {
			x = 650;
		}
		x -= 1;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

}
