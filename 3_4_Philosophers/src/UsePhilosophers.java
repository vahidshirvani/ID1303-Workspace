
public class UsePhilosophers {
	
	public static void main(String[] args) {
		
		final int NO_OF_PHILOSOPHERS = 5;
				
		Chopstick[] chopsticks = new Chopstick[NO_OF_PHILOSOPHERS];
		for(int i = 0; i < NO_OF_PHILOSOPHERS; i++)
			chopsticks[i] = new Chopstick(i); 

		ChopstickOrder order = new LeftRightOrder();
		//ChopstickOrder order = new RandomOrder();
		//ChopstickOrder order = new LowerFirstOrder();
		//Semaphor waitress = new Semaphor(false, NO_OF_PHILOSOPHERS + 10);
		
		
		Thread[] thread = new Thread[NO_OF_PHILOSOPHERS];
		Philosopher[] philosophers = new Philosopher[NO_OF_PHILOSOPHERS];
		
		for(int i = 0; i < NO_OF_PHILOSOPHERS; i++){
			philosophers[i] = new Philosopher(i, chopsticks, order);//, waitress);
			thread[i] = new Thread(philosophers[i]);
		}
		
		EatingTable eatingTable = new EatingTable(philosophers, chopsticks);
		for(int i = 0; i < NO_OF_PHILOSOPHERS; i++)
			philosophers[i].setEatingTable(eatingTable);
			
		for(int i = 0; i < NO_OF_PHILOSOPHERS; i++){
			thread[i].start();
		}
	}
}
