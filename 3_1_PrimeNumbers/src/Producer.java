
public class Producer implements Runnable {
	
	private Portal portal;
	private PrimeFinder pf;
	
	public Producer(Portal portalArg, int startNumber) {
		this.portal = portalArg;
		this.pf = new PrimeFinder(startNumber);
	}

	@Override
	public void run() {
		int prime = pf.nextPrime();
		
		while(true){
		
			try{
				Thread.sleep(500);
			}catch(InterruptedException ex){
				break;
			}
			
			// && shall not be used in this case. putFIFO should only be executed
			// if decrease has been successful. 
			if(portal.decreaseEmpty())
				if(portal.putFIFO(prime)){
					portal.increaseFull();
					prime = pf.nextPrime();
					System.out.println(prime + "\tHas been produced and fifo size is " 
							+ portal.FIFO.size());
				}
			

			
			if(Thread.interrupted()){
				break;
			}
		}
	}
}
