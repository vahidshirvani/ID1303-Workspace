import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Semaphor {

	private int maximumNumberOfBalls;
	private int currentNumberOfBalls;
	private Lock lock;
	private Condition canBeIncreased;
	private Condition canBeDecreased;
	
	public Semaphor(boolean onoff, int balls) {
		
		if(onoff){
			this.maximumNumberOfBalls  = balls - 1;
		}else{
			this.maximumNumberOfBalls = balls;
		}
		this.currentNumberOfBalls = maximumNumberOfBalls;
		
		lock = new ReentrantLock();
		canBeIncreased = lock.newCondition();
		canBeDecreased = lock.newCondition();
	}
	
	public void increase(){
		
		lock.lock();
		while(currentNumberOfBalls >= maximumNumberOfBalls){
			try {
				canBeIncreased.await();
				//this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		currentNumberOfBalls++;
		canBeDecreased.signalAll();
		lock.unlock();
		//this.notifyAll();
		
		/*
		if(currentNumberOfBalls < maximumNumberOfBalls){
			currentNumberOfBalls++;
			this.notifyAll();
		}
		//return true;
			
		boolean succeed = false;
		if(currentNumberOfBalls < maximumNumberOfBalls){
			currentNumberOfBalls++;
			succeed = true;
		}
		
		return succeed;
		*/
	}

	public void decrease(){
		
		lock.lock();
		while(currentNumberOfBalls <= 0){
			try {
				canBeDecreased.await();
				//this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		currentNumberOfBalls--;
		canBeIncreased.signalAll();
		lock.unlock();
		

		/*
		while(currentNumberOfBalls <= 0){
			try {
				//canBeDecreased.await();
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		currentNumberOfBalls--;
		//canBeIncreased.signal();
		this.notifyAll();
		
		boolean succeed = false;
		if(currentNumberOfBalls > 0){
			currentNumberOfBalls--;
			succeed = true;
		}
			
		return succeed;
		*/
	}

	public void decreaseWithoutCondition() {
		currentNumberOfBalls--;
		canBeIncreased.signalAll();
	
		
	}
}
