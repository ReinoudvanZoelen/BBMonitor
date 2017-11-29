package Java;

import javafx.scene.paint.Color;

public class BallRunnable implements Runnable {

    private Ball ball;
    private RW rw;
    private boolean inZone = false;

    public BallRunnable(Ball ball, RW rw) {
        this.ball = ball;
        this.rw = rw;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ball.move();
                if(ball.getColor() == Color.RED) {
                    if(ball.isEnteringCs()) {
                        rw.enterReader();
                        inZone = true;
                    }
                    if(ball.isLeavingCs()) {
                        rw.exitReader();
                        inZone = false;
                    }
                }
                if(ball.getColor() == Color.BLUE) {
                    if(ball.isEnteringCs()) {
                        rw.enterWriter();
                        inZone = true;
                    }
                    if(ball.isLeavingCs()) {
                        rw.exitWriter();
                        inZone = false;
                    }
                }
                Thread.sleep(ball.getSpeed());

            } catch (InterruptedException ex) {
                if(ball.getColor() == Color.RED && inZone) {
                    rw.exitReader();
                }
                if(ball.getColor() == Color.BLUE && inZone) {
                    rw.exitWriter();
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
