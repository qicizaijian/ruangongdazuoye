package day01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ColorDifferenceGame extends JFrame {
    private static final int GRID_SIZE = 5; // 颜色块的行和列数
    private JButton[] buttons;
    private Color[] colors;
    private int correctIndex;
    private int score = 0;

    public ColorDifferenceGame() {
        setTitle("Java图形色差测试游戏");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel colorPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        buttons = new JButton[GRID_SIZE * GRID_SIZE];
        colors = new Color[GRID_SIZE * GRID_SIZE];
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            JButton button = new JButton();
            buttons[i] = button;
            colorPanel.add(button);
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkAnswer(finalI);
                }
            });
        }

        add(colorPanel, BorderLayout.CENTER);
    }

    private void generateColors() {
        Random random = new Random();
        Color baseColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            colors[i] = baseColor;
        }
        correctIndex = random.nextInt(GRID_SIZE * GRID_SIZE);
        colors[correctIndex] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            buttons[i].setBackground(colors[i]);
            buttons[i].setEnabled(true);
        }
    }

    private void checkAnswer(int index) {
        if (index == correctIndex) {
            score++;
            JOptionPane.showMessageDialog(this, "正确！得分：" + score);
            generateColors();
        } else {
            JOptionPane.showMessageDialog(this, "错误！");
            buttons[correctIndex].setBackground(Color.RED);
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ColorDifferenceGame game = new ColorDifferenceGame();
            game.setVisible(true);
            game.generateColors();
        });
    }
}