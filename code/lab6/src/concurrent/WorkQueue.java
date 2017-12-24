package concurrent;

import java.util.LinkedList;

/** WorkQueue implementation from IBM: https://www.ibm.com/developerworks/library/j-jtp0730/index.html
 *  Do not modify this class. */
public class WorkQueue {
	
	private final static int nThreadDefault = 10;
    private final PoolWorker[] threads;
    private final LinkedList<Runnable> queue;
    private volatile boolean running;

	/**
	 * Construct a WorkQueue with 10 default workers.
	 */
	public WorkQueue() {
		this(nThreadDefault);
	}

	/**
	 * Construct a WorkQueue with specific number of workers.
	 * @param nThreads - number of workers
	 */
	public WorkQueue(int nThreads)
	{
        queue = new LinkedList<Runnable>();
        threads = new PoolWorker[nThreads];
        running = true;
        for (int i=0; i<nThreads; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
	}

	/**
	 * Execute a new Runnable job.
	 * @param r
	 */
	public void execute(Runnable r) {
		if (running) {
			// If running flag is true, we are welcome new jobs.
			synchronized(queue) {
	            queue.addLast(r);
	            queue.notify();
	        }
		}
	}

	/**
	 * Stop accepting new jobs.
	 * This method should not block until work is complete.
	 */
	public void shutdown() {
		running = false;
		synchronized(queue) {
            queue.notifyAll();
        }
	}

	/**
	 * Block until all jobs in the queue are complete.
	 */
	public void awaitTermination() {
		// Join to all threads to wait until they are done.
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException ignore) {
			}
		}
	}
	
	private class PoolWorker extends Thread {
        public void run() {
            Runnable r;            
            while (true) {
                synchronized(queue) {
                    while (running && queue.isEmpty()) {
                        try
                        {
                            queue.wait();
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }
                    if (!running && queue.isEmpty()) {
                    	break;
                    }
                    r = (Runnable) queue.removeFirst();
                }
                try {
                    r.run();
                }
                catch (RuntimeException ignore) {
                }
            }
        }
    }

}
