package collision;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	Random rand = new Random();
	private int qtdAliens;
	private Timer timer;
	private Craft craft;
	private ArrayList<Alien> aliens;
	private boolean ingame;
	private int B_WIDTH;
	private int B_HEIGHT;
	private MunicaoMissil municaoMissil;
	private MunicaoBomba municaoBomba;
	private JButton btTentar;

	public Board(int aliens) {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.black);
		setDoubleBuffered(true);
		setSize(650, 550);
		ingame = true;
		craft = new Craft();
		qtdAliens = aliens;
		initAliens(qtdAliens);
		municaoMissil();
		municaoBomba();
		timer = new Timer(5, this);
		timer.start();
		
		btTentar = new JButton("TENTAR DE NOVO");
		btTentar.setBackground(Color.black);
		btTentar.setForeground(Color.white);
		btTentar.setSize(150,35);
	
		btTentar.addActionListener(listener);
	}

	public void municaoMissil() {
		municaoMissil = new MunicaoMissil(rand.nextInt(2000) + 500,
				rand.nextInt(550));
	}

	public void municaoBomba() {
		municaoBomba = new MunicaoBomba(rand.nextInt(2000) + 500,
				rand.nextInt(550));
	}

	public void addNotify() {
		super.addNotify();
		B_WIDTH = getWidth();
		B_HEIGHT = getHeight();
	}

	public void initAliens(int qtd) {
		aliens = new ArrayList<Alien>();
		for (int i = 0; i < qtd; i++) {
			aliens.add(new Alien( rand.nextInt(2000) + 500, rand.nextInt(550)));
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (ingame) {
			Graphics2D g2d = (Graphics2D) g;
			if (craft.isVisible())
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),
						this);

			ArrayList<Missile> ms = craft.getMisseis();
			ArrayList<Bomba> bs = craft.getBombas();
			for (int i = 0; i < ms.size(); i++) {
				Missile m = (Missile) ms.get(i);
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
			for (int i = 0; i < bs.size(); i++) {
				Bomba b = (Bomba) bs.get(i);
				g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}

			for (int i = 0; i < aliens.size(); i++) {
				Alien a = (Alien) aliens.get(i);
				if (a.isVisible())
					g2d.drawImage
					(a.getImage(), a.getX(), a.getY(), this);
			}

			if (municaoMissil.isVisible()) {
				g2d.drawImage(municaoMissil.getImage(), municaoMissil.getX(),
						municaoMissil.getY(), this);
			}

			if (municaoBomba.isVisible()) {
				g2d.drawImage(municaoBomba.getImage(), municaoBomba.getX(),
						municaoBomba.getY(), this);
			}

			g2d.setColor(Color.white);
			g2d.drawString("Restam: " + aliens.size() + " aliens", 5, 15);
			g2d.setColor(Color.yellow);
			g2d.drawString("Bombas: " + craft.getQtdBombas(), 150, 15);
			g2d.drawString("Mísseis: " + craft.getQtdMisseis(), 250, 15);
		} else {
			String msg = "******GAME OVER******";
			Font small = new Font("Helvetica", Font.BOLD, 30);
			FontMetrics metr = this.getFontMetrics(small);

			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
					B_HEIGHT / 2);				
		
			btTentar.setLocation((B_WIDTH - btTentar.getWidth()) / 2,B_HEIGHT / 2 + 50);
			add(btTentar);
	
		}
	}
	
	private ActionListener listener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {			
			
			qtdAliens = Integer.parseInt(JOptionPane.showInputDialog("Quantos aliens desta vez?"));
			while(true) {
				if (qtdAliens < 150 || qtdAliens > 1200) {
					JOptionPane.showMessageDialog(Board.this, "Informe uma quantidade entre 150 e 1200 aliens");
					qtdAliens = Integer.parseInt(JOptionPane.showInputDialog("Quantos aliens desta vez?"));
				}else{
					break;
				}
			}
			removeAll();
			initAliens(qtdAliens);
			craft = new Craft();
			municaoMissil();
			municaoBomba();
			ingame = true;
		}
	}; 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (aliens.size() == 0)
			ingame = false;

		ArrayList<Bomba> bs = craft.getBombas();
		for (int i = 0; i < bs.size(); i++) {
			Bomba b = (Bomba) bs.get(i);
			if (b.isVisible())
				b.move();
			else
				bs.remove(i);
		}

		ArrayList<Missile> ms = craft.getMisseis();
		for (int i = 0; i < ms.size(); i++) {
			Missile m = (Missile) ms.get(i);
			if (m.isVisible())
				m.move();
			else
				ms.remove(i);
		}
		for (int i = 0; i < aliens.size(); i++) {
			Alien a = (Alien) aliens.get(i);
			if (a.isVisible())
				a.move();
			else
				aliens.remove(i);
		}
		municaoBomba.move();
		municaoMissil.move();
		craft.move();

		checkCollisions();
		repaint();
	}

	private void checkCollisions() {
		Rectangle r3 = craft.getBounds();
		Rectangle recMunMissil = municaoMissil.getBounds();
		Rectangle recBomba = municaoBomba.getBounds();

		if (r3.intersects(recMunMissil)) {
			municaoMissil.setVisible(false);
			craft.recarregaMissil();
			municaoMissil();
		}

		if (r3.intersects(recBomba)) {
			municaoBomba.setVisible(false);
			craft.recarregaBomba();
			municaoBomba();
		}

		for (int j = 0; j < aliens.size(); j++) {
			Alien a = (Alien) aliens.get(j);
			Rectangle r2 = a.getBounds();

			if (r3.intersects(r2)) {
				craft.setVisible(false);
				a.setVisible(false);
				ingame = false;
			}
		}

		ArrayList<Missile> ms = craft.getMisseis();
		for (int i = 0; i < ms.size(); i++) {
			Missile m = (Missile) ms.get(i);
			Rectangle r1 = m.getBounds();
			for (int j = 0; j < aliens.size(); j++) {
				Alien a = (Alien) aliens.get(j);
				Rectangle r2 = a.getBounds();

				if (r1.intersects(r2)) {
					m.setVisible(false);
					a.setVisible(false);
				}
			}

		}

		ArrayList<Bomba> bs = craft.getBombas();
		for (int i = 0; i < bs.size(); i++) {
			Bomba b = (Bomba) bs.get(i);
			Rectangle r4 = b.getBounds();
			for (int j = 0; j < aliens.size(); j++) {
				Alien a = (Alien) aliens.get(j);
				Rectangle r2 = a.getBounds();				
				if (r4.intersects(r2)) {
					b.setVisible(false);
					a.setVisible(false);
				}
			}

		}

	}

	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			craft.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			craft.KeyPressed(e);
		}
	}
}
