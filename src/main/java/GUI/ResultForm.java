package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultForm {
    private JPanel panel1;
    private JTextArea resultTextArea;
    private String text;
    private ResultForm(String text){
        this.text = text;
        resultTextArea.setText(text);
    }

    public static void show(String text){
        JFrame frame = new JFrame("ResultForm");
        frame.setContentPane((new ResultForm(text)).panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
