
import java.util.*;

public class QuestionBank {
    private List<Question> questions;
    private int currentIndex;

    public QuestionBank() {
        questions = new ArrayList<>();
        currentIndex = 0;
        loadSampleQuestions();
    }

    private void loadSampleQuestions() {
        questions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 0));
        questions.add(new Question("Which language runs in a web browser?", new String[]{"Java", "Python", "C", "JavaScript"}, 3));
        questions.add(new Question("Who developed Java?", new String[]{"Google", "James Gosling", "Microsoft", "Apple"}, 1));
    }

    public boolean hasNext() {
        return currentIndex < questions.size();
    }

    public Question getNextQuestion() {
        return hasNext() ? questions.get(currentIndex++) : null;
    }

    public void reset() {
        currentIndex = 0;
        Collections.shuffle(questions);
    }
}
