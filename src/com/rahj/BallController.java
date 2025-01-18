package com.rahj;

public class BallController
{
    private Rect ball;
    private Rect paddleLeft;
    private Rect paddleRight;
    private double vx = -150d;
    private double vy = 100d;
    private Text leftScore;
    private Text rightScore;

    public BallController(Rect ball, Rect paddleLeft, Rect paddleRight, Text leftScore, Text rightScore)
    {
        this.ball = ball;
        this.paddleLeft = paddleLeft;
        this.paddleRight = paddleRight;
        this.leftScore = leftScore;
        this.rightScore = rightScore;
    }

    public void update(double dt)
    {
        if (this.vx < 0) {
            if (this.ball.x <= (this.paddleLeft.x + this.paddleLeft.width) &&
                    this.ball.y >= this.paddleLeft.y &&
                    this.ball.y <= (this.paddleLeft.y + this.paddleLeft.height) &&
                    this.ball.x > this.paddleLeft.x) {
                this.vx *= -1;
                //this.vy *= -1;
            }
        } else if (this.vx > 0) {
            if ((this.ball.x + this.ball.width) >= this.paddleRight.x &&
                    this.ball.x <= (this.paddleRight.x + this.paddleRight.width) &&
                    this.ball.y >= this.paddleRight.y &&
                    this.ball.y <= (this.paddleRight.y + this.paddleRight.height)) {
                this.vx *= -1;
                //this.vy *= -1;
            }
        }

        //if the ball hits the ground it bounce
        if ((this.ball.y + this.ball.height) >= (Constants.WINDOW_HEIGHT - Constants.WINDOW_INSETS_BOTTOM)) {
            this.vy *= -1;
        }

        //if the ball hits the top
        if (this.ball.y <= Constants.WINDOW_INSETS_TOP) {
            this.vy *= -1;
        }

        //set the score now
        int leftScoreNow = Integer.parseInt(this.leftScore.text);
        int rightScoreNow = Integer.parseInt(this.rightScore.text);

        //check the left score or right score for the winner
        if (leftScoreNow >= Constants.WIN_SCORE || rightScoreNow >= Constants.WIN_SCORE) {
            //if any of the player already reached the winning score, then set the ball x and y axis to 0
            this.ball.x = 0;
            this.ball.y = 0;
        } else {
            this.ball.x += (this.vx * (Constants.BALL_SPEED * dt));
            this.ball.y += (this.vy * (Constants.BALL_SPEED * dt));
        }

        //the left paddle lost
        if (this.ball.x + this.ball.width < this.paddleLeft.x && this.ball.x < 0.0) {
            //int currentRightScore = Integer.parseInt(this.rightScore.text);
            rightScoreNow++;
            this.rightScore.text = "" + rightScoreNow;
            this.ball.x = Constants.WINDOW_WIDTH / 2;
            this.ball.y = Constants.WINDOW_HEIGHT / 2;

            this.vx = 150d;
            this.vy = 100d;
        }

        //the right paddle lost
        if (this.ball.x > (this.paddleRight.x + this.paddleRight.width) && this.ball.x > Constants.WINDOW_WIDTH) {
            //int currentLeftScore = Integer.parseInt(this.leftScore.text);
            leftScoreNow++;
            this.leftScore.text = "" + leftScoreNow;
            this.ball.x = Constants.WINDOW_WIDTH / 2;
            this.ball.y = Constants.WINDOW_HEIGHT / 2;

            this.vx = -150d;
            this.vy = 100d;
        }

    }

}
