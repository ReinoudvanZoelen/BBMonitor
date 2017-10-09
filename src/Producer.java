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
public class Producer implements Runnable {

    private BBMonitor bbm;

    public Producer(BBMonitor bbm) {
        this.bbm = bbm;
    }

    @Override
    public void run() {
        try {
            int p = 0;
            for (int i=0;i<100;i++) {
                p++;
                System.out.println("Trying to add: Product"+p);
                bbm.deposit("Product" + p);
                System.out.println("Just added: Product"+p);
                Thread.sleep((new Random()).nextInt(1000));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
