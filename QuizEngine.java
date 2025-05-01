import java.util.Random;

public class QuizEngine {
    private int num1, num2, correctAnswer, score;
    private String operation;
    private final String[] operations = {"+", "-", "*", "/"};
    private final Random random = new Random();

    public void nextQuestion() {
        operation = operations[random.nextInt(operations.length)];

        switch (operation) {
            case "+" -> {
                num1 = random.nextInt(50) + 1;
                num2 = random.nextInt(50) + 1;
                correctAnswer = num1 + num2;
            }
            case "-" -> {
                num1 = random.nextInt(50) + 1;
                num2 = random.nextInt(50) + 1;
                correctAnswer = num1 - num2;
            }
            case "*" -> {
                num1 = random.nextInt(12) + 1;
                num2 = random.nextInt(12) + 1;
                correctAnswer = num1 * num2;
            }
            case "/" -> {
                do {
                    num1 = random.nextInt(50) + 1;
                    num2 = random.nextInt(12) + 1;
                } while (num2 == 0 || num1 % num2 != 0);
                correctAnswer = num1 / num2;
            }
        }
    }

    public String getQuestion() {
        return "Quanto é " + num1 + " " + operation + " " + num2 + "?";
    }

    public boolean checkAnswer(int answer, int timeLeft) {
        if (answer == correctAnswer) {
            score += 1 + (timeLeft * 2) / 10;
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
