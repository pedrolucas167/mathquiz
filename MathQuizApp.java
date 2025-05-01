import java.awt.*;
import javax.swing.*;

public class MathQuizApp extends JFrame {
    private final JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextField answerField = new JTextField();
    private final JButton submitButton = new JButton("Responder");
    private final JButton restartButton = new JButton("Reiniciar");
    private final JLabel scoreLabel = new JLabel("Pontuação: 0", SwingConstants.CENTER);
    private final JLabel timerLabel = new JLabel("Tempo: 10", SwingConstants.CENTER);

    private final QuizEngine engine = new QuizEngine();
    private Timer timer;
    private int timeLeft = 10;
    private int questionCount = 0;
    private final int maxQuestions = 10;

    public MathQuizApp() {
        setTitle("Quiz de Matemática");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        add(questionLabel);
        add(answerField);
        add(submitButton);
        add(restartButton);
        add(scoreLabel);
        add(timerLabel);

        submitButton.addActionListener(e -> checkAnswer());
        restartButton.addActionListener(e -> restartGame());

        startGame();
    }

    private void startGame() {
        engine.reset();
        scoreLabel.setText("Pontuação: 0");
        questionCount = 0;
        nextQuestion();
        startTimer();
    }

    private void nextQuestion() {
        if (questionCount >= maxQuestions) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Fim do jogo! Pontuação final: " + engine.getScore());
            return;
        }

        engine.nextQuestion();
        questionLabel.setText(engine.getQuestion());
        answerField.setText("");
        answerField.requestFocusInWindow();
        timeLeft = 10;
        timerLabel.setText("Tempo: " + timeLeft);
        questionCount++;
    }

    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText().trim());
            if (engine.checkAnswer(userAnswer, timeLeft)) {
                scoreLabel.setText("Pontuação: " + engine.getScore());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido.");
        }
        nextQuestion();
    }

    private void restartGame() {
        timer.stop();
        startGame();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Tempo: " + timeLeft);
            if (timeLeft <= 0) {
                JOptionPane.showMessageDialog(this, "Tempo esgotado!");
                nextQuestion();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MathQuizApp::new);
    }
}
