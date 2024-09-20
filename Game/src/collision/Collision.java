package collision;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Collision extends JFrame {
	public Collision() {
		int aliens = Integer.parseInt(JOptionPane.showInputDialog("Quantos aliens?"));
		while(true) {
			if (aliens < 150 || aliens > 1200) {
				JOptionPane.showMessageDialog(this, "Informe uma quantidade entre 150 e 1200 aliens");
				aliens = Integer.parseInt(JOptionPane.showInputDialog("Quantos aliens?"));
			}else{
				break;
			}
		}
		add(new Board(aliens));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(650,550);
		setLocationRelativeTo(null);
		setTitle("Collision");
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Collision();
	}
}
