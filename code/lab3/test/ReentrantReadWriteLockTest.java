import org.junit.Assert;
import org.junit.Test;
import concurrent.*;
import java.util.concurrent.*;

/**
 * A test class for the ReentrantReadWriteLock.
 * Modified from the tests of Prof. Rollins 
 * Original author: Prof. Rollins
 */
public class ReentrantReadWriteLockTest {

	@Test
	public void testLockSimple() {
		// Acquires a write lock, then a read lock (allowed)
		// Then releases the read lock and the write lock.
		String testName = "testLockSimple";
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		lock.lockWrite();
		lock.lockRead();

		Assert.assertTrue(String.format("%n" + "Test Case: %s%n" +
				" Read lock not held. %n", testName), lock.isReadLockHeldByCurrentThread());
		lock.unlockRead();
		Assert.assertFalse(String.format("%n" + "Test Case: %s%n" +
				" Read lock not released. %n", testName), lock.isReadLockHeldByCurrentThread());
		Assert.assertTrue(String.format("%n" + "Test Case: %s%n" +
				" Write lock not held. %n", testName), lock.isWriteLockHeldByCurrentThread());
		lock.unlockWrite();
		Assert.assertFalse(String.format("%n" + "Test Case: %s%n" +
				" Write lock not released. %n", testName), lock.isWriteLockHeldByCurrentThread());
	}

	@Test
	public void testLockMultipleWrites() {
		// Acquires two write locks. After releasing one, should still have one write lock
		String testName = "testLockMultipleWrites";
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		lock.lockWrite();
		lock.lockWrite();

		lock.unlockWrite();
		Assert.assertTrue(String.format("%n" + "Test Case: %s%n" +
				" Write lock not held. %n", testName), lock.isWriteLockHeldByCurrentThread());
		lock.unlockWrite();
	}

	@Test
	public void testWriteLockMultiThread() {
		// Acquires a write lock. Another thread should not be able to acquire a read lock.
		String testName = "testWriteLockMultiThread";

		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		boolean result = lock.tryAcquiringWriteLock();
		if(!result) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" +
					" Unable to acquire write lock. %n", testName));
		}
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future future=executor.submit(new Runnable() {
			@Override
			public void run() {
				boolean gotReadLock = lock.tryAcquiringReadLock();
				if (gotReadLock)
					Assert.fail(String.format("%n" + "Test Case: %s%n" +
							"A thread acquired a read lock while somebody else is writing. Not allowed. %n", testName));
			}
		});
		try {
			future.get();
		}
		catch (Exception e) {
			Assert.fail();
		}
		finally {
			if (lock != null)
				lock.unlockWrite();
		}
	}

	@Test
	public void testReadLockMultiThread() {
		// The main thread acquires a read lock.
		// Another thread should succeed at also getting a read lock.
		String testName = "testReadLockMultiThread";
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		lock.lockRead();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future future=executor.submit(new Runnable() {
			@Override
			public void run() {
				boolean gotReadLock = lock.tryAcquiringReadLock();
				if (!gotReadLock)
					Assert.fail(String.format("%n" + "Test Case: %s%n" +
							" Not able to get another read lock. %n", testName));
			}
		});
		try {
			future.get();
		}
		catch (Exception e) {
			Assert.fail();
		}
		finally {
			if (lock != null)
				lock.unlockRead();
		}
	}

	@Test
	public void testLockUpgrade() {
		// Current reader should not be allowed to get a write lock.
		String testName = "testLockUpgrade";
		ReentrantReadWriteLock lock = null;
		try {
			lock = new ReentrantReadWriteLock();
			boolean success = lock.tryAcquiringReadLock();
			if (!success) {
				Assert.fail(String.format("%n" + "Test Case: %s%n" +
						" Should be able to get read lock, but could not. %n", testName));
			}
			lock.lockRead();

			boolean result = lock.tryAcquiringWriteLock();
			if (result) {
				Assert.fail(String.format("%n" + "Test Case: %s%n" +
						" Lock upgrade read to write should be disallowed. %n", testName));
			}
		}
		finally {
			if (lock != null)
				lock.unlockRead();
		}

	}

	
	
}
