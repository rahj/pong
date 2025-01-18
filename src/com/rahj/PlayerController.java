package com.rahj;

import java.awt.event.KeyEvent;

public class PlayerController
{
    public Rect rect;
    public KL keyListener;

    public PlayerController(Rect rect, KL keyListener)
    {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    public void update(double dt)
    {
        if (this.keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            if ((this.rect.y + Constants.PADDLE_SPEED * dt) + this.rect.height < (Constants.WINDOW_HEIGHT - Constants.WINDOW_INSETS_BOTTOM)) {
                this.rect.y += (Constants.PADDLE_SPEED * dt);
            }
        }

        if (this.keyListener.isKeyPressed(KeyEvent.VK_UP)) {
            if ((this.rect.y - Constants.PADDLE_SPEED * dt) > Constants.WINDOW_INSETS_TOP) {
                this.rect.y -= (Constants.PADDLE_SPEED * dt);
            }
        }
    }


}
