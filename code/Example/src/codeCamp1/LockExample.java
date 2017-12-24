package codeCamp1;

// Question 1 of the Quiz
public class LockExample {
    private Worker worker;
    private String name;

    private int x = 0;
    private Object lock; // non-static in this example. So that workers from demoA and demoB do not compete for it
    public LockExample(String name) {
        lock = new Object();
        this.name = name;
        this.worker = new Worker(lock);
        this.worker.start();

    }
    private class Worker extends Thread {
        private Object lock;
        public Worker(Object lock) {
            this.lock = lock;
        }
        @Override
        public void run() {
            //System.out.println("Worker " + name  + ": trying to get the lock ");
            synchronized(lock) {
                //System.out.println("Worker " + name + " got the lock!");
                x++;
                System.out.println(x);
            }
            //System.out.println("Worker " + name + " released the lock");
        }
    }


    public static void main(String[] args)  {
        LockExample demoA = new LockExample("demoA");
        LockExample demoB = new LockExample("demoB");
    }
}
