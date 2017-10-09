

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BBMonitor {

    private final String[] buffer;
    private final int capacity;
    private int front;
    private int rear;
    private int count;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BBMonitor(int capacity) {
     

        this.capacity = capacity;

        buffer = new String[capacity];
    }

    public void deposit(String data) throws InterruptedException {
        lock.lock();

        try {
            while (!(count < capacity)) {
                notFull.await();
            }

            buffer[rear] = data;
            rear = (rear + 1) % capacity;
            count++;
            
            System.out.print("Now in buffer: ");
            for (int i = 0; i < count; i++) {
                System.out.print(buffer[(front + i) % capacity]+"  ");
            }
            System.out.println();

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String fetch() throws InterruptedException {
        lock.lock();

        try {
            while (!(count > 0)) {
                notEmpty.await();
            }

            String result = buffer[front];
            front = (front + 1) % capacity;
            count--;

            System.out.print("Now in buffer: ");
            for (int i = 0; i < count; i++) {
                System.out.print(buffer[(front + i) % capacity]+"  ");
            }
            System.out.println();

            notFull.signal();

            return result;
        } finally {
            lock.unlock();
        }
    }
}