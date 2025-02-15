package com.tanvir.math_quiz;
import javax.swing.JOptionPane;

public class CSE110_Project {

    public static void main(String[] args) {
        UI_Frame frame = new UI_Frame();
        frame.launch();
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null,
                "You have to answer at least 6 " +
                "\ncorrect answer among 10 Questions to win!",
                "Quiz Rule", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
