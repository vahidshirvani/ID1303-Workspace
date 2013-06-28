
public class Consumer implements Runnable{

	private Portal portal;
	
	public Consumer(Portal portalArg) {
		this.portal = portalArg;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int prime;
		while(true){
			try{
				Thread.sleep(900);
			}catch(InterruptedException ex){
				break;
			}
			
			// Other consumers can't call takeFIFO due to synchronized mutex.
			if(portal.decreaseFull()){
				prime = portal.takeFIFO(); 
				if(prime != -1)
					System.out.println(prime + "\tHas been consumed and fifo size is " + portal.FIFO.size());
				portal.increaseEmpty();
				}
			
			
			if(Thread.interrupted())
				break;
		}	
	}
}
