/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Peter Boots
 */
public class BBMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BBMonitor bbm = new BBMonitor(3);
        Thread p1 = new Thread(new Producer(bbm));
        Thread c1 = new Thread(new Consumer(bbm));
        p1.start();
        c1.start();
    }
}
