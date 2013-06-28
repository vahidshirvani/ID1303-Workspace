import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;


public class Sprite implements Movable, Drawable, Runnable {

	private static int counterID;
	private int ID;
	private int roomWidth;
	private int roomHeight;
	
	private int diamiter;
	
	private int xCoordinate;
	private int yCoordinate;
	
	private int dx;
	private int dy;
	
	private AFrame room; 
	private TimeHandler time;
	private boolean keepRunning = true;
	
	// The color edges on each sides
	private Shape west;
	private Shape south;
	private Shape east;
	private Shape north;
	
	private Color color = Color.BLACK;
	
	public Sprite(AFrame room, TimeHandler time) {
		
		ID = counterID++;
		this.room = room; 
		this.time = time;
		room.addSprite(this);
		
		roomWidth = room.getWidth() - 5; // 5 is because of optimizing
		roomHeight = room.getHeight() - 53; // 53 is because of optimizing
		
		diamiter = 20;
			
		Random random = new Random(); 
		this.xCoordinate = random.nextInt(roomWidth);
		this.yCoordinate = random.nextInt(roomHeight);
		
		this.dx = 1 + random.nextInt(5);
		this.dy = 1 + random.nextInt(5);
		
		west = new Rectangle2D.Double(0, 0, 5, roomHeight);
		south = new Rectangle2D.Double(0, roomHeight - 5, roomWidth, 5);
		east = new Rectangle2D.Double(roomWidth - 5, 0, 5, roomHeight);
		north = new Rectangle2D.Double(0, 0, roomWidth, 5);
	}

	public void draw(Graphics2D g) {

		Ellipse2D figure = new Ellipse2D.Double(xCoordinate, yCoordinate, 
													diamiter, diamiter);
				
		g.setPaint(color);
		g.fill(figure);
		
		g.setPaint(Color.BLACK);
		g.drawString("" + ID, xCoordinate, yCoordinate + diamiter);
		
		g.setPaint(Color.RED);
		g.fill(west);
		g.setPaint(Color.GREEN);
		g.fill(south);
		g.setPaint(Color.BLUE);
		g.fill(east);
		g.setPaint(Color.YELLOW);
		g.fill(north);
	}
	
	public void move() {
		// Multiplied by timePassed so that time factor matters
		xCoordinate += (dx + time.timePassed());
		yCoordinate += (dy + time.timePassed());
	
		if(xCoordinate <= 0){
			color = Color.RED;
			dx *= -1;
		}
		
		if(xCoordinate >= roomWidth - diamiter){
			color = Color.BLUE;
			dx *= -1;
		}
		
		if(yCoordinate <= 0){
			color = Color.YELLOW;
			dy *= -1;
		}
		
		if(yCoordinate >= roomHeight - diamiter){
			color = Color.GREEN;
			dy *= -1;
		}
	}
	
	public void setDX(int dx){
		this.dx = dx;
	}
	
	public void setDY(int dy){
		this.dy = dy;
	}
	
	public void decreaseID(){
		ID--;
	}
	
	public void stopSprint(){
		keepRunning = false;
	}
	
	public void run() {
		
		// this case the thread can only be stopped by the keepRunning flag
		while(keepRunning){
			this.move();
			room.update();
			
			try{
				Thread.sleep(30);
			}catch(InterruptedException ex){
				ex.getStackTrace();				
			}
		}
	}
}
