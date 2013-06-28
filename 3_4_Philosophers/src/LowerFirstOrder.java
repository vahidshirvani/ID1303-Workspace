
public class LowerFirstOrder implements ChopstickOrder {

	public Chopstick[] getOrder(Chopstick left, Chopstick right) {
		
		Chopstick[] twoChopsticks = new Chopstick[2];
		
		if(left.getID() < right.getID()){
			twoChopsticks[0] = left;
			twoChopsticks[1] = right;
		}else{
			twoChopsticks[0] = right;
			twoChopsticks[1] = left;
		}
		
		return twoChopsticks;
	}
}
