package codeCamp1;

import java.util.LinkedList;

/** BlockingQueue: Correct version.
 * From http://tutorials.jenkov.com/java-concurrency/blocking-queues.html
 */
public class BlockingQueue {
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	private int capacity;


	public BlockingQueue(int capacity) {
		this.capacity = capacity;
	}

	// First bug: methods should be synchronized!!!

	/**
	 * Waits until there is space in the queue, then adds an element to the queue
	 * @param element the element to be added to the queue
	 * @throws InterruptedException
	 */
	public synchronized void enqueue(Integer element) throws InterruptedException {
		while (queue.size() == capacity) {
			wait(); // need to wait here
		}
		queue.add(element);
		System.out.println("Inserting " + element); //should be using Logger instead

		notifyAll(); // notify after adding an element
	}

	/**
	 * Waits until the queue has something to dequeue, then dequeues it and returns the element.
	 * @return the first element from the queue
	 * @throws InterruptedException
	 */
	public synchronized Integer dequeue() throws InterruptedException {
		while (queue.isEmpty()) { // need to check if it's empty
			wait();
		}

		Integer item = queue.remove();
		System.out.println("Removing " + item); // should be using Logger instead

		notifyAll(); // notify should be called on "this" lock, not on some other lock
		return item;
	}
}
