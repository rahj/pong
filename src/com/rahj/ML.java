package com.rahj;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ML extends MouseAdapter implements MouseListener
{
    private boolean isClicked;
    private int mouseX;
    private int mouseY;

    @Override
    public void mousePressed(MouseEvent e)
    {
        this.isClicked = true;
        this.setMouseX(e.getX());
        this.setMouseY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.isClicked = false;
    }

    public boolean isClicked()
    {
        return this.isClicked;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public void setMouseX(int mouseX)
    {
        this.mouseX = mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    public void setMouseY(int mouseY)
    {
        this.mouseY = mouseY;
    }

}
