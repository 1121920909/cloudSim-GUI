package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class AddVm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textVmNum;
    private JTextField textMips;
    private JTextField textBw;
    private JTextField textPeNum;
    private JTextField textRam;
    private JTextField textSize;
    private JLabel labelTitle;
    private JPanel panelTitle;
    private JPanel panelCustom;
    private JPanel panelBotton;
    private JLabel labelVmNum;
    private JLabel labelPeNum;
    private JLabel labelMips;
    private JLabel labelRam;
    private JLabel labelBw;
    private JLabel labelSize;


    public AddVm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddVm dialog = new AddVm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
