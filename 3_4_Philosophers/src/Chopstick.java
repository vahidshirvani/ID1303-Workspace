import java.awt.Image;

import javax.swing.ImageIcon;


public class Chopstick {
	
	private final int id;
	private Image[] imgs;
	private Image img;
	
	public Chopstick(int id) {
		loadChopImgs();
		this.id = id;
		this.img = imgs[0];
	}
	
	public int getID(){
		return id;
	}
	
	public void putDownChopImg(){
		this.img = imgs[0];
	}
	
	public void takeUpChopImg(){
		this.img = imgs[1];
	}
	
	public Image getChopImg(){
		return this.img;
	}
	
	public void loadChopImgs(){
		String[] names = {"chop.png", "noChop.png"};
		
		imgs = new Image[names.length];
		for(int i = 0; i < names.length; i++)
			imgs[i] = new ImageIcon(this.getClass().getResource(names[i])).getImage();
	}
}
