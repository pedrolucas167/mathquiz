import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MathQuiz extends JFrame {
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton submitButton, restartButton;
    private JLabel scoreLabel, timerLabel;
    private int score = 0;
    private int questionCount = 0;
    private int num1, num2, correctAnswer;
    private Random random = new Random();
    private String[] operations = {"+", "-", "*", "/"};
    private Timer timer;
    private int timeLeft = 10;

    public MathQuiz() {
        setTitle("Quiz de Matemática");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        answerField = new JTextField();
        submitButton = new JButton("Responder");
        restartButton = new JButton("Reiniciar");
        scoreLabel = new JLabel("Pontuação: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Tempo: " + timeLeft, SwingConstants.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        add(questionLabel);
        add(answerField);
        add(submitButton);
        add(restartButton);
        add(scoreLabel);
        add(timerLabel);

        nextQuestion();
        startTimer();
        setVisible(true);
    }

    private void nextQuestion() {
        if (questionCount >= 10) {
            JOptionPane.showMessageDialog(this, "Quiz finalizado! Pontuação: " + score + "/10");
            restartGame();
            return;
        }

        timeLeft = 10;
        timerLabel.setText("Tempo: " + timeLeft);
        
        String operation = operations[random.nextInt(operations.length)];
        num1 = random.nextInt(20) + 1;
        num2 = random.nextInt(20) + 1;
        
        switch (operation) {
            case "+":
                correctAnswer = num1 + num2;
                break;
            case "-":
                correctAnswer = num1 - num2;
                break;
            case "*":
                correctAnswer = num1 * num2;
                break;
            case "/":
                while (num2 == 0 || num1 % num2 != 0) {
                    num1 = random.nextInt(20) + 1;
                    num2 = random.nextInt(10) + 1;
                }
                correctAnswer = num1 / num2;
                break;
        }

        questionLabel.setText("Quanto é " + num1 + " " + operation + " " + num2 + "?");
        answerField.setText("");
        questionCount++;
    }

    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText().trim());
            if (userAnswer == correctAnswer) {
                score++;
                scoreLabel.setText("Pontuação: " + score);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido.");
            return;
        }
        nextQuestion();
    }

    private void restartGame() {
        score = 0;
        questionCount = 0;
        scoreLabel.setText("Pontuação: 0");
        nextQuestion();
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Tempo: " + timeLeft);
                if (timeLeft <= 0) {
                    JOptionPane.showMessageDialog(null, "Tempo esgotado!");
                    nextQuestion();
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MathQuiz::new);
    }
}
