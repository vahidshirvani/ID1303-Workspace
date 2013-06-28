
public class PrimeFinder {
	
	private int startAt;
	private final int UPPER_LIMIT = Integer.MAX_VALUE;
	private boolean isPrimeNumber = false;

	public PrimeFinder(int startAtArg) {
		this.startAt = startAtArg;
	}
	
	/* The method won't be finish until it finds a prime number.
	 * for example lets take number 9. The smallest integer number 
	 * greater  or equal to square 9 is 3, which gives 9%3=0. 9 is not
	 * an prime number.
	 */
	public int nextPrime(){
		while(++startAt <= UPPER_LIMIT){
			int checkUnder = (int) Math.ceil(Math.sqrt(startAt));
            isPrimeNumber = false;
            while (checkUnder > 1) {
                if ((startAt != checkUnder) && (startAt % checkUnder == 0)) {
                    isPrimeNumber = false;
                    break;
                } else if (!isPrimeNumber) {
                    isPrimeNumber = true;
                }
                --checkUnder;
            }
            if(isPrimeNumber)
            	break;
		}
		return (isPrimeNumber)? startAt : -1 ;
	}
}

