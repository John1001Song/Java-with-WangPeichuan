package concurrent;


import java.util.Collections;
import java.util.TreeMap;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * A custom reentrant read/write lock that allows:
 * 1) Multiple readers (when there is no writer). Any thread can acquire multiple read locks (if nobody is writing).
 * 2) One writer (when nobody else is writing or reading).
 * 3) A writer is allowed to acquire a read lock while holding the write lock.
 * 4) A writer is allowed to acquire another write lock while holding the write lock.
 * 5) A reader can not acquire a write lock while holding a read lock.
 *
 * Use ReentrantReadWriteLockTest to test this class.
 * The code is modified from the code of Prof. Rollins.
 */
public class ReentrantReadWriteLock {
    // FILL IN CODE:
    // Add instance variables:
    // for each threadId, store the number of read locks and write locks currently held

    private final static Logger log = LogManager.getRootLogger();

    // total number of readLock and writeLock
    private int numbOfReadLock;
    private int numbOfWriteLock;

    // Key: thread id
    // Value: number of locks in this thread
    private TreeMap<Long, Integer> readersMap;
    private TreeMap<Long, Integer> writersMap;

    /**
     * Constructor for ReentrantReadWriteLock
     */
    public ReentrantReadWriteLock() {
        // FILL IN CODE: initialize instance variables

        readersMap = new TreeMap<>();
        writersMap = new TreeMap<>();

        numbOfReadLock = 0;
        numbOfWriteLock = 0;

    }

    /**
     * Return true if the current thread holds a read lock.
     *
     * @return true or false
     */
    public synchronized boolean isReadLockHeldByCurrentThread() {
       // FILL IN CODE
        long currentThread = Thread.currentThread().getId();
        if (readersMap.containsKey(currentThread)){
            return true;
        }
        return false; // do not forget to change
    }

    /**
     * Return true if the current thread holds a write lock.
     *
     * @return true or false
     */
    public synchronized boolean isWriteLockHeldByCurrentThread() {
       // FILL IN CODE
        long currentThread = Thread.currentThread().getId();
        if (writersMap.containsKey(currentThread)){
            return true;
        }
        return false; // do not forget to change
    }

    /**
     * Non-blocking method that attempts to acquire the read lock. Returns true
     * if successful.
     * Checks conditions (whether it can acquire the read lock), and if they are true,
     * updates readers info.
     *
     * Multiple readers (when there is no writer). Any thread can acquire multiple read locks (if nobody is writing).
     *
     * Note that if conditions are false (can not acquire the read lock at the moment), this method
     * does NOT wait, just returns false
     *
     * @return true or false
     */
    public synchronized boolean tryAcquiringReadLock() {

        long currentThreadId = Thread.currentThread().getId();

        // if there is a writer, which is not the current thread, return false
        if (numbOfWriteLock > 0 && !writersMap.containsKey(currentThreadId)){
            return false;
        }

        // if nobody is writing
        if (numbOfWriteLock == 0){
            // if current thread is not in the reader map, then put it in
            if (!readersMap.containsKey(currentThreadId)){
                readersMap.put(currentThreadId, 1);
                numbOfReadLock++;
                return true;
            }
            if (readersMap.get(currentThreadId) > 0){
                readersMap.put(currentThreadId, readersMap.get(currentThreadId)+1);
                numbOfReadLock++;
                return true;
            }
        }

        // current thread is a writer and it can acquire a read lock
        if (writersMap.get(currentThreadId) > 0){
            // if the thread has not been put in the readersMap before
            if (!readersMap.containsKey(currentThreadId)){
                readersMap.put(currentThreadId, 1);
                numbOfReadLock++;
                return true;
            }
            // if the thread was put in the readersMap before
            if (readersMap.get(currentThreadId) > 0){
                readersMap.put(currentThreadId, readersMap.get(currentThreadId)+1);
                numbOfReadLock++;
                return true;
            }
        }

         return false;// do not forget to change
    }

    /**
     * Non-blocking method that attempts to acquire the write lock. Returns true
     * if successful.
     * Checks conditions (whether it can acquire the write lock), and if they are true,
     * updates writers info.
     *
     * 1) One writer (when nobody else is writing or reading) can acquire a lock.
     * 2) A writer is allowed to acquire a read lock while holding the write lock.
     * 3) A writer is allowed to acquire another write lock while holding the write lock.
     * 4) A reader can not acquire a write lock while holding a read lock.
     *
     * Note that if conditions are false (can not acquire the write lock at the moment), this method
     * does NOT wait, just returns false
     *
     * @return true or false
     */
    public synchronized boolean tryAcquiringWriteLock() {
       // FILL IN CODE

        // if there is a reader including current thread, can't acquire the write lock
        while (numbOfReadLock > 0){
            return false;
        }

        long currentThreadId = Thread.currentThread().getId();
        // if there is a writer, not current thread, return false
        if (numbOfWriteLock > 0 && (!writersMap.containsKey(currentThreadId))){
            return false;
        }

        // if no one has a write lock, directly acquire a write lock
        if (numbOfWriteLock == 0){
            writersMap.put(currentThreadId, 1);
            numbOfWriteLock++;
            return true;
        }else
        // if current thread has a write lock, it can acquire an extra write lock
        if (writersMap.get(currentThreadId) > 0){
            writersMap.put(currentThreadId, writersMap.get(currentThreadId)+1);
            numbOfWriteLock++;
            return true;
        }else return false;
        // do not forget to change this
    }

    /**
     * Blocking method that will return only when the read lock has been
     * acquired.
     * Calls tryAcquiringReadLock, and as long as it returns false, waits.
     * Catches InterruptedException.
     */
    public synchronized void lockRead() {
        // FILL IN CODE

        while (!tryAcquiringReadLock()){
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
                log.error("InterruptedException while acquiring a read lock: " + e);
            }
        }
    }

    /**
     * Releases the read lock held by the calling thread. Other threads might
     * still be holding read locks. If no more readers after unlocking, calls notifyAll().
     */
    public synchronized void unlockRead() {
        long currentThreadId = Thread.currentThread().getId();
        // FILL IN CODE
        readersMap.put(currentThreadId,readersMap.get(currentThreadId)-1);
        if (readersMap.get(currentThreadId) == 0){
            // if current thread does not have a lock, remove it from the readers map instead of updating value = 0
            readersMap.remove(currentThreadId);
        }
        numbOfReadLock--;
        if (numbOfReadLock == 0){
            this.notifyAll();
        }
    }

    /**
     * Blocking method that will return only when the write lock has been
     * acquired.
     * Calls tryAcquiringWriteLock, and as long as it returns false, waits.
     * Catches InterruptedException.
     */
    public synchronized void lockWrite() {
        // FILL IN CODE

        while (!tryAcquiringWriteLock()){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
                log.error("InterruptedException while acquiring a write lock: " + e);
            }
        }

    }

    /**
     * Releases the write lock held by the calling thread. The calling thread
     * may continue to hold a read lock.
     * If the number of writers becomes 0, calls notifyAll.
     */

    public synchronized void unlockWrite() {
       // FILL IN CODE
        long currentThreadId = Thread.currentThread().getId();
        writersMap.put(currentThreadId,writersMap.get(currentThreadId)-1);
        if (writersMap.get(currentThreadId) == 0){
            // if current thread does not have a write lock, just remove it
            writersMap.remove(currentThreadId);
            numbOfWriteLock--;
        }
        if (numbOfWriteLock == 0){
            notifyAll();
        }
    }
}
