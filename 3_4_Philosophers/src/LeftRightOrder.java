
public class LeftRightOrder implements ChopstickOrder {

	public Chopstick[] getOrder(Chopstick left, Chopstick right) {

		Chopstick[] twoChopsticks = new Chopstick[2];
		twoChopsticks[0] = left;
		twoChopsticks[1] = right;
		
		return twoChopsticks;
	}
}
