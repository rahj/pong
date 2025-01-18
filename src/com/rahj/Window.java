/**
 * RAHJ PingPong v.1 main window
 *
 * September 26, 2020
 * by Reynaldo A. Hipolito
 */
package com.rahj;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable
{
    private Graphics2D g2;
    private KL keyListener;
    private ML mouseListener;
    private Rect playerOne, playerAI, ball;
    private PlayerController playerController;
    private AIController aIController;
    private BallController ballController;

    private Text dividerScore;
    private Text leftScore;
    private Text rightScore;
    private Text playersName;
    private Text aiName;
    private Text playerWin;
    private Text playerAIWin;
    private Text restartGame;
    private Text quitGame;
    private boolean hasWinner;

    public Window()
    {
        //instantiate the keylistener
        this.keyListener = new KL();
        this.mouseListener = new ML();

        //ini the winner to false
        this.hasWinner = false;

        //init the window
        this.setTitle(Constants.WINDOW_TITLE);
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(this.keyListener);
        this.addMouseListener(this.mouseListener);
        this.setVisible(true);

        //get insets top and bottom
        Constants.WINDOW_INSETS_TOP = (double) this.getInsets().top;
        Constants.WINDOW_INSETS_RIGHT = (double) this.getInsets().right;
        Constants.WINDOW_INSETS_BOTTOM = (double) this.getInsets().bottom;
        Constants.WINDOW_INSETS_LEFT = (double) this.getInsets().left;

        //instantiate
        this.playerOne = new Rect(Constants.WINDOW_INSETS_LEFT + Constants.X_PADDING, Constants.WINDOW_HEIGHT / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        this.playerAI = new Rect(Constants.WINDOW_WIDTH - (Constants.WINDOW_INSETS_RIGHT + Constants.X_PADDING + 10), Constants.WINDOW_HEIGHT / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        this.ball = new Rect(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2, Constants.BALL_DIMENSION, Constants.BALL_DIMENSION, Constants.PADDLE_COLOR);

        //set the text score and players name
        this.dividerScore = new Text("|", (Constants.WINDOW_WIDTH / 2), 60d, new Font(Constants.FONT_FAMILY, Font.PLAIN, Constants.FONT_SIZE), Constants.FONT_COLOR);
        this.leftScore = new Text("0", (Constants.WINDOW_WIDTH / 2) - 20, 60d, new Font(Constants.FONT_FAMILY, Font.PLAIN, Constants.FONT_SIZE), Constants.FONT_COLOR);
        this.rightScore = new Text("0", (Constants.WINDOW_WIDTH / 2) + 15, 60d, new Font(Constants.FONT_FAMILY, Font.PLAIN, Constants.FONT_SIZE), Constants.FONT_COLOR);
        this.playersName = new Text("Irish, Joaquin, Luis, Andra", (Constants.WINDOW_WIDTH / 2) - 200, 60d, new Font(Constants.FONT_FAMILY, Font.PLAIN, Constants.FONT_SIZE), Constants.FONT_COLOR);
        this.aiName = new Text("Uncle Jun", (Constants.WINDOW_WIDTH / 2) + 35, 60d, new Font(Constants.FONT_FAMILY, Font.PLAIN, Constants.FONT_SIZE), Constants.FONT_COLOR);

        //init the winner text
        this.playerWin = new Text("You Win!", 300, Constants.WINDOW_HEIGHT / 2, new Font(Constants.FONT_FAMILY, Font.PLAIN, 40), Constants.FONT_COLOR);
        this.playerAIWin = new Text("Uncle Jun Win!", 290, Constants.WINDOW_HEIGHT / 2, new Font(Constants.FONT_FAMILY, Font.PLAIN, 40), Constants.FONT_COLOR);

        //init the restart game and quit
        this.restartGame = new Text("Restart", 300, (Constants.WINDOW_HEIGHT / 2) + 50, new Font(Constants.FONT_FAMILY, Font.PLAIN, 30), Constants.FONT_COLOR);
        this.quitGame = new Text("Quit", 300, (Constants.WINDOW_HEIGHT / 2) + 100, new Font(Constants.FONT_FAMILY, Font.PLAIN, 30), Constants.FONT_COLOR);

        //init controllers
        this.playerController = new PlayerController(this.playerOne, this.keyListener);
        this.aIController = new AIController(this.playerAI, this.ball);
        this.ballController = new BallController(this.ball, this.playerOne, this.playerAI, this.leftScore, this.rightScore);

        //set the graphics
        this.g2 = (Graphics2D) this.getGraphics();
    }

    @Override
    public void run()
    {
        double lastFrameTime = 0.0;
        while (true) {
            double time = Time.getTime();
            double deltaTime = (time - lastFrameTime);
            lastFrameTime = time;
            this.update(deltaTime);
        }
    }

    public void update(double dt)
    {
        Image dbImage = this.createImage(this.getWidth(), this.getHeight());
        Graphics dbGraphics = dbImage.getGraphics();
        this.draw(dbGraphics);
        this.g2.drawImage(dbImage, 0, 0, this);

        //update the playercontroller
        this.playerController.update(dt);

        //update the ballcontroller
        this.ballController.update(dt);

        //update the aicontroller
        this.aIController.update(dt);
    }

    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.playerOne.draw(g2);
        this.playerAI.draw(g2);
        this.ball.draw(g2);

        //add the scores and divider
        this.dividerScore.draw(g2);
        this.leftScore.draw(g2);
        this.rightScore.draw(g2);
        this.playersName.draw(g2);
        this.aiName.draw(g2);

        //check the winner
        this.checkWinner(g2);
    }

    public void checkWinner(Graphics2D g2)
    {
        int rightScoreNow = Integer.parseInt(this.rightScore.text);
        int leftScoreNow = Integer.parseInt(this.leftScore.text);

        //the right player wins
        if (rightScoreNow >= Constants.WIN_SCORE) {
            this.playerAIWin.draw(g2);
            this.hasWinner = true;
        }

        //the left player wins
        if (leftScoreNow >= Constants.WIN_SCORE) {
            this.playerWin.draw(g2);
            this.hasWinner = true;
        }

        if (this.hasWinner) {
            this.restartGame.draw(g2);
            this.quitGame.draw(g2);

            if (this.mouseListener.isClicked()) {
                int mouseX = this.mouseListener.getMouseX();
                int mouseY = this.mouseListener.getMouseY();

                //get the restartgame x and y position
                double restartGameX = this.restartGame.x - Constants.WINDOW_INSETS_LEFT;
                double restartGameY = this.restartGame.y - Constants.WINDOW_INSETS_TOP;
                double restartGameWidth = (this.restartGame.width - 100);
                double restartGameHeight = this.restartGame.height;

                //get the quitgame x and y position
                double quitGameX = this.quitGame.x;
                double quitGameY = this.quitGame.y;
                double quitGameWidth = this.quitGame.width;
                double quitGameHeight = this.quitGame.height;

                //check if the mouse x and y are in the restart game
                if (mouseX > restartGameX && mouseX < (restartGameX + restartGameWidth) && mouseY > restartGameY && mouseY < (restartGameY + restartGameHeight)) {


                    System.out.println("RestartGameX: " + restartGameX);
                    System.out.println("RestartGameWidth: " + restartGameWidth);
                    System.out.println("MouseX: " + mouseX);
                }
            }
        }
    }



}
