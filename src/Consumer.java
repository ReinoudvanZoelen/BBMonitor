/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter Boots
 */
public class Consumer implements Runnable {

    private BBMonitor bbm;

    public Consumer(BBMonitor bbm) {
        this.bbm = bbm;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            for (int i=0;i<100;i++) {
                System.out.println("Trying to fetch next item");
                String s = bbm.fetch();
                System.out.println("Just fetched: "+s);
                Thread.sleep((new Random()).nextInt(1000));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
