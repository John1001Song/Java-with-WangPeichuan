package multithreading;

/** The example demonstrates the use of "synchronized" keyword to protect blocks of code.
 *  Only one thread will be able to enter this synchronized block at a time. 
 *  The value of x will be 0.
 *  The example is based on the example of Prof. Engle.
 */
public class DataRaceSynchronized implements Runnable {
	private static volatile int x;
	private static Object lock = new Object();

	public void run() {
		for (int i = 0; i < 10000; i++) {
			synchronized (lock) {
				x++;
				x--;
			}
		}
	}

	
	public static void main(String[] args) throws Exception {
		Thread[] threads = new Thread[100];
		for (int i = 0; i < threads.length; i++)
			threads[i] = new Thread(new DataRaceSynchronized());
		for (int i = 0; i < threads.length; i++)
			threads[i].start();
		for (int i = 0; i < threads.length; i++)
			threads[i].join();
		System.out.println(x); // x is always 0!
	}
}
