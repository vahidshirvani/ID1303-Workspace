import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class EatingTable extends JFrame {

	private static final long serialVersionUID = 5960930308319946384L;
	private Philosopher[] philosophers;
	private Chopstick[] chopsticks;
	private PanelTable panelTable;

	public EatingTable(Philosopher[] philosophers, Chopstick[] chopsticks){
		
		this.philosophers = philosophers;
		this.chopsticks = chopsticks;
		panelTable = new PanelTable();
		
		this.setBounds(300, 100, 500, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Dining Philosophers");
		this.add(panelTable);
	}

	private class PanelTable extends JPanel{
		
		private static final long serialVersionUID = -2331231316183643106L;
		public PanelTable(){
			this.setBackground(Color.WHITE);
		}
		public void paintComponent(Graphics gr){
			super.paintComponent(gr);
			Graphics2D g = (Graphics2D) gr;
			
			g.drawImage(philosophers[0].getFace(), 30, 130, null);
			g.drawImage(philosophers[1].getFace(), 80, 320, null);
			g.drawImage(philosophers[2].getFace(), 310, 320, null);
			g.drawImage(philosophers[3].getFace(), 350, 130, null);
			g.drawImage(philosophers[4].getFace(), 190, 20, null);
			
			g.drawImage(chopsticks[0].getChopImg(), 130, 90, null);
			g.drawImage(chopsticks[1].getChopImg(), 110, 240, null);
			g.drawImage(chopsticks[2].getChopImg(), 220, 310, null);
			g.drawImage(chopsticks[3].getChopImg(), 330, 240, null);
			g.drawImage(chopsticks[4].getChopImg(), 300, 90, null);
		}
	}
	
	@Override
	public void repaint(){
		panelTable.repaint();
	}

}
