import java.util.Random;


public class RandomOrder implements ChopstickOrder {

	public Chopstick[] getOrder(Chopstick left, Chopstick right) {

		int index = new Random().nextInt(2);
		Chopstick[] twoChopsticks = new Chopstick[2];
		twoChopsticks[index] = left;
		twoChopsticks[(index + 1) % 2] = right;
		
		return twoChopsticks;
	}
}
