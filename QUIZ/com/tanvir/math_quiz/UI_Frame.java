
package com.tanvir.math_quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UI_Frame extends JFrame implements Runnable{
    private final Random random;
    private final Thread th;
    private int rgt, wrn, clr;
    private int qsn, fVar, sVar, correctAns;
    private final int[] results;
    private final String[] operators = {" mod ", " + ", " - ", " x ", " / "};
    private boolean to_run;

    {
        th = new Thread(this);
        random = new Random();
        results = new int[]{-1, -1, -1, -1};
        rAns = new JTextField();
        wAns = new JTextField();
        options = new JButton[] {
            new JButton(), new JButton(),
            new JButton(), new JButton()
        };
        startAgain = new JButton();
        endGame = new JButton();
        questionPanel = new JPanel();
        optionPanel = new JPanel();
        question = new JLabel();
        math = new JLabel();
        to_run = false;
    }

    UI_Frame() {
        this.setTitle("Math Quiz");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("icons/logo.jpg");
        this.setIconImage(imageIcon.getImage());

        cnt = this.getContentPane();
        cnt.setLayout(null);
        cnt.setBackground(new Color(3, 106, 160));

        loadDetails();
    }

    public void loadDetails() {
        JLabel hLine1 = new JLabel();
        hLine1.setLocation(0, 20);
        hLine1.setSize(400, 4);
        hLine1.setOpaque(true);
        hLine1.setBackground(new Color(255, 255, 255));
        cnt.add(hLine1);

        JLabel line = new JLabel();
        line.setLocation(358, 20);
        line.setSize(4, 300);
        line.setOpaque(true);
        line.setBackground(new Color(255, 255, 255));
        cnt.add(line);

        JLabel hLine2 = new JLabel();
        hLine2.setLocation(320, 320);
        hLine2.setSize(267, 4);
        hLine2.setOpaque(true);
        hLine2.setBackground(new Color(255, 255, 255));
        cnt.add(hLine2);

        ImageIcon rightIcon = new ImageIcon("icons/right.png");
        JLabel right = new JLabel();
        right.setIcon(rightIcon);
        right.setLocation(390, 40);
        right.setSize(rightIcon.getIconWidth(), rightIcon.getIconHeight());
        cnt.add(right);

        rAns.setLocation(470, 40);
        rAns.setSize(90, 50);
        rAns.setEnabled(false);
        rAns.setDisabledTextColor(new Color(0, 199, 89));
        rAns.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        rAns.setHorizontalAlignment(SwingConstants.CENTER);
        cnt.add(rAns);

        ImageIcon wrongIcon = new ImageIcon("icons/wrong.png");
        JLabel wrong = new JLabel();
        wrong.setIcon(wrongIcon);
        wrong.setLocation(390, 120);
        wrong.setSize(wrongIcon.getIconWidth(), wrongIcon.getIconHeight());
        cnt.add(wrong);

        wAns.setLocation(470, 120);
        wAns.setSize(90, 50);
        wAns.setEnabled(false);
        wAns.setDisabledTextColor(new Color(250, 71, 77));
        wAns.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        wAns.setHorizontalAlignment(SwingConstants.CENTER);
        cnt.add(wAns);

        startAgain = new JButton();
        startAgain.setText("Start Again");
        startAgain.setLocation(390, 200);
        startAgain.setSize(170, 40);
        startAgain.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        startAgain.setForeground(new Color(0, 0, 0));
        startAgain.setBackground(new Color(0, 255, 111));
        startAgain.setBorderPainted(false);
        cnt.add(startAgain);

        endGame = new JButton();
        endGame.setText("End Game");
        endGame.setLocation(390, 260);
        endGame.setSize(170, 40);
        endGame.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        endGame.setForeground(new Color(0, 0, 0));
        endGame.setBackground(new Color(250, 71, 77));
        endGame.setBorderPainted(false);
        cnt.add(endGame);

        startAgain.addActionListener(new ActionHandler());
        endGame.addActionListener(new ActionHandler());
        th.start();
    }

    public void launch() {
        qsn = 0;
        rgt = 0;
        wrn = 0;
        clr = 0;
        to_run = true;

        for (int i  = 0; i < 4; i++) {
            results[i] = -1;
        }

        rAns.setText(String.valueOf(rgt));
        wAns.setText(String.valueOf(wrn));

        questionPanel.removeAll();
        questionPanel.revalidate();
        questionPanel.repaint();

        questionPanel.setLayout(null);
        questionPanel.setLocation(30, 40);
        questionPanel.setSize(300, 260);
        questionPanel.setBackground(new Color(3, 106, 160));
        cnt.add(questionPanel);

        question = new JLabel();
        question.setLocation(0, 5);
        question.setSize(300, 50);
        question.setOpaque(true);
        question.setBackground(new Color(246, 234, 6));
        question.setForeground(new Color(0, 0, 0));
        question.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        question.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(question);

        math = new JLabel();
        math.setLocation(0, 75);
        math.setSize(300, 50);
        math.setOpaque(true);
        math.setBackground(new Color(255, 255, 255));
        math.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        math.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(math);

        optionPanel = new JPanel();
        optionPanel.setLocation(0, 160);
        optionPanel.setSize(300, 100);
        optionPanel.setBackground(new Color(3, 106, 160));
        optionPanel.setLayout(new GridLayout(2, 2, 30, 20));
        questionPanel.add(optionPanel);

        for (int i = 0; i < 4; i++) {
            options[i] = new JButton();
            options[i].setForeground(new Color(0, 0, 0));
            options[i].setHorizontalAlignment(SwingConstants.CENTER);
            options[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
            options[i].addActionListener(new ActionHandler());
            optionPanel.add(options[i]);
        }
        loadAQuestion();
    }

    public void loadAQuestion() {
        fVar = random.nextInt(19);
        sVar = random.nextInt(19);
        int operator = random.nextInt(5);
        if ((operator == 0 || operator == 4) && sVar == 0) sVar++;
        int temp = random.nextInt(4);
        switch (operator) {
            case 0:
                correctAns = results[temp] = fVar % sVar;
                subtract();
                multiply();
                divide();
                break;
            case 1:
                correctAns = results[temp] = fVar + sVar;
                subtract();
                modulus();
                divide();
                break;
            case 2:
                correctAns = results[temp] = fVar - sVar;
                add();
                modulus();
                multiply();
                break;
            case 3:
                correctAns = results[temp] = fVar * sVar;
                add();
                subtract();
                divide();
                break;
            case 4:
                correctAns = results[temp] = fVar / sVar;
                subtract();
                add();
                modulus();
                break;
            default:
                break;
        }
        for (int i = 0; i < 4; i++) {
            options[i].setBackground(new Color(253, 153, 57));
        }
        qsn += 1;
        question.setText("Question " + (qsn));
        math.setText(String.format("%d%s%d", fVar, operators[operator], sVar) + " = ?");
        for (int i = 0; i < 4; i++) {
            options[i].setText(String.valueOf(results[i]));
            results[i] = -1;
        }
    }

    private void add() {
        while (true) {
            int temp = random.nextInt(4);
            if (results[temp] != -1) continue;
            results[temp] = fVar + sVar;
            checkVal(temp);
            break;
        }
    }

    private void subtract() {
        while (true) {
            int temp = random.nextInt(4);
            if (results[temp] != -1) continue;
            results[temp] = fVar - sVar;
            checkVal(temp);
            break;
        }
    }

    private void multiply() {
        while (true) {
            int temp = random.nextInt(4);
            if (results[temp] != -1) continue;
            results[temp] = fVar * sVar;
            checkVal(temp);
            break;
        }
    }

    private void divide() {
        while (true) {
            int temp = random.nextInt(4);
            if (results[temp] != -1) continue;
            if (sVar == 0) results[temp] = fVar;
            else results[temp] = fVar / sVar;
            checkVal(temp);
            break;
        }
    }

    private void modulus() {
        while (true) {
            int temp = random.nextInt(4);
            if (results[temp] != -1) continue;
            if (sVar == 0) results[temp] = fVar;
            else results[temp] = fVar % sVar;
            checkVal(temp);
            break;
        }
    }

    private void checkVal(int temp) {
        for (int i = 0; i < 4; i++) {
            if (i != temp && results[i] == results[temp]) {
                boolean done = true;
                results[temp] = random.nextInt(101);
                for (int j = 0; j < 4; j++) {
                    if (results[j] == results[temp]) {
                        done = false;
                        break;
                    }
                }
                if (!done) continue;
                break;
            }
        }
    }

    private final Container cnt;
    private final JTextField rAns, wAns;
    private JButton startAgain, endGame;
    private final JButton[] options;
    private final JPanel questionPanel;
    private JPanel optionPanel;
    private JLabel question, math;

    class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == endGame) {
                System.exit(0);
            } else if (e.getSource() == startAgain) {
                launch();
            } else if (e.getSource() == options[0]) {
                action(0);
                if (checkWinning())loadAQuestion();
            } else if (e.getSource() == options[1]) {
                action(1);
                if (checkWinning())loadAQuestion();
            } else if (e.getSource() == options[2]) {
                action(2);
                if (checkWinning())loadAQuestion();
            } else if (e.getSource() == options[3]) {
                action(3);
                if (checkWinning())loadAQuestion();
            } else {
                assert true;
            }
        }
        public void action(int temp) {
            if (correctAns == Integer.parseInt(options[temp].getText())) {
                options[temp].setBackground(new Color(0, 255, 111));
                rgt++;
                rAns.setText(String.valueOf(rgt));
                clr = 1;
            } else {
                options[temp].setBackground(new Color(250, 71, 77));
                wrn++;
                wAns.setText(String.valueOf(wrn));
                clr = 2;
            }
            to_run = true;
        }

        public boolean checkWinning() {
            for (int i = 0; i < 4; i++) {
                options[i].setBackground(new Color(253, 153, 57));
            }
            if (rgt == 6) {
                JOptionPane.showMessageDialog(null, "You Won the Quiz!",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                launch();
                return false;
            } else if (wrn == 6) {
                JOptionPane.showMessageDialog(null, "Better Luck Next Time!",
                        "You lose!", JOptionPane.WARNING_MESSAGE);
                launch();
                return false;
            } else if (rgt == 5 && wrn == 5) {
                JOptionPane.showMessageDialog(null, "Quiz Draw!",
                        "Draw!", JOptionPane.INFORMATION_MESSAGE);
                launch();
                return false;
            }
            return true;
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println();
            if (to_run) {
                int loadTopSizeWidth = 0, loadTopLocationX = 400;
                int loadBottomSizeWidth = 0, loadBottomLocationX = 320;
                JLabel loadTop = new JLabel();
                JLabel loadBottom = new JLabel();
                loadTop.setOpaque(true);
                loadBottom.setOpaque(true);
                if (clr == 0) {
                    loadTop.setBackground(new Color(0, 233, 255));
                    loadBottom.setBackground(new Color(0, 233, 255));
                } else if (clr == 1) {
                    loadTop.setBackground(new Color(0, 255, 111));
                    loadBottom.setBackground(new Color(0, 255, 111));
                } else if (clr == 2) {
                    loadTop.setBackground(new Color(250, 71, 77));
                    loadBottom.setBackground(new Color(250, 71, 77));
                }
                cnt.add(loadTop);
                cnt.add(loadBottom);
                while (true) {
                    loadBottom.setLocation(loadBottomLocationX, 320);
                    loadBottom.setSize(loadBottomSizeWidth, 4);
                    loadTop.setLocation(loadTopLocationX, 20);
                    loadTop.setSize(loadTopSizeWidth, 4);
                    if (loadTopSizeWidth < 187 && loadTopSizeWidth > -1) {
                        loadTopLocationX += 1;
                        loadTopSizeWidth += 2;
                    } else {
                        loadTopLocationX += 2;
                        loadTopSizeWidth -= 2;
                        if (loadTopSizeWidth < 0) {
                            cnt.remove(loadTop);
                            cnt.repaint();
                        }
                    }
                    if (loadBottomLocationX != 0) {
                        loadBottomLocationX -= 2;
                        loadBottomSizeWidth += 1;
                    } else {
                        loadBottomSizeWidth -= 2;
                        if (loadBottomSizeWidth < 2) {
                            cnt.remove(loadBottom);
                            cnt.repaint();
                            break;
                        }
                    }
                    try {
                        Thread.sleep(4);
                    } catch (Exception e) {
                        assert true;
                    }
                }
                to_run = false;
            }
        }
    }
}
