import java.awt.Image;

import javax.swing.ImageIcon;


public class Philosopher implements Runnable {
	
	//private static int counterID;
	private final int MAXIMUM_TIMES_OF_EATING = 100000;
	private final int id;
	private final Chopstick[] chopsticks;
	private final ChopstickOrder order;
	private Chopstick[] twoChopsticks;
	//private Semaphor waitress;
	private Image[] faces;
	private Image face;
	private EatingTable eatingTable;
	
	public Philosopher(
			int id, 
			Chopstick[] chopsticks,
			ChopstickOrder order){
			//Semaphor waitress) {
		//id = counterID++;
		loadFaces();
		this.id = id;
		this.chopsticks = chopsticks;
		this.order = order;
		//this.waitress = waitress;
		this.face = faces[0];
		
	}

	public void run() {

		twoChopsticks = order.getOrder(this.getLeft(), this.getRight());
		
		// The loop will rotate until maximum has been reached
		int haveEaten = 0;
		while(haveEaten < MAXIMUM_TIMES_OF_EATING){
			
			// Load the thinking face and keep it for a while
			this.face = faces[9];
			eatingTable.repaint();
			
			try{
				Thread.sleep(10);
			}catch(InterruptedException ex){}

			// Reach for the other chopstick
			synchronized(twoChopsticks[0]){
				int cid = twoChopsticks[0].getID();
				int pid = this.id;
				
				// The picked up chopstick has to be identified 
				// in order to chose right face.
				this.face = (cid == pid)? faces[2]: faces[4];
				twoChopsticks[0].takeUpChopImg();
				eatingTable.repaint();
				
				synchronized(twoChopsticks[1]){
					// Now both the chopsticks have been picked up
					// and the eating face can be loaded
					// eating proccess will take a while
					this.face = faces[6];
					twoChopsticks[1].takeUpChopImg();
					eatingTable.repaint();
					
					try{
						Thread.sleep(10);
					}catch(InterruptedException ex){}
					
					// after eating the face will be changed to happy face
					// with one chopstick. Right hand has to identified.
					this.face = (cid == pid)? faces[7]: faces[8];
					twoChopsticks[1].putDownChopImg();
					eatingTable.repaint();
				}
				
				this.face = faces[10];
				twoChopsticks[0].putDownChopImg();
				eatingTable.repaint();
			}
		
			try{
				Thread.sleep(10);
			}catch(InterruptedException ex){}
		
			// another round has been done
			haveEaten++;
		}
	}
	
	private Chopstick getLeft(){
		return chopsticks[id];
	}
	
	private Chopstick getRight(){
		return chopsticks[(id + 1) % chopsticks.length];
	}
		
	public Image getFace(){
		return face;
	}
	
	public void setEatingTable(EatingTable et){
		this.eatingTable = et;
	}
	
	public void loadFaces(){
		String[] names = {
				"hungry.png",   "sad.png",         "leftHungry.png", 
				"leftSad.png",  "rightHungry.png", "rightSad.png",
				"eating.png",   "leftHappy.png",   "rightHappy.png",
				"thinking.png", "happy.png"};
		
		faces = new Image[names.length];
		for(int i = 0; i < names.length; i++)
			faces[i] = new ImageIcon(this.getClass().getResource(names[i])).getImage();
	}
}
