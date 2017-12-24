package multithreading;
/**  Based on NoSynchronization example, but in this one, x will always be 0,
 *   because x++ and x-- are in synchronized blocks (so the problems of atomicity and visibility are solved). */
public class WithSynchronization {
	private int x = 0;
	private Object lock  = new Object();

	private class AdditionTask implements Runnable {
		public void run() {
			synchronized(lock) {
				x++;
			}
		}

	}

	private class SubtractionTask implements Runnable {
		public void run() {
			synchronized(lock) {
				x--;
			}
		}
	}

	public void createThreads() throws InterruptedException {
		Thread t1 = new Thread(new AdditionTask());
		Thread t2 = new Thread(new SubtractionTask());
		t1.start();
		t2.start();
		// wait for threads 1 and 2 to  finish
		t1.join();
		t2.join();
		if (x != 0) // will only print something if x i not 0
			System.out.print(x + " ");
		// System.out.println("Done");
	}

	public static void main(String[] args)  {
		WithSynchronization ns = new WithSynchronization();
		try {
			for (int i = 0; i < 1000000; i++) {
				ns.createThreads();
			}
		}
		catch  (InterruptedException e) {
			System.out.println("Thread got interrupted: " + e);
		}
	}

}
