
public class UseAnimation {

	public static void main(String[] args) {
		
		// Time need to be defined as a separate thread
		// It will be used by all the moving objects to 
		// identify the current and passed time.
		TimeHandler time = new TimeHandler();
		new Thread(time);
		AFrame frame = new AFrame(time);
	
		Sprite sprite;
		Thread[] t = new Thread[10];
		for(int i = 0; i < 10; i++){
			sprite = new Sprite(frame, time);
			t[i] = new Thread(sprite);
		}
		
		for(int i = 0; i < 10; i++)
			t[i].start();	
	}
}
