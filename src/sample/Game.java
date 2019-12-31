package sample;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class Game extends Application
{

    private int width = 800;
    private int height = 600;
    private Paddle player1;
    private Paddle player2;
    private Ball ball;
    private boolean appended = false;
    private int gameStatus = 0; // 0-Menu, 1-Paused, 2-playing, 3-GameOver
    private int scoreLimit = 5,  winner;

    //Media media;
    //MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("PONG");
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //media = new Media("file: Popcorn.wav");
        //mediaPlayer = new MediaPlayer(media);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root,800, 600);



        scene.setOnKeyPressed(event ->
        {
            switch (event.getCode())
            {
                case UP:
                    player2.setUpPaddle(true);
                    break;

                    case DOWN:
                    player2.setDownPaddle(true);
                    break;

                    case W:
                    player1.setUpPaddle(true);
                    break;

                case S:
                    player1.setDownPaddle(true);
                    break;

                case ENTER:
                    if (gameStatus == 0)
                    {
                        gameStatus = 2;
                        startUP();
                        break;
                    }
                    if (gameStatus == 2)
                    {
                        gameStatus = 1;
                        break;
                    }
                    if (gameStatus == 1)
                    {
                        gameStatus = 2;
                        break;
                    }
                    if (gameStatus == 3)
                    {
                        gameStatus = 2;
                        startUP();
                        appended = false;
                        break;
                    }
                    if (gameStatus == 3 )
                    {
                        gameStatus = 2;
                        startUP();
                        break;
                    }
                    break;

                case ESCAPE:
                    if (gameStatus == 2 || gameStatus == 1 || gameStatus == 3)
                    {
                        player1.score = 0;
                        player2.score = 0;
                        appended = false;
                        gameStatus = 0;
                    }
                    break;

                case LEFT:
                    if (gameStatus == 0 && scoreLimit > 1)
                    {
                        scoreLimit--;
                    }
                    break;

                case RIGHT:
                    if (gameStatus == 0 && scoreLimit < 73)
                    {
                        scoreLimit++;
                    }
                    break;

            }
        });

        scene.setOnKeyReleased(event ->
        {
            switch (event.getCode()) {
                case UP:
                    player2.setUpPaddle(false);
                    break;

                case DOWN:
                    player2.setDownPaddle(false);
                    break;

                case W:
                    player1.setUpPaddle(false);
                    break;

                case S:
                    player1.setDownPaddle(false);
                    break;

            }
        });
        startUP();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e ->
        {
            try {
                run(gc);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }));
        t1.setCycleCount(Timeline.INDEFINITE);
        stage.setScene(scene);
        stage.show();
        t1.play();
    }
    private void startUP()
    {
        //mediaPlayer.play();

        player1 = new Paddle(1);
        player2 = new Paddle(2);
        ball = new Ball();
    }


    private void paint(GraphicsContext gc) throws IOException
    {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        if(gameStatus == 0){
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(50));
            gc.fillText("Atari PONG 1972", width/2 - 173, height/2 -100);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(25));
            gc.fillText("Press ENTER to play ", width/2 - 125, height/2 -50);
            gc.fillText("<< Limit of score for game over: "+ scoreLimit +" >>", width/2 - 215, height/2 +50 );
        }

        if(gameStatus == 2 || gameStatus == 1)
        {
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeLine(width / 2, 0, width / 2, height);
            gc.strokeOval(width / 2 - 150, height / 2 - 150, 300, 300);
            gc.strokeOval(width / 2 + 350, height / 2 - 100, 200, 200 );
            gc.strokeOval(width / 2 - 550, height / 2 - 100, 200, 200 );
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(50));
            gc.fillText(String.valueOf(player1.score), width / 2 - 60 , 60 );
            gc.fillText(String.valueOf(player2.score), width / 2 + 33 , 60 );

            player1.draw(gc);
            player2.draw(gc);
            ball.draw(gc);
        }

        if(gameStatus == 1)
        {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(50));
            gc.fillText("PAUSED", width/2 -93, height/2 -50);
        }

        if(gameStatus == 3)
        {
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(50));
            gc.fillText("PONG", width/2 -75, height/2 -100);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");


            if(winner == 2)
            {
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font(100));
                gc.fillText("Player 2 WINS!", width / 2 -300, height/2);

                if (!appended) {
                    FileWriter fileWriter = new FileWriter("pong.txt", true);
                    PrintWriter writer = new PrintWriter(fileWriter);

                    writer.println("Game time: " + sdf.format(cal.getTime()));
                    writer.println("Player1 score: " + player1.score);
                    writer.println("Player2 score: " + player2.score);
                    writer.println("Player2 WON !");
                    writer.close();
                    appended = true;
                }
            }
            if(winner == 1){
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font(100));
                gc.fillText("Player 1 WINS!", width / 2 -300, height/2);

                if(!appended)
                {
                    FileWriter fileWriter = new FileWriter("pong.txt", true);
                    PrintWriter writer = new PrintWriter(fileWriter);


                    writer.println("Game time " + sdf.format(cal.getTime()));
                    writer.println("Player1 score: " + player1.score);
                    writer.println("Player2 score: " + player2.score);
                    writer.println("Player1 WON !");
                    writer.close();
                    appended = true;
                }
            }

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(25));
            gc.fillText("Press ENTER to Play Again", width/2 -145, height/2 + 100);
            gc.fillText("Press ESC for Menu", width/2 -120, height/2 + 150);

        }
    }

    private void run(GraphicsContext gc) throws IOException {
        paint(gc);

        if (player1.score >= scoreLimit)
        {
            winner = 1;
            gameStatus = 3;
        }

        if (player2.score >= scoreLimit)
        {
            gameStatus = 3;
            winner = 2;
        }

        if(gameStatus == 2)
        {
            player1.move();
            player2.move();
            ball.move();
            ball.paddleCollision(player1, player2);
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
