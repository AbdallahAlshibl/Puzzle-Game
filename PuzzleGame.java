package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PuzzleGame extends Application {

    private photo selected = null;
    private int NUM_OF_MOVES = 0;
    private int clickCount = 2;
    private static final int NUM_PER_ROW = 4;
    private int top1 = 0;
    private int top2 = 0;
    private int top3 = 0;
    Pane CounterPane = new Pane();
    private int GameOverCounter = 0;

    public int getTop1() {
        return top1;
    }

    public void setTop1(int top1) {
        this.top1 = top1;
    }

    public void setTop2(int top2) {
        this.top2 = top2;
    }

    public void setTop3(int top3) {
        this.top3 = top3;
    }

    public int getTop2() {
        return top2;
    }

    public int getTop3() {
        return top3;
    }

    public void start(Stage primaryStage) throws Exception {
        BorderPane main = new BorderPane();

        HBox root2 = new HBox(15);
        Label timer = new Label("Time spent: ");
        timer.setTextFill(Color.BLUEVIOLET);
        timer.setFont(Font.font("Times New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 30));
        root2.getChildren().addAll(timer, new TimeLineCounter());
        root2.setPadding(new Insets(10, 10, 10, 115));
        main.setTop(root2);

        HBox stats = new HBox(50);
        Pane root = new Pane();
        Scene sc = new Scene(main, 400, 650);

        Image im1 = new Image("1.png");
        Image im2 = new Image("2.jpg");
        Image im3 = new Image("3.png");
        Image im4 = new Image("4.jpg");
        Image im5 = new Image("5.png");
        Image im6 = new Image("6.jpg");
        Image im7 = new Image("7.jpg");
        Image im8 = new Image("8.png");

        List<photo> list = new ArrayList<>();
        list.add(new photo(new ImageView(im1)));
        list.add(new photo(new ImageView(im1)));
        list.add(new photo(new ImageView(im2)));
        list.add(new photo(new ImageView(im2)));
        list.add(new photo(new ImageView(im3)));
        list.add(new photo(new ImageView(im3)));
        list.add(new photo(new ImageView(im4)));
        list.add(new photo(new ImageView(im4)));
        list.add(new photo(new ImageView(im5)));
        list.add(new photo(new ImageView(im5)));
        list.add(new photo(new ImageView(im6)));
        list.add(new photo(new ImageView(im6)));
        list.add(new photo(new ImageView(im7)));
        list.add(new photo(new ImageView(im7)));
        list.add(new photo(new ImageView(im8)));
        list.add(new photo(new ImageView(im8)));
        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            photo lists = list.get(i);

            lists.setTranslateX(100 * (i % NUM_PER_ROW));
            lists.setTranslateY(100 * (i / NUM_PER_ROW));

            root.getChildren().add(lists);

        }
        VBox t1 = new VBox(5);

        t1.getChildren().addAll(new Label("Top1"), new Label("  " + Integer.toString(getTop1())));
        VBox t2 = new VBox(5);
        t2.getChildren().addAll(new Label("Top2"), new Label("  " + Integer.toString(getTop2())));
        VBox t3 = new VBox(5);
        t3.getChildren().addAll(new Label("Top3"), new Label("  " + Integer.toString(getTop3())));
        Button reset = new Button("Play Again");


        Counter(NUM_OF_MOVES);


        reset.setOnAction(e -> {

            if (GameOverCounter == 8) {
                t1.getChildren().clear();
                t2.getChildren().clear();
                t3.getChildren().clear();
                ScoreBoard(NUM_OF_MOVES);
                t1.getChildren().addAll(new Label("Top1"), new Label("  " + Integer.toString(getTop1())));
                t2.getChildren().addAll(new Label("Top2"), new Label("  " + Integer.toString(getTop2())));
                t3.getChildren().addAll(new Label("Top3"), new Label("  " + Integer.toString(getTop3())));
            }
            GameOverCounter = 0;
            NUM_OF_MOVES = 0;
            Counter(NUM_OF_MOVES);

            root.getChildren().clear();
            Collections.shuffle(list);
            for (int i = 0; i < list.size(); i++) {
                photo lists2 = list.get(i);
                lists2.close();
                lists2.setTranslateX(100 * (i % NUM_PER_ROW));
                lists2.setTranslateY(100 * (i / NUM_PER_ROW));

                root.getChildren().add(lists2);

                stats.getChildren().clear();
                stats.getChildren().addAll(reset, CounterPane, t1, t2, t3);
            }
        });
        stats.setPadding(new Insets(10, 10, 10, 10));
        stats.getChildren().addAll(reset, CounterPane, t1, t2, t3);
        main.setCenter(root);
        main.setBottom(stats);
        main.setAlignment(stats, Pos.BASELINE_CENTER);

        main.setStyle("-fx-border-color: #355980; -fx-background-color: #73bdf3;");
        primaryStage.setTitle("Memory Puzzle Game");
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }

    public void ScoreBoard(int score) {
        score--;
        if (top1 == 0) setTop1(score);
        else if (top2 == 0 && score > top1)
            setTop2(score);
        else if (top2 == 0 && score < top1) {
            setTop2(getTop1());
            setTop1(score);
        } else if (top3 == 0 && score > top2 && score > top1)
            setTop3(score - 1);
        else if (top3 == 0 && score < top2 && score > top1) {
            setTop3(getTop2());
            setTop2(score);
        } else if (top3 == 0 && score < top2 && score < top1) {
            setTop3(getTop2());
            setTop2(getTop1());
            setTop1(score);
        } else if (score < top1 && score < top2 && score < top3) {
            setTop3(getTop2());
            setTop2(getTop1());
            setTop1(score);
        } else if (score > top1 && score < top2 && score < top3) {
            setTop3(getTop2());
            setTop2(score);
        } else if (score > top1 && score > top2 && score < top3)
            setTop3(score);

    }

    public void Counter(int num) {
        CounterPane.getChildren().clear();
        Text count = new Text(Integer.toString(num));
        count.setStroke(Color.BLACK);
        count.setScaleX(5);
        count.setScaleY(5);
        CounterPane.getChildren().add(count);
    }

    public void wrongSoundEffect() {
        String path = "wrong.wav";
        Media m = new Media(new File(path).toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
    }

    public void correctSoundEffect() {
        String path = "correct.wav";
        Media m = new Media(new File(path).toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
    }

    public void GameOver() {
        String path = "victory.wav";
        Media m = new Media(new File(path).toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();

    }

    private class photo extends StackPane {

        private ImageView iv = new ImageView();

        public photo(ImageView iv) {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(Color.SLATEGRAY);
            border.setStroke(Color.MEDIUMAQUAMARINE);
            this.iv = iv;
            this.iv.setFitHeight(100);
            this.iv.setFitWidth(100);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, this.iv);
            setOnMouseClicked(this::handleMouseClick);
            close();
        }

        public void handleMouseClick(MouseEvent event) {
            if (NUM_OF_MOVES == 0)
                Counter(NUM_OF_MOVES++);
            if (isOpen() || clickCount == 0) {

                return;
            }
            clickCount--;

            if (selected == null) {
                selected = this;
                open(() -> {
                });
            } else {
                open(() -> {
                    if (!hasSameValue(selected)) {
                        wrongSoundEffect();
                        Counter(NUM_OF_MOVES++);
                        selected.close();
                        this.close();
                    } else if (hasSameValue(selected)) {
                        GameOverCounter++;
                        Counter(NUM_OF_MOVES++);
                        if (GameOverCounter == 8) {
                            GameOver();
                        } else
                            correctSoundEffect();
                    }
                    selected = null;
                    clickCount = 2;
                });
            }

        }

        public boolean isOpen() {
            return iv.getOpacity() == 1;
        }

        public void open(Runnable action) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), iv);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        public boolean hasSameValue(photo other) {
            if (iv.getImage() == other.iv.getImage())
                return true;
            else
                return false;

        }

        public void close() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.0001), iv);
            ft.setToValue(0);
            ft.play();
        }
    }
}

abstract class Counter extends Label {

    protected int count = 0;

    public Counter() {
        setAlignment(Pos.CENTER);
        setTextFill(Color.BLUEVIOLET);
        setFont(Font.font("Times New Roman",
                FontWeight.BOLD, FontPosture.ITALIC, 30));
        count();
    }

    abstract void count();
}

class TimeLineCounter extends Counter {

    @Override
    void count() {

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(1),
                event -> {
                    setText(String.valueOf(count++));
                }
        );
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}