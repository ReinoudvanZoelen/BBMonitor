package Java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RW {

    private int readersActive = 0;
    private int writersActive = 0;
    private int readersWaiting = 0;
    private int writersWaiting = 0;

    Lock monLock = new ReentrantLock();
    Condition okToRead = monLock.newCondition();
    Condition okToWrite = monLock.newCondition();

    public RW() {

    }

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while(writersActive > 0 || writersWaiting > 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
        } catch(InterruptedException ex) {
            readersWaiting--;
            Thread.currentThread().interrupt();
        } finally {
            monLock.unlock();
        }
    }

    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) okToWrite.signal();
        } finally {
            monLock.unlock();
        }
    }

    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) {
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        } catch(InterruptedException ex) {
            writersWaiting--;
            Thread.currentThread().interrupt();
        } finally {
            monLock.unlock();
        }
    }

    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (writersWaiting > 0 && writersActive == 0) okToWrite.signal();
            else okToRead.signalAll();
        }  finally {
            monLock.unlock();
        }
    }
}
