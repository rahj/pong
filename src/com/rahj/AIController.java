package com.rahj;

public class AIController
{
    private Rect paddleRight;
    private Rect ball;

    public AIController(Rect paddleRight, Rect ball)
    {
        this.paddleRight = paddleRight;
        this.ball = ball;
    }

    public void update(double dt)
    {
        if (this.ball.y > this.paddleRight.y + this.paddleRight.height) {
            this.moveDown(dt);
        }

        if (this.ball.y < this.paddleRight.y) {
            this.moveUp(dt);
        }
    }

    public void moveUp(double dt)
    {
        if ((this.paddleRight.y - Constants.PADDLE_SPEED * dt) > Constants.WINDOW_INSETS_TOP) {
            this.paddleRight.y -= (Constants.PADDLE_SPEED * dt);
        }
    }

    public void moveDown(double dt)
    {
        if ((this.paddleRight.y + Constants.PADDLE_SPEED * dt) + this.paddleRight.height < (Constants.WINDOW_HEIGHT - Constants.WINDOW_INSETS_BOTTOM)) {
            this.paddleRight.y += (Constants.PADDLE_SPEED * dt);
        }
    }
}
