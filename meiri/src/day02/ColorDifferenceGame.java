package day02;

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
    private int difficulty = 50; // 难度值，值越小色差越小

    public ColorDifferenceGame() {
        setTitle("Java图形色差测试游戏");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 添加难度选择
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(new JLabel("选择难度:"));
        JComboBox<String> difficultyBox = new JComboBox<>(new String[]{"容易", "中等", "困难"});
        difficultyBox.setSelectedIndex(1); // 默认选中中等难度
        difficultyBox.addActionListener(e -> {
            String selected = (String) difficultyBox.getSelectedItem();
            switch (selected) {
                case "容易":
                    difficulty = 50;
                    break;
                case "中等":
                    difficulty = 25;
                    break;
                case "困难":
                    difficulty = 10;
                    break;
            }
            generateColors(); // 重新生成颜色块以应用新的难度
        });
        difficultyPanel.add(difficultyBox);

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

        add(difficultyPanel, BorderLayout.NORTH);
        add(colorPanel, BorderLayout.CENTER);
    }

    private void generateColors() {
        Random random = new Random();
        Color baseColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            colors[i] = baseColor;
        }
        correctIndex = random.nextInt(GRID_SIZE * GRID_SIZE);
        // 根据难度调整色差
        int r = random.nextInt(256) - 128;
        int g = random.nextInt(256) - 128;
        int b = random.nextInt(256) - 128;
        int maxDiff = Math.max(Math.max(Math.abs(r), Math.abs(g)), Math.abs(b)); // 获取三个差值中的最大值
        // 限制最大色差不超过难度值
        r = (r / maxDiff) * difficulty;
        g = (g / maxDiff) * difficulty;
        b = (b / maxDiff) * difficulty;
        colors[correctIndex] = new Color(r + baseColor.getRed(), g + baseColor.getGreen(), b + baseColor.getBlue());
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