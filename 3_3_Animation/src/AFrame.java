

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class AFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<Sprite> sprites;
	public Room room;
	private TimeHandler time;
	private String[] numbers;
	private JComboBox spriteNumber;
	private JSlider sliderX;
	private JSlider sliderY;
	private JButton stopB;

	AFrame(TimeHandler time){
		sprites = new ArrayList<Sprite>();
		room = new Room();
		
		numbers = new String[10];
		for(int i = 0; i < 10; i++)
			numbers[i] = i + " ";
		
		JLabel label = new JLabel("Sprite no.");
		spriteNumber = new JComboBox(numbers);
		sliderX = new JSlider(-5, 5, 0);
		sliderY = new JSlider(-5, 5, 0);
		
		stopB = new JButton("STOP");
		JPanel top = new JPanel(new GridLayout(1, 4));
		top.add(label);
		top.add(spriteNumber);
		top.add(sliderX);
		top.add(sliderY);
		top.add(stopB);
		Listener listener = new Listener();
		sliderX.addChangeListener(listener);
		sliderY.addChangeListener(listener);
		stopB.addActionListener(listener);
		
		this.time = time;
		this.setBounds(100, 100, 400, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Wall competition");
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(room, BorderLayout.CENTER);
		
	}
	
	public void update(){
		room.repaint();
	}
	
	public synchronized void addSprite(Sprite sprite){
		sprites.add(sprite);
	}
	
	public synchronized void removeDrawable(Drawable figure){
		sprites.remove(figure);
	}
	
	public synchronized void clearDrawable(){
		sprites.clear();
	}
	
	public class Room extends JPanel{
		
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics gr){
			super.paintComponent(gr);
			Graphics2D g = (Graphics2D) gr;
			Sprite sprite;
			synchronized(AFrame.this){
				for(int i = 0; i < sprites.size(); i++){
					sprite = sprites.get(i);
					sprite.draw(g);
				}
			}
		}
	}
	
	/*
	 * The sliders and stop button share the same listener. The source
	 * will be identified first then relative code be chosen to execute 
	 */
	private class Listener implements ActionListener, ChangeListener{

		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			
			if(source == stopB && sprites.size() != 0){
				int id = spriteNumber.getSelectedIndex();
				sprites.get(id).stopSprint();
				sprites.remove(id);
				for(int i = id; i < sprites.size(); i++)
					sprites.get(i).decreaseID();
				spriteNumber.removeItem(numbers[sprites.size()]);
			}else{
				time.stopTimer();
			}
		}

		public void stateChanged(ChangeEvent event) {
			Object source = event.getSource();
			if(source == sliderX){
				sprites.get(
						spriteNumber.getSelectedIndex()).setDX(
								sliderX.getValue());
			}else{
				sprites.get(
						spriteNumber.getSelectedIndex()).setDY(
								sliderY.getValue());
			}	
		}
	}
}
