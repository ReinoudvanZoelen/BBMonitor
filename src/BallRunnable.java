public class BallRunnable implements Runnable {

    private Ball ball;

    public BallRunnable(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ball.move();
                   
                Thread.sleep(ball.getSpeed());
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
