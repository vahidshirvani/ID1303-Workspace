
public class TimeHandler implements Runnable{
	
	private boolean keepRunning = true;
	private long timePassed = 0L;
	
	public void run() {

		long startTime = System.currentTimeMillis();
		long cumuliateTime = startTime;
		
		// this case the time thread can only be stopped by the keepRunning flag
		// but it can be changed to run until total time mode.
		while(keepRunning){ // OR (comuliateTime >= 5000){
			timePassed = System.currentTimeMillis() - cumuliateTime;
			cumuliateTime += timePassed;
		}
		timePassed = 0L;	
	}
	
	public void stopTimer(){
		this.keepRunning = false;
	}
	
	public int timePassed(){
		return (int) this.timePassed;
	}
}
