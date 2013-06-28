
public class UsePrimeNumbers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Portal portal = new Portal();
		Thread p1 = new Thread(new Producer(portal, 100));
		Thread p2 = new Thread(new Producer(portal, 1000));
		Thread c1 = new Thread(new Consumer(portal));
		Thread c2 = new Thread(new Consumer(portal));
		
		p1.start();
		p2.start();
		c1.start();
		c2.start();
	}
}
