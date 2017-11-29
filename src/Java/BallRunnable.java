package Java;

import javafx.scene.paint.Color;

public class BallRunnable implements Runnable {

    private Ball ball;
    private RW rw;

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
                    if(ball.getXPos() > ball.getMinCsX() && ball.getXPos() < ball.getMaxCsX()){
                        ball.setInZone(true);
                    }

                    if(ball.isEnteringCs()) {
                        rw.enterReader();
                    }
                    if(ball.isLeavingCs()) {
                        rw.exitReader();
                        ball.setInZone(false);
                    }
                }
                if(ball.getColor() == Color.BLUE) {
                    if(ball.getXPos() > ball.getMinCsX() && ball.getXPos() < ball.getMaxCsX()){
                        ball.setInZone(true);
                    }

                    if(ball.isEnteringCs()) {
                        rw.enterWriter();
                    }
                    if(ball.isLeavingCs()) {
                        rw.exitWriter();
                        ball.setInZone(false);
                    }
                }
                Thread.sleep(ball.getSpeed());

            } catch (InterruptedException ex) {
                if(ball.getColor() == Color.RED && ball.getInZone()) {
                    rw.exitReader();
                }
                if(ball.getColor() == Color.BLUE && ball.getInZone()) {
                    rw.exitWriter();
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
