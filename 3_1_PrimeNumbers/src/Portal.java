import java.util.ArrayList;


public class Portal {
	
	private final int FIFO_CAPACITY = 10;
	private Integer numberOfEmpty; // Semaphore
	private Integer numberOfFull;  // Semaphore
	private Object writeMutex;     // Mutex
	private Object readMutex;      // Mutex
	public ArrayList<Integer> FIFO;
	
	public Portal() {
		numberOfEmpty = new Integer(FIFO_CAPACITY);
		numberOfFull = new Integer(0);
		writeMutex = new Object();
		readMutex = new Object();
		FIFO = new ArrayList<Integer> (FIFO_CAPACITY);
		
	}
	
	// The method will try to add one ball into the 
	// semaphore. If successful then returns true.
	public boolean increaseEmpty(){
		boolean success = false;
		synchronized(numberOfEmpty){
			if(numberOfEmpty < FIFO_CAPACITY){
				numberOfEmpty++;
				success = true;
			}
		}
		return success; 
	}
	
	// The method will try to take one ball from the 
	// semaphore. If successful then returns true.
	public boolean decreaseEmpty(){
		boolean success = false;
		synchronized(numberOfEmpty){
			if(numberOfEmpty > 0){
				numberOfEmpty--;
				success = true;
			}
		}
		return success; 
	}
	
	// The method will try to add one ball into the 
	// semaphore. If successful then returns true.
	public boolean increaseFull(){
		boolean success = false;
		synchronized(numberOfFull){
			if(numberOfFull < FIFO_CAPACITY){
				numberOfFull++;
				success = true;
			}
		}
		return success; 
	}
	
	// The method will try to take one ball from the 
	// semaphore. If successful then returns true.
	public boolean decreaseFull(){
		boolean success = false;
		synchronized(numberOfFull){
			if(numberOfFull > 0){
				numberOfFull--;
				success = true;
			}
		}
		return success; 
	}
	
	// The method will try to add to FIFO. In order to 
	// do this it needs to lock the write mutex. Notice that
	// only producers compete with each other NOT consumers.
	public boolean putFIFO(int prime){
		boolean success = false;
		synchronized(writeMutex){
			FIFO.add(prime);
			success = true;
		}
		return success;
	}
	
	// The method will try to take from FIFO. In order to 
	// do this it needs to lock the read mutex. Consumers can 
	// Take at the same time as producers do puts.
	public int takeFIFO(){
		int prime = -1;
		synchronized(readMutex){
			prime = FIFO.remove(0);
		}
		return prime;
	}

}
