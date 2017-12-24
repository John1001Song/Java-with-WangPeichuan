package multithreading;
/** The class demonstrates how race condition occurs, when we have no synchronization.
 *  This is an example of how NOT to write multithreaded programs. */
public class NoSynchronization {
	private int x = 0;

	private class AdditionTask implements Runnable {
		public void run() {
			x++;
		}

	}

	private class SubtractionTask implements Runnable {
		public void run() {
			x--;
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
		NoSynchronization ns = new NoSynchronization();
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
