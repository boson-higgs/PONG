package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball implements Renderer
{
    private int x, y, xVelocity, yVelocity;
    private Random random;
    private int paddleHits = 0;

    Ball()
    {
        random = new Random();
        display();

    }

    public int getY() {
        return y;
    }


    @Override
    public void draw(GraphicsContext gc)
    {
        gc.setFill(Color.WHITE);
        gc.fillOval(x - 10, y - 10,20,20);  // -10 zajistuje chovani jako stred
    }


    public void move()
    {
        x += xVelocity;
        y += yVelocity;

        if(y < 10)
            yVelocity = -yVelocity;

        if(y > 590)
            yVelocity = -yVelocity;
    }

    public void paddleCollision(Paddle paddle1, Paddle paddle2)
    {

        if(x <= 40 && x >= 15) {
            if (y >= paddle1.getY() && y <= paddle1.getY() + 100)
            {
                x = 40;
                paddleHits++;
                xVelocity = 1 + (paddleHits / 4);
                yVelocity = -2 + random.nextInt(4);
                if (yVelocity == 0)
                    yVelocity = 1;
            }
        }
        else if(x <= 0)
        {
            paddle2.score++;
            display();
        }

        if(x >= 760 && x <= 775)
        {
            if (y >= paddle2.getY() && y <= paddle2.getY() + 100)
            {
                x = 760;
                paddleHits++;
                xVelocity = - 1 - (paddleHits / 4);
                yVelocity = -2 + random.nextInt(4);
                if (yVelocity == 0)
                {
                    yVelocity = 1;
                }
            }
        }
        else if(x >= 800)
        {
            paddle1.score++;
            display();
        }
    }

    public void display()
    {
        x = 400;
        y = 300;
        xVelocity = -1 + random.nextInt(2);
        if(xVelocity == 0)
        {
            xVelocity = 1;
        }
        yVelocity = -2 + random.nextInt(4);
        if(yVelocity == 0)
        {
            yVelocity = 1;
        }
        paddleHits = 0;
    }
}
