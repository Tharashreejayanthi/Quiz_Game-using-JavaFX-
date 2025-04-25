
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuizGame extends Application {
    private QuestionBank questionBank = new QuestionBank();
    private Question currentQuestion;
    private Label questionLabel = new Label();
    private Button[] optionButtons = new Button[4];
    private Label timerLabel = new Label("Time: ");
    private int score = 0;
    private int timeLeft = 10;
    private Timeline timer;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-font-size: 14;");

        root.getChildren().addAll(questionLabel, timerLabel);
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new Button();
            optionButtons[i].setMaxWidth(Double.MAX_VALUE);
            int finalI = i;
            optionButtons[i].setOnAction(e -> handleAnswer(finalI));
            root.getChildren().add(optionButtons[i]);
        }

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Quiz Game with Timer");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (timer != null) timer.stop();
        if (!questionBank.hasNext()) {
            showFinalScore();
            return;
        }
        currentQuestion = questionBank.getNextQuestion();
        questionLabel.setText(currentQuestion.getQuestion());
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setDisable(false);
        }
        timeLeft = 10;
        timerLabel.setText("Time: " + timeLeft);
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                timer.stop();
                loadNextQuestion();
            }
        }));
        timer.setCycleCount(10);
        timer.play();
    }

    private void handleAnswer(int index) {
        timer.stop();
        if (index == currentQuestion.getCorrectIndex()) {
            score++;
        }
        for (Button button : optionButtons) {
            button.setDisable(true);
        }
        new Timeline(new KeyFrame(Duration.seconds(1), e -> loadNextQuestion())).play();
    }

    private void showFinalScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Completed");
        alert.setHeaderText("Final Score");
        alert.setContentText("Your score: " + score);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
