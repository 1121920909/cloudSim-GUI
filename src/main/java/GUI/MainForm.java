package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * @author ZYP
 */
public class MainForm {
    private JPanel title;
    private JPanel centerPanel;
    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JButton exampleButton;
    private JButton simulationButton;
    private JLabel exampleLabel;
    private JLabel simulationLabel;
    private JPanel mainPanel;

    public MainForm() {
        exampleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExampleForm exampleForm = new ExampleForm();
                exampleForm.showForm();
            }
        });
        simulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CustomForm.show();
            }
        });
    }

    public  void showForm() {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
