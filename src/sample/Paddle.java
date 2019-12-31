package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle implements Renderer
{

    private int x, width = 15, height = 100;
    private boolean upPaddle, downPaddle;
    private double y, yVelocity = 0;
    private int paddleNumber;

    public int score = 0;

    Paddle(int paddleNumber)
    {
        this.paddleNumber = paddleNumber;

        if(paddleNumber == 1)
        {
            this.x = width;
        }

        if(paddleNumber == 2)
        {
            this.x = 800 - 2 * width;

        }
        this.y = 600 / 2 - this.height / 2;
    }

    void setUpPaddle(boolean input)
    {

        this.upPaddle = input;
    }
    void setDownPaddle(boolean input)
    {

        this.downPaddle = input;
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        gc.setFill(Color.WHITE);
        gc.fillRect(x, (int)y, width, height);
    }


    public void move()
    {
        if(upPaddle)
        {
            yVelocity -= 2;
        }
        else if(downPaddle)
        {
            yVelocity += 2;
        }
        else if(!upPaddle && !downPaddle)
        {
            yVelocity = 0;
        }

        if(yVelocity >=5)
            yVelocity = 5;
        if(yVelocity <= -5)
            yVelocity = -5;

        y += yVelocity;

        if(y < 0)
        {
            y = 0;
        }
        if(y  >  500)
        {
            y = 500;
        }
    }


    public int getY()
    {
        return (int)y;
    }
}
