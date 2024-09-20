package collision;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Craft {
	private String craft = "nave.jpg";
	private int dx;
	private int dy;
	private int x;
	private int y;
	private int height;
	private int width;
	private boolean visible;
	private Image image;
	private ArrayList<Missile> misseis;
	private ArrayList<Bomba> bombas;
	private int qtdBombas = 30;
	private int qtdMisseis = 100;

	public Craft() {
		ImageIcon ii = new ImageIcon(getClass().getResource(craft));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		misseis = new ArrayList<Missile>();
		bombas = new ArrayList<Bomba>();
		visible = true;
		x = 40;
		y = 60;
	}

	public void move() {
		x += dx;
		y += dy;

		if (x < 1) {
			x = 1;
		}
		if (y < 1) {
			y = 1;
		}
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

	public Image getImage() {
		return image;
	}

	public ArrayList<Missile> getMisseis() {
		return misseis;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
			fire();
		}
		if (key == KeyEvent.VK_CONTROL) {
			bomba();
		}
		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}
		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}
		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
	}

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}
		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}

	public void fire() {
		if (qtdMisseis > 0) {
			misseis.add(new Missile(x + width, y + height / 2));
			qtdMisseis--;
		}
	}
	
	public void recarregaMissil(){
		qtdMisseis++;

	}
	
	public void recarregaBomba(){
		qtdBombas++;
	}

	public void bomba() {
		if (qtdBombas > 0) {
			bombas.add(new Bomba(x + width, y + height / 2));
			qtdBombas--;
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getQtdBombas() {
		return qtdBombas;
	}

	public int getQtdMisseis() {
		return qtdMisseis;
	}


}
