package multithreading;

/**
 * CalculatePrimes -- calculate as many prime numbers as we can, in ten seconds.
 * One thread calculates primes, another thread acts as a timer.
 * Since one thread is writing to a variable called finished, and another thread is reading the value,
 * we need to provide synchronization.
 * We can declare variable as volatile (see slides, Use Pattern #1).
 *
 * Source:
 * http://www.ibm.com/developerworks/java/tutorials/j-threads/j-threads.html
 */
public class CalculatePrimes extends Thread {

	public static final int MAX_PRIMES = 1000000;
	public static final int TEN_SECONDS = 10000;

	protected volatile boolean finished = false;

	@Override
	public void run() {
		int[] primes = new int[MAX_PRIMES];
		int count = 0;

		for (int i = 2; count < MAX_PRIMES; i++) {

			// Check to see if the timer has expired
			if (finished) {
				break;
			}

			boolean prime = true;
			for (int j = 0; j < count; j++) {
				if (i % primes[j] == 0) {
					prime = false;
					break;
				}
			}

			if (prime) {
				primes[count++] = i;
				System.out.println("Found prime: " + i);
			}
		}
	}

	public static void main(String[] args) {
		CalculatePrimes calculator = new CalculatePrimes();
		calculator.start();
		try {
			Thread.sleep(TEN_SECONDS);
		} catch (InterruptedException e) {
			System.out.println("The thread was interrupted.");
		}

		calculator.finished = true;
	}
}