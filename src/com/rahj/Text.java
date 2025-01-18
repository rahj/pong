package com.rahj;

import java.awt.*;

public class Text
{
    public String text;
    public double x;
    public double y;
    public double width;
    public double height;
    public Font font;
    public Color color;

    public Text(String text, double x, double y, Font font, Color color)
    {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.color = color;
        this.width = (double) (font.getSize() * text.length());
        this.height = (double) font.getSize();
    }

    public void draw(Graphics2D g2)
    {
        g2.setFont(this.font);
        g2.setColor(this.color);
        g2.drawString(this.text, (float) this.x, (float) this.y);
    }

}
